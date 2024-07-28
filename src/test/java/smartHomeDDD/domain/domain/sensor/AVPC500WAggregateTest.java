package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.AVPC500W;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.valueobject.AVPC500WValue;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class serves as a comprehensive suite of unit tests for the AVPC500W sensor aggregate implementation.
 * - The tests cover various aspects, including:
 *   - The creation of valid sensor instances.
 *   - Ensuring their non-null status.
 *   - Validating the accuracy of the identity retrieval mechanism.
 */

class AVPC500WAggregateTest {

    /**
     * Tests if a valid sensor is created and returned as a non-null object.
     */
    @Test
    void validSensor_shouldReturnNonNullObject_shouldCreateValidObject(){
        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");
        SensorID sensorID = new SensorID("Sensor1");
        ImplFactorySensor implFactorySensor = new ImplFactorySensor(); //not a dependency
        // Act
        AVPC500W mySensor = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,sensorID);
        // Assert
        assertNotNull(mySensor);
    }


    /**
     * Tests if the sensor's identity returns expected SensorID.
     */
    @Test
    void validIdentity_ShouldReturnID(){

        // Arrange
        SensorID expectedSensorID = new SensorID("Sensor1");
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        AVPC500W mySensor = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,expectedSensorID);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(expectedSensorID, actualId);
    }

    /**
     * Test case for the sameAs method in the AVPC500W class when comparing a AVPC500W object with a non-APC500W object.
     * The test verifies that the sameAs method returns false when a AVPC500W object is compared with a non-PC500W object.
     */
    @Test
    public void nonAVPC500WObject_ShouldReturnFalse() {

        // Arrange
        SensorID expectedSensorID = new SensorID("Sensor1");
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        AVPC500W mySensor = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,expectedSensorID);

        Object obj = new Object();

        //Act + Assert
        boolean isEquals = mySensor.sameAs(obj);
        assertFalse(isEquals);
    }

    /**
     * Tests the behavior of the equals method when comparing two AVPC500W sensor instances
     * with same DeviceId and SensorModelID and the same SensorID.
     * The method should return true, indicating that the sensors are considered equal.
     */
    @Test
    void sameAs_sameDeviceAndModel_SameSensorID_ShouldReturnTrue() {

        // Arrange
        SensorID expectedSensorID = new SensorID("Sensor1");
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        AVPC500W mySensor1 = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,expectedSensorID);
        AVPC500W mySensor2 = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,expectedSensorID);

        // Act and Assert
        boolean isEquals = mySensor1.equals(mySensor2);
        assertTrue(isEquals);
    }

    /**
     * Test case for the getDeviceID method in the AVPC500W class.
     * This test verifies that the method returns the correct DeviceId that was set during the construction of the AVPC500W object.
     */
    @Test
    void getDeviceID_ShouldReturnCorrectDeviceID() {
        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        AVPC500W mySensor1 = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,sensorId);

        //Act
        DeviceId actualDeviceID = mySensor1.getDeviceID();

        // Assert
        assertEquals(deviceId, actualDeviceID);
    }

    /**
     * Test case for the getSensorModelID method in the AVPC500W class.
     * This test verifies that the method returns the correct SensorModelID that was set during the construction of the AVPC500W object.
     */
    @Test
    void getSensorModelID_ShouldReturnCorrectSensorModelID() {
        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        AVPC500W mySensor1 = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,sensorId);

        //Act
        SensorModelID actualSensorModelID = mySensor1.getSensorModelID();

        // Assert
        assertEquals(sensorModelID, actualSensorModelID);
    }

    /**
     * Test case for the getValue method in the AVPC500W class when the start and end time are equals.
     * The test verifies that the getValue method returns the correct value when the time interval is invalid
     */
    @Test
    void getValue_InvalidTime_ShouldReturnAveragePowerConsumptionZero() {

        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        AVPC500W mySensor1 = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,sensorId);

        LocalTime start = LocalTime.of(3, 0);
        LocalTime end = LocalTime.of(3, 0);

        AVPC500WValue expectedValue= new AVPC500WValue(0.0);

        // Act
        AVPC500WValue actualValue = (AVPC500WValue) mySensor1.getValue(start,end);

        // Assert
        assertEquals(expectedValue.toString(), actualValue.toString());
    }

    /**
     * Test case for the getValue method in the AVPC500W class when the start and end time exists in readings.
     * The test verifies that the getValue method returns the correct value when the time interval is valid.
     */
    @Test
    void getValue_ExistentTime_ShouldReturnAveragePowerConsumption() {

        // Arrange
        DeviceId deviceId = new DeviceId("device1");
        SensorModelID sensorModelID = new SensorModelID("AVPC500W");
        SensorID sensorId = new SensorID("ValidSensorID");

        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        AVPC500W mySensor1 = (AVPC500W) implFactorySensor.createSensor(deviceId,sensorModelID,sensorId);

        LocalTime start = LocalTime.of(3, 0);
        LocalTime end = LocalTime.of(12, 30);

        AVPC500WValue expectedValue= new AVPC500WValue(105.0);

        // Act
        AVPC500WValue actualValue = (AVPC500WValue) mySensor1.getValue(start,end);

        // Assert
        assertEquals(expectedValue.toString(), actualValue.toString());
    }


}
