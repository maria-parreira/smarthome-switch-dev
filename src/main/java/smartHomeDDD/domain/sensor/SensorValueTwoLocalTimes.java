/**
 * The SensorValueTwoLocalTimes interface extends the Value interface.
 * It provides a method to get the value of a sensor within a period.
 */
package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.Value;

import java.time.LocalTime;

public interface SensorValueTwoLocalTimes extends Sensor{

    /**
     * Returns the value of the sensor within a period.
     *
     * @param  startTime start time to get the sensor value
     * @param  endTime end time to get the sensor value
     * @return the sensor value at the given time
     */
    Value getValue(LocalTime startTime, LocalTime endTime);
}
