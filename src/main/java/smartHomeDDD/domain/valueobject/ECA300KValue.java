package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;

/**
 * This class represents a value, specifically the ECA300K sensor,
 * designed to provide the electric energy consumption within a period and per hour.
 * It implements the Value and ValueObject interfaces.
 */
public class ECA300KValue implements Value, ValueObject {

    /**
     * The value of the EEC300K sensor.
     */
    private final Double _electricEnergyConsumption;

    /**
     * Constructs a new ECA300KValue with the specified value.
     * @param value The value of the ECA300K sensor. It should be a non-negative number.
     * @throws IllegalArgumentException if the provided value is negative.
     */
    public ECA300KValue(Double value){
        if( value < 0 )
            throw new IllegalArgumentException("The measurement value is not valid: " + value);

        else
            this._electricEnergyConsumption = value;
    }

    /**
     * Method to convert the value of the ECA300KValue object to a String.
     * @return returns a String equivalent to the _nValue value.
     */
    public String toString() {
        return "Electrical Energy Consumption: " + this._electricEnergyConsumption;
    }
}
