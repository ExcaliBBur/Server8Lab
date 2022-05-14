package Models;

import java.io.Serializable;

/**
 * A class representing users/
 */
public class User implements Serializable {
    private String name;
    private String password;

    /**
     * Constructor, gets all necessary things.
     *
     * @param name     user login
     * @param password user password
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name + "\n" + password;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this.getClass() != object.getClass()) {
            return false;
        }

        final User another = (User) object;

        return ((this.getName().equals(another.getName())) && this.getPassword().equals(another.getPassword()));
    }
}
