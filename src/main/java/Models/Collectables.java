package Models;

import java.io.Serializable;

/**
 * Represents all specified classes for storage in the collection.
 */
public abstract class Collectables implements Serializable {
    private int id;
    private String name;

    public Collectables() {

    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param id   unique id of object in collection
     * @param name string identifier of the object
     */
    public Collectables(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param name string identifier of the object
     */
    public Collectables(String name) {
        this.name = name;
    }

    /**
     * Getter for ID.
     *
     * @return id of object.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of id.
     *
     * @param id Integer identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter of name.
     *
     * @param name String identifier
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of name.
     *
     * @return name of object.
     */
    public String getName() {
        return name;
    }
}
