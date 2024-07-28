package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.ImplFactoryActuator;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The SPV300AggregateTest class tests the SPV300 class.
 * It tests the following scenarios:
 * - valid parameters should create a new instance
 * - same object should return true when comparing the same object
 * - different IDs should return false when comparing two different objects
 * - different values should return false when comparing two different objects
 * - different actuator model ID should return false when comparing two different objects
 * - different device ID should return false when comparing two different objects
 * - set a new value with a valid value
 * - set a new value with an invalid value
 */
class SPV300AggregateTest {
    /**
     * Tests if the SPV300 instance is created with valid parameters.
     */
    @Test
    void validParameters_shouldCreateANewInstance() {
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");

        // Act
        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);

        // Assert
        assertNotNull(actuator);
        assertEquals(actuatorID, actuator.identity());
        assertEquals(actuatorModelID, actuator.getActuatorModelID());
        assertEquals(deviceID, actuator.getDeviceID());
    }
    /**
     * Tests if two SPV300 instances are equal using the same object.
     */
    @Test
    void sameObject_shouldReturnTrue(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");

        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        // Act
        boolean isEquals = actuator.sameAs(actuator);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if two SPV300 instances are equal with different IDs.
     */
    @Test
    void differentIds_shouldReturnFalse(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        ActuatorID actuatorID2 = new ActuatorID("actuatorID2");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");

        Actuator actuator1 = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID2, deviceID, actuatorModelID);
        // Act
        boolean isEquals = actuator1.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if two SPV300 instances are the same with different values.
     */
    @Test
    void differentValues_shouldReturnFalse(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");

        Actuator actuator1 = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        actuator1.setValue(new SPV300Value(20));
        actuator2.setValue(new SPV300Value(15));
        // Act
        boolean isEquals = actuator1.sameAs(actuator2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if two SPV300 instances are the same with different actuator model IDs.
     */
    @Test
    void differentActuatorModelID_shouldReturnFalse(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");
        ActuatorModelID actuatorModelID2 = new ActuatorModelID("ONF01A");

        Actuator actuator1 = factoryActuator.createActuator(actuatorID, deviceID,
                actuatorModelID);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID, deviceID,
                actuatorModelID2);
        // Act
        boolean isEquals = actuator1.sameAs(actuator2);
        // Assert
        assertFalse(isEquals);
    }
    /**
     * Tests if two SPV300 instances are the same with different device IDs.
     */
    @Test
    void differentDeviceID_shouldReturnFalse(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        DeviceId deviceID = new DeviceId("device01");
        DeviceId deviceID2 = new DeviceId("device02");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");

        Actuator actuator1 = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID, deviceID2, actuatorModelID);
        // Act
        boolean isEquals = actuator1.sameAs(actuator2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Sets a new value for the SPV300 instance with a valid value.
     */
    @Test
    void setValueWithValidValue_shouldReturnTheValue(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");

        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        SPV300Value valueDouble2 = mock(SPV300Value.class);
        when(valueDouble2.getValue()).thenReturn(60.0);
        // Act
        Value actualValue = actuator.setValue(valueDouble2);
        // Assert
        assertEquals(valueDouble2, actualValue);
    }

    /**
     * Sets a new value for the SPV300 instance with an invalid value.
     */

    @Test
    void setValueWithInvalidValue_shouldReturnNull(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuatorID");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SPV300");


        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        OPNCL0100Value valueDouble2 = mock(OPNCL0100Value.class);
        when(valueDouble2.getValue()).thenReturn(10);
        // Act
        Value actualValue = actuator.setValue(valueDouble2);
        // Assert
        assertNull(actualValue);
    }
}
