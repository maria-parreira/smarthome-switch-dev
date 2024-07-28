package smartHomeDDD.domain.valueobject;
import smartHomeDDD.ddd.ValueObject;

/**
 * Represents a Location value object.
 */
public class Location implements ValueObject {

    /**
     * The address associated with the location.
     */
    private final Address _address;

    /**
     * The zip code associated with the location.
     */
    private final ZipCode _zipCode;

    /**
     * The gps associated with the location.
     */
    private final GPSCoordinates _gpsCoordinates;


    /**
     * Constructs a Location object with the given address, zip code, and GPS coordinates.
     *
     * @param address        The address of the location.
     * @param zipCode        The zip code of the location.
     * @param gpsCoordinates The gps code of the location.
     */
    public Location(Address address, ZipCode zipCode, GPSCoordinates gpsCoordinates) {
        this._address = address;
        this._zipCode = zipCode;
        this._gpsCoordinates=gpsCoordinates;
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object The reference object with which to compare.
     * @return True if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof Location location) {

            return this._address.equals(location._address) && this._zipCode.equals(location._zipCode) && this._gpsCoordinates.equals(location._gpsCoordinates);
        }
        return false;
    }

    /**
     * Returns a string representation of the Location object.
     *
     * @return The string representation containing the address, zip code and gps.
     */
    @Override
    public String toString() {
        return this._address + " " + this._zipCode + " " + this._gpsCoordinates;
    }

    /**
     * Retrieves the address associated with this location.
     * @return The Address object representing the address of this location.
     */
    public Address getAddress() {
        return this._address;
    }

    /**
     * Retrieves the zip code associated with this location.
     * @return The ZipCode object representing the zip code of this location.
     */
    public ZipCode getZipCode(){
        return this._zipCode;
    }

    /**
     * Retrieves the GPS coordinates associated with this location.
     * @return The GPSCoordinates object representing the GPS coordinates of this location.
     */
    public GPSCoordinates getGPSCoordinates(){
        return this._gpsCoordinates;
    }

    /** Returns the hash code value for this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return _address.hashCode() + _zipCode.hashCode() + _gpsCoordinates.hashCode();
    }
}
