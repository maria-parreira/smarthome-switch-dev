package smartHomeDDD.domain.sensorReading;

import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;

import java.sql.Timestamp;


/**
 * ImplFactorySensorReading is an implementation of the FactorySensorReading interface.
 * It provides a concrete implementation for creating SensorReading objects.
 */
@Component
public class ImplFactorySensorReading implements FactorySensorReading
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
    @Override
    public SensorReading createSensorReading(SensorReadingID sensorReadingID, Reading reading, DeviceId deviceID, SensorID sensorID, Timestamp timeStamp)
    {
        return new SensorReading(sensorReadingID, reading, deviceID, sensorID, timeStamp);
    }

}


