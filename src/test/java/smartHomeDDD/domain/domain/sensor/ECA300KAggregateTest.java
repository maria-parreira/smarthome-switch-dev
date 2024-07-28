package smartHomeDDD.domain.domain.sensor;


import smartHomeDDD.domain.sensor.ECA300K;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ECA300KValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class serves as a comprehensive suite of unit tests for the ECA300K sensor aggregate implementation.
 * - The tests cover various aspects, including:
 * - The creation of valid sensor instances.
 * - Ensuring their non-null status.
 * - Validating the accuracy of the identity retrieval mechanism.
 */

class ECA300KAggregateTest {

    /**
     * Tests if a valid sensor is created and returned as a non-null object.
     */
    @Test
    void validSensor_shouldReturnNonNullObject_shouldCreateValidObject() {
        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");
        SensorID sensorID = new SensorID("Sensor1"); // include in aggregate
        ImplFactorySensor implFactorySensor = new ImplFactorySensor(); //include in aggregate
        // Act
        ECA300K mySensor = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        // Assert
        assertNotNull(mySensor);
    }

    /**
     * Tests if the sensor's identity returns expected SensorID.
     */
    @Test
    void validIdentity_ShouldReturnID() {

        // Arrange
        SensorID expectedSensorID = new SensorID("Sensor1");
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, expectedSensorID);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(expectedSensorID.toString(), actualId.toString());
    }

    /**
     * Test case for the sameAs method in the ECA300K class when comparing a ECA300K object with a non-ECA300K object.
     * The test verifies that the sameAs method returns false when a ECA300K object is compared with a non-ECA300K object.
     */
    @Test
    void nonAVPC500WObject_ShouldReturnFalse() {

        // Arrange
        SensorID expectedSensorID = new SensorID("Sensor1");
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, expectedSensorID);

        Object obj = new Object();

        //Act and Assert
        boolean isEquals = mySensor.sameAs(obj);
        assertFalse(isEquals);
    }


    /**
     * Tests the behavior of the sameAs method when comparing two ECA300K sensor instances
     * with the same parameters.
     * The method should return true, indicating that the sensors are considered equal.
     */
    @Test
    void sameAs_SameObject_ShouldReturnTrue() {

        // Arrange

        SensorID expectedSensorID = new SensorID("Sensor1");
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor1 = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, expectedSensorID);
        ECA300K mySensor2 = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, expectedSensorID);

        // Act and Assert
        assertTrue(mySensor1.sameAs(mySensor2));
    }

    /**
     * Tests the behavior of the sameAs method when comparing two ECA300K sensor instances
     * with different DeviceId and SensorModelID but the same SensorID.
     * The method should return false, indicating that the sensors are not considered equal.
     */
    @Test
    void sameAs_DifferentDeviceAndModel_SameSensorID_ShouldReturnFalse() {

        // Arrange
        SensorID expectedSensorID = new SensorID("Sensor1");
        DeviceId deviceId = new DeviceId("Device 1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");

        DeviceId deviceId2 = new DeviceId("Device 2");
        SensorModelID sensorModelId2 = new SensorModelID("ECA300K");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor1 = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, expectedSensorID);
        ECA300K mySensor2 = (ECA300K) implFactorySensor.createSensor(deviceId2, sensorModelId2, expectedSensorID);

        // Act and Assert
        boolean isEquals = mySensor1.sameAs(mySensor2);
        assertFalse(isEquals);
    }

    /**
     * Test case for the getDeviceID method in the ECA300K class.
     * This test verifies that the method returns the correct DeviceId that was set during the construction of the ECA300K object.
     */
    @Test
    void getDeviceID_ShouldReturnCorrectDeviceID() {
        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor1 = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorId);

        //Act
        DeviceId actualDeviceID = mySensor1.getDeviceID();
        // Assert
        assertEquals(deviceId, actualDeviceID);
    }

    /**
     * Test case for the getSensorModelID method in the ECA300K class.
     * This test verifies that the method returns the correct SensorModelID that was set during the construction of the ECA300K object.
     */
    @Test
    void getSensorModelID_ShouldReturnCorrectSensorModelID() {
        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor1 = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorId);

        //Act
        SensorModelID actualSensorModelID = mySensor1.getSensorModelID();
        // Assert
        assertEquals(sensorModelID, actualSensorModelID);
    }

    /**
     * Test case for the getValue method in the ECA300K class when the start and end time are equals.
     * The test verifies that the getValue method returns the correct value when the time interval is invalid
     */
    @Test
    void getValue_InvalidTime_ShouldReturnEnergyConsumptionZero() {

        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor1 = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorId);

        LocalTime start = LocalTime.of(3, 0);
        LocalTime end = LocalTime.of(3, 0);

        ECA300KValue expectedValue = new ECA300KValue(0.0);

        // Act
        ECA300KValue actualValue = (ECA300KValue) mySensor1.getValue(start, end);
        // Assert
        assertEquals(expectedValue.toString(), actualValue.toString());
    }

    /**
     * Test case for the getValue method in the ECA300K class when the start and end time exists in readings.
     * The test verifies that the getValue method returns the correct value when the time interval is valid.
     */
    @Test
    void getValue_ExistentTime_ShouldReturnEnergyConsumptionPerHour() {

        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("ECA300K");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        ECA300K mySensor1 = (ECA300K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorId);

        LocalTime start = LocalTime.of(3, 0);
        LocalTime end = LocalTime.of(11, 30);

        ECA300KValue expectedValue = new ECA300KValue(3.75);
        // Act
        ECA300KValue actualValue = (ECA300KValue) mySensor1.getValue(start, end);
        // Assert
        assertEquals(expectedValue.toString(), actualValue.toString());
    }


}
