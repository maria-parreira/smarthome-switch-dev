package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.ONF01S;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ONF01SValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This is the test class for the ONF01S Sensor. It tests the following scenarios:
 * - Test the successful instantiation of the ONF01S object
 * - Test that the sameAs method returns true when the object is the same
 * - Test that the sameAs method returns false when the sensor ID is different
 * - Test that the sameAs method returns false when the Device ID is different
 * - Test that the sameAs method returns false when the Sensor Model ID is different
 * - Test that the getValue method returns the correct value for reading 1
 * - Test that the getValue method returns the correct value for reading 2
 * - Test that the getValue method returns the correct value for reading 3
 * - Test that the getValue method returns the correct value for reading 4
 */
class ONF01SAggregateTest {

    /**
     * Test that the constructor creates a new instance of ONF01S with valid parameters.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorID sensorID = new SensorID("ONF01S-1");

        // Act
        ONF01S onf01s = (ONF01S) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Assert
        assertNotNull(onf01s);
        assertEquals(onf01s.identity(), sensorID);
        assertEquals(onf01s.getDeviceID(), deviceId);
        assertEquals(onf01s.getSensorModelID(), sensorModelID);
    }

    /**
     * Test that the sameAs method returns true when the object is the same.
     */
    @Test
    void sameObject_shouldReturnTrue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorID sensorID = new SensorID("ONF01S-1");
        Sensor onf01s = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = onf01s.sameAs(onf01s);

        // Assert
        assertTrue(result);
    }

    /**
     * Test that the sameAs method returns false when the object has a different sensor ID.
     */
    @Test
    void differentIds_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("ONF01S");

        SensorID sensorID = new SensorID("ONF01S-1");
        SensorID sensorID2 = new SensorID("ONF01S-2");

        Sensor onf01s = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Sensor onf01sv2 = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID2);

        // Act
        boolean result = onf01s.sameAs(onf01sv2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test that the sameAs method returns false when the object has a different Device ID.
     */
    @Test
    void differentDeviceIds_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();

        DeviceId deviceId = new DeviceId("Device1");
        DeviceId deviceId2 = new DeviceId("Device2");

        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorID sensorID = new SensorID("ONF01S-1");

        Sensor onf01s = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Sensor onf01sv2 = implFactorySensor.createSensor(deviceId2, sensorModelID, sensorID);

        // Act
        boolean result = onf01s.sameAs(onf01sv2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test that the sameAs method returns false when the object has a different Sensor Model ID.
     */

    @Test
    void differentSensorModelIds_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);

        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorModelID sensorModelID2 = new SensorModelID("WS8600");

        SensorID sensorID = new SensorID("ONF01S-1");

        Sensor onf01s = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Sensor onf01sv2 = implFactorySensor.createSensor(deviceId, sensorModelID2, sensorID);

        // Act
        boolean result = onf01s.sameAs(onf01sv2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that the getValue method correctly returns the value for reading no. 1
     */
    @Test
    void getValueFromReading1_shouldReturnSensorValue(){

        // Arrange
        Integer readingNumber = 1;
        String expectedValue = "ON";

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorID sensorID = new SensorID("ONF01S-1");

        ONF01S onf01s = (ONF01S) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        onf01s.setValue(readingNumber);

        // Act
        ONF01SValue onf01sValue = (ONF01SValue) onf01s.getValue();

        // Assert
        assertEquals(expectedValue, onf01sValue.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading no. 2
     */
    @Test
    void getValueFromReading2_shouldReturnSensorValue(){

        // Arrange
        Integer readingNumber = 2;
        String expectedValue = "OFF";

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorID sensorID = new SensorID("ONF01S-1");

        ONF01S onf01s = (ONF01S) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        onf01s.setValue(readingNumber);

        // Act
        ONF01SValue onf01sValue = (ONF01SValue) onf01s.getValue();

        // Assert
        assertEquals(expectedValue, onf01sValue.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading no. 3
     */
    @Test
    void getValueFromReading3_shouldReturnSensorValue(){

        // Arrange
        Integer readingNumber = 3;
        String expectedValue = "OFF";

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorID sensorID = new SensorID("ONF01S-1");

        ONF01S onf01s = (ONF01S) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        onf01s.setValue(readingNumber);

        // Act
        ONF01SValue onf01sValue = (ONF01SValue) onf01s.getValue();

        // Assert
        assertEquals(expectedValue, onf01sValue.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading no. 4
     */
    @Test
    void getValueFromReading4_shouldReturnSensorValue(){

        // Arrange
        Integer readingNumber = 4;
        String expectedValue = "ON";

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("ONF01S");
        SensorID sensorID = new SensorID("ONF01S-1");

        ONF01S onf01s = (ONF01S) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        onf01s.setValue(readingNumber);

        // Act
        ONF01SValue onf01sValue = (ONF01SValue) onf01s.getValue();

        // Assert
        assertEquals(expectedValue, onf01sValue.toString());
    }
}
