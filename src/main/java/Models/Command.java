package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents all specified classes with unique purposes.
 */
public abstract class Command {
    private final String name;
    private final List<Argument> arguments;
    private final String description;

    /**
     * Constructor, gets all necessary thins.
     *
     * @param name        command name
     * @param description command description
     */
    public Command(String name, List<Argument> arguments, String description) {
        this.name = name;
        this.arguments = arguments;
        this.description = description;
    }

    /**
     * Getter of name.
     *
     * @return command name
     */
    public String getName() {
        return name;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    /**
     * Getter of description.
     *
     * @return command name
     */
    public String getDescription() {
        return description;
    }

    /**
     * Divides command arguments
     *
     * @return divided arguments
     */
    public List<String> parseArguments(String arguments) {
        return new ArrayList<>(Arrays.asList(arguments.split("\n")));
    }

    /**
     * Does some stuff.
     *
     * @param arguments input parameters
     * @return message
     */
    public abstract CustomPair<String, Boolean> doOption(List<String> arguments, User user);

    @Override
    public String toString() {
        return this.getName() + ": " + this.getDescription() + "\n";
    }

    /**
     * Represents all kinds of arguments.
     */
    public enum Argument {
        NUMBER("number"),
        STRING("string"),
        NONE("none"),
        OBJECT("object"),
        USER("user");

        private final String name;

        Argument(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Represents additional data for transfer.
     */
    public static class CommandData implements Serializable {
        private final String name;
        private List<Argument> args;
        private String description;
        private List<String> userArgs;

        public CommandData(String name, List<Argument> args, String description, List<String> userArgs) {
            this.name = name;
            this.args = args;
            this.description = description;
            this.userArgs = userArgs;
        }

        public CommandData(String name, List<Argument> args, String description) {
            this.name = name;
            this.args = args;
            this.description = description;
        }

        public CommandData(String name, List<String> userArgs) {
            this.name = name;
            this.userArgs = userArgs;
        }

        public String getName() {
            return name;
        }

        public List<Argument> getArgs() {
            return args;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getUserArgs() {
            return userArgs;
        }

        @Override
        public String toString() {
            return this.getName() + ": " + this.getDescription();
        }
    }
}
