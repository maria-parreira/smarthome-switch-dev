package smartHomeDDD.domain.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.ImplFactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;
import smartHomeDDD.services.ServiceSensor;
import smartHomeDDD.services.ServiceSensorReading;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for ServiceSensorReading
 * It tests the following test cases:
 * Throw exception if null SensorReadingRepository is passed
 * Throw exception if null SensorRepository is passed
 * Throw exception if null ServiceSensor is passed
 * Throw exception if null FactorySensorReading is passed
 * Throw exception if null RepositoryDevice is passed
 * Get measurements from device within period
 * Get measurements from device within period with empty list
 * Get readings between timestamp
 * Get readings between invalid timestamp
 * Get max difference between readings
 * Get sensor reading by device ID
 * Get sensor reading by ID
 * Get sensor reading by non existent ID
 * Add sensor reading with non existent device ID
 * Add sensor reading with non existent sensor ID
 */
class ServiceSensorReadingTest {

    /**
     * Test to verify if the constructor throws an exception when a null SensorReadingRepository is passed
     */
    @Test
    void nullSensorReadingRepository_shouldThrowException() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        String expected = "Sensor Reading Repository cannot be null";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ServiceSensorReading(null, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice));
        String actual = exception.getMessage();
        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify if the constructor throws an exception when a null SensorRepository is passed
     */
    @Test
    void nullSensorRepository_shouldThrowException() {
        // Arrange
        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        String expected = "Sensor Repository cannot be null";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ServiceSensorReading(repositorySensorReading,null, serviceSensor, factorySensorReading, repositoryDevice));
        String actual = exception.getMessage();
        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify if the constructor throws an exception when a null ServiceSensor is passed
     */
    @Test
    void nullServiceSensor_shouldThrowException() {
        // Arrange
        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        String expected = "Service Sensor cannot be null";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ServiceSensorReading(repositorySensorReading, repositorySensor, null, factorySensorReading, repositoryDevice));
        String actual = exception.getMessage();
        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify if the constructor throws an exception when a null FactorySensorReading is passed
     */
    @Test
    void nullFactorySensorReading_shouldThrowException() {
        // Arrange
        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        String expected = "Factory Sensor Reading cannot be null";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, null, repositoryDevice));
        String actual = exception.getMessage();
        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify if the constructor throws an exception when a null RepositoryDevice is passed
     */
    @Test
    void nullRepositoryDevice_shouldThrowException() {
        // Arrange
        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        String expected = "Device Repository cannot be null";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, null));
        String actual = exception.getMessage();
        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify measurements from device within period are retrieved
     */
    @Test
    void getMeasurementsFromDevice_shouldReturnMeasurements() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        Timestamp timestamp = mock(Timestamp.class);
        Timestamp timestamp2 = mock(Timestamp.class);

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        SensorReading sensorReading = mock(SensorReading.class);
        SensorReading sensorReading2 = mock(SensorReading.class);
        when(repositorySensorReading.getMeasurementsFromDeviceWithinPeriod(deviceId, timestamp, timestamp2)).thenReturn(List.of(sensorReading, sensorReading2));

        // Act
        List<SensorReading> measurements = serviceSensorReading.getMeasurementsFromDeviceWithinPeriod(deviceId, timestamp, timestamp2);

        // Assert
        assert(measurements.contains(sensorReading));
        assert(measurements.contains(sensorReading2));
    }

    /**
     * Test to verify that an empty list is returned when no measurements are found for a device within a defined period
     */
    @Test
    void getMeasurementsFromDevice_shouldReturnEmptyList() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        Timestamp timestamp = mock(Timestamp.class);
        Timestamp timestamp2 = mock(Timestamp.class);

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        // Act
        List<SensorReading> measurements = serviceSensorReading.getMeasurementsFromDeviceWithinPeriod(deviceId, timestamp, timestamp2);

        // Assert
        assert(measurements.isEmpty());
    }

    /**
     * Test to verify that readings between timestamps are retrieved
     */
    @Test
    void getReadingsBetweenTimeStamp_shouldReturnListOfReadings() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp = Timestamp.valueOf("2024-01-01 00:00:00");
        Timestamp timestamp2 = Timestamp.valueOf("2024-01-01 23:00:00");

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        SensorReading sensorReading = mock(SensorReading.class);
        SensorReading sensorReading2 = mock(SensorReading.class);
        when(repositorySensorReading.getSensorReadingsBetweenTimestamp(deviceId, sensorID, timestamp, timestamp2)).thenReturn(List.of(sensorReading, sensorReading2));

        // Act
        List<SensorReading> readings = serviceSensorReading.getSensorReadingsBetweenTimestamp(deviceId, sensorID, timestamp, timestamp2);

        // Assert
        assert(readings.contains(sensorReading));
        assert(readings.contains(sensorReading2));
    }

    /**
     * Test to verify that an exception is thrown when invalid timestamps are passed
     */
    @Test
    void getReadingsBetweenInvalidTimeStamp_shouldThrowException() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp = Timestamp.valueOf("2024-01-01 23:00:00");
        Timestamp timestamp2 = Timestamp.valueOf("2024-01-01 00:00:00");

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        String expected = "Invalid time period";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                serviceSensorReading.getSensorReadingsBetweenTimestamp(deviceId, sensorID, timestamp, timestamp2));
        String actual = exception.getMessage();

        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify that the maximum difference between readings is retrieved
     */
    @Test
    void getMaxDifferenceBetweenReadings(){
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        DeviceId deviceId1 = mock(DeviceId.class);

        SensorID sensorID = mock(SensorID.class);
        SensorID sensorID1 = mock(SensorID.class);

        Timestamp timestamp = Timestamp.valueOf("2024-01-01 00:00:00");
        Timestamp timestamp1 = Timestamp.valueOf("2024-01-01 23:00:00");
        Timestamp timestamp2 = Timestamp.valueOf("2024-01-01 00:45:00");
        Timestamp timestamp3 = Timestamp.valueOf("2024-01-01 23:03:00");

        SensorReadingID sensorReadingID = mock(SensorReadingID.class);
        SensorReadingID sensorReadingID1 = mock(SensorReadingID.class);
        SensorReadingID sensorReadingID2 = mock(SensorReadingID.class);
        SensorReadingID sensorReadingID3 = mock(SensorReadingID.class);

        Reading reading = new Reading("10");
        Reading reading1 = new Reading("20");
        Reading reading2 = new Reading("30");
        Reading reading3 = new Reading("40");

        int delta = 5;

        int expected = 20;

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);

        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReading sensorReading = factorySensorReading.createSensorReading(sensorReadingID, reading, deviceId, sensorID, timestamp);
        SensorReading sensorReading1 = factorySensorReading.createSensorReading(sensorReadingID1, reading1, deviceId, sensorID, timestamp1);
        SensorReading sensorReading2 = factorySensorReading.createSensorReading(sensorReadingID2, reading2, deviceId1, sensorID1, timestamp2);
        SensorReading sensorReading3 = factorySensorReading.createSensorReading(sensorReadingID3, reading3, deviceId1, sensorID1, timestamp3);

        List<SensorReading> insideList = List.of(sensorReading, sensorReading1);
        List<SensorReading> outsideList = List.of(sensorReading2, sensorReading3);

        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        // Act
        int maxDifference = serviceSensorReading.getDifferenceBetweenReadings(insideList, outsideList, delta);

        // Assert
        assertEquals(expected, maxDifference);
    }

    /**
     * Test to verify if the sensor readings are retrieved based on device ID
     */
    @Test
    void getSensorReadingByDeviceID_shouldReturnSensorReading(){
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        SensorReading sensorReading = mock(SensorReading.class);
        SensorReading sensorReading1 = mock(SensorReading.class);
        when(repositorySensorReading.getSensorReadingsByDeviceId(deviceId)).thenReturn(List.of(sensorReading, sensorReading1));

        // Act
        List<SensorReading> list = serviceSensorReading.getSensorReadingsByDeviceID(deviceId);

        // Assert
        assert(list.contains(sensorReading));
        assert(list.contains(sensorReading1));
    }

    /**
     * Test to verify if the sensor reading is retrieved based on its ID
     */
    @Test
    void getSensorReadingByID_shouldReturnSensorReading(){
        // Arrange
        SensorReadingID sensorReadingID = mock(SensorReadingID.class);

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        SensorReading sensorReading = mock(SensorReading.class);
        when(repositorySensorReading.ofIdentity(sensorReadingID)).thenReturn(Optional.of(sensorReading));

        // Act
        SensorReading actual = serviceSensorReading.getSensorReadingById(sensorReadingID);

        // Assert
        assert(sensorReading.equals(actual));
    }

    /**
     * Test to verify if an exception is thrown when a non-existent sensor reading ID is passed
     */
    @Test
    void getSensorReadingByNonExistentID_shouldThrowException(){
        // Arrange
        SensorReadingID sensorReadingID = mock(SensorReadingID.class);

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        when(repositorySensorReading.ofIdentity(sensorReadingID)).thenReturn(Optional.empty());

        String expected = "Sensor Reading not found";

        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                serviceSensorReading.getSensorReadingById(sensorReadingID));
        String actual = exception.getMessage();

        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify if an exception is thrown when a non-existent device ID is passed to create a sensor reading
     */
    @Test
    void addSensorReadingWithNonExistentDeviceID_shouldThrowException() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        Reading reading = mock(Reading.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp = mock(Timestamp.class);

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);

        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        when(repositorySensor.containsOfIdentity(sensorID)).thenReturn(true);

        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);

        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        when(repositoryDevice.containsOfIdentity(deviceId)).thenReturn(false);

        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        String expected = "Device not found";

        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                serviceSensorReading.addSensorReading(reading, deviceId, sensorID, timestamp));
        String actual = exception.getMessage();

        // Assert
        assert(actual.contains(expected));
    }

    /**
     * Test to verify if an exception is thrown when a non-existent sensor ID is passed to create a sensor reading
     */
    @Test
    void addSensorReadingWithNonExistentSensorID_shouldThrowException() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        Reading reading = mock(Reading.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timestamp = mock(Timestamp.class);

        IRepositorySensorReading repositorySensorReading = mock(IRepositorySensorReading.class);

        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        when(repositorySensor.containsOfIdentity(sensorID)).thenReturn(false);

        ServiceSensor serviceSensor = mock(ServiceSensor.class);
        FactorySensorReading factorySensorReading = mock(FactorySensorReading.class);

        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        when(repositoryDevice.containsOfIdentity(deviceId)).thenReturn(true);

        ServiceSensorReading serviceSensorReading = new ServiceSensorReading(repositorySensorReading, repositorySensor, serviceSensor, factorySensorReading, repositoryDevice);

        String expected = "Sensor not found";

        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                serviceSensorReading.addSensorReading(reading, deviceId, sensorID, timestamp));
        String actual = exception.getMessage();

        // Assert
        assert(actual.contains(expected));
    }

}
