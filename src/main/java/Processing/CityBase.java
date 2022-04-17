package Processing;

import Data.City;
import Data.JsonBase;
import Exceptions.DeserializationException;

import java.util.SortedSet;

/**
 * Implementation of a database for storing objects of the city class
 */
public class CityBase extends JsonBase<City> {
    /**
     * Constructor, gets all necessary things.
     *
     * @param collectionType type of stored objects
     * @param sortedSet      collection base itself
     */
    public CityBase(Class<City> collectionType, SortedSet<City> sortedSet) {
        super(collectionType, sortedSet);
    }

    @Override
    public void uploadContent(SortedSet<City> sortedSet) throws DeserializationException.LoadException {
        this.setSortedSet(sortedSet);
    }
}
