package Interaction;

import Data.TransferData;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Class for sending serialized data to client app.
 */
public class Sender implements Runnable {
    private final DatagramChannel socketChannel;
    private TransferData transferData;

    /**
     * Constructor, gets all necessary things.
     *
     * @param socketChannel channel to transfer data
     * @param transferData  data to transfer
     */
    public Sender(DatagramChannel socketChannel, TransferData transferData) {
        this.socketChannel = socketChannel;
        this.transferData = transferData;
    }

    public void run() {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.getTransferData().getKey());

            synchronized (socketChannel) {
                this.getSocketChannel().send(byteBuffer, this.getTransferData().getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramChannel getSocketChannel() {
        return socketChannel;
    }

    public TransferData getTransferData() {
        return transferData;
    }

    public void setTransferData(TransferData transferData) {
        this.transferData = transferData;
    }
}
