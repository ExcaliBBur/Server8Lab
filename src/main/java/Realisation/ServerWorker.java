package Realisation;

import Interfaces.Initializable;
import Models.Command;
import Models.CustomPair;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides information for server-wide commands.
 */
public class ServerWorker implements Initializable {
    private UserController userController;

    /**
     * Constructor, gets all necessary things.
     *
     * @param userController way to get user information
     */
    public ServerWorker(UserController userController) {
        this.userController = userController;
    }

    @Override
    public List<Command> initialize() {
        List<Command> arrayList = new ArrayList<>();

        new Reflections("Realisation.ServerWorker").getSubTypesOf(Command.class).forEach(x -> {
            try {
                arrayList.add(x.getConstructor(ServerWorker.class).newInstance(this));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                    InstantiationException e) {
                e.printStackTrace();
            }
        });

        return arrayList;
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
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                User object = objectMapper.readValue(arguments.get(0), User.class);

                try {
                    if (object.equals(ServerWorker.this.getUserController().getUser(object.getName()))) {
                        return new CustomPair<>("success", true);
                    } else {
                        return new CustomPair<>("wrong_password", false);
                    }
                } catch (SQLException e) {
                    return new CustomPair<>("no_account", false);
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
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                ServerWorker.this.getUserController().insertUser(objectMapper.readValue(arguments.get(0), User.class));

                return new CustomPair<>("new_account", true);
            } catch (SQLException e) {
                return new CustomPair<>("already_exists", false);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
