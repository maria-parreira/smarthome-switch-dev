package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * Value object for TSY01Value.
 */
public class TSY01Value implements Value, ValueObject {

    /**
     * The value of the TSY01Value.
     */
    private final Integer _value;

    /**
     * Constructor for TSY01Value.
     * @param value The value of the TSY01Value.
     * @throws IllegalArgumentException If the value is out of range.
     */

    public TSY01Value(Integer value) throws IllegalArgumentException {
        if ( !areConstructorArgumentsValid(value) ) {
            throw new IllegalArgumentException("Value out of range");
        }
        this._value = value;
    }

    /**
     * Checks if the constructor arguments are valid.
     * @param value The value of the TSY01Value.
     * @return True if the arguments are valid, false otherwise.
     */
    private boolean areConstructorArgumentsValid(Integer value) {
        int MAX_VALUE = 100;
        Integer MIN_VALUE = 0;
        if (value < MIN_VALUE || value > MAX_VALUE) {
            return false;
        }
        return true;
    }

    /**
     * Returns the value of the TSY01Value in a string format.
     */
    public String toString()
    {
        return this._value + "";
    }
}
