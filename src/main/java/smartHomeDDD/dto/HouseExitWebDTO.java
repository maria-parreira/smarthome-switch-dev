package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * This class represents a House Exit Data Transfer Object.
 */
public class HouseExitWebDTO extends RepresentationModel<HouseExitWebDTO> {

    /**
     * The unique identifier of the house.
     */
    private final String id;

    /**
     * The address of the house.
     */
    private final String address;

    /**
     * The country of the house.
     */
    private final String country;

    /**
     * The zip code of the house.
     */
    private final String zipCode;

    /**
     * The latitude of the house.
     */
    private final double latitude;

    /**
     * The longitude of the house.
     */
    private final double longitude;

    /**
     * Constructs a new HouseExitWebDTO object with the specified information.
     * @param id The unique identifier of the house.
     * @param address The address of the house.
     * @param zipCode The zip code of the house.
     * @param latitude The latitude of the house.
     * @param longitude The longitude of the house.
     */
    public HouseExitWebDTO(String id, String address, String country, String zipCode, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.country = country;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Retrieves the ID of the house.
     * @return The ID of the house.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the address of the house.
     * @return The address of the house.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Retrieves the country of the house.
     * @return The country of the house.
     */
    public String getCountry() {
        return country;
    }
    /**
     * Retrieves the zip code of the house.
     * @return The zip code of the house.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Retrieves the latitude of the house.
     * @return The latitude of the house.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Retrieves the longitude of the house.
     * @return The longitude of the house.
     */
    public double getLongitude() {
        return longitude;
    }

}
