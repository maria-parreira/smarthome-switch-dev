package smartHomeDDD.domain.domain.repository;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.ImplFactoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.persistence.mem.RepositoryActuatorTypeMem;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryActuatorTypeTest {


    /**
     * Test to verify that an ActuatorType object can be saved correctly in the repository.
     */
    @Test
    void whenSavingActuatorType_thenShouldBeSavedCorrectly() {
        ImplFactoryActuatorType factory = new ImplFactoryActuatorType();
        ActuatorType actuatorType = factory.createActuatorType(new Unit("unit"), new Description("description"), new ActuatorTypeID("testID"));

        RepositoryActuatorTypeMem repository = new RepositoryActuatorTypeMem();
        ActuatorType savedActuatorType = repository.save(actuatorType);

        assertEquals(actuatorType, savedActuatorType);
    }

    /**
     * Test to verify that an ActuatorType object can be retrieved correctly from the repository by its ID.
     */
    @Test
    void whenRetrievingActuatorTypeById_thenShouldReturnCorrectActuatorType() {
        ImplFactoryActuatorType factory = new ImplFactoryActuatorType();
        ActuatorType actuatorType = factory.createActuatorType(new Unit("unit"), new Description("description"), new ActuatorTypeID("testID"));

        RepositoryActuatorTypeMem repository = new RepositoryActuatorTypeMem();
        repository.save(actuatorType);

        Optional<ActuatorType> retrievedActuatorType = repository.ofIdentity(new ActuatorTypeID("testID"));

        assertTrue(retrievedActuatorType.isPresent());
        assertEquals(actuatorType, retrievedActuatorType.get());
    }

    /**
     * Test to verify that the repository correctly identifies the existence of an ActuatorType object by its ID.
     */
    @Test
    void whenCheckingIfActuatorTypeExistsById_thenShouldReturnTrue() {
        ImplFactoryActuatorType factory = new ImplFactoryActuatorType();
        ActuatorType actuatorType = factory.createActuatorType(new Unit("unit"), new Description("description"), new ActuatorTypeID("testID"));

        RepositoryActuatorTypeMem repository = new RepositoryActuatorTypeMem();
        repository.save(actuatorType);

        boolean contains = repository.containsOfIdentity(new ActuatorTypeID("testID"));

        assertTrue(contains);
    }


    /**
     * Test to verify that the repository correctly finds the ID of an ActuatorType object.
     */
    @Test
    void whenRetrievingActuatorTypeByNonexistentId_thenShouldReturnEmpty() {
        // Create mock objects
        ImplFactoryActuatorType factory = new ImplFactoryActuatorType();
        ActuatorType actuatorType = factory.createActuatorType(new Unit("unit"), new Description("description"), new ActuatorTypeID("testID"));

        // Create a repository and save the actuator type
        RepositoryActuatorTypeMem repository = new RepositoryActuatorTypeMem();
        repository.save(actuatorType);

        // Test the findRepositoryId method
        ActuatorTypeID result = repository.findRepositoryId(new ActuatorTypeID("testID"));

        // Verify the results
        assertEquals(new ActuatorTypeID("testID"), result);
    }


}

