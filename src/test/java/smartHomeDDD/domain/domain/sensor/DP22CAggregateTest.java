package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.DP22C;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is aggregate test class for the DP22C Sensor.
 * It encompasses the following scenarios:
 * - sensor object creation
 * - identity retrieval
 * - comparison of two sensor objects
 * - comparison of a sensor object with a non-sensor object
 * - retrieval of the device ID
 * - retrieval of the sensor model ID
 */
class DP22CAggregateTest {

    /**
     * Tests a successful instantiation of the DP22C Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject() {
        // Arrange
        FactorySensor factorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("ValidDeviceID");
        SensorModelID sensorModelID = new SensorModelID("DP22C");
        SensorID sensorID =  new SensorID ("ValidSensorID");

        // Act
        DP22C mySensor = (DP22C) factorySensor.createSensor(deviceID, sensorModelID, sensorID);

        // Assert
        assertNotNull(mySensor);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {
        // Arrange
        FactorySensor factorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("ValidDeviceID");
        SensorModelID sensorModelID = new SensorModelID("DP22C");
        SensorID sensorID = new SensorID ("ValidSensorID");


        DP22C mySensor = (DP22C) factorySensor.createSensor(deviceID, sensorModelID, sensorID);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(sensorID, actualId);
    }

    /**
     * Test case for the sameAs method in the DP22C class when comparing a DP22C object with a non-DP22C object.
     * The test verifies that the sameAs method returns false when a DP22C object is compared with a non-DP22C object.
     */
    @Test
    void nonDP22CObject_ShouldReturnFalse() {

        // Arrange
        FactorySensor factorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("ValidDeviceID");
        SensorModelID sensorModelID = new SensorModelID("DP22C");
        SensorID sensorID = new SensorID ("ValidSensorID");

        DP22C dp22c = (DP22C) factorySensor.createSensor(deviceID, sensorModelID, sensorID);

        Object obj = new Object();

        //Act + Assert
        assertFalse(dp22c.sameAs(obj));
    }

    /**
     * Test case for the sameAs method in the DP22C class when comparing the same object.
     * The test verifies that the sameAs method returns true when the same object is compared.
     */
    @Test
    void sameAs_SameObject_ShouldReturnTrue() {

        // Arrange
        FactorySensor factorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("ValidDeviceID");
        SensorModelID sensorModelID = new SensorModelID("DP22C");
        SensorID sensorID1 = new SensorID ("ValidSensorID");
        SensorID sensorID2 = new SensorID ("ValidSensorID");


        DP22C dp22c1 = (DP22C) factorySensor.createSensor(deviceID, sensorModelID, sensorID1);

        DP22C dp22c2 = (DP22C) factorySensor.createSensor(deviceID, sensorModelID, sensorID2);

        // Act and Assert
        assertTrue(dp22c1.sameAs(dp22c2));
    }

    /**
     * Test case for the getDeviceID method in the DP22C class.
     * This test verifies that the method returns the correct DeviceId that was set during the construction of the DP22C object.
     */
    @Test
    void getDeviceID_ShouldReturnCorrectDeviceID() {
        // Arrange
        FactorySensor factorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("ValidDeviceID");
        SensorModelID sensorModelID = new SensorModelID("DP22C");
        SensorID sensorID = new SensorID ("ValidSensorID");

        //Act
        DP22C sensor = (DP22C) factorySensor.createSensor(deviceID, sensorModelID, sensorID);
        DeviceId actualDeviceID = sensor.getDeviceID();

        // Assert
        assertEquals(deviceID, actualDeviceID);
    }

    /**
     * Test case for the getSensorModelID method in the DP22C class.
     * This test verifies that the method returns the correct SensorModelID that was set during the construction of the DP22C object.
     */
    @Test
    void getSensorModelID_ShouldReturnCorrectSensorModelID() {
        // Arrange
        FactorySensor factorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("ValidDeviceID");
        SensorModelID sensorModelID = new SensorModelID("DP22C");
        SensorID sensorID = new SensorID ("ValidSensorID");

        // Act
        DP22C sensor = (DP22C) factorySensor.createSensor(deviceID, sensorModelID, sensorID);
        SensorModelID actualSensorModelID = sensor.getSensorModelID();

        // Assert
        assertEquals(sensorModelID, actualSensorModelID);
    }
}
