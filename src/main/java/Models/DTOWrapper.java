package Models;

import java.io.Serializable;

public class DTOWrapper implements Serializable {
    private final byte[] data;
    private final boolean isTerminating;

    public DTOWrapper(byte[] data, boolean isTerminating) {
        this.data = data;
        this.isTerminating = isTerminating;
    }

    public boolean isTerminating() {
        return isTerminating;
    }

    public byte[] getData() {
        return data;
    }
}
