package Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that represents a data transfer object for a server app.
 */
public class ServerDTO implements Serializable {
    private final byte[] message;
    private ArrayList<Command.CommandData> commandData;
    private boolean isSuccess;
    private boolean isTerminating;

    /**
     * Constructor, gets all necessary things.
     *
     * @param message       response in byte format
     * @param commandData   list of all available command data
     * @param isSuccess     marks completed operations
     * @param isTerminating marks the last response in chain
     */
    public ServerDTO(byte[] message, ArrayList<Command.CommandData> commandData, boolean isSuccess,
                     boolean isTerminating) {
        this.message = message;
        this.commandData = commandData;
        this.isSuccess = isSuccess;
        this.isTerminating = isTerminating;
    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param message       response in byte format
     * @param isSuccess     marks completed operations
     * @param isTerminating marks the last response in chain
     */
    public ServerDTO(byte[] message, boolean isSuccess, boolean isTerminating) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.isTerminating = isTerminating;
    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param message response in byte format
     */
    public ServerDTO(byte[] message, ArrayList<Command.CommandData> commandData) {
        this.message = message;
        this.commandData = commandData;
    }

    public byte[] getMessage() {
        return message;
    }

    public ArrayList<Command.CommandData> getCommandData() {
        return commandData;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isTerminating() {
        return isTerminating;
    }
}
