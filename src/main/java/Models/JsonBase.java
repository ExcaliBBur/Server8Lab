package Models;

import Exceptions.DeserializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.*;

/**
 * Responsible for working with collection.
 * Performs serialization and deserialization of collection objects in json format.
 */
public abstract class JsonBase<T extends Collectables> extends FormatBase<T> {
    /**
     * Constructor, gets all the necessary things.
     *
     * @param collectionType type of collection content
     */
    public JsonBase(Class<T> collectionType, Set<T> set) {
        super(collectionType, set);
    }

    @Override
    public T deserialize(String json) throws DeserializationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            return mapper.readValue(json, this.getCollectionType());
        } catch (JsonProcessingException e) {
            throw new DeserializationException.LoadException();
        }
    }
}