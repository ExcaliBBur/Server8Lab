package Models;

import java.sql.Connection;

/**
 * Abstract class, represents a way to interact with a database
 */
public abstract class Controller {
    private Connection connection;

    /**
     * Constructor, gets all necessary things.
     *
     * @param connection connection to the database
     */
    public Controller(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
