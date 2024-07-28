package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

import java.util.Objects;

/**
 * Represents a Latitude value object.
 */
public class Latitude implements ValueObject {
    /**
     * The value of the latitude.
     */
    private final double latitude;

    /**
     * Constructs a Latitude object with the given value.
     *
     * @param latitude The value of the latitude.
     * @throws IllegalArgumentException If the latitude is not between -90.0 and 90.0.
     */
    public Latitude(double latitude) {
        if (!isLatitudeValid(latitude)) {
            throw new IllegalArgumentException("Invalid latitude. Must be between -90.0 and 90.0.");
        }
        this.latitude = latitude;
    }

    /**
     * Returns the value of the latitude.
     *
     * @return The value of the latitude.
     */
    public double getValue() {
        return this.latitude;
    }

    /**
     * Checks if the given latitude is valid.
     *
     * @param latitude The latitude to check.
     * @return True if the latitude is between -90.0 and 90.0, false otherwise.
     */

    private boolean isLatitudeValid(double latitude) {
        return latitude >= -90.0 && latitude <= 90.0;
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
        if (object instanceof Latitude latitude1) {
            return Objects.equals(this.latitude, latitude1.latitude);
        }
        return false;
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(latitude);
    }
    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return String.valueOf(latitude);
    }
}