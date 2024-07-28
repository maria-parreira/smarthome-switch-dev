package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetListOfSensorTypesController;
import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.domain.repository.IRepositorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.dto.SensorTypeDTO;
import smartHomeDDD.persistence.mem.RepositorySensorTypeMem;
import smartHomeDDD.services.ServiceSensorType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for GetSensorTypesController.
 * Test cases:
 * -nullService_ShouldThrowIllegalArgumentException
 * -getTypes_ShouldGiveListWithTypes
 */

class GetSensorTypesControllerTest {
    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     */
    @Test
    void nullService_ShouldThrowIllegalArgumentException() {
        // Arrange
        String expectedMessage = "Service cannot be null";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new GetListOfSensorTypesController(null));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test case to verify that the list of sensor types is returned.
     */
    @Test
    void getTypes_ShouldGiveListWithTypes() {
        // Arrange
        IRepositorySensorType repository = mock(RepositorySensorTypeMem.class);
        FactorySensorType factory = new ImplFactorySensorType();
        ServiceSensorType service = new ServiceSensorType(repository, factory);

        Unit unit = new Unit("Celsius");
        Description description = new Description("Temperature");
        SensorTypeID sensorTypeID = new SensorTypeID("T1");
        Unit unit1 = new Unit("Percentage");
        Description description1 = new Description("Humidity");
        SensorTypeID sensorTypeID1 = new SensorTypeID("T2");
        SensorType sensorType1 = factory.createSensorType(sensorTypeID, description, unit);
        SensorType sensorType2 = factory.createSensorType(sensorTypeID1, description1, unit1);
        when(repository.findAll()).thenReturn(List.of(sensorType1, sensorType2));


        // Act

        GetListOfSensorTypesController controller = new GetListOfSensorTypesController(service);

        // Assert
        assertFalse(controller.getSensorTypes().isEmpty());
        assertTrue(controller.getSensorTypes().contains(new SensorTypeDTO("T1", "Temperature", "Celsius")));
        assertTrue(controller.getSensorTypes().contains(new SensorTypeDTO("T2", "Humidity", "Percentage")));

    }

}
