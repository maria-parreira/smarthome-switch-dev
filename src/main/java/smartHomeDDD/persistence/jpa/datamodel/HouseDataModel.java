package smartHomeDDD.persistence.jpa.datamodel;
import smartHomeDDD.domain.house.FactoryHouse;
import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.valueobject.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a data model for a house entity in the database.
 * This class is used to map the House domain entity to a format that can be stored in the database.
 */

@Entity
@Table(name = "HOUSE")

public class HouseDataModel {

    /** The unique identifier for the house. */
    @Id
    @Column(name = "house_id")
    private String _houseId;

    /** The address of the house. */
    @Column(name = "house_address")
    private String _address;

    /** The country where the house is located. */
    @Column(name = "house_country")
    private String _country;

    /** The zip code of the house. */
    @Column(name = "house_zipcode")
    private String _zipCode;

    /** The latitude coordinate of the house location. */
    @Column(name = "house_latitude")
    private double _latitude;

    /** The longitude coordinate of the house location. */
    @Column(name = "house_longitude")
    private double _longitude;

    /**
     * Constructs a HouseDataModel object based on a House entity.
     * @param house The House entity to create the data model from.
     */
    public HouseDataModel(House house) {
        _address = house.getHouseLocation().getAddress().toString();
        _houseId = house.identity().toString();
        _country = house.getHouseLocation().getZipCode().getCountry();
        _zipCode = house.getHouseLocation().getZipCode().toString();
        _latitude = house.getHouseLocation().getGPSCoordinates().getLatitude();
        _longitude = house.getHouseLocation().getGPSCoordinates().getLongitude();
    }

    /**
     * Default constructor for HouseDataModel with no parameters.
     */
    public HouseDataModel() {}

    /**
     * Converts a HouseDataModel object to a House domain entity.
     * @param factory The factory for creating House instances.
     * @param houseDataModel The HouseDataModel object to convert.
     * @return The House domain entity.
     */
    static public House toDomain(FactoryHouse factory, HouseDataModel houseDataModel) {
        HouseId houseId = new HouseId(houseDataModel._houseId);
        Address houseAddress = new Address(houseDataModel._address);
        ZipCode houseZipCode = new ZipCode(houseDataModel._country,houseDataModel._zipCode);
        Latitude houseLatitude = new Latitude(houseDataModel._latitude);
        Longitude houseLongitude = new Longitude(houseDataModel._longitude);
        GPSCoordinates houseGpsCoordinates = new GPSCoordinates(houseLatitude,houseLongitude);
        Location houseLocation = new Location(houseAddress,houseZipCode,houseGpsCoordinates);

        return factory.createHouse(houseId,houseLocation);
    }

    /**
     * Converts a list of HouseDataModel objects to a list of House domain entities.
     * @param factory The factory for creating House instances.
     * @param listDataModel The list of HouseDataModel objects to convert.
     * @return An iterable collection of House domain entities.
     */
    static public Iterable<House> toDomain(FactoryHouse factory, Iterable<HouseDataModel> listDataModel) {
        List<House> listDomain = new ArrayList<>();

        listDataModel.forEach( houseDataModel -> {
            House houseDomain = toDomain(factory, houseDataModel);
            listDomain.add(houseDomain);
        });

        return listDomain;
    }

    /**
     * Updates the current House object with information from another House object.
     * @param house The House object containing the updated information.
     * @return True if the update was successful, otherwise false.
     */
    public boolean updateFromDomain(House house)
    {
        this._address = house.getHouseLocation().getAddress().toString();
        this._zipCode = house.getHouseLocation().getZipCode().toString();
        this._houseId = house.identity().toString();
        this._country = house.getHouseLocation().getZipCode().getCountry();
        this._latitude = house.getHouseLocation().getGPSCoordinates().getLatitude();
        this._longitude = house.getHouseLocation().getGPSCoordinates().getLongitude();

        return true;
    }
}
