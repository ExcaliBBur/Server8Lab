package Data;

import java.io.Serializable;

/**
 * A class that represents a data transfer object for a client app.
 */
public class ClientDTO implements Serializable {
    private Command.CommandData commandData;
    private boolean isRequest;
    private User user;

    /**
     * Constructor, gets all necessary things.
     *
     * @param commandData command data and arguments
     * @param isRequest   shows that client app needs full command data
     * @param user        user data
     */
    public ClientDTO(Command.CommandData commandData, boolean isRequest, User user) {
        this.commandData = commandData;
        this.isRequest = isRequest;
        this.user = user;
    }

    public ClientDTO(Command.CommandData commandData, User user) {
        this.commandData = commandData;
        this.user = user;
    }

    public ClientDTO(boolean isRequest) {
        this.isRequest = isRequest;
    }

    public Command.CommandData getCommandData() {
        return commandData;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public User getUser() {
        return user;
    }
}
