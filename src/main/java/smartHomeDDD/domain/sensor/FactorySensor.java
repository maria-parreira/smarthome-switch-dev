/**
 * The FactorySensor interface outlines the requirements for a factory class
 * that constructs Sensor objects. The factory class is tasked with creating
 * Sensor objects using the given parameters.
 */
package smartHomeDDD.domain.sensor;

import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

public interface FactorySensor {

    /**
     * Constructs a Sensor object using the provided parameters.
     *
     * @param deviceId        The unique identifier for the device to which
     *                        the sensor will be associated.
     * @param sensorModelID   The unique identifier for the sensor model.
     * @param sensorID        The unique identifier for the sensor.
     * @return A Sensor object constructed with the provided parameters.
     */
    Sensor createSensor(DeviceId deviceId, SensorModelID sensorModelID, SensorID sensorID);

}
