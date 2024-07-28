package smartHomeDDD.domain.domain.repository;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.persistence.mem.RepositorySensorTypeMem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the RepositorySensorType class.
 * It encompasses the following scenarios:
 * - Save sensor type to empty repository: Should contain sensor type.
 * - Empty repository: Should not contain sensor type.
 * - Find all sensor types: Empty repository should return empty iterable.
 * - Find all sensor types: Non-empty repository should return non-empty iterable.
 * - Nonexistent ID: Should return false.
 * - Save duplicate sensor type: Should throw exception.
 */
class RepositorySensorTypeTest {

    /**
     * Tests if a sensor type can be saved to an initially empty repository and then retrieved.
     */
    @Test
    void saveSensorTypeToEmptyRepository_ShouldContainSensorType() {
        // Arrange
        IRepositorySensorType repository = new RepositorySensorTypeMem();
        SensorTypeID sensorTypeIdDouble = mock(SensorTypeID.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);

        // Act
        SensorType savedSensorType = repository.save(sensorTypeDouble);
        sensorTypeIdDouble = savedSensorType.identity();
        Optional<SensorType> retrievedSensorTypeOptional = repository.ofIdentity(sensorTypeIdDouble);

        // Assert
        assertTrue(repository.containsOfIdentity(sensorTypeIdDouble));
        assertTrue(retrievedSensorTypeOptional.isPresent());
    }
    /**
     * Tests if an initially empty repository does not contain any sensor types.
     */
    @Test
    void emptyRepository_ShouldNotContainSensorType() {
        // Arrange
        IRepositorySensorType repository = new RepositorySensorTypeMem();
        SensorTypeID sensorTypeIdDouble = mock(SensorTypeID.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeIdDouble);

        // Act
        Optional<SensorType> retrievedSensorTypeOptional = repository.ofIdentity(sensorTypeIdDouble);

        // Assert
        assertFalse(repository.containsOfIdentity(sensorTypeIdDouble));
        assertFalse(retrievedSensorTypeOptional.isPresent());
    }
    /**
     * Tests if retrieving all sensor types from an initially empty repository returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {
        // Arrange
        IRepositorySensorType repository = new RepositorySensorTypeMem();

        // Act
        Iterable<SensorType> allSensorTypes = repository.findAll();

        // Assert
        assertFalse(allSensorTypes.iterator().hasNext());
    }

    @Test
    void nonexistentId_ShouldReturnFalse() {
        // Arrange
        IRepositorySensorType repository = new RepositorySensorTypeMem();
        SensorTypeID nonExistentId = new SensorTypeID("nonexistent");

        // Act
        boolean contains = repository.containsOfIdentity(nonExistentId);

        // Assert
        assertFalse(contains);
    }

    /**
     * Tests if saving a sensor type with an already existing identity throws an exception.
     * The test verifies that the save method of the RepositorySensorType class throws an IllegalArgumentException
     * when it is called with a sensor type that has the same identity as a sensor type already in the repository.
     */
    @Test
    void saveDuplicateSensorType_ShouldThrowException() {

        // Arrange
        IRepositorySensorType repository = new RepositorySensorTypeMem();
        SensorTypeID sensorTypeID = mock(SensorTypeID.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.identity()).thenReturn(sensorTypeID);
        repository.save(sensorTypeDouble);

        String expectedMessage = "Sensor Type already exists";

        // Act & Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> repository.save(sensorTypeDouble));

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
