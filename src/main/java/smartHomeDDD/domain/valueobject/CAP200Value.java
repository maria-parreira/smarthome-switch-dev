package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * The CAP200Value class represents the value of the CAP200 sensor.
 * It is a Value Object that represents the measurement of the sensor.
 */
public class CAP200Value implements Value, ValueObject {

    /**
     * The temperature value of the CAP200 sensor.
     */
    private final Integer _capacityValue;

    /**
     * Constructs a new instance of CAP200Value with the specified value.
     * It measures the capacity vale in percentage.
     *  An InstantiationException is thrown if the capacity value is not between 0 and 100
     *
     * @param value The value to be set for the CAP200 sensor.
     * @throws IllegalArgumentException If the value is null or not within the range of 0 and 100.
     */
    public CAP200Value(Integer value) {
        if (areConstructorArgumentsValid(value)) {
            this._capacityValue = value;
        } else {
            throw new IllegalArgumentException("The measurement value is not valid: " + value);
        }
    }

    /**
     * Checks if the provided value is valid.
     * A value is considered valid if it is not null and is within the range of 0 and 100.
     *
     * @param value The value to be checked.
     * @return True if the value is valid, otherwise return false.
     */
    private boolean areConstructorArgumentsValid(Integer value){
        if (value == null || value < 0 || value > 100) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the value of the CAP200 sensor.
     * @return A string representation of the Capacity value.
     */
    public String toString() {
        return this._capacityValue + "";
    }
}
