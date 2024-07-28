package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * The SIV280Value class represents the value of the SIV280 actuator.
 * It implements the Value interface and the ValueObject interface.
 */

public class SIV280Value implements Value, ValueObject {

    /**
     * The actual value of the SIV280 actuator.
     */
    private final int _nValue;

    /**
     * The minimum allowed value for the SIV280 actuator.
     */
    private static final int _minValue = -50;

    /**
     * The maximum allowed value for the SIV280 actuator.
     */
    private static final int _maxValue = 50;

    /**
     * Constructor for the SIV280Value class.
     *
     * @param nValue The value to be set for this SIV280Value.
     */
    public SIV280Value(int nValue) {
        if (!areConstructorArgumentsValid(nValue)) {
            throw new IllegalArgumentException("Value is out of range");
        } else {
            this._nValue = nValue;
        }
    }

    /**
     * Checks if the provided value is within the specified range.
     *
     * @param nValue the value to be checked
     * @return true if the value is within the range, false otherwise
     */

    private boolean areConstructorArgumentsValid(int nValue) {
        return nValue >= _minValue && nValue <= _maxValue;
    }

    /**
     * Checks if the provided value is equal to SIV280Value.
     *
     * @param object the value to be checked
     * @return true if the value is the same, false otherwise
     */
    public boolean equals(Object object) {

        if (this == object)
            return true;

        return object instanceof SIV280Value siv280Value && (this._nValue == siv280Value._nValue);
    }

    /**
     * Returns a string representation of the SIV280Value.
     * @return A string representation of the value.
     */
    public String toString()
    {
        return this._nValue + "";
    }

    /**
     * Returns the value of this SIV280Value.
     * @return The value.
     */
    public int getValue()
    {
        return this._nValue;
    }

    /**
     * Returns the hash code of this SIV280Value.
     * @return The hash code of this SIV280Value.
     */
    public int hashCode() {
        return Integer.hashCode(_nValue);
    }
}
