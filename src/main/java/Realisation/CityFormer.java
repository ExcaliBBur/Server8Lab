package Realisation;

import Models.City;
import Interfaces.Formable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

/**
 * Class for forming City object.
 */
public class CityFormer implements Formable<City> {

    @Override
    public City formObj(String object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            City city = objectMapper.readValue(object, City.class);
            city.setCreationDate(LocalDateTime.now());
            return city;
        } catch (JsonProcessingException ignore) {

        }
        return null;
    }
}