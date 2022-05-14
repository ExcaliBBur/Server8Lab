package Models;

import java.net.SocketAddress;

/**
 * Specifies containment object, CustomPair
 */
public class TransferData extends CustomPair<byte[], SocketAddress> {
    public TransferData(byte[] data, SocketAddress address) {
        super(data, address);
    }
}
