/**
 * The SensorValue interface extends the Sensor interface.
 * It provides a method to get the value of a Sensor.
 */
package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.Value;

public interface SensorValue extends Sensor {

    /**
     * Retrieves the value of an object that implements
     * the ValueTypeInstant interface.
     *
     * @return The value of the object.
     */
    Value getValue();
}
