package smartHomeDDD.persistence.mem;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;

import java.sql.Timestamp;
import java.util.*;

/**
 * RepositorySensorReadingMem is a class that implements the IRepositorySensorReading interface for SensorReading objects.
 * It provides methods for storing SensorReading objects and retrieving them using their SensorReadingID.
 */

public class RepositorySensorReadingMem implements IRepositorySensorReading
{
    /**
     * A HashMap used for storing SensorReading objects. The SensorReadingID of a SensorReading object is used as the key.
     */
    private final Map<SensorReadingID, SensorReading> DATA = new HashMap<>();

    /**
     * Saves the provided SensorReading entity in the repository.
     * If a SensorReading with the same identity already exists in the repository, an IllegalArgumentException is thrown.
     * Otherwise, the SensorReading entity is stored in the repository and returned.
     *
     * @param entity the SensorReading entity to save.
     * @return the saved SensorReading entity.
     * @throws IllegalArgumentException if a SensorReading with the same identity already exists in the repository.
     */
    @Override
    public SensorReading save(SensorReading entity)
    {
        if (entity == null) {
            throw new IllegalArgumentException("SensorReading cannot be null");
        }
        if (containsOfIdentity(entity.identity())) {
            throw new DataIntegrityViolationException("SensorReading already exists");
        }
        DATA.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Retrieves all SensorReading objects stored in the repository.
     *
     * @return An Iterable containing all SensorReading objects in the repository.
     */
    @Override
    public Iterable<SensorReading> findAll()
    {
        return DATA.values();
    }

    /**
     * Retrieves a SensorReading object with the specified SensorReadingID from the repository.
     *
     * @param id The SensorReadingID of the SensorReading object to be retrieved.
     * @return An Optional containing the SensorReading object if it exists in the repository, or an empty Optional if it does not.
     */
    @Override
    public Optional<SensorReading> ofIdentity(SensorReadingID id)
    {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else
            return Optional.of(DATA.get(id));
    }

    /**
     * Checks if a SensorReading object with the specified SensorReadingID exists in the repository.
     *
     * @param id The SensorReadingID of the SensorReading object to check for.
     * @return true if a SensorReading object with the specified SensorReadingID exists in the repository, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(SensorReadingID id)
    {
        for (SensorReadingID sensorReadingID : DATA.keySet()) {
            if (sensorReadingID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of SensorReading objects from a specific device within a given time period.
     *
     * @param deviceID The unique identifier of the device.
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @return A list of SensorReading objects.
     */
    @Override
    public List<SensorReading> getMeasurementsFromDeviceWithinPeriod(DeviceId deviceID, Timestamp start, Timestamp end){
        List<SensorReading> readingsOfDevice = getReadingByDeviceId(deviceID);
        return getReadingWithinPeriod(readingsOfDevice, start, end);
    }



    /**
     * Retrieves a list of SensorReading objects associated with a specific device.
     *
     * @param id The unique identifier of the device.
     * @return A list of SensorReading objects.
     */
    private List <SensorReading> getReadingByDeviceId (DeviceId id){
        List <SensorReading> readingsList = new ArrayList<>();
        Iterable <SensorReading> allReadings = findAll();
        for (SensorReading reading : allReadings){
            if(reading.getDeviceID().equals(id)){
                readingsList.add(reading);
            }
        }
        return readingsList;
    }

    /**
     * Filters a list of SensorReading objects to include only those within a specific time period.
     *
     * @param readings The list of SensorReading objects to filter.
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @return A list of SensorReading objects within the specified time period.
     * @throws IllegalArgumentException if the start time is after the end time.
     */
    private List<SensorReading> getReadingWithinPeriod(List<SensorReading> readings, Timestamp start, Timestamp end) {
        if(start.after(end)){
            throw new IllegalArgumentException("Invalid time period");
        }
        List<SensorReading> readingsWithinPeriod = new ArrayList<>();
        for (SensorReading reading : readings) {
            if (reading.getTimeStamp().after(start) && reading.getTimeStamp().before(end)) {
                readingsWithinPeriod.add(reading);
            }
        }
        return readingsWithinPeriod;
    }


    /**
     * Retrieves a list of sensor readings from a specific device and sensor within a given time period.
     *
     * @param deviceId The unique identifier of the device.
     * @param sensorIDIndoors The unique identifier of the sensor.
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @return A list of SensorReading objects.
     */
    @Override
    public List<SensorReading> getSensorReadingsBetweenTimestamp(DeviceId deviceId, SensorID sensorIDIndoors, Timestamp start, Timestamp end) {
        Iterable<SensorReading> allReadings = findAll();
        List<SensorReading> readingsBetweenTimeStamp = new ArrayList<>();
        for (SensorReading reading : allReadings) {
            if (reading.getDeviceID().equals(deviceId) && reading.getSensorID().equals(sensorIDIndoors)
                    && ( ( reading.getTimeStamp().after(start) && reading.getTimeStamp().before(end)) ) || reading.getTimeStamp().equals(start) || reading.getTimeStamp().equals(end)){
                // Add the reading to the list of readings to return
                readingsBetweenTimeStamp.add(reading);
            }
        }
        return readingsBetweenTimeStamp;
    }

    /**
     * Method to retrieve the latest reading from a specific Sensor
     *
     * @param sensorID the unique identifier of the sensor
     * @return the latest reading from a Sensor
     */

    @Override
    public Optional<SensorReading> getLatestReadingFromSensor(SensorID sensorID) {
        List<SensorReading> readingsFromSensor = getReadingsFromSensor(sensorID);
        SensorReading latestReading = readingsFromSensor.get(0);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp closestTimestamp = latestReading.getTimeStamp();
        long closestDifference = Math.abs(currentTime.getTime() - closestTimestamp.getTime());
        for (SensorReading reading : readingsFromSensor) {
            long difference = Math.abs(currentTime.getTime() - reading.getTimeStamp().getTime());
            if (difference < closestDifference) {
                closestDifference = difference;
                latestReading = reading;
            }
        }
        return Optional.of(latestReading);
    }

    /**
     * Retrieves a list of SensorReadings associated with a specific Sensor.
     *
     * @param sensorID The unique identifier of the sensor.
     * @return A list of SensorReading objects.
     */

    private List<SensorReading> getReadingsFromSensor(SensorID sensorID){
        Iterable<SensorReading> allReadings = findAll();
        List<SensorReading> readingsFromSensor = new ArrayList<>();
        for (SensorReading reading : allReadings) {
            if (reading.getSensorID().equals(sensorID)){
                readingsFromSensor.add(reading);
            }
        }
        return readingsFromSensor;
    }

    @Override
    public List<SensorReading> getSensorReadingsByDeviceId(DeviceId deviceId) {
        Iterable<SensorReading> allReadings = findAll();
        List<SensorReading> readingsFromDevice = new ArrayList<>();
        for (SensorReading reading : allReadings) {
            if (reading.getDeviceID().equals(deviceId)){
                readingsFromDevice.add(reading);
            }
        }
        return readingsFromDevice;
    }

}
