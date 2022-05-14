package Models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * A class that represents a data transfer object for a server app.
 */
public class ServerDTO<T> implements Serializable {
    private byte[] message;
    private List<Command.CommandData> commandData;
    private Set<T> collection;
    private boolean isSuccess;
    private final DTOType dtoType;

    /**
     * Constructor, gets all necessary things.
     *
     * @param message     response in byte format
     * @param commandData list of all available command data
     * @param isSuccess   marks completed operations
     */
    public ServerDTO(byte[] message, List<Command.CommandData> commandData, Set<T> collection, boolean isSuccess,
                     DTOType dtoType) {
        this.message = message;
        this.commandData = commandData;
        this.collection = collection;
        this.isSuccess = isSuccess;
        this.dtoType = dtoType;
    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param message   response in byte format
     * @param isSuccess marks completed operations
     */
    public ServerDTO(byte[] message, boolean isSuccess, DTOType dtoType) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.dtoType = dtoType;
    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param message response in byte format
     */
    public ServerDTO(byte[] message, List<Command.CommandData> commandData, DTOType dtoType) {
        this.message = message;
        this.commandData = commandData;
        this.dtoType = dtoType;
    }

    public ServerDTO(Set<T> collection, DTOType dtoType) {
        this.collection = collection;
        this.dtoType = dtoType;
    }

    public byte[] getMessage() {
        return message;
    }

    public List<Command.CommandData> getCommandData() {
        return commandData;
    }

    public Set<T> getCollection() {
        return collection;
    }

    public DTOType getDtoType() {
        return dtoType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public enum DTOType {
        RESPONSE("RESPONSE"),
        UPDATE("UPDATE");

        private final String name;

        DTOType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
