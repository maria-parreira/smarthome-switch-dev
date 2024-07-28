package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.ImplFactoryActuator;
import smartHomeDDD.domain.actuator.OPNCL0100;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.DeviceId;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

/**
 * This is a test class for the ImplFactoryActuator class. It tests the following scenario:
 * - Successful instantiation of an OPNCL0100 actuator Object via factory use.
 */
class ImplFactoryActuatorTest {
    /**
     * Tests a successful instantiation of a OPNCL0100 actuator Object using the specified parameters.
     */
    @Test
    void validActuator_ShouldReturnOPNCL0100Instance() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIdDouble = mock(DeviceId.class);
        String actuatorModelNameString = "OPNCL0100";
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Mocking ActuatorModelID behavior
        when(actuatorModelIDDouble.toString()).thenReturn(actuatorModelNameString);


        // Mocking Description behavior
        Description typeDescriptionDouble = mock(Description.class);
        when(typeDescriptionDouble.toString()).thenReturn("OpenClose");

        ImplFactoryActuator factory = new ImplFactoryActuator();

        // Act
        try (MockedConstruction<OPNCL0100> mockedConstruction = mockConstruction(OPNCL0100.class,
                (mock, context) -> {
                    when(mock.getActuatorModelID()).thenReturn(actuatorModelIDDouble);
                    when(mock.getDeviceID()).thenReturn(deviceIdDouble);
                })) {
            Actuator actuatorOPNCL0100 = factory.createActuator(
                    actuatorIDDouble,
                    deviceIdDouble,
                    actuatorModelIDDouble);

            // Assert
            List<OPNCL0100> actuatorTypes = mockedConstruction.constructed();
            assertFalse(actuatorTypes.isEmpty());
            assertNotNull(actuatorOPNCL0100);
        }
    }

    /**
     * Tests the scenario where the ActuatorModelID does not correspond to a valid class.
     * This should result in a ClassNotFoundException being thrown and caught, and the method should return null.
     */
    @Test

    void createActuator_ShouldReturnNull_WhenClassNotFound() {
        // Arrange
        ActuatorID actuatorID = mock(ActuatorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        when(actuatorModelID.toString()).thenReturn("InvalidClassName");

        ImplFactoryActuator factory = new ImplFactoryActuator();

        // Act
        Actuator actuator = factory.createActuator(actuatorID, deviceId, actuatorModelID);

        // Assert
        assertNull(actuator);
    }

    /**
     * Tests the scenario where the ActuatorModelID corresponds to a valid class, but the class does not have a matching constructor.
     * This should result in a NoSuchMethodException being thrown and caught, and the method should return null.
     */
    @Test

    void createActuator_ShouldReturnNull_WhenConstructorNotFound() {
        // Arrange
        ActuatorID actuatorID = mock(ActuatorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        ActuatorModelID actuatorModelID = mock(ActuatorModelID.class);
        when(actuatorModelID.toString()).thenReturn("SomeValidClassNameWithoutMatchingConstructor");

        ImplFactoryActuator factory = new ImplFactoryActuator();

        // Act
        Actuator actuator = factory.createActuator(actuatorID, deviceId, actuatorModelID);

        // Assert
        assertNull(actuator);
    }

}