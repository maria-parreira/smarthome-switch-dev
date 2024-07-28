package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.PC500W;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.PC500WValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the PC500W Sensor.
 * The tests cover the following scenarios:
 * - Creating a valid sensor
 * - Getting the sensor's identity
 * - Getting the sensor's value
 * - Getting the sensor's device ID
 * - Getting the sensor's sensor model ID
 * - Setting the sensor's data
 * - Comparing two sensors
 * - Comparing a sensor with itself
 * - Comparing a sensor with a different object
 * - Comparing two sensors with different IDs
 * - Comparing two sensors with different device IDs
 * - Comparing two equal sensors
 * - Comparing two different sensors
 * - Comparing a sensor with a different object
 */

class PC500WAggregateTest {

    /**
     * Test case for creating a valid sensor.
     * The test verifies that a non-null object is returned and the device ID, sensor model ID, and sensor ID are as expected.
     */
    @Test
    void validSensor_shouldReturnNonNullObject() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID = new SensorID( "sensorID");

        // Act
        smartHomeDDD.domain.sensor.PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);

        // Assert
        assertNotNull(pc500w);
        assertEquals(sensorID, pc500w.identity());
        assertEquals(PC500W, pc500w.getSensorModelID());
        assertEquals(deviceId, pc500w.getDeviceID());
    }

    /**
     * Test case for checking the identity of a sensor.
     * The test verifies that the identity method of the PC500W class returns the expected SensorID.
     */
    @Test
    void validIdentity_ShouldReturnEqualsID() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        SensorID actualId = pc500w.identity();

        // Assert
        assertEquals(sensorID, actualId);
    }

    /**
     * Test case for getting the value of a sensor at a specific time.
     * The test verifies that the getValue method returns the correct value when the time exists in the readings.
     */
    @Test
    void getValue_ExistingTime_ShouldReturnCorrectValue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        LocalTime time = LocalTime.of(3, 0);
        PC500WValue expectedValue = new PC500WValue(100.0);

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        Value actualValue = pc500w.getValue(time);

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    /**
     * Test case for getting the value of a sensor at a time that does not exist in the readings.
     * The test verifies that the getValue method returns the default value when the time does not exist in the readings.
     */
    @Test
    void getValue_NonExistingTime_ShouldReturnDefaultValue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");


        LocalTime time = LocalTime.of(1, 0);
        PC500WValue expectedValue = new PC500WValue(0.0);

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        Value actualValue = pc500w.getValue(time);

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    /**
     * Test case for getting the DeviceID of a sensor.
     * This test verifies that the method returns the correct DeviceId that was set during the construction of the PC500W object.
     */
    @Test
    public void getDeviceID_ShouldReturnCorrectDeviceID() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        DeviceId returnedDeviceId = pc500w.getDeviceID();

        // Assert
        assertEquals(deviceId, returnedDeviceId);
    }

    /**
     * Test case for getting the SensorModelID of a sensor.
     * This test verifies that the method returns the correct SensorModelID that was set during the construction of the PC500W object.
     */
    @Test
    public void getSensorModelID_ShouldReturnCorrectSensorModelID() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        SensorModelID returnedSensorModelID = pc500w.getSensorModelID();

        // Assert
        assertEquals(PC500W, returnedSensorModelID);
    }

    /**
     * Test case for setting the data of a sensor.
     * This test verifies that the setData method returns a map with the correct readings at the correct times.
     */
    @Test
    void setData_ShouldReturnCorrectReadings() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        Map<LocalTime, Double> readings = pc500w.setData();

        // Assert
        assertEquals(100.0, readings.get(LocalTime.of(3, 0)));
        assertEquals(200.0, readings.get(LocalTime.of(15, 15)));
        assertEquals(250.5, readings.get(LocalTime.of(12, 30)));
        assertEquals(300.0, readings.get(LocalTime.of(18, 2)));
    }

    /**
     * Test case for comparing a PC500W object with itself.
     * The test verifies that the sameAs method of the PC500W class returns true when comparing the same object.
     */
    @Test
    void samePC500WObject_shouldReturnTrue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        boolean result = pc500w.sameAs(pc500w);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing a PC500W object with a non-PC500W object.
     * The test verifies that the sameAs method of the PC500W class returns false when comparing a PC500W object with a non-PC500W object.
     */
    @Test
    void twoDifferentPC500WObject_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");
        Object obj = new Object();
        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);

        // Act
        boolean result = pc500w.sameAs(obj);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two PC500W objects with different SensorIDs.
     * The test verifies that the sameAs method of the PC500W class returns false when comparing two objects with different SensorIDs.
     */
    @Test
    void differentPC500WIds_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID1 =  new SensorID ("sensorID1");
        SensorID sensorID2 =  new SensorID ("sensorID2");

        // Act
        PC500W pc500w1 = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID1);
        PC500W pc500w2 = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID2);

        boolean result = pc500w1.sameAs(pc500w2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two PC500W objects with different DeviceIds.
     * The test verifies that the sameAs method of the PC500W class returns false when comparing two objects with different DeviceIds.
     */
    @Test
    void differentPC500WDeviceIds_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId1 = new DeviceId("deviceID1");
        DeviceId deviceId2 = new DeviceId("deviceID2");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w1 = (PC500W) implFactorySensor.createSensor(deviceId1, PC500W, sensorID);
        PC500W pc500w2 = (PC500W) implFactorySensor.createSensor(deviceId2, PC500W, sensorID);

        boolean result = pc500w1.sameAs(pc500w2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing a PC500W object with itself.
     * The test verifies that the equals method of the PC500W class returns true when comparing the same object.
     */
    @Test
    void whenComparingItself_shouldReturnTrue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        boolean result = pc500w.equals(pc500w);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two PC500W objects with the same values.
     * The test verifies that the equals method of the PC500W class returns true when comparing two objects with the same DeviceId, SensorModelID, and SensorID.
     */
    @Test
    void twoEqualPC500W_shouldReturnTrue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w1 = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        PC500W pc500w2 = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);

        boolean result = pc500w1.equals(pc500w2);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two PC500W objects with different values.
     * The test verifies that the equals method of the PC500W class returns false when comparing two objects with different DeviceIds, SensorModelIDs, and SensorIDs.
     */
    @Test
    void twoDifferentPC500W_shouldReturnFalse() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId1 = new DeviceId("deviceID1");
        DeviceId deviceId2 = new DeviceId("deviceID2");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID1 =  new SensorID ("sensorID1");
        SensorID sensorID2 =  new SensorID ("sensorID2");

        // Act
        PC500W pc500w1 = (PC500W) implFactorySensor.createSensor(deviceId1, PC500W, sensorID1);
        PC500W pc500w2 = (PC500W) implFactorySensor.createSensor(deviceId2, PC500W, sensorID2);

        boolean result = pc500w1.equals(pc500w2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing a PC500W object with a non-PC500W object.
     * The test verifies that the equals method of the PC500W class returns false when comparing a PC500W object with a non-PC500W object.
     */
    @Test
    void whenComparingPC500WWithDifferentObject_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelID PC500W = new SensorModelID("PC500W");
        SensorID sensorID =  new SensorID ("sensorID");

        // Act
        PC500W pc500w = (PC500W) implFactorySensor.createSensor(deviceId, PC500W, sensorID);
        Object obj = new Object();

        boolean result = pc500w.equals(obj);

        // Assert
        assertFalse(result);
    }

}
