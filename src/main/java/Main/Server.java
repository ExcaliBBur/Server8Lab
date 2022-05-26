package Main;

import Models.*;
import Interaction.Parser;
import Interaction.Receiver;
import Realisation.*;

import java.io.*;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Server {
    public static Logger logger;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("LoggerConfig.txt");
            LogManager.getLogManager().readConfiguration(fileInputStream);
            logger = Logger.getLogger(Server.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (DatagramChannel datagramChannel = DatagramChannel.open(); Selector selector = Selector.open();
             Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Cities",
                     "postgres", new Scanner(System.in).nextLine())) {

            Set<SocketAddress> connectedUsers = new HashSet<>();
            Informer<City> informer = new Informer<>(datagramChannel, connectedUsers);

            CityBase cityBase = new CityBase(City.class, Collections.synchronizedSortedSet(new TreeSet<>()), informer);
            CityController cityController = new CityController(connection, cityBase);

            datagramChannel.configureBlocking(false);
            SocketAddress socketAddress = new InetSocketAddress(6666);
            datagramChannel.bind(socketAddress);

            datagramChannel.register(selector, SelectionKey.OP_READ);

            logger.log(Level.INFO, "The server has started working successfully.");

            UserController userController = new UserController(connection);
            ServerWorker serverWorker = new ServerWorker(userController);
            CityWorker<City> cityWorker = new CityWorker<>(cityController, new CityFormer(),
                    userController);
            cityWorker.getController().moveContent();

            Executioner executioner = new Executioner(datagramChannel, cityController, connectedUsers, serverWorker,
                    cityWorker);

            Thread thread = new Thread(() -> {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                    String line;
                    logger.log(Level.INFO, "The server is ready to execute local commands.");

                    while (true) {
                        if (bufferedReader.ready()) {
                            line = bufferedReader.readLine();

                            if ("exit".equals(line)) {
                                logger.log(Level.INFO, "The server has finished working.");
                                System.exit(0);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

            CustomPair<byte[], SocketAddress> pair;
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isReadable()) {
                        pair = new Receiver((DatagramChannel) selectionKey.channel()).getResponse();
                        logger.log(Level.INFO, "Request was received from the user.");
                        ClientDTO clientDTO = (ClientDTO) Parser.parseFrom(pair.getKey());
                        if (clientDTO != null) {
                            executioner.setClientDTO(clientDTO);
                            executioner.setSocketAddress(pair.getValue());
                            new Thread(executioner).start();
                        }
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
