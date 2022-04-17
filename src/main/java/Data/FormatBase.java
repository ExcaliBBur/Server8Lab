package Data;

import java.util.SortedSet;

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
     * @param sortedSet      containment base itself
     */
    public FormatBase(Class<T> collectionType, SortedSet<T> sortedSet) {
        super(collectionType, sortedSet);
    }

    /**
     * Deserializes data from custom format.
     *
     * @param formattedData serialized data
     * @return deserialized object
     */
    public abstract T deserialize(String formattedData);
}
