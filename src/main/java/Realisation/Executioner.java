package Realisation;

import Models.*;
import Interaction.Sender;
import Main.Server;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.logging.Level;

/**
 * Class for command execution.
 */
public class Executioner implements Runnable {
    private final List<Command> unitedCommandArray = new ArrayList<>();
    private ClientDTO clientDTO;
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private CityController cityController;

    /**
     * Constructor, gets all necessary things.
     *
     * @param clientDTO       client data transfer object
     * @param datagramChannel datagram channel
     * @param socketAddress   socket address
     * @param workers         command realizations
     */
    public Executioner(ClientDTO clientDTO, DatagramChannel datagramChannel, SocketAddress socketAddress,
                       CityController cityController, Worker... workers) {
        this.clientDTO = clientDTO;
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
        this.cityController = cityController;

        for (Worker worker : workers) {
            this.getUnitedCommandArray().addAll(worker.getCommands());
        }
    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param datagramChannel datagram channel
     * @param workers         command realizations
     */
    public Executioner(DatagramChannel datagramChannel, CityController cityController, Worker... workers) {
        this.datagramChannel = datagramChannel;
        this.cityController = cityController;

        for (Worker worker : workers) {
            this.getUnitedCommandArray().addAll(worker.getCommands());
        }
    }

    public void run() {
        Sender<City> sender = new Sender<>(this.getDatagramChannel(), null, null);
        ServerDTO<City> serverCityDTO = null;
        if (this.getClientDTO().isRequest()) {
            Server.logger.log(Level.INFO, "New connection has been registered.");
            ArrayList<Command.CommandData> commandData = new ArrayList<>();
            this.getUnitedCommandArray().forEach(x -> commandData.add(new Command
                    .CommandData(x.getName(), x.getArguments(), x.getDescription(), null)));

            serverCityDTO = new ServerDTO<>("Ready to execute, type help to see all available commands.".getBytes(),
                    commandData, this.getCityController().getCollectionBase().getSet(), true,
                    ServerDTO.DTOType.RESPONSE);
        } else {
            String keyWord = this.getClientDTO().getCommandData().getName();
            Server.logger.log(Level.INFO, "Execute the command " + keyWord + ".");
            for (Command commands : this.getUnitedCommandArray()) {
                if (commands.getName().equals(keyWord)) {
                    CustomPair<String, Boolean> result = commands.doOption(this.getClientDTO().getCommandData()
                            .getUserArgs(), this.getClientDTO().getUser());
                    serverCityDTO = new ServerDTO<>(result.getKey().getBytes(), result.getValue(),
                            ServerDTO.DTOType.RESPONSE);
                    System.out.println();
                }
            }
        }

        sender.setServerDTO(serverCityDTO);
        sender.setSocketAddress(this.getSocketAddress());
        Server.connectedUsers.add(this.getSocketAddress());
        new Thread(sender).start();
    }

    public List<Command> getUnitedCommandArray() {
        return unitedCommandArray;
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public CityController getCityController() {
        return cityController;
    }
}
