package smartHomeDDD.domain.domain.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;
import smartHomeDDD.persistence.mem.RepositorySensorReadingMem;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * RepositorySensorReadingMemTest is a test class for the RepositorySensorReadingMem class.
 * It has the following test cases:
 * - Verifies if the initially empty SensorReadingRepository contains a SensorReading after saving it in the repository.
 * - Verifies that an empty repository doesn't contain any SensorReadings.
 * - Verifies that the save method throws an IllegalArgumentException when trying to save a null SensorReading.
 * - Ensures that, when the repository is empty, the findAll() method returns an empty iterable.
 * - Verifies if the findAll() method returns a non-empty iterable when the repository contains Rooms.
 * - Verifies that, when saving a SensorReading with the same identity as a SensorReading already in the repository, the save method returns null.
 * - Verifies that when the repository contains a SensorReading with the specified device id and between the time period, a list of Sensor Readings is returned.
 * - Verifies that when the repository contains a SensorReading with the specified device id and sensor id and between time period, a list of Sensor Readings is returned.
 */
class RepositorySensorReadingMemTest {

    /**
     * Verifies if the initially empty SensorReadingRepository contains a SensorReading after saving it in the repository.
     */
    @Test
    void saveSensorReadingToEmptyRepository_ShouldContainSensorReading() {
        //Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        SensorReadingID sensorReadingId = mock(SensorReadingID.class);
        SensorReading sensorReading = mock(SensorReading.class);
        when(sensorReading.identity()).thenReturn(sensorReadingId);

        //Act
        SensorReading savedSensorReading = repository.save(sensorReading);
        sensorReadingId = savedSensorReading.identity();
        Optional<SensorReading> retrievedSensorReadingOptional = repository.ofIdentity(sensorReadingId);

        //Assert
        assertTrue(repository.containsOfIdentity(sensorReadingId));
        assertTrue(retrievedSensorReadingOptional.isPresent());
    }

    /**
     * Verifies that an empty repository doesn't contain any SensorReadings.
     */
    @Test
    void emptyRepository_ShouldNotContainSensorReading() {
        //Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        SensorReadingID sensorReadingId = mock(SensorReadingID.class);

        //Act
        Optional<SensorReading> retrievedSensorReadingOptional = repository.ofIdentity(sensorReadingId);

        //Assert
        assertTrue(retrievedSensorReadingOptional.isEmpty());
    }

    /**
     * Verifies that the save method throws an IllegalArgumentException when trying to save a null SensorReading.
     */
    @Test
    void saveNullSensorReading_ShouldThrowIllegalArgumentException() {
        //Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        String expectedMessage = "SensorReading cannot be null";

        //Act
        Exception exception =  assertThrows(IllegalArgumentException.class, () -> repository.save(null));
        String actualMessage = exception.getMessage();

        //Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Ensures that, when the repository is empty, the findAll() method returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {
        // Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();

        // Act
        Iterable<SensorReading> allSensorReadings = repository.findAll();

        // Assert
        assertFalse(allSensorReadings.iterator().hasNext());
    }

    /**
     * Verifies if the findAll() method returns a non-empty iterable when the repository contains Rooms.
     */
    @Test
    void findAll_NonEmptyRepository_ShouldReturnNonEmptyIterable() {
        // Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        SensorReadingID sensorReadingId1 = mock(SensorReadingID.class);
        SensorReadingID sensorReadingId2 = mock(SensorReadingID.class);
        SensorReading sensorReading1 = mock(SensorReading.class);
        when(sensorReading1.identity()).thenReturn(sensorReadingId1);
        SensorReading sensorReading2 = mock(SensorReading.class);
        when(sensorReading2.identity()).thenReturn(sensorReadingId2);
        repository.save(sensorReading1);
        repository.save(sensorReading2);

        // Act
        Iterable<SensorReading> allSensorReadings = repository.findAll();

        // Assert
        assertNotNull(allSensorReadings);
        assertTrue(allSensorReadings.iterator().hasNext());
    }

    /**
     * Verifies that, when saving a SensorReading with the same identity as a SensorReading already in the repository, the save method returns null.
     */
    @Test
    void saveDuplicateSensorReading_ShouldThrowException() {
        // Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        SensorReading sensorReading = mock(SensorReading.class);
        SensorReadingID sensorReadingId = mock(SensorReadingID.class);
        when(sensorReading.identity()).thenReturn(sensorReadingId);
        repository.save(sensorReading);
        String expectedMessage = "SensorReading already exists";

        // Act
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.save(sensorReading));
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Verifies that when the repository contains a SensorReading with the specified device id and between the time period, a list of Sensor Readings is returned.
     */
    @Test
    void getMeasurementsFromDevice_shouldReturnListOfSensorReadings() {
        // Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.toString()).thenReturn("1");

        Timestamp startTime = Timestamp.valueOf("2021-01-01 13:00:00");
        Timestamp endTime = Timestamp.valueOf("2021-01-02 15:00:00");

        Timestamp time = Timestamp.valueOf("2021-01-02 14:00:00");

        SensorReadingID sensorReadingId = mock(SensorReadingID.class);
        SensorReading sensorReading = mock(SensorReading.class);
        when(sensorReading.identity()).thenReturn(sensorReadingId);
        when(sensorReading.getDeviceID()).thenReturn(deviceId);
        when(sensorReading.getTimeStamp()).thenReturn(time);

        repository.save(sensorReading);

        // Act
        Iterable<SensorReading> allSensorReadings = repository.getMeasurementsFromDeviceWithinPeriod(deviceId, startTime, endTime);

        // Assert
        assertNotNull(allSensorReadings);
        assertTrue(allSensorReadings.iterator().hasNext());
    }

