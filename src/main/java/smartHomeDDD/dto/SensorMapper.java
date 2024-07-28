package smartHomeDDD.dto;

import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.*;

/**
 * The SensorMapper class provides static methods for converting between Sensor domain objects and SensorDTO objects.
 */
public class SensorMapper {

    /**
     * Converts a sensor ID string to a SensorID object.
     *
     * @param sensorId the sensor ID string
     * @return the SensorID object
     */
    public static SensorID convertToSensorId(String sensorId) {
        return new SensorID(sensorId);
    }

    /**
     * Converts a sensor model ID string to a SensorModelID object.
     *
     * @param sensorModelId the sensor model ID string
     * @return the SensorModelID object
     */
    public static SensorModelID convertToSensorModelId(String sensorModelId) {
        return new SensorModelID(sensorModelId);
    }

    /**
     * Transforms a Sensor object into a SensorDTO object.
     * If the input Sensor object is null, the method will return null.
     *
     * @param sensor The Sensor object to be transformed into a SensorDTO object.
     * @return A SensorDTO object containing the properties of the input Sensor object or null if the input Sensor object is null.
     */
    public static SensorDTO convertSensorToDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.identity().toString(),
                sensor.getDeviceID().toString(),
                sensor.getSensorModelID().toString()
        );
    }

    /**
     * Transforms a SensorDTO object into a Sensor object.
     * @param sensor The SensorDTO object to be transformed into a Sensor object.
     * @return A Sensor object containing the properties of the input SensorDTO object.
     */
    public static SensorIDExitWebDTO convertSensorIDToExitWebDTO(Sensor sensor) {
        if (sensor == null)
            return null;
        return new SensorIDExitWebDTO(sensor.identity().toString());
    }

    /**
     * Transforms a Sensor object into a SensorExitWebDTO object.
     * @param sensor The Sensor object to be transformed into a SensorExitWebDTO object.
     * @return A SensorExitWebDTO object containing the properties of the input Sensor object.
     */

    public static SensorExitWebDTO convertSensorToExitWebDTO(Sensor sensor) {
        String sensorID = sensor.identity().toString();
        String deviceID = sensor.getDeviceID().toString();
        String sensorModelID = sensor.getSensorModelID().toString();
        return new SensorExitWebDTO(sensorID, deviceID, sensorModelID);
    }
}