package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetActuatorTypesController;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.actuatorType.ImplFactoryActuatorType;
import smartHomeDDD.domain.repository.IRepositoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.dto.ActuatorTypeDTO;
import smartHomeDDD.persistence.mem.RepositoryActuatorTypeMem;
import smartHomeDDD.services.ServiceActuatorType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The GetActuatorTypesControllerTest class contains test cases for the
 * GetActuatorTypesController class.
 * Test cases:
 * -nullService_ShouldThrowIllegalArgumentException
 * -getTypes_ShouldGiveListWithTypes
 */

class GetActuatorTypesControllerTest {
    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     */
    @Test
    void nullService_ShouldThrowIllegalArgumentException() {
        // Arrange
        ServiceActuatorType serviceActuatorType = null;
        String expectedMessage = "Service cannot be null";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new GetActuatorTypesController(serviceActuatorType));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test case to verify that the list of actuator types is returned.
     */
    @Test
    void getTypes_ShouldGiveListWithTypes() {
        // Arrange
        IRepositoryActuatorType repository = mock(RepositoryActuatorTypeMem.class);
        FactoryActuatorType factory = new ImplFactoryActuatorType();
        ServiceActuatorType service = new ServiceActuatorType(repository, factory);

        Unit unit = new Unit("Celsius");
        Description description = new Description("Status");
        ActuatorTypeID actuatorTypeID1 = new ActuatorTypeID("T1");
        ActuatorType actuatorType1 = factory.createActuatorType(unit, description, actuatorTypeID1);
        when(repository.findAll()).thenReturn(List.of(actuatorType1));

        // Act
        GetActuatorTypesController controller = new GetActuatorTypesController(service);

        // Assert
        assertFalse(controller.getActuatorTypes().isEmpty());
        assertTrue(controller.getActuatorTypes().contains(new ActuatorTypeDTO("T1", "Status")));
    }


}
