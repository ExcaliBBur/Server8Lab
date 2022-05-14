package Realisation;

import Models.City;
import Models.JsonBase;
import Exceptions.DeserializationException;

import java.util.Set;

/**
 * Implementation of a database for storing objects of the city class
 */
public class CityBase extends JsonBase<City> {
    /**
     * Constructor, gets all necessary things.
     *
     * @param collectionType type of stored objects
     */
    public CityBase(Class<City> collectionType, Set<City> set) {
        super(collectionType, set);
    }

    @Override
    public void uploadContent(Set<City> set) throws DeserializationException.LoadException {
        this.setSet(set);
    }
}
