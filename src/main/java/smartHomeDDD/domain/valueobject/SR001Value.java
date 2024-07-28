package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

public class SR001Value implements Value, ValueObject {

    /**
     * The Solar Irradiance value of the SR001 sensor.
     */
    private final Double _solarIrradianceValue;

    /**
     * Constructs a new instance of SR001Value with the specified value.
     * It represents the solar irradiance value and its value is measured in Watts per square meter.
     *
     * @param value The value to be set for the SR001 sensor.
     * @throws IllegalArgumentException If the value is null or below 0.
     */
    public SR001Value(Double value) {
        if (areConstructorArgumentsValid(value)) {
            this._solarIrradianceValue = value;
        } else {
            throw new IllegalArgumentException("The measurement value is not valid: " + value);
        }
    }

    /**
     * Checks if the provided value is valid.
     * A value is considered not valid if it is null or if it is below 0.
     *
     * @param value The value to be checked.
     * @return True if the value is valid, otherwise return false.
     */
    private boolean areConstructorArgumentsValid(Double value) {
        if (value == null || value < 0) {
            return false;
        }
        return true;
    }

    /**
     * @return A string representation of the Capacity value.
     */
    public String toString() {
        return this._solarIrradianceValue + "";
    }
}
