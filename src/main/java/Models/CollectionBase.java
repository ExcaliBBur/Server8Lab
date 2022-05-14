package Models;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Responsible for working with collection.
 *
 * @param <T> collection type
 */
public abstract class CollectionBase<T> {
    private final Class<T> collectionType;
    private final java.time.LocalDateTime initializationDate;
    private Set<T> set;

    /**
     * Constructor, gets all the necessary things.
     *
     * @param collectionType type of collection content
     */
    public CollectionBase(Class<T> collectionType, Set<T> set) {
        this.collectionType = collectionType;
        this.set = set;
        this.initializationDate = LocalDateTime.now();
    }

    public abstract void uploadContent(Set<T> Set);

    /**
     * Getter of sortedSet.
     *
     * @return SortedSet of objects
     */
    public Set<T> getSet() {
        return set;
    }

    public void setSet(Set<T> set) {
        this.set = set;
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
