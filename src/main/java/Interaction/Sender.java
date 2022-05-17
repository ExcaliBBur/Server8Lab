package Interaction;

import Main.Server;
import Models.DTOWrapper;
import Models.ServerDTO;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Class for sending serialized data to client app.
 */
public class Sender<T> implements Runnable {
    private final DatagramChannel socketChannel;
    private final SocketAddress socketAddress;
    private ServerDTO<T> serverDTO;

    /**
     * Constructor, gets all necessary things.
     *
     * @param socketChannel channel to transfer data
     */
    public Sender(DatagramChannel socketChannel, ServerDTO<T> serverDTO, SocketAddress socketAddress) {
        this.socketChannel = socketChannel;
        this.serverDTO = serverDTO;
        this.socketAddress = socketAddress;
    }

    public void run() {
        try {
            synchronized (socketAddress) {
                byte[] data = Parser.parseTo(serverDTO);
                final int INCREMENT = 1024;
                DTOWrapper dtoWrapper;

                for (int position = 0, limit = INCREMENT, capacity = 0; Objects.requireNonNull(data).length >
                        capacity; position = limit, limit += INCREMENT) {
                    byte[] window = Arrays.copyOfRange(data, position, limit);
                    capacity += limit - position;

                    if (capacity >= data.length) {
                        dtoWrapper = new DTOWrapper(window, true);
                        Server.logger.log(Level.INFO, "The response has been sent to the client.");
                    } else {
                        dtoWrapper = new DTOWrapper(window, false);
                    }

                    ByteBuffer byteBuffer = ByteBuffer.wrap(Objects.requireNonNull(Parser.parseTo(dtoWrapper)));

                    synchronized (socketChannel) {
                        this.getSocketChannel().send(byteBuffer, this.getSocketAddress());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramChannel getSocketChannel() {
        return socketChannel;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
