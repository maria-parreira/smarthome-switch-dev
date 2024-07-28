package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceMapper;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * ServiceSensorReading is a service class for handling operations related to sensor readings.
 * It uses an instance of IRepositorySensorReading to interact with the underlying sensor readings repository.
 */
@Service
public class ServiceSensorReading {

    /**
     * The repository for sensor readings.
     */
    private final IRepositorySensorReading repositorySensorReading;

    /**
     * Repository for accessing sensor data.
     * This field is used to perform CRUD operations on sensor data. It is initialized through
     * dependency injection and provides the necessary methods to interact with the sensor repository.
     * This field is marked as final, indicating that it cannot be reassigned once it has been initialized.
     * @see IRepositorySensor
     */
    private final IRepositorySensor repositorySensor;

    /**
     * This field is used to perform various operations related to sensors, such as data processing
     * and business logic execution. It is initialized through dependency injection.
     * This field is marked as final, indicating that it cannot be reassigned once it has been initialized.
     */
    private final ServiceSensor serviceSensor;

    /**
     * This field is used to instantiate objects related to sensor readings. It follows the factory design
     * pattern and is initialized through dependency injection to ensure the creation of consistent and
     * valid sensor reading instances.
     * This field is marked as final, indicating that it cannot be reassigned once it has been initialized.
     */
    private final FactorySensorReading factorySensorReading;

    /**
     * This field is used to perform CRUD operations on device data. It is initialized through
     * dependency injection and provides the necessary methods to interact with the device repository.
     * This field is marked as final, indicating that it cannot be reassigned once it has been initialized.
     */
    private final IRepositoryDevice repositoryDevice;


    /**
     * Constructor for the ServiceSensorReading class
     * @param repositorySensorReading The repository for sensor readings.
     * @param repositorySensor The repository for sensors.
     * @param serviceSensor The service for sensors.
     */

    public ServiceSensorReading(IRepositorySensorReading repositorySensorReading, IRepositorySensor repositorySensor, ServiceSensor serviceSensor, FactorySensorReading factorySensorReading, IRepositoryDevice repositoryDevice) {
        if (repositorySensorReading == null){
            throw new IllegalArgumentException("Sensor Reading Repository cannot be null");}
        if (repositorySensor == null){
            throw new IllegalArgumentException("Sensor Repository cannot be null");}
        if (serviceSensor == null){
            throw new IllegalArgumentException("Service Sensor cannot be null");}
        if (factorySensorReading == null){
            throw new IllegalArgumentException("Factory Sensor Reading cannot be null");
        }
        if (repositoryDevice == null){
            throw new IllegalArgumentException("Device Repository cannot be null");
        }
        this.repositorySensorReading = repositorySensorReading;
        this.repositorySensor = repositorySensor;
        this.serviceSensor = serviceSensor;
        this.factorySensorReading= factorySensorReading;
        this.repositoryDevice = repositoryDevice;
    }


    /**
     * Retrieves a list of sensor readings from a specific device within a given time period.
     * @param deviceID  The unique identifier of the device.
     * @param startTime The start of the time period.
     * @param endTime   The end of the time period.
     * @return A list of SensorReading objects.
     */
    public List<SensorReading> getMeasurementsFromDeviceWithinPeriod(DeviceId deviceID, Timestamp startTime, Timestamp endTime) {
        return repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(deviceID, startTime, endTime);
    }

    /**
     * Retrieves a list of sensor readings from a specific device within a given time period.
     * @param deviceId  The unique identifier of the device.
     * @param sensorID  The unique identifier of the sensor.
     * @param startTime The start of the time period.
     * @param endTime   The end of the time period.
     * @return A list of SensorReading objects.
     */
    public List<SensorReading> getSensorReadingsBetweenTimestamp(DeviceId deviceId, SensorID sensorID, Timestamp startTime, Timestamp endTime) {
        if (startTime.after(endTime)) {
            throw new IllegalArgumentException("Invalid time period");
        }

        return repositorySensorReading.getSensorReadingsBetweenTimestamp(deviceId, sensorID, startTime, endTime);
    }


    /**
     * Retrieves the maximum difference between the inside and outside temperature readings within a given time period.
     * @param insideReadings  The inside temperature readings.
     * @param outsideReadings The outside temperature readings.
     * @param deltaTime       The time interval between readings.
     * @return The maximum difference between the inside and outside temperature readings.
     */
    public int getDifferenceBetweenReadings(List<SensorReading> insideReadings, List<SensorReading> outsideReadings, int deltaTime) {
        int maxDifference = 0;
        // if a difference is found, set flag to 1
        int flag = 0;
        for (SensorReading insideReading : insideReadings) {
            for (SensorReading outsideReading : outsideReadings) {
                long timeDifference = ChronoUnit.MINUTES.between(insideReading.getTimeStamp().toInstant(), outsideReading.getTimeStamp().toInstant());
                //int timeDifference = Math.abs(Integer.parseInt(insideReading.getTimeStamp().toString()) - Integer.parseInt(outsideReading.getTimeStamp().toString()));
                if (timeDifference <= deltaTime) {
                    int difference = Math.abs(Integer.parseInt(insideReading.getReading().toString()) - Integer.parseInt(outsideReading.getReading().toString()));
                    if (difference >= maxDifference) {
                        maxDifference = difference;
                        flag = 1;
                    }
                }
            }
        }
        if (flag == 0) {
            throw new IllegalArgumentException("No readings found within the given time period.");
        }
        return maxDifference;
    }

