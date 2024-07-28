package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.CAP200;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.valueobject.CAP200Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This is the test class for the CAP200 Sensor. It lists the following scenarios:
 * validSensor_shouldReturnNonNullObject() - Successful instantiation of the CAP200 object with mock dependencies.
 * EqualsCAP200Objects_ShouldReturnTrue() - Equality of two CAP200 Sensor Objects when all values are the same.
 * CAP200ObjectsWithDifferentIDs_shouldReturnFalse() - Non-equality of two CAP200 Sensor Objects when only the SensorID differs.
 * CAP200ObjectsWithDifferentDeviceIDs_shouldReturnFalse() - Non-equality of two CAP200 Sensor Objects when only the DeviceID differs.
 * getValueFromReading1_shouldReturnSensorValue() - Test to ensure that the correct valid values of the readings are being retrieved from the CAP200 sensor.
 * getValueFromReading2_shouldReturnSensorValue() - Test to ensure that the correct valid values of the readings are being retrieved from the CAP200 sensor.
 * getValueFromReading3_shouldReturnSensorValue() - Test to ensure that the correct valid values of the readings are being retrieved from the CAP200 sensor.
 */
class CAP200AggregateTest {

    /**
     * Tests a successful instantiation of the CAP200 Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject()  {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceIDDouble = new DeviceId("Device01");
        SensorModelID sensorModelIDDouble = new SensorModelID("CAP200");

        // Act
        CAP200 CAP200 = (CAP200) implFactorySensor.createSensor(deviceIDDouble, sensorModelIDDouble, SensorID);
        // Assert
        assertNotNull(CAP200);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID expectedSensorID = new SensorID("Sensor01");
        DeviceId deviceIDDouble = new DeviceId("Device01");
        SensorModelID SensorModelIDDouble = new SensorModelID("CAP200");

        CAP200 CAP200 = (CAP200) implFactorySensor.createSensor(deviceIDDouble, SensorModelIDDouble, expectedSensorID);

        // Act
        SensorID actualId = CAP200.identity();

        // Assert
        assertEquals(expectedSensorID, actualId);
    }

    /**
     * Tests that two CAP200 Sensor objects with the same state are considered equivalent by the sameAs method.
     */
    @Test
    void EqualsCAP200Objects_ShouldReturnTrue() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceIDDouble = new DeviceId("Device01");
        SensorModelID SensorModelIDDouble = new SensorModelID("CAP200");

        CAP200 Sensor1 = (CAP200) implFactorySensor.createSensor(deviceIDDouble, SensorModelIDDouble, SensorID);
        CAP200 Sensor2 = (CAP200) implFactorySensor.createSensor(deviceIDDouble, SensorModelIDDouble, SensorID);

        // Act
        boolean result = Sensor1.sameAs(Sensor2);

        // Assert
        assertTrue(result);
    }

    /**
     * Tests that two CAP200 Sensor objects with different Sensor IDs are not considered equivalent.
     */
    @Test
    void CAP200ObjectsWithDifferentIDs_shouldReturnFalse() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        SensorID SensorID2 = new SensorID("Sensor02");
        DeviceId deviceIDDouble = new DeviceId("Device01");
        SensorModelID SensorModelIDDouble = new SensorModelID("CAP200");

        CAP200 Sensor1 = (CAP200) implFactorySensor.createSensor(deviceIDDouble, SensorModelIDDouble, SensorID);
        CAP200 Sensor2 = (CAP200) implFactorySensor.createSensor(deviceIDDouble, SensorModelIDDouble, SensorID2);

        // Act
        boolean result = Sensor1.sameAs(Sensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that two CAP200 Sensor objects with different Device IDs are not considered equivalent.
     */
    @Test
    void CAP200ObjectsWithDifferentDeviceIDs_shouldReturnFalse() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceIDDouble = new DeviceId("Device01");
        DeviceId deviceIDDouble2 = new DeviceId("Device02");
        SensorModelID SensorModelIDDouble = new SensorModelID("CAP200");

        CAP200 Sensor1 = (CAP200) implFactorySensor.createSensor(deviceIDDouble, SensorModelIDDouble, SensorID);
        CAP200 Sensor2 = (CAP200) implFactorySensor.createSensor(deviceIDDouble2, SensorModelIDDouble, SensorID);

        // Act
        boolean result = Sensor1.sameAs(Sensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 1
     */
    @Test
    void getValueFromReading1_shouldReturnSensorValue(){
        // Arrange
        Integer readingID = 1;
        int value1 = 1;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = new SensorModelID("CAP200");
        SensorID sensorID = new SensorID("Sensor01");

        CAP200 CAP200 = (CAP200) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        CAP200.setValue(readingID);

        // Act
        CAP200Value CAP200Value = (CAP200Value) CAP200.getValue();

        // Assert
        assertEquals(Integer.toString(value1), CAP200Value.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 2
     */

    @Test
    void getValueFromReading2_shouldReturnSensorValue(){
        // Arrange
        Integer readingID = 2;
        int value2 = 0;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = new SensorModelID("CAP200");
        SensorID sensorID = new SensorID("Sensor01");

        CAP200 CAP200 = (CAP200) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        CAP200.setValue(readingID);

        // Act
        CAP200Value CAP200Value = (CAP200Value) CAP200.getValue();

        // Assert
        assertEquals(Integer.toString(value2), CAP200Value.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 3
     */

    @Test
    void getValueFromReading3_shouldReturnSensorValue(){
        // Arrange
        Integer readingID = 3;
        int value3 = 100;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = new SensorModelID("CAP200");
        SensorID sensorID = new SensorID("Sensor01");

        CAP200 CAP200 = (CAP200) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        CAP200.setValue(readingID);

        // Act
        CAP200Value CAP200Value = (CAP200Value) CAP200.getValue();

        // Assert
        assertEquals(Integer.toString(value3), CAP200Value.toString());
    }

}

