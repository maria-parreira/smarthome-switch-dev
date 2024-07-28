package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * The ONF01AValue class represents the value of the ONF01A actuator in the SmartHome system.
 * It implements the Value interface.
 * The value is a string that can be either 'ON' or 'OFF'.
 */
public class ONF01AValue implements Value, ValueObject {

    /**
     * The actual value of the ONF01A actuator, representing its status.
     */
    private final String _sValue;

    /**
     * Initializes a new instance of the ONF01AValue class.
     *
     * @param sValue The string value representing the state ('ON' or 'OFF').
     * @throws IllegalArgumentException If the provided value is not 'ON' or 'OFF'.
     */
    public ONF01AValue(String sValue) throws IllegalArgumentException {
        if (!areConstructorArgumentsValid(sValue))
            throw new IllegalArgumentException("Invalid value for ONF01AValue. Must be 'ON' or 'OFF'.");
        this._sValue = sValue;
    }

    /**
     * Validates the constructor arguments.
     *
     * @param sValue The string value representing the state ('ON' or 'OFF').
     *               The value is case-insensitive.
     *               The value must be 'ON' or 'OFF'.
     *               The value must not be null, empty or blank.
     * @return True if the constructor arguments are valid, otherwise false.
     */
    private boolean areConstructorArgumentsValid(String sValue) {
        if (sValue.isEmpty() || sValue.isBlank())
            return false;
        return sValue.equalsIgnoreCase("ON") || sValue.equalsIgnoreCase("OFF");
    }

    /**
     * Returns a string representation of this ONF01AValue.
     *
     * @return The string representation of the value.
     */
    public String toString() {
        return this._sValue;
    }

    public String getValue() {
        return this._sValue;
    }
}
