package smartHomeDDD.domain.domain.repository;

import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.persistence.mem.RepositoryActuatorMem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the RepositoryActuator class. It tests the following scenarios:
 * Saving an actuator To Repository.
 * Checking for an initially empty repository.
 * Finding all actuators in a non-empty repository.
 * Not finding an actuator.
 * Attempting to save an actuator with the same identity as an existing actuator.
 */
class RepositoryActuatorMemTest
{
    /**
     * Tests if the RepositoryActuator correctly stores an actuator.
     */
    @Test
    void validSaveOfActuatorsaveActuator_ShouldReturnActuator()
    {
        // Arrange
        RepositoryActuatorMem repository = new RepositoryActuatorMem();
        ActuatorID actuatorID = mock(ActuatorID.class);
        Actuator actuator = mock(Actuator.class);
        when(actuator.identity()).thenReturn(actuatorID);
        // Act
        repository.save(actuator);
        Optional<Actuator> retrievedActuator = repository.ofIdentity(actuatorID);
        // Assert
        assertTrue(retrievedActuator.isPresent());
        assertEquals(actuator, retrievedActuator.get());
    }

    /**
     * Tests if the RepositoryActuator throws an exception when attempting to save a null actuator.
     */
    @Test
    void saveOfNullActuator_ShouldReturnExceptions()
    {
        // Arrange
        RepositoryActuatorMem repository = new RepositoryActuatorMem();
        ActuatorID actuatorID = mock(ActuatorID.class);
        Actuator actuator = mock(Actuator.class);
        when(actuator.identity()).thenReturn(actuatorID);
        // Act
        repository.save(actuator);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> repository.save(actuator));
        String exceptionMessage = exception.getMessage();
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(exceptionMessage));
    }

    /**
     * Tests if the repository is empty in the moment of creation;
     */
    @Test
    void initiallyEmptyRepository_shouldReturnFalse()
    {
        // Arrange
        RepositoryActuatorMem repository = new RepositoryActuatorMem();
        // Act
        Iterable<Actuator> allActuators = repository.findAll();
        // Assert
        assertFalse(allActuators.iterator().hasNext());
    }

    /**
     * Tests whether all actuators saved to the repository are retrieved.
     */
    @Test
    void savedActuatorsWithinRepository_ShouldReturnActuators()
    {
        // Arrange
        RepositoryActuatorMem repository = new RepositoryActuatorMem();
        Actuator actuator1 = mock(Actuator.class);
        when(actuator1.identity()).thenReturn(mock(ActuatorID.class));
        Actuator actuator2 = mock(Actuator.class);
        when(actuator1.identity()).thenReturn(mock(ActuatorID.class));
        repository.save(actuator1);
        repository.save(actuator2);
        // Act
        Iterable<Actuator> allActuators = repository.findAll();
        // Assert
        assertNotNull(allActuators);
        assertTrue(allActuators.iterator().hasNext());
    }

    /**
     * Tests whether the result of searching for a non-existing actuator is an empty return.
     */
    @Test
    void actuatorNotFound_ShouldReturnEmpty()
    {
        // Arrange
        RepositoryActuatorMem repository = new RepositoryActuatorMem();
        ActuatorID actuatorID = mock(ActuatorID.class);
        // Act
        Optional<Actuator> retrievedActuator = repository.ofIdentity(actuatorID);
        // Assert
        assertFalse(retrievedActuator.isPresent());
    }

    /**
     * Verifies that, when saving an actuator with the same identity as an existing actuator, the repository doesn't save the actuator.
     */
    @Test
    void saveDuplicateActuator_ShouldReturnException()
    {
        // Arrange
        RepositoryActuatorMem repository = new RepositoryActuatorMem();
        ActuatorID actuatorID = mock(ActuatorID.class);
        Actuator actuator = mock(Actuator.class);
        when(actuator.identity()).thenReturn(actuatorID);
        // Act
        repository.save(actuator);
        Actuator actuatorDuplicate = mock(Actuator.class);
        when(actuatorDuplicate.identity()).thenReturn(actuatorID);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> repository.save(actuator));
        String exceptionMessage = exception.getMessage();
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(exceptionMessage));
    }

}

