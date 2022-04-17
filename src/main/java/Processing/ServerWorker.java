package Processing;

import Data.Command;
import Data.CustomPair;
import Data.User;
import Data.Worker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides information for server-wide commands.
 */
public class ServerWorker extends Worker {
    private UserController userController;

    /**
     * Constructor, gets all necessary things.
     *
     * @param arrayList      list to store command realizations
     * @param userController way to get user information
     */
    public ServerWorker(ArrayList<Command> arrayList, UserController userController) {
        super(arrayList);
        this.userController = userController;

        this.initCommands();
    }

    @Override
    public void initCommands() {
        ArrayList<Command> arrayList = new ArrayList<>();

        new Reflections("Processing.ServerWorker").getSubTypesOf(Command.class).forEach(x -> {
            try {
                arrayList.add(x.getConstructor(ServerWorker.class).newInstance(this));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                    InstantiationException e) {
                e.printStackTrace();
            }
        });

        this.setCommands(arrayList);
    }

    public UserController getUserController() {
        return userController;
    }

    /**
     * Log in into existing user account.
     */
    public class LogIn extends Command {
        public LogIn() {
            super("login", new ArrayList<>(Arrays.asList(Argument.USER)), "Allows to log in" +
                    " into existing account.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(ArrayList<String> arguments, User user) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                User object = objectMapper.readValue(arguments.get(0), User.class);

                try {
                    if (object.equals(ServerWorker.this.getUserController().getUser(object.getName()))) {
                        return new CustomPair<>("Success!", true);
                    } else {
                        return new CustomPair<>("Wrong password", false);
                    }
                } catch (SQLException e) {
                    return new CustomPair<>("There is no such account", false);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Register new user account.
     */
    public class Register extends Command {
        public Register() {
            super("register", new ArrayList<>(Arrays.asList(Argument.USER)), "Allows to register " +
                    "new account and log in.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(ArrayList<String> arguments, User user) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                ServerWorker.this.getUserController().insertUser(objectMapper.readValue(arguments.get(0), User.class));

                return new CustomPair<>("New account has been successfully registered.", true);
            } catch (SQLException e) {
                return new CustomPair<>("Account with such name had been already registered.", false);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
