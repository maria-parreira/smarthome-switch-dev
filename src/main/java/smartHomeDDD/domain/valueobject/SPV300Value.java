package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * The SIV280Value class represents the value of the SIV280 actuator.
 * It implements the Value interface and the ValueObject interface.
 */

public class SPV300Value implements Value, ValueObject {

    /**
     * The actual value of the SPV300 actuator.
     */
    private final double _nValue;

    /**
     * The minimum allowed value for the SPV300 actuator.
     */
    private static final double _minValue = 0;

    /**
     * The maximum allowed value for the SPV300 actuator.
     */
    private static final double _maxValue = 100;

    /**
     * Constructs a new SPV300Value with the specified value.
     * The value is rounded to the specified precision and checked to be within the specified range.
     * If the value is not within the range, an InstantiationException is thrown.
     *
     * @param nValue the value to be set
     */
    public SPV300Value(double nValue) {
        if (!areConstructorArgumentsValid (nValue)) {
            throw new IllegalArgumentException("Value is out of range");
        }
        else {
            int _precision = 3;
            double factor = Math.pow(10, _precision);
            this._nValue = Math.round(nValue * factor) / factor;
        }
    }

    /**
     * Checks if the provided value is within the specified range.
     *
     * @param nValue the value to be checked
     * @return true if the value is within the range, false otherwise
     */
    private boolean areConstructorArgumentsValid(double nValue) {
        return !(nValue < _minValue) && !(nValue > _maxValue);
    }

    /**
     * Checks if the provided value is equal to SPV300Value.
     *
     * @param object the value to be checked
     * @return true if the value is the same, false otherwise
     */
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof SPV300Value spv300Value && (this._nValue == spv300Value._nValue)) {
                return true;

        }
        return false;
    }

    /**
     * Returns a string representation of the SPV300Value.
     * The string representation is the string of the value.
     *
     * @return a string representation of the SPV300Value
     */
    public String toString()
    {
        return this._nValue + "";
    }

    /**
     * Returns the value of the SPV300Value.
     *
     * @return the value of the SPV300Value
     */
    public double getValue()
    {
        return this._nValue;
    }

    /**
     * Returns the hash code of this SPV300Value.
     *
     * @return the hash code of this SPV300Value
     */
    @Override
    public int hashCode() {
        return Double.hashCode(_nValue);
    }
}
