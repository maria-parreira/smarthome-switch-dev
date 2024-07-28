package smartHomeDDD.domain.domain.repository;

import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.persistence.mem.RepositorySensorModelMem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the RepositorySensorType class.
 * It compasses the following scenarios:
 * - Save sensor type to empty repository: Should contain sensor type.
 * - Empty repository: Should not contain sensor type.
 * - Find all sensor types: Empty repository should return empty iterable.
 * - Find all sensor types: Non-empty repository should return non-empty iterable.
 * - Nonexistent ID: Should return false.
 * - Save duplicate sensor type: Should throw exception.
 */
class RepositorySensorModelMemTest {

    /**
     * Tests if a sensor model can be saved to an initially empty repository and then retrieved.
     */
    @Test
    void saveSensorModelToEmptyRepository_ShouldContainSensorModel() {
        // Arrange
        IRepositorySensorModel repository = new RepositorySensorModelMem();
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorModel sensorModelDouble = mock(SensorModel.class);
        when(sensorModelDouble.identity()).thenReturn(sensorModelIdDouble);

        // Act
        SensorModel savedSensorType = repository.save(sensorModelDouble);
        sensorModelIdDouble = savedSensorType.identity();
        Optional<SensorModel> retrievedSensorModelOptional = repository.ofIdentity(sensorModelIdDouble);

        // Assert
        assertTrue(repository.containsOfIdentity(sensorModelIdDouble));
        assertTrue(retrievedSensorModelOptional.isPresent());
    }
    /**
     * Tests if an initially empty repository does not contain any sensor models.
     */
    @Test
    void emptyRepository_ShouldNotContainSensorModel() {
        // Arrange
        IRepositorySensorModel repository = new RepositorySensorModelMem();
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorModel sensorModelDouble = mock(SensorModel.class);
        when(sensorModelDouble.identity()).thenReturn(sensorModelIdDouble);

        // Act
        Optional<SensorModel> retrievedSensorModelOptional = repository.ofIdentity(sensorModelIdDouble);

        // Assert
        assertFalse(repository.containsOfIdentity(sensorModelIdDouble));
        assertFalse(retrievedSensorModelOptional.isPresent());
    }
    /**
     * Tests if retrieving all sensor models from an initially empty repository returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {
        // Arrange
        IRepositorySensorModel repository = new RepositorySensorModelMem();

        // Act
        Iterable<SensorModel> allSensorModels = repository.findAll();

        // Assert
        assertFalse(allSensorModels.iterator().hasNext());
    }


    /**
     * Tests if saving a sensor type with an already existing identity throws an exception.
     * The test verifies that the save method of the RepositorySensorType class throws an IllegalArgumentException
     * when it is called with a sensor type that has the same identity as a sensor type already in the repository.
     */
    @Test
    void saveDuplicateSensorType_ShouldThrowException() {

        // Arrange
        IRepositorySensorModel repository = new RepositorySensorModelMem();
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorModel sensorModelDouble = mock(SensorModel.class);
        when(sensorModelDouble.identity()).thenReturn(sensorModelIdDouble);
        repository.save(sensorModelDouble);

        String expectedMessage = "Sensor Model already exists";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> repository.save(sensorModelDouble));

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
