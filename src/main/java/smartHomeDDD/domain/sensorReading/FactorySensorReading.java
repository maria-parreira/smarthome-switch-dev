package smartHomeDDD.domain.sensorReading;

import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;

import java.sql.Timestamp;

/**
 * FactorySensorReading is an interface for a factory that creates SensorReading objects.
 * This factory pattern allows for the encapsulation of SensorReading object creation.
 */
public interface FactorySensorReading
{
    /**
     * Creates a SensorReading object with the provided parameters.
     *
     * @param sensorReadingID The unique identifier of the sensor reading.
     * @param reading The reading value of the sensor.
     * @param deviceID The unique identifier of the device associated with the sensor reading.
     * @param sensorID The unique identifier of the sensor that made the reading.
     * @param timeStamp The timestamp of when the sensor reading was made.
     * @return A SensorReading object.
     */
    SensorReading createSensorReading(SensorReadingID sensorReadingID, Reading reading, DeviceId deviceID, SensorID sensorID, Timestamp timeStamp);
}
