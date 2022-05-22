package Realisation;

import Main.Server;
import Models.*;
import Interfaces.Formable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

/**
 * Responsible for working with the collection of "Data.City" elements.
 */
public class CityWorker<T extends City> extends CollectionWorker<T> {
    private final UserController userController;

    public CityWorker(CollectionController<T> collectionController, Formable<T> formable,
                      UserController userController) {
        super(collectionController, formable);
        this.userController = userController;
    }

    /**
     * Serializes City object in json format.
     *
     * @param city object to serialize
     * @return String format of the serialized object
     */
    public String serializeCity(City city) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(city);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Verifies user data.
     *
     * @param user user to check
     * @return result of check
     */
    public boolean verifyUser(User user) {
        try {
            return this.getUserController().getUser(user.getName()).getPassword().equals(user.getPassword());
        } catch (SQLException e) {
            return false;
        }
    }

    public UserController getUserController() {
        return userController;
    }

    @Override
    public List<Command> initialize() {
        List<Command> arrayList = new ArrayList<>();

        new Reflections("Realisation.CityWorker").getSubTypesOf(Command.class).forEach(x -> {
            try {
                arrayList.add(x.getConstructor(CityWorker.class).newInstance(this));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                    InstantiationException e) {
                e.printStackTrace();
            }
        });

        return arrayList;
    }

    /**
     * Add command realization with additional data.
     */
    public class Add extends Command {
        public Add() {
            super("add", new ArrayList<>(Arrays.asList(Argument.OBJECT)), "Add a new" +
                    " element to the collection.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            try {
                if (verifyUser(user)) {
                    T contest = CityWorker.this.getIFormer().formObj(arguments.get(0));
                    contest.setId(CityWorker.this.getController().getSequenceID());
                    boolean success = CityWorker.this.getController().addContent(contest, user.getName());

                    if (success) {
                        CityWorker.this.getController().moveContent();
                        return new CustomPair<>("object_added", true);
                    }
                    return new CustomPair<>("went_wrong", false);
                } else {
                    return new CustomPair<>("no_rights", false);
                }
            } catch (NullPointerException e) {
                Server.logger.log(Level.INFO, "went_wrong");
                return null;
            }
        }
    }

    /**
     * Update command realization with additional data.
     */
    public class Update extends Command {
        public Update() {
            super("update", new ArrayList<>(Arrays.asList(Argument.NUMBER, Argument.OBJECT)),
                    "Update the value of a collection element whose id is equal to the specified one.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                int id = Integer.parseInt(arguments.get(0));
                T contest = CityWorker.this.getIFormer().formObj(arguments.get(1));
                contest.setId(id);

                boolean success = CityWorker.this.getController().updateContent(contest, user.getName());

                if (success) {
                    CityWorker.this.getController().moveContent();
                    return new CustomPair<>("object_updated", true);
                }
                return new CustomPair<>("impossible_to_update", false);
            } else {
                return new CustomPair<>("no_rights", false);
            }
        }
    }

    /**
     * RemoveById command realization with additional data.
     */
    public class RemoveById extends Command {
        public RemoveById() {
            super("remove_by_id", new ArrayList<>(Arrays.asList(Argument.NUMBER)),
                    "Delete an item from the collection by its id.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                boolean success = CityWorker.this.getController().removeContent(arguments.get(0), user.getName());

                if (success) {
                    CityWorker.this.getController().moveContent();
                    return new CustomPair<>("deletion_completed", true);
                }
                return new CustomPair<>("impossible_to_delete", false);
            } else {
                return new CustomPair<>("no_rights", false);
            }
        }
    }

    /**
     * Clear command realization with additional data.
     */
    public class Clear extends Command {
        public Clear() {
            super("clear", new ArrayList<>(Arrays.asList()), "Clear collection.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                boolean success = CityWorker.this.getController().clearContent(user.getName());

                if (success) {
                    CityWorker.this.getController().moveContent();
                    return new CustomPair<>("deletion_completed", true);
                } else {
                    return new CustomPair<>("impossible_to_delete", false);
                }
            } else {
                return new CustomPair<>("no_rights", false);
            }
        }
    }

    /**
     * RemoveGreater command realization with additional data.
     */
    public class RemoveGreater extends Command {
        public RemoveGreater() {
            super("remove_greater", new ArrayList<>(Arrays.asList(Argument.OBJECT)),
                    "Remove all items from the collection that exceed the specified one.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                T contest = CityWorker.this.getIFormer().formObj(arguments.get(0));
                long toCompare = contest.getArea() + contest.getMeters() + contest.getPopulation();
                int summary;

                String addition = "FULL JOIN coordinates ON city.city_id = coordinates.coordinates_id" +
                        " FULL JOIN human ON city.city_id = human.human_id WHERE (area + population" +
                        " + meters_above_sea_level) > " + toCompare;

                summary = (int) CityWorker.this.getController().getContent(addition).stream().filter(city ->
                        CityWorker.this.getController().removeContent(Integer.toString(city.getId()),
                                user.getName())).count();

                if (summary != 0) {
                    CityWorker.this.getController().moveContent();
                    return new CustomPair<>("deletion_completed", true);
                } else {
                    return new CustomPair<>("impossible_to_delete", false);
                }
            } else {
                return new CustomPair<>("no_rights", false);
            }
        }
    }

    /**
     * RemoveLower command realization with additional data.
     */
    public class RemoveLower extends Command {
        public RemoveLower() {
            super("remove_lower", new ArrayList<>(Arrays.asList(Argument.OBJECT)),
                    "Remove from the collection all items smaller than the specified.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                T contest = CityWorker.this.getIFormer().formObj(arguments.get(0));
                long toCompare = contest.getArea() + contest.getMeters() + contest.getPopulation();
                int summary;

                String addition = "FULL JOIN coordinates ON city.city_id = coordinates.coordinates_id" +
                        " FULL JOIN human ON city.city_id = human.human_id WHERE (area + population" +
                        " + meters_above_sea_level) < " + toCompare;

                summary = (int) CityWorker.this.getController().getContent(addition).stream().filter(city ->
                        CityWorker.this.getController().removeContent(Integer.toString(city.getId()),
                                user.getName())).count();

                if (summary != 0) {
                    CityWorker.this.getController().moveContent();
                    return new CustomPair<>("deletion_completed", true);
                } else {
                    return new CustomPair<>("impossible_to_delete", false);
                }
            } else {
                return new CustomPair<>("no_rights", false);
            }
        }
    }

    /**
     * AddIfMin command realization with additional data.
     */
    public class AddIfMin extends Command {
        public AddIfMin() {
            super("add_if_min", new ArrayList<>(Arrays.asList(Argument.OBJECT)), "Add a new" +
                    " element to the collection if its value is less than that of the smallest element.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                T contest = CityWorker.this.getIFormer().formObj(arguments.get(0));
                long toCompare = contest.getArea() + contest.getMeters() + contest.getPopulation();

                String addition = "FULL JOIN coordinates ON city.city_id = coordinates.coordinates_id" +
                        " FULL JOIN human ON city.city_id = human.human_id WHERE (area + population" +
                        " + meters_above_sea_level) < " + toCompare;

                if (CityWorker.this.getController().getContent(addition).size() == 0) {
                    contest.setId(CityWorker.this.getController().getSequenceID());
                    CityWorker.this.getController().addContent(contest, user.getName());
                    CityWorker.this.getController().moveContent();
                    return new CustomPair<>("object_added", true);
                }
                return new CustomPair<>("Tnot_minimal", false);
            } else {
                return new CustomPair<>("no_rights", false);
            }
        }
    }
}