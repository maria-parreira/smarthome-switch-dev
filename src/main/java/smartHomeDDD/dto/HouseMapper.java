package smartHomeDDD.dto;

import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.valueobject.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HouseMapper is responsible for mapping data between House objects and their corresponding data representations,
 * such as DTOs (Data Transfer Objects) or database entities.
 */
public class HouseMapper {
    /**
     * Maps the provided address, zip code, latitude, and longitude to a Location object.
     * @param locationDTO DTO provided by controller object.
     * @return The Location object representing the mapped location.
     */
    public static Location DTOToLocation(LocationDTO locationDTO){

        String address = locationDTO.getAddress();
        double longitude = locationDTO.getLongitude();
        String country = locationDTO.getCountry();
        String zipCode = locationDTO.getZipCode();
        double latitude = locationDTO.getLatitude();

        return getLocation(address, longitude, country, zipCode, latitude);
    }

    private static Location getLocation(String address, double longitude, String country, String zipCode, double latitude) {
        Address addressVO = addressToVo(address);
        ZipCode zipCodeVO = zipCodeToVo(country,zipCode);
        Latitude latitudeVO = latitudeToVo(latitude);
        Longitude longitudeVO = longitudeToVo(longitude);
        GPSCoordinates gpsCoordinatesVO = createGPSCoordinates(latitudeVO,longitudeVO);
        return new Location(addressVO,zipCodeVO,gpsCoordinatesVO);
    }

    /**
     * Converts the provided house ID string to a HouseId object.
     * @param houseId The house ID string to convert.
     * @return A HouseId object representing the provided house ID.
     * @throws IllegalArgumentException if the provided house ID is null.
     */
    public static HouseId DTOToHouseId(String houseId){
        if (houseId == null) {
            throw new IllegalArgumentException("HouseId can't be null");
        }
        return new HouseId(houseId);
    }

    /**
     * Converts the provided address string to an Address value object.
     * @param address The address string to convert.
     * @return An Address value object representing the provided address.
     */
    private static Address addressToVo(String address){
        return new Address(address);
    }

    /**
     * Converts the provided zip code string to a ZipCode value object.
     * @param zipCode The zip code string to convert.
     * @return A ZipCode value object representing the provided zip code.
     */
    private static ZipCode zipCodeToVo(String country,String zipCode){
        return new ZipCode(country,zipCode);
    }

    /**
     * Converts the provided latitude value to a Latitude value object.
     * @param latitude The latitude value to convert.
     * @return A Latitude value object representing the provided latitude.
     */
    private static Latitude latitudeToVo(double latitude){
        return new Latitude(latitude);
    }

    /**
     * Converts the provided longitude value to a Longitude value object.
     * @param longitude The longitude value to convert.
     * @return A Longitude value object representing the provided longitude.
     */
    private static Longitude longitudeToVo(double longitude){
        return new Longitude(longitude);
    }

    /**
     * Creates a GPSCoordinates value object using the provided latitude and longitude.
     * @param latitude  The latitude value object.
     * @param longitude The longitude value object.
     * @return A GPSCoordinates value object representing the provided latitude and longitude.
     */
    private static GPSCoordinates createGPSCoordinates(Latitude latitude, Longitude longitude){
        return new GPSCoordinates(latitude,longitude);
    }

    /**
     * Maps the provided House object to a HouseDTO object.
     * @param house The House object to map.
     * @return The HouseDTO object representing the mapped house.
     */
    public static HouseDTO houseToDTO(House house){
        String id = house.identity().toString();
        return new HouseDTO(id);
    }

    /**
     * Converts an iterable collection of House objects to a list of HouseDTO objects.
     * @param houses The iterable collection of House objects to convert.
     * @return A list of HouseDTO objects representing the mapped houses.
     */
    public static List<HouseDTO> housesListToDTO(Iterable<House> houses){
        List<HouseDTO> housesDTO = new ArrayList<>();
        for (House house : houses){
            housesDTO.add(houseToDTO(house));
        }
        return housesDTO;
    }

    /**
     * Converts a House domain object into a HouseWebDTO object.
     * @param house The House domain object to be converted. This object contains the unique identifier for a house.
     * @return A HouseWebDTO object that represents the given House object. The returned HouseWebDTO object includes the unique identifier for a house.
     */
    public static HouseIDExitWebDTO houseToIDExitWebDTO(House house){
        String id = house.identity().toString();
        return new HouseIDExitWebDTO(id);
    }

    /**
     * Converts a peak power consumption value to a PeakPowerConsumptionWebDTO object.
     * @param peakPowerConsumption The peak power consumption value to convert.
     * @return A PeakPowerConsumptionWebDTO object representing the peak power consumption value.
     */
    public static PeakPowerConsumptionWebDTO housePeakPowerConsumptionDomainToWebDTO(double peakPowerConsumption) {
        return new PeakPowerConsumptionWebDTO(String.valueOf(peakPowerConsumption));
    }

    /**
     * Converts a House domain object into a HouseExitWebDTO object.
     *
     * @param house The House domain object to be converted
     * @return A HouseExitWebDTO object that represents the given House object.
     * The returned HouseExitWebDTO object includes the unique identifier for a house, the address,
     * zip code, latitude and longitude.
     */
    public static HouseExitWebDTO houseToExitWebDTO(House house){
        String id = house.identity().toString();
        String address = house.getHouseLocation().getAddress().toString();
        String country = house.getHouseLocation().getZipCode().getCountry();
        String zipCode = house.getHouseLocation().getZipCode().toString();
        double latitude = house.getHouseLocation().getGPSCoordinates().getLatitude();
        double longitude = house.getHouseLocation().getGPSCoordinates().getLongitude();
        return new HouseExitWebDTO(id, address, country, zipCode, latitude, longitude);
    }

}
