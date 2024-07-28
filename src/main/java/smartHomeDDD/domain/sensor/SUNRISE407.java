package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SUNRISE407Value;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.GPSCoordinates;
import smartHomeDDD.domain.valueobject.Latitude;
import smartHomeDDD.domain.valueobject.Longitude;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * The SUNRISE407 class represents the SUNRISE407 sensor.
 * It measures the sunrise instant at a specific GPS location.
 * It implements the SensorValueLocalDateGps, and AggregateRoot interfaces.
 */
public class SUNRISE407 implements SensorValueLocalDateGps, AggregateRoot<SensorID> {

    /**
     * The value of the SUNRISE407 sensor.
     */
    private SUNRISE407Value value;

    /**
     * The unique identifier of the sensor.
     */
    private final SensorID sensorID;

    /**
     * The unique identifier of the device which the sensor belongs to.
     */
    private final DeviceId deviceID;

    /**
     * The model of the sensor.
     */
    private final SensorModelID sensorModelID;

    /**
     * A map that holds the sensor data. The key is a LocalDate object
     * indicating the date of the reading, and the value is a map of
     * GPSCoordinates and LocalTime.
     */
    private Map<LocalDate, Map<GPSCoordinates, LocalTime>> data;

    /**
     * Constructs a SUNRISE407 sensor with the specified parameters.
     *
     * @param deviceId      The unique identifier of the device.
     * @param sensorModelID The model of the sensor.
     * @param sensorID      The unique identifier of the sensor.
     */

    public SUNRISE407(DeviceId deviceId, SensorModelID sensorModelID, SensorID sensorID) {
        this.deviceID = deviceId;
        this.sensorModelID = sensorModelID;
        this.sensorID = sensorID;
        this.data = setData();
    }

    /**
     * Generates sample data for each date in the specified chosen year (2024),
     * associating each date with GPS coordinates and local time.
     *
     * @return A map where each date is associated with a map of GPS coordinates and
     * local time.
     */
    public Map<LocalDate, Map<GPSCoordinates, LocalTime>> setData() {
        Map<LocalDate, Map<GPSCoordinates, LocalTime>> data = new HashMap<>();
        Latitude lat = new Latitude(50.0);
        Longitude lon = new Longitude(110.0);
        GPSCoordinates gps = new GPSCoordinates(lat, lon);
        int year = 2024;
        IntStream.rangeClosed(1, 12).forEach(month -> {
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();
            IntStream.rangeClosed(1, daysInMonth).forEach(day -> {
                LocalDate currentDate = LocalDate.of(year, month, day);
                Map<GPSCoordinates, LocalTime> dayData = new HashMap<>();
                dayData.put(gps, LocalTime.of(7, 0, day));
                data.put(currentDate, dayData);
            });
        });
        return data;
    }

    /**
     * Checks if the given GPS coordinates are present in the data map.
     *
     * @param map The map to check for the presence of the GPS coordinates.
     * @param gps The GPSCoordinates to check for presence in the map.
     */
    public GPSCoordinates givesGPS(Map<GPSCoordinates, LocalTime> map, GPSCoordinates gps) {
        for (Map.Entry<GPSCoordinates, LocalTime> entry : map.entrySet()) {
            if (entry.getKey().equals(gps)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Checks if the given date and GPS coordinates are present in the data map.
     *
     * @param date The LocalDate to check for presence in the map.
     * @param gps  The GPSCoordinates to check for presence in the map.
     */
    private boolean areArgumentsInMap(LocalDate date, GPSCoordinates gps) {
        GPSCoordinates realGps = givesGPS(this.data.get(date), gps);
        return data.containsKey(date) && data.get(date).containsKey(realGps);
    }

    /**
     * Given a period, it sums the respective readings.
     *
     * @param date the LocalDate of the instant sunrise.
     * @param gps  the GPSCoordinates of the instant sunrise.
     * @return returns the sunrise instant
     */
    public Value getValue(LocalDate date, GPSCoordinates gps) {
        if (!areArgumentsInMap(date, gps)) {
            return null;
        }
        GPSCoordinates realGps = givesGPS(this.data.get(date), gps);
        LocalTime instant = this.data.get(date).get(realGps);
        this.value = new SUNRISE407Value(instant);
        return this.value;
    }

    /**
     * Retrieves the unique identity of the sensor.
     * This method returns the unique identity (SensorID) of the sensor.
     *
     * @return The unique identity (SensorID) of the sensor.
     */
    @Override
    public SensorID identity() {
        return this.sensorID;
    }

    /**
     * Retrieves the DeviceId associated with the sensor.
     * This method returns the DeviceId associated with the sensor.
     *
     * @return The DeviceId associated with the sensor.
     */
    @Override
    public DeviceId getDeviceID() {
        return this.deviceID;
    }

    /**
     * Retrieves the SensorModelID associated with the sensor.
     * This method returns the SensorModelID associated with the sensor.
     *
     * @return The SensorModelID associated with the sensor.
     */
    @Override
    public SensorModelID getSensorModelID() {
        return this.sensorModelID;
    }

    /**
     * Checks if this sensor is the same as the specified object.
     *
     * @param object The object to compare with.
     * @return true if the sensor is the same as the specified object, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof SUNRISE407 sunrise407 && Objects.equals(this.sensorID, sunrise407.sensorID) &&
                    Objects.equals(this.deviceID, sunrise407.deviceID) &&
                    Objects.equals(this.sensorModelID, sunrise407.sensorModelID)) {
                return true;
            }

        return false;
    }

    /**
     * Compares the current object with another object of the same class.
     * The result is true if and only if the argument is not null and is
     * a SUNRISE407 object that has the same sensor ID.
     *
     * @param object The object to compare this object to.
     * @return True if the objects have the same sensor ID, otherwise return false.
     */
    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object instanceof SUNRISE407 sunrise407) {
            return this.sensorID.equals(sunrise407.sensorID);
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
        return Objects.hash(sensorID);
    }

    public void setDataMap(Map<LocalDate, Map<GPSCoordinates, LocalTime>> data) {
        this.data = data;
    }
}
