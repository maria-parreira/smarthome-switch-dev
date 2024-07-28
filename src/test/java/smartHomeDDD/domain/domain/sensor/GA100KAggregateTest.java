package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.GA100K;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.GA100KValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

/**
 * This is the test class for the GA100K Sensor. It lists the following scenarios:
 * validSensor_shouldReturnNonNullObject() - Successful instantiation of the GA100K object with mock dependencies.
 * validIdentity_ShouldReturnEqualsIDs() - Equality of ID when the identity method is called, ensuring it returns the correct SensorID.
 * EqualsGA100KObjects_ShouldReturnTrue() - Equality of two GA100K Sensor Objects when all values are the same.
 * GA100KObjectsWithSameValues_shouldReturnTrue() - Equality of two GA100K Sensor Objects when all values are the same.
 * GA100KObjectsWithDifferentIDs_shouldReturnFalse() - Non-equality of two GA100K Sensor Objects when only the SensorID differs.
 * GA100KObjectsWithDifferentDeviceIDs_shouldReturnFalse() - Non-equality of two GA100K Sensor Objects when only the DeviceID differs.
 * GA100KObjectsWithDifferentSensorModelIDs_shouldReturnFalse() - Non-equality of two GA100K Sensor Objects when only the SensorModelID differs.
 */
class GA100KAggregateTest {

    /**
     * Tests a successful instantiation of the GA100K Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Temper1");
        DeviceId deviceID = new DeviceId("Device1");
        SensorModelID sensorModelIDDouble = new SensorModelID("GA100K");

        // Act
        GA100K GA100K = (GA100K) implFactorySensor.createSensor(deviceID, sensorModelIDDouble, SensorID);

        // Assert
        assertNotNull(GA100K);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {

        // Arrange
        String SensorID = "Sensor01";
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID expectedSensorID = new SensorID(SensorID);
        DeviceId deviceID = new DeviceId("Device1");
        SensorModelID SensorModelID = new SensorModelID("GA100K");

        GA100K GA100K = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, expectedSensorID);

        // Act
        SensorID actualId = GA100K.identity();

        // Assert
        assertEquals(expectedSensorID, actualId);
    }

    /**
     * Tests that two GA100K Sensor objects with the same state are considered equivalent by the Equals method.
     */
    @Test
    void EqualsGA100KObjects_ShouldReturnTrue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceID = new DeviceId("Device1");
        SensorModelID SensorModelID = new SensorModelID("GA100K");

        GA100K Sensor1 = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, SensorID);
        GA100K Sensor2 = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, SensorID);

        // Act and Assert
        assertEquals(Sensor1, Sensor2);
    }

    /**
     * Tests that two GA100K Sensor objects with equals values are considered the same when using the sameAs method.
     */
    @Test
    void GA100KObjectsWithSameValues_shouldReturnTrue() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();

        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceID = new DeviceId("Device1");
        SensorModelID SensorModelID = new SensorModelID("GA100K");

        GA100K Sensor1 = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, SensorID);
        GA100K Sensor2 = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, SensorID);

        // Act and Assert
        assertTrue(Sensor1.sameAs(Sensor2));
    }

    /**
     * Tests that two GA100K Sensor objects with different Sensor IDs are not considered equivalent.
     */
    @Test
    void GA100KObjectsWithDifferentIDs_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        SensorID SensorID2 = new SensorID("Sensor02");
        DeviceId deviceID = new DeviceId("Device1");
        SensorModelID SensorModelID = new SensorModelID("GA100K");

        GA100K Sensor1 = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, SensorID);
        GA100K Sensor2 = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, SensorID2);

        // Act and Assert
        assertFalse(Sensor1.sameAs(Sensor2));
    }

    /**
     * Tests that two GA100K Sensor objects with different Device IDs are not considered equivalent.
     */
    @Test
    void GA100KObjectsWithDifferentDeviceIDs_shouldReturnFalse() {

        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        SensorID SensorID = new SensorID("Sensor01");
        DeviceId deviceID = new DeviceId("Device1");
        DeviceId deviceID2 = new DeviceId("Device2");
        SensorModelID SensorModelID = new SensorModelID("GA100K");
        GA100K Sensor1 = (GA100K) implFactorySensor.createSensor(deviceID, SensorModelID, SensorID);
        GA100K Sensor2 = (GA100K) implFactorySensor.createSensor(deviceID2, SensorModelID, SensorID);

        // Act and Assert
        assertFalse(Sensor1.sameAs(Sensor2));
    }

    /**
     * Tests that the getValue method returns the correct value for the GA100K Sensor for reading 1.
     */
    @Test
    void getValueFromReading1_shouldReturnSensorValue() {

        // Arrange
        Integer readingID = 1;
        int value1 = 0;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = new SensorModelID("GA100K");
        SensorID sensorID = mock(SensorID.class);

        GA100K GA100K = (GA100K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        GA100K.setValue(readingID);

        // Act
        GA100KValue GA100KValue = (GA100KValue) GA100K.getValue();

        // Assert
        assertEquals(Integer.toString(value1), GA100KValue.toString());
    }

    /**
     * Tests that the getValue method returns the correct value for the GA100K Sensor for reading 2.
     */
    @Test
    void getValueFromReading2_shouldReturnSensorValue() {

        // Arrange
        Integer readingID = 2;
        int value2 = -120;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = new SensorModelID("GA100K");
        SensorID sensorID = mock(SensorID.class);

        GA100K GA100K = (GA100K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        GA100K.setValue(readingID);

        // Act
        GA100KValue GA100KValue = (GA100KValue) GA100K.getValue();

        // Assert
        assertEquals(Integer.toString(value2), GA100KValue.toString());
    }

    /**
     * Tests that the getValue method returns the correct value for the GA100K Sensor for reading 3.
     */
    @Test
    void getValueFromReading3_shouldReturnSensorValue() {

        // Arrange
        Integer readingID = 3;
        int value3 = 96;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = new SensorModelID("GA100K");
        SensorID sensorID = mock(SensorID.class);

        GA100K GA100K = (GA100K) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        GA100K.setValue(readingID);

        // Act
        GA100KValue GA100KValue = (GA100KValue) GA100K.getValue();

        // Assert
        assertEquals(Integer.toString(value3), GA100KValue.toString());
    }
}