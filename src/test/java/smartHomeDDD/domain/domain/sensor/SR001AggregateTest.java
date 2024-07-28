package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.SR001;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SR001Value;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is the test class for the SR001 Sensor. It lists the following scenarios:
 * validSensor_shouldReturnNonNullObject() - Successful instantiation of the SR001 object with mock dependencies.
 * validIdentity_ShouldReturnEqualsIDs() - Equality of ID when the identity method is called, ensuring it returns the correct SensorID.
 * EqualsSR001Objects_ShouldReturnTrue() - Equality of two SR001 Sensor Objects when all values are the same.
 * SR001ObjectsWithDifferentIDs_shouldReturnFalse() - Non-equality of two SR001 Sensor Objects when only the SensorID differs.
 * SR001ObjectsWithDifferentDeviceIDs_shouldReturnFalse() - Non-equality of two SR001 Sensor Objects when only the DeviceID differs.
 * getValueFromReading1_shouldReturnSensorValue() - Test to ensure that the correct valid values of the readings are being retrieved from the SR001 sensor.
 * getValueFromReading2_shouldReturnSensorValue() - Test to ensure that the correct valid values of the readings are being retrieved from the SR001 sensor.
 * getValueFromReading3_shouldReturnSensorValue() - Test to ensure that the correct valid values of the readings are being retrieved from the SR001 sensor.
 */
class SR001AggregateTest {

    /**
     * Tests a successful instantiation of the SR001 Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject() throws IllegalArgumentException {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceID = new DeviceId("Device01");
        SensorModelID sensorModelID = new SensorModelID("SR001");

        // Act
        SR001 SR001 = (SR001) implFactorySensor.createSensor(deviceID, sensorModelID, SensorID);
        // Assert
        assertNotNull(SR001);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID expectedSensorID = new SensorID("Sensor01");
        DeviceId deviceID = new DeviceId("Device01");
        SensorModelID SensorModelIDDouble = new SensorModelID("SR001");

        SR001 SR001 = (SR001) implFactorySensor.createSensor(deviceID, SensorModelIDDouble, expectedSensorID);
        // Act
        SensorID actualId = SR001.identity();
        // Assert
        assertEquals(expectedSensorID, actualId);
    }

    /**
     * Tests that two SR001 Sensor objects with the same state are considered equivalent by the sameAS method.
     */
    @Test
    void EqualsSR001Objects_ShouldReturnTrue() throws IllegalArgumentException {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceID = new DeviceId("Device01");
        SensorModelID SensorModelIDDouble = new SensorModelID("SR001");

        SR001 Sensor1 = (SR001) implFactorySensor.createSensor(deviceID, SensorModelIDDouble, SensorID);
        SR001 Sensor2 = (SR001) implFactorySensor.createSensor(deviceID, SensorModelIDDouble, SensorID);

        // Act
        boolean result = Sensor1.sameAs(Sensor2);

        // Assert
        assertTrue(result);
    }

    /**
     * Tests that two SR001 Sensor objects with different Sensor IDs are not considered equivalent.
     */
    @Test
    void SR001ObjectsWithDifferentIDs_shouldReturnFalse() throws IllegalArgumentException {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        SensorID SensorID2 = new SensorID("Sensor02");
        DeviceId deviceID = new DeviceId("Device01");
        SensorModelID SensorModelIDDouble = new SensorModelID("SR001");

        SR001 Sensor1 = (SR001) implFactorySensor.createSensor(deviceID, SensorModelIDDouble, SensorID);
        SR001 Sensor2 = (SR001) implFactorySensor.createSensor(deviceID, SensorModelIDDouble, SensorID2);

        // Act
        boolean result = Sensor1.sameAs(Sensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that two SR001 Sensor objects with different Device IDs are not considered equivalent.
     */
    @Test
    void SR001ObjectsWithDifferentDeviceIDs_shouldReturnFalse() throws IllegalArgumentException {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceID = new DeviceId("Device01");
        DeviceId deviceID2 = new DeviceId("Device02");
        SensorModelID SensorModelIDDouble = new SensorModelID("SR001");

        SR001 Sensor1 = (SR001) implFactorySensor.createSensor(deviceID, SensorModelIDDouble, SensorID);
        SR001 Sensor2 = (SR001) implFactorySensor.createSensor(deviceID2, SensorModelIDDouble, SensorID);

        // Act
        boolean result = Sensor1.sameAs(Sensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 1
     */
    @Test
    void getValueFromReading1_shouldReturnSensorValue() {

        // Arrange
        Integer readingID = 1;
        double value1 = 0.0;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("Device01");
        SensorModelID sensorModelIdDouble = new SensorModelID("SR001");
        SensorID sensorID = new SensorID("Sensor01");

        SR001 SR001 = (SR001) implFactorySensor.createSensor(deviceID, sensorModelIdDouble, sensorID);
        SR001.setValue(readingID);

        // Act
        SR001Value SR001Value = (SR001Value) SR001.getValue();

        // Assert
        assertEquals(Double.toString(value1), SR001Value.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 2
     */
    @Test
    void getValueFromReading2_shouldReturnSensorValue() {

        // Arrange
        Integer readingID = 2;
        double value2 = 12345.6;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("Device01");
        SensorModelID sensorModelIdDouble = new SensorModelID("SR001");
        SensorID sensorID = new SensorID("Sensor01");

        SR001 SR001 = (SR001) implFactorySensor.createSensor(deviceID, sensorModelIdDouble, sensorID);
        SR001.setValue(readingID);

        // Act
        SR001Value SR001Value = (SR001Value) SR001.getValue();

        // Assert
        assertEquals(Double.toString(value2), SR001Value.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 3
     */
    @Test
    void getValueFromReading3_shouldReturnSensorValue() {

        // Arrange
        Integer readingID = 3;
        double value3 = 250.19;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceID = new DeviceId("Device01");
        SensorModelID sensorModelIdDouble = new SensorModelID("SR001");
        SensorID sensorID = new SensorID("Sensor01");

        SR001 SR001 = (SR001) implFactorySensor.createSensor(deviceID, sensorModelIdDouble, sensorID);
        SR001.setValue(readingID);

        // Act
        SR001Value SR001Value = (SR001Value) SR001.getValue();

        // Assert
        assertEquals(Double.toString(value3), SR001Value.toString());
    }
}