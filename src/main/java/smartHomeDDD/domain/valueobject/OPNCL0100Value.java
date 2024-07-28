package smartHomeDDD.domain.valueobject;


import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * Implementation of the Value interface representing the value of an OPNCL0100 sensor.
 */
public class OPNCL0100Value implements Value, ValueObject {

    /**
     * The actual value of the OPNCL0100 actuator.
     */
    private final int _dValue;

    /**
     * Constructs an OPNCL0100Value with a specified integer.
     * It throws an InstantiationException if the provided integer is not within the range of minValue and maxValue.
     *
     * @param dValue The integer to initialize the value with.
     * @throws IllegalArgumentException If the provided integer is not within the range of minValue and maxValue.
     */
    public OPNCL0100Value(int dValue) throws IllegalArgumentException {
        if ( !areConstructorArgumentsValid(dValue) ) {
            throw new IllegalArgumentException("Value out of range");
        }

        this._dValue = dValue;

    }
    /**
     * Checks if the provided constructor argument is valid.
     * The argument is considered valid if it is within a specified range.
     *
     * @param dValue the constructor argument to validate.
     * @return true if the argument is within the range of minValue and maxValue (inclusive), false otherwise.
     */
    private boolean areConstructorArgumentsValid(int dValue) {
        int maxValue = 100;
        int minValue = 0;
        return minValue <= dValue && maxValue >= dValue;
    }

    /**
     * Returns the string representation of this value.
     *
     * @return The string representation of this value.
     */
    public String toString()
    {
        return this._dValue + "";
    }

    /**
     * Retrieves the integer representation of this value.
     *
     * @return The integer representation of this value.
     */
    public int getValue()
    {
        return this._dValue;
    }
}
