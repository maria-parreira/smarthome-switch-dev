package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.CAP200;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.CAP200Value;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

/**
 * This is aggregate test class for the CAP200 Sensor. It lists the following scenarios:
 * validSensor_shouldReturnNonNullObject - Successful instantiation of the CAP200 object with mock dependencies.
 * validIdentity_ShouldReturnEqualsIDs - Equality of ID when the identity method is called, ensuring it returns the correct SensorID.
 * EqualCAP200Objects_shouldReturnTrue - Equality of two CAP200 Sensor Objects with the same attributes.
 * CAP200ObjectsWithDifferentIDs_shouldReturnFalse - Non-equality of two CAP200 Sensor Objects when only the SensorID differs.
 * CAP200ObjectsWithDifferentDeviceIDs_shouldReturnFalse - Non-equality of two CAP200 Sensor Objects when only the DeviceID differs.
 * CAP200ObjectsWithDifferentSensorModels_shouldReturnFalse - Non-equality of two CAP200 Sensor Objects when only the sensorModel differs.
 * getValueForAllValidReadings - Test to ensure that the correct valid values of the readings are being retrieved from the CAP200 sensor.
 */
class CAP200Test {

    /**
     * Tests a successful instantiation of the CAP200 Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject() throws IllegalArgumentException {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        // Act
        CAP200 mySensor = new CAP200(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        // Assert
        assertNotNull(mySensor);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() throws IllegalArgumentException {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);


        CAP200 mySensor = new CAP200(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(sensorIdDouble, actualId);
    }

    /**
     * Test to ensure that two CAP200 Sensor objects with the same attributes are considered equivalent.
     */
    @Test
    void EqualCAP200Objects_shouldReturnTrue() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        CAP200 mySensor1 = new CAP200(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        CAP200 mySensor2 = new CAP200(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);

        // Assert
        assertTrue(result);
    }

    /**
     * Tests that two CAP200 Sensor objects with different sensor IDs are not considered equivalent.
     */
    @Test
    void CAP200ObjectsWithDifferentIDs_shouldReturnFalse() throws IllegalArgumentException {
        // Arrange
        SensorID sensorID1 = mock(SensorID.class);
        SensorID sensorID2 = mock(SensorID.class);

        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);

        CAP200 mySensor1 = new CAP200(deviceIdDouble, sensorModelIdDouble, sensorID1);
        CAP200 mySensor2 = new CAP200(deviceIdDouble, sensorModelIdDouble, sensorID2);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that two CAP200 Sensor objects with different Device IDs are not considered equivalent.
     */
    @Test
    void CAP200ObjectsWithDifferentDeviceIDs_shouldReturnFalse() throws IllegalArgumentException {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);

        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        CAP200 mySensor1 = new CAP200(deviceId1, sensorModelIdDouble, sensorID);
        CAP200 mySensor2 = new CAP200(deviceId2, sensorModelIdDouble, sensorID);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that two CAP200 Sensor objects with different Sensor Models are not considered equivalent.
     */
    @Test
    void CAP200ObjectsWithDifferentSensorModels_shouldReturnFalse() throws IllegalArgumentException {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorModelID sensorModelIdDouble2 = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        CAP200 mySensor1 = new CAP200(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        CAP200 mySensor2 = new CAP200(deviceIdDouble, sensorModelIdDouble2, sensorIdDouble);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);
        // Assert
        assertFalse(result);
    }

    /**
     * The following test if the values are being set correctly for the CAP200 sensor while using the MockConstruction
     * feature of Mockito.
     */

    @Test
    void mockConstructGetValueForAllReadings_shouldReturnCorrectValues() {
        // Arrange
        Integer readingID1 = 1;
        int expectedValue1 = 1;
        Integer readingID2 = 2;
        int expectedValue2 = 0;
        Integer readingID3 = 3;
        int expectedValue3 = 100;

        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<CAP200Value> CAP200ValueDouble = mockConstruction(CAP200Value.class, (mock, context) -> {
            Integer valueReading = (Integer) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {
            CAP200 CAP200 = new CAP200(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            CAP200.setValue(readingID1);
            CAP200.setValue(readingID2);
            CAP200.setValue(readingID3);

            // Act
            List<CAP200Value> values = CAP200ValueDouble.constructed();
            assertEquals(3, values.size());

            CAP200Value mockedValue1 = values.get(0);
            CAP200Value mockedValue2 = values.get(1);
            CAP200Value mockedValue3 = values.get(2);
            //Assert
            assertEquals(Integer.toString(expectedValue1), mockedValue1.toString());
            assertEquals(Integer.toString(expectedValue2), mockedValue2.toString());
            assertEquals(Integer.toString(expectedValue3), mockedValue3.toString());
        }
    }

    /**
     * Tests that the equals method returns true when comparing a Sensor object to itself.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        CAP200 mySensor = new CAP200(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests that the getSensorModelID method returns the correct SensorModelID.
     */
    @Test
    void ShouldReturnSensorModelID_WhenGetSensorModelIDIsCalled() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        CAP200 mySensor = new CAP200(deviceId, sensorModelID, sensorIdDouble);

        // Act
        SensorModelID actual = mySensor.getSensorModelID();

        // Assert
        assertEquals(sensorModelID, actual);
    }

    /**
     * Tests that the getDeviceID method returns the correct DeviceID.
     */
    @Test
    void ShouldReturnDeviceID_WhenGetDeviceIDIsCalled() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        CAP200 mySensor = new CAP200(deviceId, sensorModelID, sensorIdDouble);

        // Act
        DeviceId actual = mySensor.getDeviceID();

        // Assert
        assertEquals(deviceId, actual);
    }

    /**
     * Tests if the equals method of the CAP200 sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        CAP200 mySensor = new CAP200(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the sameAs method of the CAP200 sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        CAP200 mySensor = new CAP200(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }


    /**
     * Tests if the equals method of the CAP200 sensor returns false
     * when comparing sensor with different ids
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareSensorsWithDifferentSensorId() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        CAP200 mySensor = new CAP200(deviceId, sensorModelID, sensorIdDouble);
        CAP200 mySensor2 = new CAP200(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }
}
