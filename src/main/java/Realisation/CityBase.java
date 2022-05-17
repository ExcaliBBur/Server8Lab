package Realisation;

import Interaction.Sender;
import Interfaces.Informable;
import Main.Server;
import Models.City;
import Models.JsonBase;
import Exceptions.DeserializationException;
import Models.ServerDTO;

import java.util.Set;

/**
 * Implementation of a database for storing objects of the city class
 */
public class CityBase extends JsonBase<City> {
    private Informable<City> informable;

    /**
     * Constructor, gets all necessary things.
     *
     * @param collectionType type of stored objects
     */
    public CityBase(Class<City> collectionType, Set<City> set, Informable<City> informable) {
        super(collectionType, set);
        this.informable = informable;
    }

    @Override
    public void uploadContent(Set<City> set) throws DeserializationException.LoadException {
        this.setSet(set);

        informable.inform(set);
    }
}