    /**
     * Returns a list of device identifiers that have energy sensors matching the provided sensor model ID.
     * @param sensorModelId The ID of the sensor model to compare against.
     * @return A list of device identifiers that have energy sensors matching the provided sensor model ID.
     * @throws IllegalArgumentException If no devices are found with matching energy sensors.
     */
    public List<DeviceId> getDevicesBySensorModel(String sensorModelId) {
        Iterable<Sensor> sensors = repositorySensor.findAll();
        List<DeviceId> deviceIds = new ArrayList<>();
        for (Sensor sensor : sensors) {
            if (sensor.getSensorModelID().toString().equals(sensorModelId)) {
                deviceIds.add(sensor.getDeviceID());
            }
        }
        // Returns the list of device identifiers
        return deviceIds;
    }

    /**
     * Calculates the peak power consumption for a house within a specified period.
     * @param powerGridMeter The power grid meter device.
     * @param startTime The start time of the period.
     * @param endTime The end time of the period.
     * @param intervalInMinutes The interval in minutes to calculate the power consumption for.
     * @return A double representing the peak power consumption for each interval within the period.
     */
    public double calculateHousePeakPowerConsumptionWithinPeriod(Device powerGridMeter, Timestamp startTime, Timestamp endTime, long intervalInMinutes){
        if(!powerGridMeter.getDeviceName().toString().equals("Power Grid Meter")){
            throw new EntityNotFoundException("PowerGridMeter must be used");
        }
        if(endTime.before(startTime)){
            throw new IllegalArgumentException("end time can't be before start time");
        }
        if(intervalInMinutes < 0){
            throw new IllegalArgumentException("end time can't be negative");
        }
        List<DeviceId> devicesId = getDevicesBySensorModel("PC500W"); // find all devices with energy sensors
        return calculatePeakPowerConsumptionWithinPeriod(devicesId,startTime, endTime,intervalInMinutes);
    }


    /**
     * Calculates the peak power consumption for a list of devices within a specified period.
     * The peak power consumption is the maximum power consumption between all the intervals within the period.
     * @param devices The list of devices to calculate the power consumption for.
     * @param startTime The start time of the period.
     * @param endTime The end time of the period.
     * @param intervalInMinutes The interval in minutes to calculate the power consumption for.
     * @return A double representing the peak power consumption for each interval within the period.
     */
    private double calculatePeakPowerConsumptionWithinPeriod(List<DeviceId> devices, Timestamp startTime, Timestamp endTime, long intervalInMinutes) {
        // Calculate the total duration in minutes
        long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(endTime.getTime() - startTime.getTime());
        int numberOfIntervals = (int) Math.ceil((double) durationInMinutes / intervalInMinutes);
        double[] powerConsumptions = new double[numberOfIntervals];

        // Iterate through each interval and calculate power consumption
        for (int interval = 0; interval < numberOfIntervals; interval++) {
            powerConsumptions[interval] = calculateAveragePowerConsumptionOnInterval(devices, startTime, endTime, intervalInMinutes, interval);
        }
        return Arrays.stream(powerConsumptions).max().getAsDouble();
    }

    /**
     * Calculates the power consumption for a specific interval.
     * @param devices The list of devices to calculate the power consumption for.
     * @param startTime The start time of the period.
     * @param endTime The end time of the period.
     * @param intervalInMinutes The interval in minutes to calculate the power consumption for.
     * @param intervalIndex The index of the interval to calculate the power consumption for (e.g., 0 for the first interval).
     * @return The power consumption for the specified interval.
     */
    private double calculateAveragePowerConsumptionOnInterval(List<DeviceId> devices, Timestamp startTime, Timestamp endTime, long intervalInMinutes, int intervalIndex) {
        // Calculate the start time for the current interval based on the interval index
        Timestamp currentStartTime = addMinutesToTimestamp(startTime, intervalIndex * intervalInMinutes);
        Timestamp currentEndTime = addMinutesToTimestamp(currentStartTime, intervalInMinutes);

        if (currentEndTime.after(endTime)) {
            currentEndTime = endTime;
        }

        // Calculate the average power consumption for the current interval
        return getDevicesAverageReadingsWithinInterval(devices, currentStartTime, currentEndTime);
    }

