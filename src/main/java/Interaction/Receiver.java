package Interaction;

import Models.CustomPair;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Class for receiving data from client app.
 */
public class Receiver {
    private final DatagramChannel socketChannel;

    /**
     * Constructor, gets all necessary things.
     *
     * @param socketChannel channel to get data from
     */
    public Receiver(DatagramChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    /**
     * Gets address and byte data from channel.
     *
     * @return address and byte data
     * @throws IOException caused by working with channel
     */
    public CustomPair<byte[], SocketAddress> getResponse() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        SocketAddress socketAddress = this.getSocketChannel().receive(byteBuffer);
        byteBuffer.flip();

        return new CustomPair<>(byteBuffer.array(), socketAddress);
    }

    public DatagramChannel getSocketChannel() {
        return socketChannel;
    }
}
