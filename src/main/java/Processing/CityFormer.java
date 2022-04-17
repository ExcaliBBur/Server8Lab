package Processing;

import Data.City;
import Interfaces.IFormer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

/**
 * Class for forming City object.
 */
public class CityFormer implements IFormer<City> {

    @Override
    public City formObj(String object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            City city = objectMapper.readValue(object, City.class);
            city.setCreationDate(LocalDateTime.now());
            return city;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}