    /**
     * Adds a specified number of minutes to a timestamp.
     * @param timestamp The timestamp to add minutes to.
     * @param minutes The number of minutes to add.
     * @return A new timestamp with the specified number of minutes added.
     */
    private Timestamp addMinutesToTimestamp(Timestamp timestamp, long minutes) {
        return new Timestamp(timestamp.getTime() + minutes * 60 * 1000);
    }

    /**
     * Calculates the average power consumption for a list of devices within a specified interval.
     * @param devices The list of devices to calculate the power consumption for.
     * @param startTime The start time of the interval.
     * @param endTime The end time of the interval.
     * @return The average power consumption for the devices within the interval.
     */
    private double getDevicesAverageReadingsWithinInterval(List<DeviceId> devices, Timestamp startTime, Timestamp endTime) {
        double powerConsumption = 0;
        for (DeviceId deviceID : devices) {
            powerConsumption += getAverageReadingsWithinInterval(deviceID, startTime, endTime);
        }
        return powerConsumption;
    }

    /**
     * Calculates the average power consumption for a device within a specified interval.
     * @param deviceID The unique identifier of the device.
     * @param start The start time of the interval.
     * @param end The end time of the interval.
     * @return The average power consumption for the device within the interval.
     */
    private double getAverageReadingsWithinInterval(DeviceId deviceID, Timestamp start, Timestamp end) {
        List<SensorReading> deviceReadings = getMeasurementsFromDeviceWithinPeriod(deviceID, start, end);
        int sumOfReadings = 0;
        int countOfReadings = 0;
        double averageOfReadings = 0;
        for (SensorReading reading : deviceReadings) {
            int readingValue = Integer.parseInt(reading.getReading().toString());
            sumOfReadings += readingValue;
            countOfReadings++;
        }
        if (countOfReadings > 0) {
            averageOfReadings = (double) sumOfReadings / countOfReadings;
        }
        return averageOfReadings;
    }

    /**
     * Retrieves all readings from a device.
     *
     * @param deviceId The unique identifier of the device.
     * @return a list of SensorReading objects associated with the device.
     */
    public List<SensorReading> getSensorReadingsByDeviceID(DeviceId deviceId) {
        return repositorySensorReading.getSensorReadingsByDeviceId(deviceId);
    }

    /**
     * Retrieves all temperature readings from a device within a specified time period.
     *
     * @param deviceId The unique identifier of the device.
     * @param startTime The start time of the period.
     * @param endTime The end time of the period.
     * @return A list of SensorReading objects.
     */
    public List<SensorReading> getTemperatureReadingsFromDevice(String deviceId, Timestamp startTime, Timestamp endTime) {
        List<SensorReading> allTemperatureReadings = new ArrayList<>();
        DeviceId deviceID = DeviceMapper.DTOToDeviceId(deviceId);
        List<Sensor> sensorsInDevice = serviceSensor.getSensorsByDeviceID(deviceID);
        for (Sensor sensor : sensorsInDevice) {
            if (serviceSensor.isSensorOfTemperature(sensor)) {
                List<SensorReading> sensorReadingsWithinPeriod = getSensorReadingsBetweenTimestamp(deviceID, sensor.identity(), startTime, endTime);
                allTemperatureReadings.addAll(sensorReadingsWithinPeriod);
            }

        }
        if (allTemperatureReadings.isEmpty()) {
            throw new DataIntegrityViolationException("No temperature readings found for the given time period");
        }

        return allTemperatureReadings;

    }

    /**
     * Retrieves a sensor reading by its unique identifier.
     *
     * @param sensorReadingID The unique identifier of the sensor reading.
     * @return The sensor reading object, if it exists in the repository.
     */
    public SensorReading getSensorReadingById(SensorReadingID sensorReadingID) {
        Optional<SensorReading> sensorReading = repositorySensorReading.ofIdentity(sensorReadingID);
        if (sensorReading.isPresent()) {
            return sensorReading.get();
        }
        throw new EntityNotFoundException("Sensor Reading not found");
    }

    /**
     * Creates a new sensor reading and adds it to the repository.
     * @param reading The reading value.
     * @param deviceID The unique identifier of the device.
     * @param sensorID The unique identifier of the sensor.
     * @param timeStamp The timestamp of the reading.
     * @return The sensor reading object that was created and saved.
     */
    public SensorReading addSensorReading(Reading reading, DeviceId deviceID, SensorID sensorID, Timestamp timeStamp) {
        if(!repositoryDevice.containsOfIdentity(deviceID)){
            throw new EntityNotFoundException("Device not found");
        }
        if(!repositorySensor.containsOfIdentity(sensorID)){
            throw new EntityNotFoundException("Sensor not found");
        }
        SensorReadingID sensorReadingID = new SensorReadingID(UUID.randomUUID().toString());
        SensorReading sensorReading = factorySensorReading.createSensorReading(sensorReadingID, reading, deviceID, sensorID, timeStamp);
        return repositorySensorReading.save(sensorReading);
    }

}
