package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensor.WS8600;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.WS8600Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This is the test class for the WS8600 Sensor. It tests the following scenarios:
 * - Test the successful instantiation of the WS8600 object
 * - Test that the sameAs method returns true when the object is the same
 * - Test that the sameAs method returns false when the sensor ID is different
 * - Test that the sameAs method returns false when the Device ID is different
 * - Test that the sameAs method returns false when the Sensor Model ID is different
 * - Test that the getValue method returns the correct value for reading 1
 * - Test that the getValue method returns the correct value for reading 2
 */
class WS8600AggregateTest {

    /**
     * Test that the constructor creates a new instance of WS8600 with valid parameters.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("WS8600");
        SensorID sensorID = new SensorID("WS8600-1");

        // Act
        WS8600 ws8600 = (WS8600) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Assert
        assertNotNull(ws8600);
        assertEquals(ws8600.identity(), sensorID);
        assertEquals(ws8600.getDeviceID(), deviceId);
        assertEquals(ws8600.getSensorModelID(), sensorModelID);
    }

    /**
     * Test that the sameAs method returns true when the object is the same.
     */
    @Test
    void sameObject_shouldReturnTrue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("WS8600");
        SensorID sensorID = new SensorID("WS8600-1");
        Sensor ws8600 = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = ws8600.sameAs(ws8600);

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
        SensorModelID sensorModelID = new SensorModelID("WS8600");

        SensorID sensorID = new SensorID("WS8600-1");
        SensorID sensorID2 = new SensorID("WS8600-2");

        Sensor ws8600 = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Sensor ws8600v2 = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID2);

        // Act
        boolean result = ws8600.sameAs(ws8600v2);

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

        SensorModelID sensorModelID = new SensorModelID("WS8600");
        SensorID sensorID = new SensorID("WS8600-1");

        Sensor ws8600 = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Sensor ws8600v2 = implFactorySensor.createSensor(deviceId2, sensorModelID, sensorID);

        // Act
        boolean result = ws8600.sameAs(ws8600v2);

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

        SensorModelID sensorModelID = new SensorModelID("WS8600");
        SensorModelID sensorModelID2 = new SensorModelID("SUNRISE407");

        SensorID sensorID = new SensorID("WS8600-1");

        Sensor ws8600 = implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Sensor ws8600v2 = implFactorySensor.createSensor(deviceId, sensorModelID2, sensorID);

        // Act
        boolean result = ws8600.sameAs(ws8600v2);

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
        String expectedValue = "Wind Speed: 10.5 Km/h; Wind Direction: 0.0 rad - East";

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("WS8600");
        SensorID sensorID = new SensorID("WS8600-1");

        WS8600 ws8600 = (WS8600) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        ws8600.setValue(readingNumber);

        // Act
        WS8600Value ws8600Value = (WS8600Value) ws8600.getValue();

        // Assert
        assertEquals(expectedValue, ws8600Value.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading no. 2
     */
    @Test
    void getValueFromReading2_shouldReturnSensorValue(){

        // Arrange
        Integer readingNumber = 2;
        String expectedValue = "Wind Speed: 20.5 Km/h; Wind Direction: 4.714 rad - South";

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("Device1");
        SensorModelID sensorModelID = new SensorModelID("WS8600");
        SensorID sensorID = new SensorID("WS8600-1");

        WS8600 ws8600 = (WS8600) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        ws8600.setValue(readingNumber);

        // Act
        WS8600Value ws8600Value = (WS8600Value) ws8600.getValue();

        // Assert
        assertEquals(expectedValue, ws8600Value.toString());
    }
}
