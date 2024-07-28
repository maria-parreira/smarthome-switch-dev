package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ECA300KValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an ECA300K sensor. This sensor measures electric energy consumption.
 * Implements the SensorValueTwoLocalTimes, and AggregateRoot interfaces.
 */
public class ECA300K implements SensorValueTwoLocalTimes, AggregateRoot<SensorID> {

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
     * The data readings from the sensor. They are represented as a map where the key
     * corresponds to the reading number and the value corresponds to the reading value.
     */
    private final Map<LocalTime, Double> _readings;

    /**
     * Constructs an EEC300K sensor with specified parameters.
     *
     * @param deviceId      The unique identifier of the device.
     * @param sensorModelID The model of the sensor.
     * @param sensorID      The unique identifier of the sensor.
     * @throws IllegalArgumentException if any of the arguments are null.
     */
    public ECA300K(DeviceId deviceId, SensorModelID sensorModelID, SensorID sensorID) {
            this._deviceID = deviceId;
            this._sensorModelID = sensorModelID;
            this._sensorID = sensorID;
            this._readings = setData();
    }

    /**
     * Initializes the data readings for the sensor.
     *
     * @return The data readings for the sensor.
     */
    public Map<LocalTime,Double> setData() {
        Map<LocalTime, Double> readings = new HashMap<>();
        readings.put(LocalTime.of(9, 0), 10.0);
        readings.put(LocalTime.of(10, 15), 20.0);
        readings.put(LocalTime.of(11, 30), 30.0);
        readings.put(LocalTime.of(12, 45), 40.0);
        return readings;
    }

    /**
     * Retrieves the readings within a specified period.
     *
     * @param start The start time of the period.
     * @param end   The end time of the period.
     * @return A list of readings within the specified period.
     */
    private List<Double> readingsWithinPeriod(LocalTime start, LocalTime end) {
        List<Double> readingsWithinPeriod = new ArrayList<>();
        this._readings.entrySet().stream()
                .filter(entry -> entry.getKey().isAfter(start) && entry.getKey().isBefore(end))
                .forEach(entry -> readingsWithinPeriod.add(entry.getValue()));
        return readingsWithinPeriod;
    }

    /**
     * Retrieves the average value of readings per hour within the specified time period.
     * This method calculates the average value of readings per hour between the given start and end times.
     * It retrieves the readings within the specified period, calculates the sum of readings, and divides
     * it by the duration in hours to obtain the average value. The result is encapsulated in an instance
     * of the appropriate subclass of the Value class (e.g., ECA300KValue).
     *
     * @param start The start time of the period.
     * @param end The end time of the period.
     * @return An instance of the Value class representing the average value of readings per hour
     * within the specified period.
     */
    private Value readingsPerHour(LocalTime start, LocalTime end) {
        List<Double> valuesList = readingsWithinPeriod(start, end);
        if(valuesList.isEmpty()){
            return new ECA300KValue(0.0);
        }
        double sumReadings = 0;
        for (double reading : valuesList) {
            sumReadings += reading;
        }
        long hours = Duration.between(start, end).toHours();
        double readingsPerHour = sumReadings / hours;
        return new ECA300KValue(readingsPerHour);
    }

    /**
     * Retrieves the value of the sensor within the specified time period.
     * This method delegates the retrieval of the sensor's value to the getReadingsPerHour method,
     * which calculates and returns the average value of readings per hour within the specified time period.
     *
     * @param startTime The start time of the period.
     * @param endTime The end time of the period.
     * @return An instance of the Value class representing the sensor's value within the specified period.
     */
    @Override
    public Value getValue(LocalTime startTime, LocalTime endTime) {
        return readingsPerHour(startTime, endTime);
    }

    /**
     * Retrieves the unique identifier of the sensor.
     *
     * @return The unique identifier of the sensor.
     */
    @Override
    public SensorID identity() {return this._sensorID;}

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
     * @return true if this sensor is the same as the specified object, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof ECA300K sensor) {
            return Objects.equals(this._readings, sensor._readings) &&
                    Objects.equals(this._deviceID, sensor._deviceID) &&
                    Objects.equals(this._sensorModelID, sensor._sensorModelID)&&
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
        if (object instanceof ECA300K eca300k) {
            return this._sensorID.equals(eca300k._sensorID);
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
