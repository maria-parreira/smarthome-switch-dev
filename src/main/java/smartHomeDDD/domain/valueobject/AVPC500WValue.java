package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.ddd.ValueObject;
/**
 * This class represents a value, specifically the AVPC500WValue sensor, designed to provide the average energy consumption.
 * It implements the Value and ValueObject interfaces.
 */
public class AVPC500WValue implements Value, ValueObject {

        /**
         * The value of the APC500W sensor.
         */
        private final Double _averagePowerConsumption;

        /**
         * Constructs a new AVPC500WValue with the specified value.
         *
         * @param value The value of the AVPC500W sensor. It should be a non-negative number.
         * @throws IllegalArgumentException if the provided value is negative.
         */
        public AVPC500WValue(Double value){
            if( value < 0 )
                throw new IllegalArgumentException("The measurement value is not valid: " + value);

            else
                this._averagePowerConsumption = value;
        }

        /**
         * Method to convert the value of the AVPC500WValue object to a String.
         * @return returns a String equivalent to the _nValue value.
         */
        public String toString() {
            return "Average Energy Consumption: " + this._averagePowerConsumption;
        }
}
