package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetActuatorModelsController;
import smartHomeDDD.controllers.GetListOfSensorModelsController;
import smartHomeDDD.controllers.GetListOfSensorTypesController;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.actuatorModel.ImplFactoryActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.ImplFactorySensorModel;
import smartHomeDDD.domain.repository.IRepositorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.dto.ActuatorModelDTO;
import smartHomeDDD.dto.ActuatorTypeDTO;
import smartHomeDDD.dto.SensorModelDTO;
import smartHomeDDD.dto.SensorTypeDTO;
import smartHomeDDD.persistence.mem.RepositoryActuatorModelMem;
import smartHomeDDD.persistence.mem.RepositorySensorModelMem;
import smartHomeDDD.persistence.mem.RepositorySensorTypeMem;
import smartHomeDDD.services.ServiceActuatorModel;
import smartHomeDDD.services.ServiceSensorModel;
import org.junit.jupiter.api.Test;
import smartHomeDDD.services.ServiceSensorType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for GetSensorModelsController.
 * Test cases:
 * -nullService_ShouldThrowIllegalArgumentException
 * -getModelsFromSpecificType_ShouldGiveListWithModelsFromThatType
 * -getModelsFromNonExistingType_shouldGiveEmptyList
 */


class GetSensorModelsControllerTest {
    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     */
    @Test
    void nullService_ShouldThrowIllegalArgumentException() {
        // Arrange
        String expectedMessage = "Service cannot be null";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new GetListOfSensorModelsController(null));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case to verify that the list of sensor models is returned.
     */
    @Test
    void getModelsFromSpecificType_ShouldGiveListWithModelsFromThatType() {
        //Arrange
        IRepositorySensorModel repository = mock(RepositorySensorModelMem.class);
        FactorySensorModel factory = new ImplFactorySensorModel();
        ServiceSensorModel service = new ServiceSensorModel(repository, factory);

        SensorTypeID sensorTypeID = new SensorTypeID("T1");
        SensorModelID sensorModelID = new SensorModelID("GA100K");

        SensorModel sensorModel1 = factory.createSensorModel(sensorModelID, sensorTypeID);
        when(repository.getModelsBySensorType(any())).thenReturn(List.of(sensorModel1));
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("T1", "Temperature", "Celsius");

        GetListOfSensorModelsController controller = new GetListOfSensorModelsController(service);

        // Act
        List<SensorModelDTO> list = controller.getSensorModels(sensorTypeDTO);

        // Assert
        assertFalse(list.isEmpty());
        assertTrue(list.contains(new SensorModelDTO("GA100K", "T1")));
    }

    /**
     * Test case to verify that the list of sensor models is empty.
     */
    @Test
    void getModelsFromNonExistingType_shouldGiveEmptyList() {
        // Arrange
        IRepositorySensorModel repositorySensorModel = mock(RepositorySensorModelMem.class);
        FactorySensorModel factory = new ImplFactorySensorModel();
        ServiceSensorModel service = new ServiceSensorModel(repositorySensorModel, factory);
        when(repositorySensorModel.getModelsBySensorType(any())).
                thenReturn(List.of());
        // Act
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("T1", "Temperature", "Celsius");
        GetListOfSensorModelsController controller = new GetListOfSensorModelsController(service);
        List<SensorModelDTO> sensorModelDTOs = controller.getSensorModels(sensorTypeDTO);
        // Assert
        assertTrue(sensorModelDTOs.isEmpty());
    }

}
