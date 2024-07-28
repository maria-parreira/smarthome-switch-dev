package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

import java.time.LocalTime;
/**
 * This class represents a value, specifically the SUNRISE407 sensor, designed to provide the sunrise time.
 * It implements the Value and ValueObject interfaces.
 */

public class SUNRISE407Value implements Value, ValueObject{

    /**
     * The value associated with the SUNRISE407 sensor.
     */
    private final LocalTime _iValue;

    /**
     * Construct of the Value object
     * @param instant The value associated with the Value Object
     * @throws IllegalArgumentException exception thrown if the arguments are invalid
     */
    public SUNRISE407Value(LocalTime instant) throws IllegalArgumentException {
        if (!areConstructorArgumentsValid(instant)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this._iValue = instant;
    }
    

    /**
     * Validation of the SUNRISE407Value arguments
     * @param instant The value associated with the Value Object
     * @return returns false is the arguments are invalid (null), and true if they are valid
     */
    private boolean areConstructorArgumentsValid(LocalTime instant) {
        return instant != null;
    }

    /**
     * Transposition of the iValue of the SUNRISE407Value object to String
     * @return returns a String equivalent to the iValue value.
     */
    public String toString() {
        return this._iValue + "";
    }
}