    /**
     * Verifies that when the repository contains a SensorReading with the specified device id and sensor id and between time period, a list of Sensor Readings is returned.
     */
    @Test
    void getSensorReadingsBetweenTimestamps_shouldReturnListOfSensorReadings() {
        // Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.toString()).thenReturn("d1");
        SensorID sensorId = mock(SensorID.class);
        when(sensorId.toString()).thenReturn("s1");

        Reading reading = mock(Reading.class);
        when(reading.toString()).thenReturn("10");

        Timestamp startTime = Timestamp.valueOf("2021-01-01 13:00:00");
        Timestamp endTime = Timestamp.valueOf("2021-01-02 15:00:00");

        Timestamp time = Timestamp.valueOf("2021-01-02 14:00:00");

        SensorReadingID sensorReadingId = mock(SensorReadingID.class);
        SensorReading sensorReading = mock(SensorReading.class);
        when(sensorReading.identity()).thenReturn(sensorReadingId);
        when(sensorReading.getDeviceID()).thenReturn(deviceId);
        when(sensorReading.getSensorID()).thenReturn(sensorId);
        when(sensorReading.getReading()).thenReturn(reading);
        when(sensorReading.getTimeStamp()).thenReturn(time);

        repository.save(sensorReading);

        // Act
        Iterable<SensorReading> allSensorReadings = repository.getSensorReadingsBetweenTimestamp(deviceId, sensorId, startTime, endTime);

        // Assert
        assertNotNull(allSensorReadings);
        assertTrue(allSensorReadings.iterator().hasNext());
    }

    /**
     * Verifies that the latest reading from a specific sensor is returned
     */
    @Test
    void getLatestReadingFromSensor() {
        // Arrange
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.toString()).thenReturn("d1");
        SensorID sensorId = mock(SensorID.class);
        when(sensorId.toString()).thenReturn("s1");

        Reading reading = mock(Reading.class);
        when(reading.toString()).thenReturn("10");

        Timestamp time = Timestamp.valueOf("2024-01-02 14:00:00");
        Timestamp time1 = Timestamp.valueOf("2024-02-02 21:00:00");
        Timestamp time2 = Timestamp.valueOf("2024-04-02 08:00:00");

        SensorReadingID sensorReadingId = mock(SensorReadingID.class);
        SensorReadingID sensorReadingId1 = mock(SensorReadingID.class);
        SensorReadingID sensorReadingId2 = mock(SensorReadingID.class);
        SensorReading sensorReading = mock(SensorReading.class);
        SensorReading sensorReading1 = mock(SensorReading.class);
        SensorReading sensorReading2 = mock(SensorReading.class);

        when(sensorReading.identity()).thenReturn(sensorReadingId);
        when(sensorReading.getDeviceID()).thenReturn(deviceId);
        when(sensorReading.getSensorID()).thenReturn(sensorId);
        when(sensorReading.getReading()).thenReturn(reading);
        when(sensorReading.getTimeStamp()).thenReturn(time);

        when(sensorReading1.identity()).thenReturn(sensorReadingId1);
        when(sensorReading1.getDeviceID()).thenReturn(deviceId);
        when(sensorReading1.getSensorID()).thenReturn(sensorId);
        when(sensorReading1.getReading()).thenReturn(reading);
        when(sensorReading1.getTimeStamp()).thenReturn(time1);

        when(sensorReading2.identity()).thenReturn(sensorReadingId2);
        when(sensorReading2.getDeviceID()).thenReturn(deviceId);
        when(sensorReading2.getSensorID()).thenReturn(sensorId);
        when(sensorReading2.getReading()).thenReturn(reading);
        when(sensorReading2.getTimeStamp()).thenReturn(time2);

        repository.save(sensorReading);
        repository.save(sensorReading1);
        repository.save(sensorReading2);

        //Act
        Optional<SensorReading> latestReading = repository.getLatestReadingFromSensor(sensorId);

        //Assert
        assertEquals(latestReading.get().getTimeStamp(), time2);
        assertEquals(latestReading.get(), sensorReading2);
    }

}
