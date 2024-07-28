package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * Represents a GPSCoordinates value object.
 */
public class GPSCoordinates implements ValueObject {
    /**
     * The latitude associated with the GPS coordinates.
     */
    private final Longitude longitude;
    private final Latitude latitude;


    /**
     * Constructs a GPSCoordinates object with the given latitude and longitude.
     *
     * @param latitude  The latitude of the GPS coordinates.
     * @param longitude The longitude of the GPS coordinates.
     */
    public GPSCoordinates(Latitude latitude, Longitude longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns the value of the latitude.
     *
     * @return The value of the latitude.
     */
    public double getLatitude() {
        return this.latitude.getValue();
    }

    /**
     * Returns the value of the longitude.
     *
     * @return The value of the longitude.
     */
    public double getLongitude() {
        return this.longitude.getValue();
    }

    /**
     * Checks if the GPS coordinates are valid.
     *
     * @return True if both latitude and longitude are not null, false otherwise.
     */
    public boolean areGPSCoordinatesValid() {
        return this.latitude != null && this.longitude != null;
    }


    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */

    @Override
    public String toString() {
        return "GPSCoordinates{" +
                "latitude=" + latitude.getValue() +
                ", longitude=" + longitude.getValue() +
                '}';
    }
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof GPSCoordinates gpsCoordinates) {

            return this.latitude.equals(gpsCoordinates.latitude) &&
                    this.longitude.equals(gpsCoordinates.longitude);
        }
        return false;
    }

    /**
     * Returns the hash code of the GPSCoordinates.
     *
     * @return The hash code of the GPSCoordinates.
     */
    @Override
    public int hashCode() {
        return this.latitude.hashCode() + this.longitude.hashCode();
    }
}
