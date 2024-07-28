package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


/**
 * IRepositorySensorReading is an interface for a repository that manages SensorReading objects.
 * It extends the Repository interface with SensorReadingID as the ID type and SensorReading as the entity type.
 */
public interface IRepositorySensorReading extends Repository<SensorReadingID, SensorReading>
{
    /**
     * Retrieves a list of SensorReading objects from a specific device within a given time period.
     *
     * @param deviceID The unique identifier of the device.
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @return A list of SensorReading objects.
     */
    List<SensorReading> getMeasurementsFromDeviceWithinPeriod(DeviceId deviceID, Timestamp start, Timestamp end);
    /**
     * Retrieves a list of SensorReading objects from a specific device within a given time period.
     *
     * @param deviceId The unique identifier of the device.
     * @param sensorIDIndoors The unique identifier of the sensor.
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @return A list of SensorReading objects.
     */

    List<SensorReading> getSensorReadingsBetweenTimestamp(DeviceId deviceId, SensorID sensorIDIndoors, Timestamp start, Timestamp end);

    /**
     * Retrieves the latest sensor reading for a specific sensor.
     *
     * @param sensorID The ID of the sensor for which the latest reading is to be retrieved.
     * @return The latest sensor reading for the specified sensor.
     */

    Optional<SensorReading> getLatestReadingFromSensor(SensorID sensorID);

    List<SensorReading> getSensorReadingsByDeviceId(DeviceId deviceId);

}





