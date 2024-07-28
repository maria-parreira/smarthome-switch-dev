package smartHomeDDD.domain.sensor;
import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.AVPC500WValue;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an APC500W sensor. This sensor measures the average power consumption.
 * Implements the SensorValuerTwoLocalTimes, and AggregateRoot interfaces.
 */
public class AVPC500W implements SensorValueTwoLocalTimes, AggregateRoot<SensorID>  {

    /**
     * The unique identifier of the sensor.
     */
    private final SensorID _sensorID;

    /**
     * The unique identifier of the device which the sensor belongs to.
     */
    private final DeviceId _deviceID;

    /**
     * The model of the actuator.
     */
    private final SensorModelID _sensorModelID;

    /**
     * A map that holds the sensor data. The key is a LocalTime object
     * indicating the time of the reading, and the value is a Double object
     * representing the reading at that time.
     */
    private final Map<LocalTime, Double> _readings;

    /**
     * Constructs an APC500W sensor with the specified parameters.
     *
     * @param deviceId      The unique identifier of the device.
     * @param sensorModelID The model of the sensor.
     * @param sensorID      The unique identifier of the sensor.
     * @throws IllegalArgumentException if any of the arguments are invalid.
     */
    public AVPC500W(DeviceId deviceId,
                    SensorModelID sensorModelID,
                    SensorID sensorID) {

            this._deviceID = deviceId;
            this._sensorModelID = sensorModelID;
            this._sensorID = sensorID;
            this._readings = setData();
    }

    /**
     * Initializes and returns a map of data readings for the APC500W sensor.
     * The readings are pre-defined for demonstration purposes.
     *
     * @return A map containing the pre-defined data readings for the sensor.
     */
    private Map<LocalTime, Double> setData() {
        Map<LocalTime, Double> readings = new HashMap<>();
        readings.put(LocalTime.of(3, 0), 100.0);
        readings.put(LocalTime.of(3, 10), 105.0);
        readings.put(LocalTime.of(12, 30), 150.5);
        readings.put(LocalTime.of(15, 15), 200.0);
        readings.put(LocalTime.of(18, 2), 300.0);
        return readings;
    }

    /**
     * Retrieves the readings within the specified period.
     *
     * @param start The start time of the period.
     * @param end   The end time of the period.
     * @return A list containing the readings within the specified period.
     */
    private List<Double> readingsWithinPeriod(LocalTime start, LocalTime end) {
        List<Double> readingsWithinPeriod = new ArrayList<>();
        this._readings.entrySet().stream()
                .filter(entry -> entry.getKey().isAfter(start) && entry.getKey().isBefore(end))
                .forEach(entry -> readingsWithinPeriod.add(entry.getValue()));
        return readingsWithinPeriod;
    }

    /**
     * Calculates the average power consumption of the sensor within the specified period.
     *
     * @param start The start time of the period.
     * @param end   The end time of the period.
     * @return The average power consumption value calculated for the specified period.
     */

    private AVPC500WValue averagePowerConsumption(LocalTime start, LocalTime end){
        List<Double> valuesList = readingsWithinPeriod(start,end);
        double sumReadings = 0;
        for (double reading : valuesList) {
            sumReadings += reading;
        }
        double averageReadings = 0;
        if (!valuesList.isEmpty()) {
            averageReadings = sumReadings / valuesList.size();
        }
        return new AVPC500WValue(averageReadings);
    }

    /**
     * Returns the value of the sensor.
     */
    @Override
    public Value getValue(LocalTime startTime, LocalTime endTime) {
        return averagePowerConsumption(startTime,endTime);
    }

    /**
     * Retrieves the unique identity of the sensor.
     * This method returns the unique identity (SensorID) of the sensor.
     *
     * @return The unique identity (SensorID) of the sensor.
     */
    @Override
    public SensorID identity() {
        return this._sensorID;
    }

    /**
     * Retrieves the DeviceId associated with the sensor.
     * This method returns the DeviceId associated with the sensor.
     *
     * @return The DeviceId associated with the sensor.
     */
    @Override
    public DeviceId getDeviceID() {
        return this._deviceID;
    }

    /**
     * Retrieves the SensorModelID associated with the sensor.
     * This method returns the SensorModelID associated with the sensor.
     *
     * @return The SensorModelID associated with the sensor.
     */
    @Override
    public SensorModelID getSensorModelID() {
        return this._sensorModelID;
    }

    /**
     * Checks if this sensor is the same as the specified object.
     *
     * @param object The object to compare with.
     * @return true if the sensor is the same as the specified object, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof AVPC500W sensor) {
            return Objects.equals(this._readings, sensor._readings) &&
                    Objects.equals(this._deviceID, sensor._deviceID) &&
                    Objects.equals(this._sensorModelID, sensor._sensorModelID) &&
                    equals(object);
        }
        return false;
    }

    /**
     * Compares the current object with the object passed as a parameter.
     * Returns true if the objects' IDs are the same, false otherwise.
     *
     * @param object The object to compare with.
     */
    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object instanceof AVPC500W avpc500w) {
            return this._sensorID.equals(avpc500w._sensorID);
        }
        return false;
    }

    /**
     * Returns the hash code of the sensor.
     *
     * @return The hash code of the sensor.
     */
    @Override
    public int hashCode() {
        return Objects.hash(_sensorID);
    }
}
