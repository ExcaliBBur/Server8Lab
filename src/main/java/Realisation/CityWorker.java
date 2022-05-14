package Realisation;

import Models.*;
import Interfaces.Formable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * Responsible for working with the collection of "Data.City" elements.
 */
public class CityWorker<T extends City> extends CollectionWorker<T> {
    private final UserController userController;

    public CityWorker(CollectionController<T> collectionController, List<Command> commands, Formable<T> formable,
                      UserController userController) {
        super(collectionController, commands, formable);
        this.initCommands();
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
    public void initCommands() {
        ArrayList<Command> arrayList = new ArrayList<>();

        new Reflections("Realisation.CityWorker").getSubTypesOf(Command.class).forEach(x -> {
            try {
                arrayList.add(x.getConstructor(CityWorker.class).newInstance(this));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                    InstantiationException e) {
                e.printStackTrace();
            }
        });

        this.setCommands(arrayList);
    }

    /**
     * Info command realization with additional data.
     */
    public class Info extends Command {
        public Info() {
            super("info", new ArrayList<>(), "Output information about the collection to" +
                    " the standard output stream.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                return new CustomPair<>(CityWorker.this.getController().getCollectionBase().getSet().getClass()
                        .toString().replace("class", "Collection type:") + "\nNumber of elements in" +
                        " the collection: " + CityWorker.this.getController().getCollectionBase().getSet().size()
                        + "\nCollection initialization time: " + CityWorker.this.getController().getCollectionBase()
                        .getInitializationDate() + "\n", true);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
            }
        }
    }

    /**
     * Show command realization with additional data.
     */
    public class Show extends Command {
        public Show() {
            super("show", new ArrayList<>(), "Output to the standard output stream all the elements" +
                    " of the collection in the string representation.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                StringBuilder stringBuilder = new StringBuilder();
                CityWorker.this.getController().getCollectionBase().getSet().forEach(x -> stringBuilder
                        .append(x.toString()).append("\n"));
                stringBuilder.append("End of elements output.\n");
                return new CustomPair<>(stringBuilder.toString(), true);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
            }
        }
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
            if (verifyUser(user)) {
                T contest = CityWorker.this.getIFormer().formObj(arguments.get(0));
                contest.setId(CityWorker.this.getController().getSequenceID());
                boolean success = CityWorker.this.getController().addContent(contest, user.getName());

                if (success) {
                    CityWorker.this.getController().moveContent();
                    return new CustomPair<>("The object has been added to the collection.\n", true);
                }
                return new CustomPair<>("Something went wrong.\n", false);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
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
                    return new CustomPair<>("The object with the entered ID has been updated.\n", true);
                }
                return new CustomPair<>("Impossible to update object with such id.\n", false);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
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
                    return new CustomPair<>("Deletion completed successfully.\n", true);
                }
                return new CustomPair<>("Impossible to delete this object.\n", false);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
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
                    return new CustomPair<>("The collection has been successfully cleared.\n", true);
                } else {
                    return new CustomPair<>("It is not possible to delete items from the collection.\n", false);
                }
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
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
                    return new CustomPair<>("Deletion completed successfully.\n", true);
                } else {
                    return new CustomPair<>("Impossible to delete objects.\n", false);
                }
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
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
                    return new CustomPair<>("Deletion completed successfully.\n", true);
                } else {
                    return new CustomPair<>("Impossible to delete objects.\n", false);
                }
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
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
                    return new CustomPair<>("The object has been added to the collection.\n", true);
                }
                return new CustomPair<>("The object is not minimal in the collection.\n", false);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
            }
        }
    }

    /**
     * MinByArea command realization with additional data.
     */
    public class MinByArea extends Command {
        public MinByArea() {
            super("min_by_area", new ArrayList<>(), "Output any object from the collection whose" +
                    " value of the area field is minimal.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                T contest = CityWorker.this.getController().getCollectionBase().getSet().stream()
                        .min(Comparator.comparingInt(T::getArea)).orElse(null);

                if (contest != null) {
                    return new CustomPair<>(contest.toString(), true);
                }
                return new CustomPair<>("There are no cities in the collection.\n", false);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
            }
        }
    }

    /**
     * FilterByGovernor command realization with additional data.
     */
    public class FilterByGovernor extends Command {
        public FilterByGovernor() {
            super("filter_by_governor", new ArrayList<>(Arrays.asList(Argument.STRING)),
                    "Output elements whose value of the governor field is equal to the specified one.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                StringBuilder stringBuilder = new StringBuilder();

                CityWorker.this.getController().getCollectionBase().getSet().stream().filter(x ->
                        x.getGovernor().getHumanName().equals(arguments.get(0))).forEach(x -> stringBuilder
                        .append(x).append("\n"));
                stringBuilder.append("End of elements output.\n");
                return new CustomPair<>(stringBuilder.toString(), true);
            } else {
                return new CustomPair<>("You have no rights to use this command.\n", false);
            }
        }
    }

    /**
     * FilterStartsWithName command realization with additional data.
     */
    public class FilterStartsWithName extends Command {
        public FilterStartsWithName() {
            super("filter_starts_with_name", new ArrayList<>(Arrays.asList(Argument.STRING)),
                    "print elements, the value of the name field, which begins with the" +
                            " specified substring.");
        }

        @Override
        public CustomPair<String, Boolean> doOption(List<String> arguments, User user) {
            if (verifyUser(user)) {
                StringBuilder stringBuilder = new StringBuilder();

                CityWorker.this.getController().getCollectionBase().getSet().stream().filter(x -> x.getName()
                        .startsWith(arguments.get(0))).forEach(x -> stringBuilder.append(x).append("\n"));
                stringBuilder.append("End of elements output.\n");
                return new CustomPair<>(stringBuilder.toString(), true);
            } else {
                return new CustomPair<>("You have no rights to use this command.", false);
            }
        }
    }
}