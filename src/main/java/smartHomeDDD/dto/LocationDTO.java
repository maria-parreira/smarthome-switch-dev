package smartHomeDDD.dto;

/**
 * A data transfer object (DTO) representing location information.
 * This class encapsulates details about a specific location including address, zip code,
 * latitude, longitude, and associated house ID.
 */
public class LocationDTO {

    /**
     * The street address of the location.
     */
    private final String _address;
    /**
     * The Country of the location.
     */
    private final String _country;
    /**
     * The Zip Code of the location.
     */
    private final String _zipCode;
    /**
     * The Latitude of the location.
     */
    private final double _latitude;
    /**
     * The Longitude of the location.
     */
    private final double _longitude;
    /**
     * The unique identifier for a house.
     */
    private final String _houseId;

    /**
     * Constructs a new LocationDTO with the specified address, zip code, latitude,
     * longitude, and house ID.
     *
     * @param address   The address of the location.
     * @param country   The zip code of the location.
     * @param zipCode   The zip code of the location.
     * @param latitude  The latitude coordinate of the location.
     * @param longitude The longitude coordinate of the location.
     * @param houseId   The ID of the house associated with this location.
     */
    public LocationDTO(String address, String country,String zipCode, double latitude, double longitude, String houseId) {
        this._address=address;
        this._country=country;
        this._zipCode=zipCode;
        this._latitude=latitude;
        this._longitude=longitude;
        this._houseId=houseId;
    }

    /**
     * Retrieves the house ID associated with this location.
     *
     * @return The house ID.
     */
    public String getHouseId(){
        return this._houseId;
    }

    /**
     * Retrieves the address of this location.
     *
     * @return The address.
     */
    public String getAddress(){return this._address;}

    /**
     * Retrieves the zip code of this location.
     *
     * @return The zip code.
     */
    public String getZipCode(){
        return this._zipCode;
    }

    /**
     * Retrieves the latitude coordinate of this location.
     *
     * @return The latitude.
     */
    public double getLatitude(){
        return this._latitude;
    }

    /**
     * Retrieves the longitude coordinate of this location.
     *
     * @return The longitude.
     */
    public double getLongitude(){
        return this._longitude;
    }

    public String getCountry(){
        return this._country;
    }



}
