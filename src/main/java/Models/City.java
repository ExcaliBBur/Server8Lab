package Models;

import Exceptions.DeserializationException;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.io.Serializable;

/**
 * Class whose objects are contained in the collection.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class City extends Collectables implements Comparable<City>, Serializable {
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;
    private Integer area;
    private Integer population;
    private int meters;
    private Climate climate;
    private Government government;
    private StandardOfLiving standardOfLiving;
    private Human governor;
    @JsonIgnore
    private String username;

    public static final long serialVersionUID = 42L;

    public City() {

    }

    /**
     * Constructor, gets all necessary things.
     *
     * @param id               unique Integer identifier
     * @param name             String identifier
     * @param coordinates      location
     * @param creationDate     initialization date
     * @param area             total area
     * @param population       total population
     * @param meters           meters above sea level
     * @param climate          current climate
     * @param government       current government
     * @param standardOfLiving current standard of living
     * @param governor         current governor
     */
    public City(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, Integer area,
                Integer population, int meters, Climate climate, Government government,
                StandardOfLiving standardOfLiving, Human governor, String username) {
        super(id, name);
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.meters = meters;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
        this.username = username;
    }

    public City(int id, String name, java.time.LocalDateTime creationDate, Integer area,
                Integer population, int meters, String username) {
        super(id, name);
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.meters = meters;
        this.username = username;
    }
    @JsonGetter("username")
    public String getUsername() {
        return username;
    }
    /**
     * Specified setter of id.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("id")
    public void setId(String line) throws DeserializationException {
        try {
            int id = Integer.parseInt(line);
            this.setId(id);
        } catch (NumberFormatException e) {
            throw new DeserializationException.InvalidParameterException();
        }
    }

    /**
     * Specified setter of name.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("name")
    public void setInputName(String line) throws DeserializationException {
        if (!line.equals("")) {
            this.setName(line);
        } else {
            throw new DeserializationException.InvalidParameterException.InvalidParameterException();
        }
    }

    /**
     * Setter of coordinates.
     *
     * @param coordinates current coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Getter of coordinates.
     *
     * @return current location
     */
    @JsonGetter("coordinates")
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Setter of creationDate.
     *
     * @param creationDate date of initialization
     */
    public void setCreationDate(java.time.LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Getter of creationDate.
     *
     * @return initialization date
     */
    @JsonGetter("creationDate")
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Setter of area.
     *
     * @param area current area
     */
    public void setArea(Integer area) {
        this.area = area;
    }


    /**
     * Specified setter of area.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("area")
    public void setInputArea(String line) throws DeserializationException {
        if (!line.equals("")) {
            try {
                this.area = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new DeserializationException.InvalidParameterException();
            }
        } else {
            throw new DeserializationException.InvalidParameterException();
        }
    }

    /**
     * Getter of area.
     *
     * @return current area
     */
    @JsonGetter("area")
    public Integer getArea() {
        return area;
    }

    /**
     * Setter of population.
     *
     * @param population current population
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * Specified setter of population.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("population")
    public void setInputPopulation(String line) throws DeserializationException {
        if (!line.equals("")) {
            try {
                this.population = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new DeserializationException.InvalidParameterException();
            }
        } else {
            throw new DeserializationException.InvalidParameterException();
        }
    }

    /**
     * Getter of population
     *
     * @return current population
     */
    @JsonGetter("population")
    public Integer getPopulation() {
        return population;
    }

    /**
     * Setter of meters.
     *
     * @param meters current meters above sea level.
     */
    public void setMeters(int meters) {
        this.meters = meters;
    }

    /**
     * Specified setter of meters.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("meters")
    public void setInputMeters(String line) throws DeserializationException {
        if (!line.equals("")) {
            try {
                this.meters = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new DeserializationException.InvalidParameterException();
            }
        } else {
            throw new DeserializationException.InvalidParameterException();
        }
    }

    /**
     * Getter of meters.
     *
     * @return current meters above sea level
     */
    @JsonGetter("meters")
    public int getMeters() {
        return meters;
    }

    /**
     * Setter of climate.
     *
     * @param climate current climate
     */
    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    /**
     * Specified setter of climate.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("climate")
    public void setInputClimate(String line) throws DeserializationException {
        if (line != null) {
            if (!line.equals("")) {
                switch (line) {
                    case "MONSOON": {
                        this.climate = Climate.MONSOON;
                        return;
                    }
                    case "MEDITERRANIAN": {
                        this.climate = Climate.MEDITERRANIAN;
                        return;
                    }
                    case "TUNDRA": {
                        this.climate = Climate.TUNDRA;
                        return;
                    }
                    case "OCEANIC": {
                        this.climate = Climate.OCEANIC;
                        return;
                    }
                }
                throw new DeserializationException.InvalidParameterException();
            }
        } else {
            this.climate = null;
        }
    }

    /**
     * Getter of climate.
     *
     * @return current climate.
     */
    @JsonGetter("climate")
    public Climate getClimate() {
        return climate;
    }

    /**
     * Setter of government.
     *
     * @param government current government
     */
    public void setGovernment(Government government) {
        this.government = government;
    }

    /**
     * Specified setter of government.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("government")
    public void setInputGovernment(String line) throws DeserializationException {
        if (line != null) {
            if (!line.equals("")) {
                switch (line) {
                    case "MATRIARCHY": {
                        this.government = Government.MATRIARCHY;
                        return;
                    }
                    case "PLUTOCRACY": {
                        this.government = Government.PLUTOCRACY;
                        return;
                    }
                    case "OLIGARCHY": {
                        this.government = Government.OLIGARCHY;
                        return;
                    }
                    case "JUNTA": {
                        this.government = Government.JUNTA;
                        return;
                    }
                }
                throw new DeserializationException.InvalidParameterException();
            }
        } else {
            this.government = null;
        }
    }

    /**
     * Getter of government.
     *
     * @return current government
     */
    @JsonGetter("government")
    public Government getGovernment() {
        return government;
    }

    /**
     * Setter of standardOfLiving.
     *
     * @param standardOfLiving current standard of living
     */
    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    /**
     * Specified setter of standard of living.
     *
     * @param line data to be set.
     * @throws DeserializationException caused by incorrect data in file.
     */
    @JsonSetter("standardOfLiving")
    public void setInputStandardOfLiving(String line) throws DeserializationException {
        if (line != null) {
            if (!line.equals("")) {
                switch (line) {
                    case "ULTRA_HIGH": {
                        this.standardOfLiving = StandardOfLiving.ULTRA_HIGH;
                        return;
                    }
                    case "VERY_HIGH": {
                        this.standardOfLiving = StandardOfLiving.VERY_HIGH;
                        return;
                    }
                    case "MEDIUM": {
                        this.standardOfLiving = StandardOfLiving.MEDIUM;
                        return;
                    }
                    case "LOW": {
                        this.standardOfLiving = StandardOfLiving.LOW;
                        return;
                    }
                    case "ULTRA_LOW": {
                        this.standardOfLiving = StandardOfLiving.ULTRA_LOW;
                        return;
                    }
                }
                throw new DeserializationException.InvalidParameterException();
            }
        } else {
            this.standardOfLiving = null;
        }
    }

    /**
     * Getter of standardOfLiving.
     *
     * @return current standard of living
     */
    @JsonGetter("standardOfLiving")
    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    /**
     * Setter of governor.
     *
     * @param governor current governor
     */
    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    /**
     * Getter of governor.
     *
     * @return current governor
     */
    @JsonGetter("governor")
    public Human getGovernor() {
        return governor;
    }

    /**
     * Defines String representation of an object.
     *
     * @return string representation of an object
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID of the city: ").append(this.getId()).append(".\n").append("Name of the city: ")
                .append(this.getName()).append(".\n").append("Coordinates of the city: X = ")
                .append(this.coordinates.getFirstCoordinates()).append(", Y = ")
                .append(this.coordinates.getSecondCoordinates()).append(".\n")
                .append("Creation time of the city: ").append(this.creationDate.toString()
                        .replace("T", " ")).append(".\n")
                .append("Area of the city: ").append(this.area).append(".\n").append("Population of the city: ")
                .append(this.population).append(".\n").append("Meters above sea level of the city: ")
                .append(this.meters).append(".\n");
        stringBuilder.append("Climate of the city: ");
        try {
            stringBuilder.append(this.climate.getName()).append(".\n");
        } catch (NullPointerException e) {
            stringBuilder.append("null").append(".\n");
        }
        stringBuilder.append("Government of the city: ");
        try {
            stringBuilder.append(this.government.getName()).append(".\n");
        } catch (NullPointerException e) {
            stringBuilder.append("null").append(".\n");
        }
        stringBuilder.append("Standard of living of the city: ");
        try {
            stringBuilder.append(this.standardOfLiving.getName()).append(".\n");
        } catch (NullPointerException e) {
            stringBuilder.append("null").append(".\n");
        }
        stringBuilder.append("Governor name of the city: ").append(this.governor.getHumanName()).append(".")
                .append("\n");
        return stringBuilder.toString();
    }

    /**
     * Defines if objects are equal.
     *
     * @param object comparable
     * @return decision
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this.getClass() != object.getClass()) {
            return false;
        }

        final City another = (City) object;

        return this.getId() == another.getId();
    }

    /**
     * defines natural sorting.
     *
     * @param city comparable
     * @return decision
     */
    public int compareTo(City city) {
        long firstCost = this.getArea() + this.getPopulation() + this.getMeters();
        long secondCost = city.getArea() + city.getPopulation() + city.getMeters();

        if (firstCost == secondCost) {
            return 1;
        } else {
            return Long.compare(firstCost, secondCost);
        }
    }

    /**
     * Represents combination of Data.City coordinates.
     */
    public static class Coordinates implements Serializable {
        static final long serialVersionUID = 42L;

        private Double firstCoordinates;
        private Float secondCoordinates;

        public Coordinates() {

        }

        /**
         * Constructor, gets current position.
         *
         * @param firstCoordinates  x-coordinates
         * @param secondCoordinates y-coordinates
         */
        public Coordinates(Double firstCoordinates, Float secondCoordinates) {
            this.firstCoordinates = firstCoordinates;
            this.secondCoordinates = secondCoordinates;
        }

        /**
         * Getter of firstCoordinates.
         *
         * @return current x-coordinates
         */
        @JsonGetter("firstCoordinates")
        public Double getFirstCoordinates() {
            return firstCoordinates;
        }

        /**
         * Getter of secondCoordinates.
         *
         * @return current y-coordinates
         */
        @JsonGetter("secondCoordinates")
        public Float getSecondCoordinates() {
            return secondCoordinates;
        }

        /**
         * Setter of firstCoordinates.
         *
         * @param x current x-coordinates
         */
        public void setFirstCoordinates(Double x) {
            this.firstCoordinates = x;
        }

        /**
         * Specified setter of firstCoordinates.
         *
         * @param line data to be set.
         * @throws DeserializationException caused by incorrect data in file.
         */
        @JsonSetter("firstCoordinates")
        public void setInputFirstCoordinates(String line) throws DeserializationException {
            if (!line.equals("")) {
                try {
                    double tmp = Double.parseDouble(line);
                    if (tmp > 627) throw new DeserializationException.InvalidParameterException();
                    this.firstCoordinates = tmp;
                } catch (NumberFormatException e) {
                    throw new DeserializationException.InvalidParameterException();
                }
            } else {
                throw new DeserializationException.InvalidParameterException();
            }
        }

        /**
         * Setter of secondCoordinates.
         *
         * @param y current y-coordinates.
         */
        public void setSecondCoordinates(Float y) {
            this.secondCoordinates = y;
        }

        /**
         * Specified setter of secondCoordinates.
         *
         * @param line data to be set.
         * @throws DeserializationException caused by incorrect data in file.
         */
        @JsonSetter("secondCoordinates")
        public void setInputSecondCoordinates(String line) throws DeserializationException {
            if (!line.equals("")) {
                try {
                    this.secondCoordinates = Float.parseFloat(line);
                } catch (NumberFormatException e) {
                    throw new DeserializationException.InvalidParameterException();
                }
            } else {
                throw new DeserializationException.InvalidParameterException();
            }
        }
    }

    /**
     * Description of the governor.
     */
    public static class Human implements Serializable {
        static final long serialVersionUID = 42L;

        private String humanName = null;

        public Human() {

        }

        /**
         * Constructor, gets governor name.
         *
         * @param humanName human name
         */
        public Human(String humanName) {
            this.humanName = humanName;
        }

        /**
         * Getter of humanName.
         *
         * @return current governor name
         */
        @JsonGetter("humanName")
        public String getHumanName() {
            return humanName;
        }

        /**
         * Setter of humanName.
         *
         * @param humanName name of a current governor
         */
        public void setHumanName(String humanName) {
            this.humanName = humanName;
        }

        /**
         * Specified setter of humanName.
         *
         * @param line data to be set.
         * @throws DeserializationException caused by incorrect data in file.
         */
        @JsonSetter("humanName")
        public void setInputHumanName(String line) throws DeserializationException {
            if (!line.equals("")) {
                this.humanName = line;
            } else {
                throw new DeserializationException.InvalidParameterException();
            }
        }
    }

    /**
     * Climate of the city.
     */
    public enum Climate {
        MONSOON("MONSOON"),
        OCEANIC("OCEANIC"),
        MEDITERRANIAN("MEDITERRANIAN"),
        TUNDRA("TUNDRA");

        private final String name;

        Climate(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Government of the city.
     */
    public enum Government {
        MATRIARCHY("MATRIARCHY"),
        OLIGARCHY("OLIGARCHY"),
        PLUTOCRACY("PLUTOCRACY"),
        JUNTA("JUNTA");

        private final String name;

        Government(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Standard of living of the city.
     */
    public enum StandardOfLiving {
        ULTRA_HIGH("ULTRA_HIGH"),
        VERY_HIGH("VERY_HIGH"),
        MEDIUM("MEDIUM"),
        LOW("LOW"),
        ULTRA_LOW("ULTRA_LOW");

        private final String name;

        StandardOfLiving(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
