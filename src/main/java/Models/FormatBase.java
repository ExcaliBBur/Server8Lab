package Models;

import java.util.Set;

/**
 * Abstract class, represents a database for storing data in memory and loading it from a serialized format.
 *
 * @param <T> type of objects to contain
 */
public abstract class FormatBase<T extends Collectables> extends CollectionBase<T> {
    /**
     * Constructor, gets all necessary things
     *
     * @param collectionType type of objects to contain
     */
    public FormatBase(Class<T> collectionType, Set<T> set) {
        super(collectionType, set);
    }

    /**
     * Deserializes data from custom format.
     *
     * @param formattedData serialized data
     * @return deserialized object
     */
    public abstract T deserialize(String formattedData);
}
