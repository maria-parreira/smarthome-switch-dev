package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.ImplFactoryActuator;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.OPNCL0100Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the test class for the OPNCL0100 actuator. It lists the following scenarios:
 * - Successful instantiation of the OPNCL0100 object with mock dependencies.
 * - Equality of ID when the identity method is called, ensuring it returns the correct ActuatorID.
 * - Equality of two OPNCL0100 actuator Objects when they have the same attributes.
 * - Non-equality of two OPNCL0100 actuator Objects when only the Value differs.
 * - Non-equality of two OPNCL0100 actuator Objects when only the ActuatorID differs.
 * - Non-equality of two OPNCL0100 actuator Objects when only the DeviceID differs.
 * - Non-equality of two OPNCL0100 actuator Objects when only the ActuatorModelID differs.
 * -
 *
 */
class OPNCL0100AggregateTest {
    /**
     * Tests a successful instantiation of the OPNCL0100 actuator Object
     */
    @Test
    void validActuator_shouldReturnNonNullObject(){
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        OPNCL0100Value opncl0100Value = new OPNCL0100Value(20);
        ActuatorID actuatorID = new ActuatorID("actuator01");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        Actuator actuator = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        actuator.setValue(opncl0100Value);
        // Assert
        assertNotNull(actuator);
        assertEquals(actuatorID, actuator.identity());
        assertEquals(actuatorModelID, actuator.getActuatorModelID());
        assertEquals(deviceID, actuator.getDeviceID());
    }

    /**
     * Tests that the identity method correctly retrieves the ActuatorID used during instantiation.
     * @throws IllegalArgumentException throws an exception if one of Value Objects is invalid.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuator01");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        Actuator actuator = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        // Act
        ActuatorID actualId = actuator.identity();
        // Assert
        assertEquals(actuatorID, actualId);
    }
    /**
     * Tests that two OPNCL0100 actuator objects with the same state are considered equivalent by the Equals method.
     */
    @Test
    void EqualsOPNCL0100Objects_ShouldReturnTrue(){
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuator01");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        // Act and Assert
        assertTrue(actuator1.sameAs(actuator2));
    }
    /**
     * Tests that two OPNCL0100 actuator objects with different actuator IDs are not considered equivalent.
     */
    @Test
    void OPNCL0100ObjectsWithDifferentIDs_shouldReturnFalse(){
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuator01");
        ActuatorID actuatorID2 = new ActuatorID("actuator02");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");

        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID2, deviceID, actuatorModelID);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that two OPNCL0100 actuator objects with different Device IDs are not considered equivalent.
     */
    @Test
    void OPNCL0100ObjectsWithDifferentDeviceIDs_shouldReturnFalse(){
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuator01");
        DeviceId deviceID = new DeviceId("device01");
        DeviceId deviceID2 = new DeviceId("device02");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");

        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID, deviceID2, actuatorModelID);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that two OPNCL0100 actuator objects with different actuatorModel IDs are not considered equivalent.
     */
    @Test
    void OPNCL0100ObjectsWithDifferentActuatorModelIDs_shouldReturnFalse() {
        // Arrange
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID actuatorID = new ActuatorID("actuator01");
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        ActuatorModelID actuatorModelID2 = new ActuatorModelID("SPV300");

        Actuator actuator1 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        Actuator actuator2 = implFactoryActuator.createActuator(actuatorID, deviceID, actuatorModelID2);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }
    /**
     * Test case for setting a valid value (between 0 and 100) to an instance of OPNCL0100.
     * It should set the value and return it.
     */
    @Test
    void validSetOPNCL0100Value_ShouldReturnValid() {
        // arrange
        String actuatorID = "actuator01";
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        int newValue= 3;
        OPNCL0100Value newOpncl0100Value = new OPNCL0100Value(newValue);
        ActuatorID expectedActuatorID = new ActuatorID(actuatorID);
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");

        // Act
        Actuator actuator = implFactoryActuator.createActuator(expectedActuatorID, deviceID, actuatorModelID);
        actuator.setValue(newOpncl0100Value);

        String expectedValueString = "3";
        // assert
        assertEquals(expectedValueString, actuator.getValue().toString());
    }
    /**
     * Test case for setting a frontier value (0) to an instance of OPNCL0100.
     * It should set the value and return it.
     */
    @Test
    void validSetZeroValueOPNCL0100_ShouldReturnValid() {
        // arrange
        String actuatorID = "actuator01";
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        int newValue= 0;
        OPNCL0100Value newOpncl0100Value = new OPNCL0100Value(newValue);
        ActuatorID expectedActuatorID = new ActuatorID(actuatorID);
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        Actuator actuator = implFactoryActuator.createActuator(expectedActuatorID, deviceID, actuatorModelID);
        actuator.setValue(newOpncl0100Value);
        String expectedValueString = "0";
        // assert
        assertEquals(expectedValueString, actuator.getValue().toString());
    }
    /**
     * Test case for setting a frontier value (100) to an instance of OPNCL0100.
     * It should return null.
     * @throws IllegalArgumentException if the value is not an instance of OPNCL0100Value
     */
    @Test
    void validSetHundredValueOPNCL0100_ShouldReturnValid() {
        // arrange
        String actuatorID = "actuator01";
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        int newValue= 100;
        OPNCL0100Value newOpncl0100Value = new OPNCL0100Value(newValue);
        ActuatorID expectedActuatorID = new ActuatorID(actuatorID);
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        Actuator actuator = implFactoryActuator.createActuator(expectedActuatorID, deviceID, actuatorModelID);
        actuator.setValue(newOpncl0100Value);
        String expectedValueString = "100";
        // assert
        assertEquals(expectedValueString, actuator.getValue().toString());
    }
    /**
     * Test case for setting an invalid value to an instance of OPNCL0100 (below zero).
     */
    @Test
    void validOPNCL0100_SetInvalidValueBelowZero_shouldReturnNull(){
        // Arrange
        String actuatorID = "actuator01";
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID expectedActuatorID = new ActuatorID(actuatorID);
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");


        // Act
        Actuator actuator = implFactoryActuator.createActuator(expectedActuatorID, deviceID,
                actuatorModelID);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> actuator.setValue(new OPNCL0100Value(-1)));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Value out of range";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
    /**
     * Test case for setting an invalid value to an instance of OPNCL0100 (above hundred).
     */
    @Test
    void validOPNCL0100_SetInvalidValueAboveHundred_shouldReturnNull(){
        // arrange
        String actuatorID = "actuator01";
        ImplFactoryActuator implFactoryActuator = new ImplFactoryActuator();
        ActuatorID expectedActuatorID = new ActuatorID(actuatorID);
        DeviceId deviceID = new DeviceId("device01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("OPNCL0100");
        // Act
        Actuator actuator = implFactoryActuator.createActuator(expectedActuatorID, deviceID,
                actuatorModelID);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> actuator.setValue(new OPNCL0100Value(101)));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Value out of range";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}