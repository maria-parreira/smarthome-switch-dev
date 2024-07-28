package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

public class DP22CValue implements Value, ValueObject {

    /**
     * The temperature value of the DP22C sensor.
     */
    private final double _temperatureValue;

    /**
     * Constructs a new instance of DP22CValue with the specified value.
     * If the value is null or not within the range of -273.0 to 300.0, an InstantiationException is thrown.
     *
     * @param value The value to be set for the DP22C sensor.
     * @throws IllegalArgumentException If the value is null or not within the range of -273.0 to 300.0.
     */
    public DP22CValue(Double value) {
        if (areConstructorArgumentsValid(value)) {
            this._temperatureValue = value;
        } else {
            throw new IllegalArgumentException("The measurement value is not valid: " + value);
        }
    }

    /**
     * Checks if the provided value is valid.
     * A value is considered valid if it is not null and is within the range of -273.0 to 300.0.
     *
     * @param value The value to be checked.
     * @return True if the value is valid, false otherwise.
     */
    private boolean areConstructorArgumentsValid(Double value) {
        if (value == null || value <= -273.0 || value >= 300.0) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the temperature value of the DP22C sensor.
     *
     * @return A string representation of the temperature value.
     */
    public String toString() {
        return this._temperatureValue + "";
    }
}
