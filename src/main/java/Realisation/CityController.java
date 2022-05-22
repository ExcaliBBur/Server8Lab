package Realisation;

import Models.City;
import Models.CollectionController;

import java.sql.*;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * represents a class for working with a database that contains elements of the city class.
 */
public class CityController extends CollectionController<City> {
    public CityController(Connection connection, CityBase cityBase) {
        super(connection, cityBase);
    }

    public CityController(Connection connection) {
        super(connection);
    }

    @Override
    public synchronized int getSequenceID() {
        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nextval('city_id_seq') ");
            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SortedSet<City> getContent(String addition) {
        try {
            SortedSet<City> sortedSet = new TreeSet<>();
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("SELECT * FROM city " +
                    addition);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                City city = new City(resultSet.getInt("city_id"), resultSet.getString("city_name"),
                        ((Timestamp) resultSet.getObject("creation_date")).toLocalDateTime(),
                        resultSet.getInt("area"), resultSet.getInt("population"), resultSet
                        .getInt("meters_above_sea_level"), resultSet.getString("city_user"));

                try {
                    city.setClimate(City.Climate.valueOf(resultSet
                            .getString("climate")));
                } catch (NullPointerException e) {
                    city.setClimate(null);
                }

                try {
                    city.setGovernment(City.Government.valueOf(resultSet
                            .getString("government")));
                } catch (NullPointerException e) {
                    city.setGovernment(null);
                }

                try {
                    city.setStandardOfLiving(City.StandardOfLiving.valueOf(resultSet
                            .getString("standard_of_living")));
                } catch (NullPointerException e) {
                    city.setStandardOfLiving(null);
                }

                city.setCoordinates(new City.Coordinates(resultSet.getDouble("x"), resultSet
                        .getFloat("y")));


                city.setGovernor(new City.Human(resultSet.getString("human_name")));
                sortedSet.add(city);
            }

            return sortedSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized boolean updateContent(City object, String owner) {
        if (removeContent(Integer.toString(object.getId()), owner)) {
            addContent(object, owner);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean removeContent(String key, String owner) {
        int result = 0;

        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("DELETE FROM city WHERE " +
                    "city_id = ? AND city_user = ?");
            preparedStatement.setInt(1, Integer.parseInt(key));
            preparedStatement.setString(2, owner);

            result += preparedStatement.executeUpdate();

            if (result > 0) {
                preparedStatement = this.getConnection().prepareStatement("DELETE FROM coordinates" +
                        " WHERE coordinates_id = ?");
                preparedStatement.setInt(1, Integer.parseInt(key));
                result += preparedStatement.executeUpdate();

                preparedStatement = this.getConnection().prepareStatement("DELETE FROM human WHERE " +
                        "human_id = ?");
                preparedStatement.setInt(1, Integer.parseInt(key));
                result += preparedStatement.executeUpdate();
            }

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean clearContent(String owner) {
        int result = 0;
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("SELECT * FROM city WHERE" +
                    " city_user = ?");
            preparedStatement.setString(1, owner);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                preparedStatement = this.getConnection().prepareStatement("DELETE FROM city WHERE city_id = ?");
                preparedStatement.setInt(1, resultSet.getInt(1));
                result += preparedStatement.executeUpdate();

                preparedStatement = this.getConnection().prepareStatement("DELETE FROM coordinates " +
                        "WHERE coordinates_id = ?");
                preparedStatement.setInt(1, resultSet.getInt(1));
                result += preparedStatement.executeUpdate();

                preparedStatement = this.getConnection().prepareStatement("DELETE FROM human WHERE human_id = ?");
                preparedStatement.setInt(1, resultSet.getInt(1));
                result += preparedStatement.executeUpdate();
            }

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean addContent(City object, String login) {
        try {
            int result = 0;

            PreparedStatement preparedStatement = this.getConnection().prepareStatement("INSERT INTO" +
                    " city(city_id, city_name, creation_date, area, population," +
                    " meters_above_sea_level, climate, government, standard_of_living, city_user)" +
                    " VALUES (?, ?, ?, ?, ?, ?,CAST(? AS city_climate), CAST (? AS city_government), CAST(? AS " +
                    "city_standard_of_living), ?)");

            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getName());
            preparedStatement.setObject(3, object.getCreationDate());
            preparedStatement.setInt(4, object.getArea());
            preparedStatement.setInt(5, object.getPopulation());
            preparedStatement.setInt(6, object.getMeters());

            try {
                preparedStatement.setObject(7, object.getClimate().getName());
            } catch (NullPointerException e) {
                preparedStatement.setObject(7, null);
            }

            try {
                preparedStatement.setObject(8, object.getGovernment().getName());
            } catch (NullPointerException e) {
                preparedStatement.setObject(8, null);
            }

            try {
                preparedStatement.setObject(9, object.getStandardOfLiving().getName());
            } catch (NullPointerException e) {
                preparedStatement.setObject(9, null);
            }

            preparedStatement.setString(10, login);

            result += preparedStatement.executeUpdate();

            preparedStatement = this.getConnection().prepareStatement("INSERT INTO " +
                    "coordinates(coordinates_id, x, y) VALUES (?,?,?)");

            preparedStatement.setInt(1, object.getId());
            preparedStatement.setDouble(2, object.getCoordinates().getFirstCoordinates());
            preparedStatement.setFloat(3, object.getCoordinates().getSecondCoordinates());

            result += preparedStatement.executeUpdate();

            preparedStatement = this.getConnection().prepareStatement("INSERT INTO human(human_id, human_name) " +
                    "VALUES (?,?)");

            preparedStatement.setInt(1, object.getId());
            preparedStatement.setString(2, object.getGovernor().getHumanName());

            result += preparedStatement.executeUpdate();

            return result == 3;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized void moveContent() {
        this.getCollectionBase().getSet().clear();

        String addition = "FULL JOIN coordinates ON city.city_id = coordinates.coordinates_id" +
                " FULL JOIN human ON city.city_id = human.human_id";
        this.getCollectionBase().uploadContent(this.getContent(addition));
    }
}
