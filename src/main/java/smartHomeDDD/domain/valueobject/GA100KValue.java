package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * Value object for GA100KValue.
 */
public class GA100KValue implements Value, ValueObject {

    /**
     * The value of the GA100KValue.
     */
    private final Integer _temperatureValue;

    /**
     * Constructor for GA100KValue.
     * @param value The value of the GA100KValue.
     * @throws IllegalArgumentException If the value is null.
     */

    public GA100KValue(Integer value) throws IllegalArgumentException {
        if ( !areConstructorArgumentsValid(value) ) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        this._temperatureValue = value;
    }

    /**
     * Checks if the constructor arguments are valid.
     * @param value The value of the GA100KValue.
     * @return True if the arguments are valid, false otherwise.
     */
    private boolean areConstructorArgumentsValid(Integer value) {
        if (value == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns the value of the GA100KValue in a string format.
     *
     * @return The value of the GA100KValue in a string format.
     */
    public String toString()
    {
        return this._temperatureValue + "";
    }
}
