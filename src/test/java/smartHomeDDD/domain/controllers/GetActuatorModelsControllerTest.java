package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetActuatorModelsController;
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.actuatorModel.ImplFactoryActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.dto.ActuatorModelDTO;
import smartHomeDDD.dto.ActuatorTypeDTO;
import smartHomeDDD.persistence.mem.RepositoryActuatorModelMem;
import smartHomeDDD.services.ServiceActuatorModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for GetActuatorModelsController.
 * Test cases:
 * -nullService_ShouldThrowIllegalArgumentException
 * -getModelsFromSpecificType_ShouldGiveListWithModelsFromThatType
 * -getModelsFromNonExistingType_shouldGiveEmptyList
 */

class GetActuatorModelsControllerTest {

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     */
    @Test
    void nullService_ShouldThrowIllegalArgumentException() {
        // Arrange
        ServiceActuatorModel serviceActuatorModels = null;
        String expectedMessage = "Service cannot be null";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new GetActuatorModelsController(serviceActuatorModels));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case to verify that the list of actuator models is returned.
     */
    @Test
    void getModelsFromSpecificType_ShouldGiveListWithModelsFromThatType() {
        // Arrange
        IRepositoryActuatorModel repositoryActuatorModel = mock(RepositoryActuatorModelMem.class);
        FactoryActuatorModel factory = new ImplFactoryActuatorModel();
        ServiceActuatorModel service = new ServiceActuatorModel(repositoryActuatorModel, factory);

        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("T1");
        ActuatorModelID actuatorModelID1 = new ActuatorModelID("ONF01A");
        ActuatorModel actuatorModel1 = factory.createActuatorModel(actuatorModelID1, actuatorTypeID);
        when(repositoryActuatorModel.getModelsByActuatorType(any())).
                thenReturn(List.of(actuatorModel1));

        // Act
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO("T1", "Status");
        GetActuatorModelsController controller = new GetActuatorModelsController(service);
        List<ActuatorModelDTO> actuatorModelDTOs = controller.getActuatorModels(actuatorTypeDTO);
        // Assert
        assertFalse(actuatorModelDTOs.isEmpty());
        assertTrue(actuatorModelDTOs.contains(new ActuatorModelDTO("ONF01A", "T1")));
    }

    /**
     * Test case to verify that the list of actuator models is empty.
     */
    @Test
    void getModelsFromNonExistingType_shouldGiveEmptyList() {
        // Arrange
        IRepositoryActuatorModel repositoryActuatorModel = mock(RepositoryActuatorModelMem.class);
        FactoryActuatorModel factory = new ImplFactoryActuatorModel();
        ServiceActuatorModel service = new ServiceActuatorModel(repositoryActuatorModel, factory);
        when(repositoryActuatorModel.getModelsByActuatorType(any())).
                thenReturn(List.of());
        // Act
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO("T5", "NonExistingType");
        GetActuatorModelsController controller = new GetActuatorModelsController(service);
        List<ActuatorModelDTO> actuatorModelDTOs = controller.getActuatorModels(actuatorTypeDTO);
        // Assert
        assertTrue(actuatorModelDTOs.isEmpty());
    }


}
