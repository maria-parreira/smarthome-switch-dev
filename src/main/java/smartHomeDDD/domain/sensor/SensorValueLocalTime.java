/**
 * The SensorValueLocalTime interface extends the Value interface.
 * It provides a method to get the value of a sensor at a specific time.
 */
package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.Value;

import java.time.LocalTime;

public interface SensorValueLocalTime extends Sensor {

    /**
     * Returns the value of the sensor at the given time.
     *
     * @param dateTime the time to get the sensor value
     * @return the sensor value at the given time
     */
    Value getValue(LocalTime dateTime);
}
