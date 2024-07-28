package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.ImplFactoryActuator;
import smartHomeDDD.domain.actuator.SIV280;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The SIV280AggregateTest class tests the SIV280 class.
 * It tests the following scenarios:
 * - valid parameters should create a new instance
 * - same object should return true
 * - different IDs should return false
 * - different values should return false
 * - different actuator model ID should return false
 * - different device ID should return false
 * - set a new value with a valid value
 * - set a new value with an invalid value
 */
class SIV280AggregateTest {

    /**
     * Tests if valid parameters should create a new instance.
     */
    @Test
    void validParameters_shouldCreateANewInstance() {
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("1");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SIV280");

        // Act
        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);

        // Assert
        assertNotNull(actuator);
        assertEquals(actuatorID, actuator.identity());
        assertEquals(actuatorModelID, actuator.getActuatorModelID());
        assertEquals(deviceID, actuator.getDeviceID());
    }
    /**
     * Tests if two SIV280 instances are the same using the same object.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("1");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SIV280");
        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);

        // Act & Assert
        assertTrue(actuator.sameAs(actuator));
    }

    /**
     * Tests if two SIV280 instances are the same using instances with different IDs.
     */
    @Test
    void differentIds_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID1 = new ActuatorID("1");
        ActuatorID actuatorID2 = new ActuatorID("2");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SIV280");
        Actuator actuator1 = factoryActuator.createActuator(actuatorID1, deviceID, actuatorModelID);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID2, deviceID, actuatorModelID);

        // Act & Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests if two SIV280 instances are the same using instances with different values.
     */
    @Test
    void differentValues_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("1");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SIV280");
        SIV280 siv280_1 = (SIV280) factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        siv280_1.setValue(new SIV280Value(20));
        SIV280 siv280_2 = (SIV280) factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        siv280_2.setValue(new SIV280Value(25));

        // Act & Assert
        assertFalse(siv280_1.sameAs(siv280_2));
    }

    /**
     * Tests if two SIV280 instances are the same using instances with different actuator model IDs.
     */
    @Test
    void differentActuatorModelID_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("1");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID1 = new ActuatorModelID("SIV280");
        ActuatorModelID actuatorModelID2 = new ActuatorModelID("SPV300");
        Actuator actuator1 = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID1);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID2);

        // Act & Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests if two SIV280 instances are the same using instances with different device IDs.
     */
    @Test
    void differentDeviceID_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("1");
        DeviceId deviceID1 = new DeviceId("device01");
        DeviceId deviceID2 = new DeviceId("device02");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SIV280");
        Actuator actuator1 = factoryActuator.createActuator(actuatorID, deviceID1, actuatorModelID);
        Actuator actuator2 = factoryActuator.createActuator(actuatorID, deviceID2, actuatorModelID);

        // Act & Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests the setting a new value with a valid value, should return the value.
     */
    @Test
    void setValueWithValidValue_shouldReturnTheValue(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("1");
        DeviceId deviceID1 = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SIV280");

        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID1,
                actuatorModelID);
        SIV280Value valueToSet = new SIV280Value(30);

        // Act
        Value actualValue = actuator
                .setValue(valueToSet);

        // Assert
        assertEquals(valueToSet, actualValue);
    }

    /**
     * Tests the setting a new value with an invalid value, should return null.
     */
    @Test
    void setValueWithInvalidValue_shouldReturnNull(){
        // Arrange
        ImplFactoryActuator factoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("1");
        DeviceId deviceID1 = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("SIV280");


        Actuator actuator = factoryActuator.createActuator(actuatorID, deviceID1,
                actuatorModelID);
        OPNCL0100Value valueDouble = mock(OPNCL0100Value.class);
        when(valueDouble.getValue()).thenReturn(10);

        // Act
        Value actualValue = actuator.setValue(valueDouble);

        // Assert
        assertNull(actualValue);
    }
}
