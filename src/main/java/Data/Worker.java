package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents class for working with commands.
 */
public abstract class Worker {
    private List<Command> commands;

    /**
     * Constructor, gets all necessary things.
     *
     * @param commands list of commands to work with
     */
    public Worker(List<Command> commands) {
        this.commands = commands;
    }

    /**
     * Initializing realization of commands.
     */
    public abstract void initCommands();

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
