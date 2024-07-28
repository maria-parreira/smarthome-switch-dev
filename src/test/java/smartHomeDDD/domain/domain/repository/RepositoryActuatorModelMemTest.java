package smartHomeDDD.domain.domain.repository;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.persistence.mem.RepositoryActuatorModelMem;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is the respective Test Class of the RepositoryActuatorModel Class. It contains tests for the following scenarios:
 * Creation of a Valid List of actuator Models with existing Models;
 * The comparison between two different Model Lists;
 * The creation and storage of a novel actuator Model and its verification on the list afterward;
 * The verification of an actuator Model that is not in the list;
 * The attempt to obtain all the actuator Models when the list is empty;
 * The attempt to obtain all the actuator Models when the list is not empty;
 * The attempt to save an actuator Model with the same identity as an existing actuator Model;
 */
class RepositoryActuatorModelMemTest {

    @Test
    void validModelList_ShouldCompareBothListsAndReturnTrue() throws org.apache.commons.configuration2.ex.ConfigurationException {
        // Arrange
        List<String> expectedModelNames = Arrays.asList(
                "actuators.OPNCL0100",
                "actuators.SIV280",
                "actuators.SPV300",
                "actuators.ONF01A"
        );
        // Act
        RepositoryActuatorModelMem repository = new RepositoryActuatorModelMem();
        String[] modelList = repository.generateModelList("config.properties");
        List<String> modelListAsList = Arrays.asList(modelList);
        // Assert
        expectedModelNames.forEach(modelName -> assertTrue(modelListAsList.contains(modelName)));
    }

    @Test
    void differentModelList_ShouldCompareBothListsAndReturnFalse() throws org.apache.commons.configuration2.ex.ConfigurationException {
        // Arrange
        List<String> expectedModelNames = Arrays.asList(
                "actuators.sw",
                "actuators.SIswsV280",
                "actuators.SPVaa300",
                "actuators.ASWS"
        );
        // Act
        RepositoryActuatorModelMem repository = new RepositoryActuatorModelMem();
        String[] modelList = repository.generateModelList("config.properties");
        List<String> modelListAsList = Arrays.asList(modelList);
        // Assert
        expectedModelNames.forEach(modelName ->
                assertFalse(modelListAsList.contains(modelName))
        );
    }

    /**
     * Verifies if, after saving an actuator model to an initially empty repository, the repository contains the saved actuator model.
     */
    @Test
    void saveActuatorModelToEmptyRepository_ShouldContainActuatorModel() {
        // Arrange
        RepositoryActuatorModelMem repository = new RepositoryActuatorModelMem();
        ActuatorModelID actuatorModelIdDouble = mock(ActuatorModelID.class);
        ActuatorModel actuatorModelDouble = mock(ActuatorModel.class);
        when(actuatorModelDouble.identity()).thenReturn(actuatorModelIdDouble);

        // Act
        ActuatorModel savedActuatorModel = repository.save(actuatorModelDouble);
        actuatorModelIdDouble = savedActuatorModel.identity();
        Optional<ActuatorModel> retrievedActuatorModelOptional = repository.ofIdentity(actuatorModelIdDouble);

        // Assert
        assertTrue(repository.containsOfIdentity(actuatorModelIdDouble));
        assertTrue(retrievedActuatorModelOptional.isPresent());
    }

    /**
     * Verifies that an empty repository doesn't contain any actuator models.
     */
    @Test
    void emptyRepository_ShouldNotContainActuatorType() {
        // Arrange
        RepositoryActuatorModelMem repository = new RepositoryActuatorModelMem();
        ActuatorModelID actuatorModelIdDouble = mock(ActuatorModelID.class);
        ActuatorModel actuatorModelDouble = mock(ActuatorModel.class);
        when(actuatorModelDouble.identity()).thenReturn(actuatorModelIdDouble);

        // Act
        Optional<ActuatorModel> retrievedActuatorModelOptional = repository.ofIdentity(actuatorModelIdDouble);

        // Assert
        assertFalse(repository.containsOfIdentity(actuatorModelIdDouble));
        assertFalse(retrievedActuatorModelOptional.isPresent());
    }

    /**
     * Ensures that, when the repository is empty, the findAll() method returns an empty iterable.
     */
    @Test
    void findAll_EmptyRepository_ShouldReturnEmptyIterable() {
        // Arrange
        RepositoryActuatorModelMem repository = new RepositoryActuatorModelMem();

        // Act
        Iterable<ActuatorModel> allActuatorModels = repository.findAll();

        // Assert
        assertFalse(allActuatorModels.iterator().hasNext());
    }

    /**
     * Verifies if, when the repository contains actuator models, the findAll() method returns a non-empty iterable.
     */
    @Test
    void findAll_NonEmptyRepository_ShouldReturnNonEmptyIterable() {
        // Arrange
        RepositoryActuatorModelMem repository = new RepositoryActuatorModelMem();
        ActuatorModelID actuatorModelIdDouble1 = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIdDouble2 = mock(ActuatorModelID.class);
        ActuatorModel actuatorModelDouble1 = mock(ActuatorModel.class);
        when(actuatorModelDouble1.identity()).thenReturn(actuatorModelIdDouble1);
        ActuatorModel actuatorModelDouble2 = mock(ActuatorModel.class);
        when(actuatorModelDouble2.identity()).thenReturn(actuatorModelIdDouble2);
        repository.save(actuatorModelDouble1);
        repository.save(actuatorModelDouble2);

        // Act
        Iterable<ActuatorModel> allActuatorModels = repository.findAll();

        // Assert
        assertNotNull(allActuatorModels);
        assertTrue(allActuatorModels.iterator().hasNext());
    }
}