package Realisation;

import Models.Controller;
import Models.User;
import Main.Server;

import java.sql.*;
import java.util.logging.Level;

/**
 * Provides access to information about users located in the database.
 */
public class UserController extends Controller {
    public UserController(Connection connection) {
        super(connection);
    }

    /**
     * insert new User into the database.
     *
     * @param user new User to add
     * @throws SQLException caused by existing user
     */
    public synchronized void insertUser(User user) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement("INSERT INTO users(login,"
                + "user_password) VALUES (?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());

        if (preparedStatement.executeUpdate() > 0) {
            Server.logger.log(Level.INFO, "New user has been added to the database.");
        }
    }

    /**
     * get existing user from database.
     *
     * @param login user account name
     * @return complete User data
     * @throws SQLException caused by nonexistent user.
     */
    public User getUser(String login) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement("SELECT * FROM" +
                " users WHERE login = ?");
        preparedStatement.setString(1, login);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return new User(resultSet.getString(1), resultSet.getString(2));
    }
}
