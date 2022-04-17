package Processing;

import Data.*;
import Interaction.Parser;
import Interaction.Sender;
import Main.Server;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for command execution.
 */
public class Executioner implements Runnable {
    private final List<Command> unitedCommandArray = new ArrayList<>();
    private ClientDTO clientDTO;
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;

    /**
     * Constructor, gets all necessary things.
     *
     * @param clientDTO       client data transfer object
     * @param datagramChannel datagram channel
     * @param socketAddress   socket address
     * @param workers         command realizations
     */
    public Executioner(ClientDTO clientDTO, DatagramChannel datagramChannel, SocketAddress socketAddress,
                       Worker... workers) {
        this.clientDTO = clientDTO;
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;

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
    public Executioner(DatagramChannel datagramChannel, Worker... workers) {
        this.datagramChannel = datagramChannel;
        for (Worker worker : workers) {
            this.getUnitedCommandArray().addAll(worker.getCommands());
        }
    }

    public void run() {
        Sender sender = new Sender(this.getDatagramChannel(), null);
        if (this.getClientDTO().isRequest()) {
            Server.logger.log(Level.INFO, "New connection has been registered.");
            ArrayList<Command.CommandData> commandData = new ArrayList<>();
            this.getUnitedCommandArray().forEach(x -> commandData.add(new Command
                    .CommandData(x.getName(), x.getArguments(), x.getDescription(), null)));

            sender.setTransferData(new TransferData(Parser.parseTo(
                    new ServerDTO(("Ready to execute, type help to see all available commands.").getBytes(),
                            commandData, true, true)), this.getSocketAddress()));
            new Thread(sender).start();
        } else {
            String keyWord = this.getClientDTO().getCommandData().getName();
            Server.logger.log(Level.INFO, "Execute the command " + keyWord + ".");
            for (Command commands : this.getUnitedCommandArray()) {
                if (commands.getName().equals(keyWord)) {
                    CustomPair<String, Boolean> result = commands.doOption(this.getClientDTO().getCommandData()
                            .getUserArgs(), this.getClientDTO().getUser());

                    byte[] data = result.getKey().getBytes();
                    final int INCREMENT = 1024;
                    ServerDTO serverDTO;

                    for (int position = 0, limit = INCREMENT, capacity = 0; data.length > capacity; position = limit,
                            limit += INCREMENT) {
                        byte[] window = Arrays.copyOfRange(data, position, limit);
                        capacity += limit - position;

                        if (capacity >= data.length) {
                            serverDTO = new ServerDTO(window, result.getValue(), true);
                            Server.logger.log(Level.INFO, "The response has been sent to the client.");
                        } else {
                            serverDTO = new ServerDTO(window, result.getValue(), false);
                        }
                        sender.setTransferData(new TransferData(Parser.parseTo(serverDTO),
                                this.getSocketAddress()));
                        new Thread(sender).start();

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
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
}
