package Data;

import java.time.LocalDateTime;
import java.util.SortedSet;

/**
 * Responsible for working with collection.
 *
 * @param <T> collection type
 */
public abstract class CollectionBase<T> {
    private final Class<T> collectionType;
    private final java.time.LocalDateTime initializationDate;
    private SortedSet<T> sortedSet;

    /**
     * Constructor, gets all the necessary things.
     *
     * @param collectionType type of collection content
     * @param sortedSet      collection base itself
     */
    public CollectionBase(Class<T> collectionType, SortedSet<T> sortedSet) {
        this.collectionType = collectionType;
        this.sortedSet = sortedSet;
        this.initializationDate = LocalDateTime.now();
    }

    public abstract void uploadContent(SortedSet<T> sortedSet);

    /**
     * Getter of sortedSet.
     *
     * @return SortedSet of objects
     */
    public SortedSet<T> getSortedSet() {
        return sortedSet;
    }

    public void setSortedSet(SortedSet<T> sortedSet) {
        this.sortedSet = sortedSet;
    }

    /**
     * Getter of initializationDate.
     *
     * @return String representation of the initialization date.
     */
    public String getInitializationDate() {
        return initializationDate.toString().replace("T", " ");
    }

    /**
     * Getter of collectionType
     *
     * @return collection type
     */
    public Class<T> getCollectionType() {
        return collectionType;
    }
}
