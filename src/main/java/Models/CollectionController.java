package Models;

import java.sql.Connection;
import java.util.Set;

/**
 * The class responsible for working with the database.
 *
 * @param <T> objects whose information is in the database
 */
public abstract class CollectionController<T extends Collectables> extends Controller {
    private CollectionBase<T> collectionBase;

    /**
     * Constructor, gets all necessary things.
     *
     * @param connection     connection to database to work with
     * @param collectionBase collection of objects for storing data in memory
     */
    public CollectionController(Connection connection, CollectionBase<T> collectionBase) {
        super(connection);
        this.collectionBase = collectionBase;
    }

    public CollectionController(Connection connection) {
        super(connection);
    }

    /**
     * Get data from the database.
     *
     * @param addition addition to the request
     * @return the set of received objects
     */
    public abstract Set<T> getContent(String addition);

    /**
     * Update content in the database.
     *
     * @param object object to deal with
     * @param owner  user to access and update data
     * @return result of updating
     */
    public abstract boolean updateContent(T object, String owner);

    /**
     * Remove content from the database.
     *
     * @param key   identifier of the object
     * @param owner user to access and delete data
     * @return result of deleting
     */
    public abstract boolean removeContent(String key, String owner);

    /**
     * Clears all content of the specified user.
     *
     * @param owner user to access and clear data
     * @return result of clearing
     */
    public abstract boolean clearContent(String owner);

    /**
     * Add content to the database.
     *
     * @param object object to deal with
     * @param owner  user to access and add data
     * @return result of adding
     */
    public abstract boolean addContent(T object, String owner);

    /**
     * Get next available sequence-generated key
     *
     * @return sequence-generated key
     */
    public abstract int getSequenceID();

    /**
     * Move content from the database to collectionBase
     */
    public abstract void moveContent();

    public CollectionBase<T> getCollectionBase() {
        return collectionBase;
    }
}
