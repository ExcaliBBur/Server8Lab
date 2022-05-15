package Realisation;

import Interaction.Sender;
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

        Server.connectedUsers.forEach(x -> new Sender<>(Server.channel, new ServerDTO<>(set, ServerDTO.DTOType.UPDATE),
                x).start());
    }
}
