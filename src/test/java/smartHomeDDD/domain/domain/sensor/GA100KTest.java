package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.GA100K;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.GA100KValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the GA100K Sensor. The tests cover the following scenarios:
 * - Creation of a GA100K object with valid dependencies.
 * - Verification of the identity method, ensuring it returns the correct SensorID.
 * - Equality of two GA100K objects with identical attributes.
 * - Non-equality of two GA100K objects with differing SensorIDs.
 * - Non-equality of two GA100K objects with differing DeviceIDs.
 * - Non-equality of two GA100K objects with differing sensor models.
 * - Retrieval of valid readings from the GA100K sensor.
 * - Exception handling when an invalid value is set for the GA100K sensor.
 */
class GA100KTest {

    /**
     * Tests a successful instantiation of the GA100K Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject() {

        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        // Act
        GA100K mySensor = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        // Assert
        assertNotNull(mySensor);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);


        GA100K mySensor = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(sensorIdDouble, actualId);
    }

    /**
     * Test to ensure that two GA100K Sensor objects with the same attributes are considered equivalent.
     */
    @Test
    void EqualGA100KObjects_shouldReturnTrue() {

        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        GA100K mySensor1 = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        GA100K mySensor2 = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);

        // Act and Assert
        assertEquals(mySensor1, mySensor2);
    }

    /**
     * Tests that two GA100K Sensor objects with different sensor IDs are not considered equivalent.
     */
    @Test
    void GA100KObjectsWithDifferentIDs_shouldReturnFalse() {

        // Arrange
        SensorID sensorID1 = mock(SensorID.class);
        SensorID sensorID2 = mock(SensorID.class);

        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);


        GA100K mySensor1 = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorID1);
        GA100K mySensor2 = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorID2);

        // Act and Assert
        assertFalse(mySensor1.sameAs(mySensor2));
    }

    /**
     * Tests that two GA100K Sensor objects with different Device IDs are not considered equivalent.
     */
    @Test
    void GA100KObjectsWithDifferentDeviceIDs_shouldReturnFalse() {

        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);


        GA100K mySensor1 = new GA100K(deviceId1, sensorModelIdDouble, sensorIdDouble);
        GA100K mySensor2 = new GA100K(deviceId2, sensorModelIdDouble, sensorIdDouble);


        // Act and Assert
        assertFalse(mySensor1.sameAs(mySensor2));
    }

    /**
     * Tests that two GA100K Sensor objects with different Sensor Models are not considered equivalent.
     */
    @Test
    void GA100KObjectsWithDifferentSensorModels_shouldReturnFalse() {

        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorModelID sensorModelIdDouble2 = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        //Act
        GA100K mySensor1 = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        GA100K mySensor2 = new GA100K(deviceIdDouble, sensorModelIdDouble2, sensorIdDouble);

        // Assert
        assertFalse(mySensor1.sameAs(mySensor2));
    }

    /**
     * Test to ensure that the correct values of the readings are being retrieved from the GA100K sensor.
     */
    @Test
    void getValueForAllValidReadings() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        GA100K sensor = new GA100K(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);

        // Act
        sensor.setValue(1);
        String value1 = sensor.getValue().toString();

        sensor.setValue(2);
        String value2 = sensor.getValue().toString();

        sensor.setValue(3);
        String value3 = sensor.getValue().toString();

        // Assert
        assertEquals("0", value1);
        assertEquals("-120", value2);
        assertEquals("96", value3);
    }

    /**
     * The following test if the values are being set correctly for the GA100K sensor while using the MockConstruction
     * feature of Mockito.
     */
    @Test
    void mockConstructGetValueForAllReadings_shouldReturnCorrectValues() {
        // Arrange
        Integer readingID1 = 1;
        int expectedValue1 = 0;
        Integer readingID2 = 2;
        int expectedValue2 = -120;
        Integer readingID3 = 3;
        int expectedValue3 = 96;

        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<GA100KValue> GA100KValueDouble = mockConstruction(GA100KValue.class, (mock, context) -> {
            Integer valueReading = (Integer) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {
            GA100K GA100K = new GA100K(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            GA100K.setValue(readingID1);
            GA100K.setValue(readingID2);
            GA100K.setValue(readingID3);

            // Act
            List<GA100KValue> values = GA100KValueDouble.constructed();
            assertEquals(3, values.size());

            GA100KValue mockedValue1 = values.get(0);
            GA100KValue mockedValue2 = values.get(1);
            GA100KValue mockedValue3 = values.get(2);
            //Assert
            assertEquals(Integer.toString(expectedValue1), mockedValue1.toString());
            assertEquals(Integer.toString(expectedValue2), mockedValue2.toString());
            assertEquals(Integer.toString(expectedValue3), mockedValue3.toString());
        }
    }

    /**
     * Tests if the equals method of the GA100K sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        GA100K mySensor = new GA100K(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the GA100K sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareSensorsWithDifferentSensorID() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        GA100K mySensor = new GA100K(deviceId, sensorModelID, sensorIdDouble);
        GA100K mySensor2 = new GA100K(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the sameAs method of the GA100K sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareDifferentClassSensors() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        GA100K mySensor = new GA100K(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the GA100K sensor returns true
     * when the same sensor instance is compared.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        GA100K mySensor = new GA100K(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the sameAs method of the GA100K sensor returns false
     * when comparing two GA100K sensor instances with different SensorIDs.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareSensorsWithDifferentSensorIDs() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        GA100K mySensor = new GA100K(deviceId, sensorModelID, sensorIdDouble);
        GA100K mySensor2 = new GA100K(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Test to ensure that the getDeviceID method of the GA100K class returns the correct DeviceId.
     * This test uses Mockito to create a mock DeviceId, SensorModelID, and SensorID.
     * It then asserts that the actual DeviceId returned by the getDeviceID method is equal to the mock DeviceId.
     */
    @Test
    void getDeviceID_shouldReturnCorrectDeviceID() {
        // Arrange
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        SensorID sensorIdMock = mock(SensorID.class);
        GA100K ga100k = new GA100K(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        DeviceId actualDeviceId = ga100k.getDeviceID();

        // Assert
        assertEquals(deviceIdMock, actualDeviceId);
    }

    /**
     * Test to ensure that the getSensorModelID method of the GA100K class returns the correct SensorModelID.
     * This test uses Mockito to create a mock DeviceId, SensorModelID, and SensorID.
     * It then asserts that the actual SensorModelID returned by the getSensorModelID method is equal to the mock SensorModelID.
     */
    @Test
    void getSensorModelID_shouldReturnCorrectSensorModelID() {
        // Arrange
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        SensorID sensorIdMock = mock(SensorID.class);
        GA100K ga100k = new GA100K(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        SensorModelID actualSensorModelId = ga100k.getSensorModelID();

        // Assert
        assertEquals(sensorModelIdMock, actualSensorModelId);
    }

    /**
     * Test to ensure that the hashCode method of the GA100K class returns the correct hash code.
     * This test uses Mockito to create a mock DeviceId, SensorModelID, and SensorID.
     * It then asserts that the actual hash code returned by the hashCode method is equal to the hash code of the mock SensorID.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode() {
        // Arrange
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        SensorID sensorIdMock = mock(SensorID.class);
        GA100K ga100k = new GA100K(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = ga100k.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }

    /**
     * Test to ensure that the equals method of the GA100K class returns true when the SensorIDs of two GA100K objects are equal.
     * This test uses Mockito to create two GA100K objects with the same mock DeviceId, SensorModelID, and SensorID.
     * It then asserts that the equals method returns true when comparing these two GA100K objects.
     */
    @Test
    void equals_shouldReturnTrueWhenSensorIDsAreEqual() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        GA100K ga100k1 = new GA100K(deviceIdMock, sensorModelIdMock, sensorIdMock);
        GA100K ga100k2 = new GA100K(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        boolean isEqual = ga100k1.equals(ga100k2);

        // Assert
        assertTrue(isEqual);
    }

    /**
     * Test to ensure that the sameAs method of the GA100K class returns true when the SensorIDs, DeviceIDs, and SensorModelIDs of two GA100K objects are equal.
     * This test uses Mockito to create two GA100K objects with the same mock DeviceId, SensorModelID, and SensorID.
     * It then asserts that the sameAs method returns true when comparing these two GA100K objects.
     */
    @Test
    void sameAs_shouldReturnTrueWhenSensorIDsDeviceIDsAndSensorModelIDsAreEqual() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        GA100K ga100k1 = new GA100K(deviceIdMock, sensorModelIdMock, sensorIdMock);
        GA100K ga100k2 = new GA100K(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        boolean isSame = ga100k1.sameAs(ga100k2);

        // Assert
        assertTrue(isSame);
    }

}


