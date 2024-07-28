package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

import java.util.Objects;

/**
 * This class represents a value of a PC500W sensor in a smart home system.
 * It implements the Value interface and provides a concrete implementation for the toString method.
 * The value is represented as a Double.
 *
 */
public class PC500WValue implements Value, ValueObject {

    /**
     * The value of the PC500W sensor.
     */
    private final Double _powerConsumptionValue;

    /**
     * Constructs a new PC500WValue with the specified value.
     *
     * @param value The value of the PC500W sensor. It should be a non-negative number.
     * @throws IllegalArgumentException if the provided value is negative.
     */
    public PC500WValue(Double value) throws IllegalArgumentException {
        if( value < 0 )
            throw new IllegalArgumentException("Invalid value. Value cannot be negative.");

        else
            this._powerConsumptionValue = value;
    }

    /**
     * Method to convert the value of the PC500WValue object to a String.
     * @return returns a String equivalent to the _nValue value.
     */
    public String toString() {
        return this._powerConsumptionValue + "";
    }

    /**
     * Returns the value of the PC500W sensor.
     * @return the value of the PC500W sensor
     */
    public double get_Value() {
        return _powerConsumptionValue;
    }

    /**
     * Checks if the given object is equal to this PC500WValue object.
     * It checks if the object is of the same class and if their _powerConsumptionValue attributes are equal.
     * @param o the object to compare with
     * @return true if the given object is equal to this PC500WValue object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PC500WValue that = (PC500WValue) o;
        return Objects.equals(_powerConsumptionValue, that._powerConsumptionValue);
    }

    /**
     * Returns the hash code of this PC500WValue object.
     * @return the hash code of this PC500WValue object
     */
    @Override
    public int hashCode() {
        return Objects.hash(_powerConsumptionValue);
    }
}
