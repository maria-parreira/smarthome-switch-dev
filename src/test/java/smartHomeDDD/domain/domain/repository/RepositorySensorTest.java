package smartHomeDDD.domain.domain.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.persistence.mem.RepositorySensorMem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the RepositorySensor class.
 * It encompasses the following scenarios:
 * - Saving a sensor to an initially empty repository and then retrieving it.
 * - Checking if an initially empty repository does not contain any sensors.
 * - Retrieving all sensors from an initially empty repository returns an empty iterable.
 * - Retrieving all sensors from a non-empty repository returns a non-empty iterable.
 * - Checking for the existence of a sensor that is present in the repository returns true.
 * - Checking for the existence of a sensor that is not present in the repository returns false.
 * - Saving a sensor with an already existing identity throws an exception.
 */
class RepositorySensorTest {
    /**
     * Tests if a sensor can be saved to an initially empty repository and then retrieved.
     */
    @Test
    void saveSensorToEmptyRepository_ShouldContainSensor() {
        // Arrange
        IRepositorySensor repository = new RepositorySensorMem();
        SensorID sensorIdDouble = mock(SensorID.class);
        Sensor sensorDouble = mock(Sensor.class);
        when(sensorDouble.identity()).thenReturn(sensorIdDouble);

        // Act
        Sensor savedSensor = repository.save(sensorDouble);
        sensorIdDouble = savedSensor.identity();
        Optional<Sensor> retrievedSensorOptional = repository.ofIdentity(sensorIdDouble);

        // Assert
        assertTrue(repository.containsOfIdentity(sensorIdDouble));
        assertTrue(retrievedSensorOptional.isPresent());
    }

    /**
     * Tests if an initially empty repository does not contain any sensors.
     */
    @Test
    void emptyRepository_ShouldNotContainSensor() {
        // Arrange
        IRepositorySensor repository = new RepositorySensorMem();
        SensorID sensorIdDouble = mock(SensorID.class);
        Sensor sensorDouble = mock(Sensor.class);
        when(sensorDouble.identity()).thenReturn(sensorIdDouble);

        // Act
        Optional<Sensor> retrievedSensorOptional = repository.ofIdentity(sensorIdDouble);

        // Assert
        assertFalse(repository.containsOfIdentity(sensorIdDouble));
        assertFalse(retrievedSensorOptional.isPresent());
    }
    /**
     * Tests if retrieving all sensors from an initially empty repository returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {
        // Arrange
        IRepositorySensor repository = new RepositorySensorMem();

        // Act
        Iterable<Sensor> allSensors = repository.findAll();

        // Assert
        assertFalse(allSensors.iterator().hasNext());
    }
    /**
     * Tests if retrieving all sensors from a non-empty repository returns a non-empty iterable.
     */
    @Test
    void findAll_NonEmptyRepository_ShouldReturnNonEmptyIterable() {
        // Arrange
        IRepositorySensor repository = new RepositorySensorMem();
        SensorID sensorIdDouble1 = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        Sensor sensorDouble1 = mock(Sensor.class);
        when(sensorDouble1.identity()).thenReturn(sensorIdDouble1);
        Sensor sensorDouble2 = mock(Sensor.class);
        when(sensorDouble2.identity()).thenReturn(sensorIdDouble2);
        repository.save(sensorDouble1);
        repository.save(sensorDouble2);

        // Act
        Iterable<Sensor> allSensors = repository.findAll();

        // Assert
        assertNotNull(allSensors);
        assertTrue(allSensors.iterator().hasNext());
    }
    /**
     * Tests if checking for the existence of a sensor that is present in the repository returns true.
     */
    @Test
    void containsOfIdentity_SensorPresent_ShouldReturnTrue() {
        // Arrange
        IRepositorySensor repository = new RepositorySensorMem();
        SensorID sensorIdDouble = mock(SensorID.class);
        Sensor sensorDouble = mock(Sensor.class);
        when(sensorDouble.identity()).thenReturn(sensorIdDouble);
        repository.save(sensorDouble);

        // Act & Assert
        assertTrue(repository.containsOfIdentity(sensorIdDouble));
    }
    /**
     * Tests if checking for the existence of a sensor that is not present in the repository returns false.
     */
    @Test
    void containsOfIdentity_SensorNotPresent_ShouldReturnFalse() {
        // Arrange
        IRepositorySensor repository = new RepositorySensorMem();
        SensorID sensorIdDouble = mock(SensorID.class);

        // Act & Assert
        assertFalse(repository.containsOfIdentity(sensorIdDouble));
    }

    /**
     * Tests if saving a sensor with an already existing identity throws an exception.
     * The test verifies that the save method of the RepositorySensor class throws an IllegalArgumentException when it is called with a sensor that has the same identity as a sensor already in the repository.
     */
    @Test
    void saveDuplicateSensor_ShouldThrowException() {
        // Arrange
        IRepositorySensor repository = new RepositorySensorMem();
        Sensor sensor = mock(Sensor.class);
        SensorID sensorId = mock(SensorID.class);
        when(sensor.identity()).thenReturn(sensorId);
        repository.save(sensor);

        String expectedMessage = "Sensor already exists";

        // Act & Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.save(sensor));

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

