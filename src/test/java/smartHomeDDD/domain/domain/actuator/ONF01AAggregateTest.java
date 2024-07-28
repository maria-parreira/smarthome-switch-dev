package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.ImplFactoryActuator;
import smartHomeDDD.domain.actuator.ONF01A;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ONF01AValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the aggregate test class for the ONF01A actuator. It lists the following scenarios:
 * Successful instantiation of the ONF01A object with mock dependencies.
 * Equality of ID when the identity method is called, ensuring it returns the correct ActuatorID.
 * Two ONF01A actuator objects are considered equal when they have the same values.
 * Two ONF01A actuator objects are not considered equal when they have different actuator IDs.
 * Two ONF01A actuator objects are not considered equal when they have different Device IDs.
 * Two ONF01A actuator objects are not considered equal when they have different actuatorModel IDs.
 * Set a new value with a valid value.
 * Set a new value with an invalid value.
 */

class ONF01AAggregateTest {
    /**
     * Tests the instantiation of a new ONF01A with valid parameters.
     */
    @Test
    void validParameters_shouldCreateANewInstance(){
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        // Act
        Actuator actuator = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        // Assert
        assertNotNull(actuator);
        assertEquals(actuatorID, actuator.identity());
        assertEquals(actuatorModelID, actuator.getActuatorModelID());
        assertEquals(deviceID, actuator.getDeviceID());
    }
    /**
     * Verifies that the identity method correctly retrieves the ActuatorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        Actuator actuator = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);

        // Act
        ActuatorID actualId = actuator.identity();
        // Assert
        assertEquals(actuatorID, actualId);
    }
    /**
     * Tests that the equals method returns true when two ONF01A actuator objects are the same.
     */
    @Test
    void EqualsONF01AObjects_ShouldReturnTrue() {
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        // Act and Assert
        assertTrue(actuator1.sameAs(actuator2));
    }
    /**
     * Tests that two ONF01A actuator objects with different actuator IDs are not considered as the same object when using the sameAs method.
     */
    @Test
    void ONF01AObjectsWithDifferentIDs_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        ActuatorID actuatorID2 = new ActuatorID("Onoffy2");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID2, deviceID, actuatorModelID);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }
    /**
     * Tests that two ONF01A actuator objects with different Device IDs are not considered the same.
     */
    @Test
    void ONF01AObjectsWithDifferentDeviceIDs_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        DeviceId deviceID = new DeviceId("device01");
        DeviceId deviceID2 = new DeviceId("device02");
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID, deviceID2, actuatorModelID);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }
    /**
     * Tests that two ONF01A actuator objects with different actuatorModel IDs are not considered equivalent.
     */
    @Test
    void ONF01AObjectsWithDifferentActuatorModelIDs_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");
        ActuatorModelID actuatorModelID2 = new ActuatorModelID("OPNCL0100");

        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID2);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }
    /**
     * Test case for setting a valid value to an instance of ONF01A, where the value is either 'ON' or 'OFF', where
     * the value is set to 'OFF' from 'ON'.
     */
    @Test
    void validSetValueONF01A_ShouldReturnValid() {
        // arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        String newValue = "OFF";
        ONF01AValue newONF01AValue = new ONF01AValue(newValue);
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        DeviceId deviceID = new DeviceId("device01");

        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        // Act
        ONF01A ONF01A = (ONF01A) implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        ONF01A.setValue(newONF01AValue);

        String expectedValue = "OFF";
        // assert
        assertEquals(ONF01A.getONF01AValue().getValue(), expectedValue);
    }
    /**
     * Test case for setting an invalid value to an instance of ONF01A, where the value isn't either 'ON' or 'OFF'.
     */
    @Test
    void validONF01A_SetInvalidValueNotOnOrOff_shouldReturnNull() {
        // arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("Onoffy");
        DeviceId deviceID = new DeviceId("device01");

        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        // Act
        Actuator actuator = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actuator.setValue(new ONF01AValue("OFE"));
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Invalid value for ONF01AValue. Must be 'ON' or 'OFF'.";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

}