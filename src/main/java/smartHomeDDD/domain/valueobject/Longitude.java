package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents a Longitude value object.
 */
public class Longitude implements ValueObject {
    /**
     * The value of the longitude.
     */
    private final double longitude;


    /**
     * Constructs a Longitude object with the given value.
     *
     * @param longitude The value of the longitude.
     * @throws IllegalArgumentException If the longitude is not between -180.0 and 180.0.
     */
    public Longitude(double longitude) {
        if (!isLongitudeValid(longitude)) {
            throw new IllegalArgumentException("Invalid longitude. Must be between -180.0 and 180.0.");
        }
        this.longitude = longitude;
    }

    /**
     * Returns the value of the longitude.
     *
     * @return The value of the longitude.
     */
    public double getValue() {
        return this.longitude;
    }

    /**
     * Checks if the given longitude is valid.
     *
     * @param longitude The longitude to check.
     * @return True if the longitude is between -180.0 and 180.0, false otherwise.
     */
    private boolean isLongitudeValid(double longitude) {
        return longitude >= -180.0 && longitude <= 180.0;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param object The reference object with which to compare.
     * @return True if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof Longitude longitude1) {
            return Objects.equals(this.longitude, longitude1.longitude);
        }
        return false;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(longitude);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return String.valueOf(longitude);
    }
}
