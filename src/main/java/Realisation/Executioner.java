package Realisation;

import Interfaces.Initializable;
import Models.*;
import Interaction.Sender;
import Main.Server;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
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
    private final Set<SocketAddress> connectedUsers;

    /**
     * Constructor, gets all necessary things.
     *
     * @param clientDTO       client data transfer object
     * @param datagramChannel datagram channel
     * @param socketAddress   socket address
     * @param workers         command realizations
     */
    public Executioner(ClientDTO clientDTO, DatagramChannel datagramChannel, SocketAddress socketAddress,
                       CityController cityController, Set<SocketAddress> connectedUsers, Initializable... workers) {
        this.clientDTO = clientDTO;
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
        this.cityController = cityController;
        this.connectedUsers = connectedUsers;

        for (Initializable worker : workers) {
            this.getUnitedCommandArray().addAll(worker.initialize());
        }
    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param datagramChannel datagram channel
     * @param workers         command realizations
     */
    public Executioner(DatagramChannel datagramChannel, CityController cityController, Set<SocketAddress>
            connectedUsers, Initializable... workers) {
        this.datagramChannel = datagramChannel;
        this.cityController = cityController;
        this.connectedUsers = connectedUsers;

        for (Initializable worker : workers) {
            this.getUnitedCommandArray().addAll(worker.initialize());
        }
    }

    public void run() {
        ServerDTO<City> serverDTO;
        CustomPair<String, Boolean> result = null;

        ArrayList<Command.CommandData> commandData = new ArrayList<>();
        this.getUnitedCommandArray().forEach(x -> commandData.add(new Command
                .CommandData(x.getName(), x.getArguments(), x.getDescription(), null)));

        String keyWord = this.getClientDTO().getCommandData().getName();
        Server.logger.log(Level.INFO, "Execute the command " + keyWord + ".");
        for (Command commands : this.getUnitedCommandArray()) {
            if (commands.getName().equals(keyWord)) {
                result = commands.doOption(this.getClientDTO().getCommandData().getUserArgs(),
                        this.getClientDTO().getUser());
            }
        }

        if (this.getClientDTO().isRequest()) {
            serverDTO = new ServerDTO<>(clientDTO.getLanguage().getResources().getString(Objects.requireNonNull(result)
                    .getKey()).getBytes(StandardCharsets.UTF_8), commandData, this.getCityController()
                    .getCollectionBase().getSet(), result.getValue(), ServerDTO.DTOType.RESPONSE);

            synchronized (connectedUsers) {
                this.getConnectedUsers().add(this.getSocketAddress());
            }

            new Thread(new Sender<>(this.getDatagramChannel(), serverDTO, connectedUsers.stream().filter(x ->
                    x.equals(this.getSocketAddress())).findFirst().get())).start();
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

    public CityController getCityController() {
        return cityController;
    }

    public Set<SocketAddress> getConnectedUsers() {
        return connectedUsers;
    }
}
