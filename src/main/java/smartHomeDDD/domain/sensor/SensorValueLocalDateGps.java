/**
 * The SensorValueLocalDateGps interface extends the Value interface.
 * It provides a method to get the value of a sensor at a specific
 * date and GPS coordinates.
 */
package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.GPSCoordinates;

import java.time.LocalDate;

public interface SensorValueLocalDateGps extends Sensor {

    /**
     * Returns the value of the sensor at the given date and GPS coordinates.
     *
     * @param date the date to get the sensor value
     * @param gps the GPS coordinates to get the sensor value
     * @return the sensor value at the given date and GPS coordinates
     */
    Value getValue(LocalDate date, GPSCoordinates gps);
}
