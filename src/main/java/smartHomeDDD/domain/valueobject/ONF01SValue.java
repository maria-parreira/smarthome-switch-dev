package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * Implementation of the Value interface representing the value of an ONF01S sensor.
 */
public class ONF01SValue implements Value, ValueObject {

    /**
     * The on/off value of the ONF01S sensor.
     */
    private final String _onOffValue;

    /**
     * Constructs a new instance of ONF01SValue with the specified value.
     * If the value is null, empty, blank or not 'ON' or 'OFF' (case-insensitive), an IllegalArgumentException is thrown.
     *
     * @param value The value to be set for the ONF01S sensor.
     * @throws IllegalArgumentException If the provided value is invalid
     */
    public ONF01SValue(String value) throws IllegalArgumentException {

        if (areConstructorArgumentsValid(value))
            this._onOffValue = value.toUpperCase();
        else
            throw new IllegalArgumentException("Invalid value for ONF01SValue. Must be 'ON' or 'OFF'.");
    }

    /**
     * Validates the constructor arguments.
     *
     * @param onOffValue the value to be validated
     * @return true if the value is valid, otherwise false
     */
    private boolean areConstructorArgumentsValid(String onOffValue) {

        if(onOffValue == null || onOffValue.isEmpty() || onOffValue.isBlank())
            return false;
        if (!(onOffValue.equalsIgnoreCase("ON") || onOffValue.equalsIgnoreCase("OFF")))
            return false;
        else
            return true;
    }

    /**
     * Returns a string representation of the on/off value of the ONF01S sensor.
     *
     * @return A string representation of the on/off value.
     */
    public String toString() {
        return this._onOffValue;
    }
}
