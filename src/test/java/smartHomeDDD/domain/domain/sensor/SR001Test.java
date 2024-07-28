package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.SR001;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SR001Value;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * This is aggregate test class for the SR001 Sensor.
 * It encompasses the following scenarios:
 * Successful instantiation of the SR001 object with mock dependencies.
 * Equality of ID when the identity method is called, ensuring it returns the correct SensorID.
 * Equality of two SR001 Sensor Objects with the same attributes.
 * Non-equality of two SR001 Sensor Objects when only the SensorID differs.
 * Non-equality of two SR001 Sensor Objects when only the DeviceID differs.
 * Non-equality of two SR001 Sensor Objects when only the sensorModel differs.
 * Test to ensure that the correct valid values of the readings are being retrieved from the SR001 sensor.
 * Test to ensure that the equals method returns false when comparing different sensor instances.
 * Test to ensure that the sameAs method returns false when comparing different sensor instances.
 * Test to ensure that the equals method returns true when comparing the same sensor instance.
 * Test to ensure that the equals method returns true when comparing duplicate sensor instances.
 * Test to ensure that the equals method returns false when comparing sensor instances with different SensorIDs.
 * Test to ensure that the getDeviceID method returns the correct DeviceID.
 * Test to ensure that the getSensorModelID method returns the correct SensorModelID.
 * Test to ensure that the hashCode method returns the correct hash code.
 * Test to ensure that the getValue method returns the correct value.
 */
class SR001Test {

    /**
     * Tests a successful instantiation of the SR001 Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject() throws IllegalArgumentException {

        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        // Act
        SR001 mySensor = new SR001(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);

        // Assert
        assertNotNull(mySensor);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() throws IllegalArgumentException {

        // Arrange
        SensorID expectedSensorId = mock(SensorID.class);
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);

        SR001 mySensor = new SR001(deviceIdDouble, sensorModelIdDouble, expectedSensorId);

        // Act
        SensorID actualId = mySensor.identity();

        // Assert
        assertEquals(expectedSensorId, actualId);
    }

    /**
     * Test to ensure that two SR001 Sensor objects with the same attributes are considered equivalent.
     */
    @Test
    void EqualSR001Objects_shouldReturnTrue() {

        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        SR001 mySensor1 = new SR001(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        SR001 mySensor2 = new SR001(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);

        // Assert
        assertTrue(result);
    }

    /**
     * Tests that two SR001 Sensor objects with different sensor IDs are not considered equivalent.
     */
    @Test
    void SR001ObjectsWithDifferentIDs_shouldReturnFalse() throws IllegalArgumentException {

        // Arrange
        SensorID sensorIdDouble1 = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);

        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);

        SR001 mySensor1 = new SR001(deviceIdDouble, sensorModelIdDouble, sensorIdDouble1);
        SR001 mySensor2 = new SR001(deviceIdDouble, sensorModelIdDouble, sensorIdDouble2);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that two SR001 Sensor objects with different Device IDs are not considered equivalent.
     */
    @Test
    void SR001ObjectsWithDifferentDeviceIDs_shouldReturnFalse() throws IllegalArgumentException {

        // Arrange
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        SR001 mySensor1 = new SR001(deviceIdDouble1, sensorModelIdDouble, sensorID);
        SR001 mySensor2 = new SR001(deviceIdDouble2, sensorModelIdDouble, sensorID);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);

        // Assert
        assertFalse(result);
    }

    /**
     * Tests that two SR001 Sensor objects with different Sensor Models are not considered equivalent.
     */
    @Test
    void SR001ObjectsWithDifferentSensorModels_shouldReturnFalse() throws IllegalArgumentException {

        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);
        SensorModelID sensorModelIdDouble2 = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        SR001 mySensor1 = new SR001(deviceIdDouble, sensorModelIdDouble, sensorIdDouble);
        SR001 mySensor2 = new SR001(deviceIdDouble, sensorModelIdDouble2, sensorIdDouble);

        // Act
        boolean result = mySensor1.sameAs(mySensor2);

        // Assert
        assertFalse(result);
    }


    /**
     * The following test if the values are being set correctly for the SR001 sensor while using the MockConstruction
     * feature of Mockito.
     */
    @Test
    void mockConstructGetValueForAllReadings_shouldReturnCorrectValues() {

        // Arrange
        Integer readingID1 = 1;
        double expectedValue1 = 0.0;
        Integer readingID2 = 2;
        double expectedValue2 = 12345.6;
        Integer readingID3 = 3;
        double expectedValue3 = 250.19;

        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<SR001Value> SR001ValueDouble = mockConstruction(SR001Value.class, (mock, context) -> {
            Double valueReading = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {
            SR001 SR001 = new SR001(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            SR001.setValue(readingID1);
            SR001.setValue(readingID2);
            SR001.setValue(readingID3);

            // Act
            List<SR001Value> values = SR001ValueDouble.constructed();
            assertEquals(3, values.size());

            SR001Value mockedValue1 = values.get(0);
            SR001Value mockedValue2 = values.get(1);
            SR001Value mockedValue3 = values.get(2);
            //Assert
            assertEquals(Double.toString(expectedValue1), mockedValue1.toString());
            assertEquals(Double.toString(expectedValue2), mockedValue2.toString());
            assertEquals(Double.toString(expectedValue3), mockedValue3.toString());
        }
    }


    /**
     * Tests if the equals method of the SR001 sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        SR001 mySensor = new SR001(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the sameAs method of the SR001 sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareDifferentClassSensors() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        SR001 mySensor = new SR001(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the SR001 sensor returns true
     * when the same sensor instance is compared.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        SR001 mySensor = new SR001(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the SR001 sensor returns true
     * when compared to duplicate instance.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareDuplicateSensor() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        SR001 mySensor = new SR001(deviceId, sensorModelID, sensorIdDouble);
        SR001 mySensor2 = new SR001(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the SR001 sensor returns false
     * when compared to sensor with different sensorID.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareSensorWithDifferentSensorID() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        SR001 mySensor = new SR001(deviceId, sensorModelID, sensorIdDouble);
        SR001 mySensor2 = new SR001(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }


    /**
     * Tests if the getDeviceID method of the SR001 sensor returns the correct device ID.
     */
    @Test
    void ShouldReturnDeviceID_WhenGetDeviceIDIsCalled() {

        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        SR001 mySensor = new SR001(deviceId, sensorModelID, sensorIdDouble);

        // Act
        DeviceId actual = mySensor.getDeviceID();

        // Assert
        assertEquals(deviceId, actual);
    }

    /**
     * Tests if the getSensorModelID method of the SR001 sensor returns the correct sensor model ID.
     */
    @Test
    void ShouldReturnSensorModelID_WhenGetSensorModelIDIsCalled() {

        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorIdDouble = mock(SensorID.class);

        SR001 mySensor = new SR001(deviceId, sensorModelID, sensorIdDouble);

        // Act
        SensorModelID actual = mySensor.getSensorModelID();

        // Assert
        assertEquals(sensorModelID, actual);
    }

    /**
     * Test case for the hashCode method in the SR001 class.
     * This test verifies that the hashCode method returns the correct hash code.
     * The test uses Mockito to create a mock SensorID, DeviceId, and SensorModelID.
     * It then asserts that the actual hash code returned by the hashCode method is equal to the hash code of the mock SensorID.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        SR001 sr001 = new SR001(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = sr001.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }

    /**
     * Test case for the getValue method in the SR001 class.
     * This test verifies that the getValue method returns the correct value.
     * The test uses Mockito to create mock objects for DeviceId, SensorModelID, and SensorID.
     * It then creates an SR001 object using these mock objects and sets a value for the sensor.
     * The test asserts that the value returned by the getValue method is not null.
     */
    @Test
    void getValue_shouldReturnCorrectValue() {
        // Arrange
        Integer expectedReadingNumber = 1;
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SR001 sr001 = new SR001(deviceId, sensorModelID, sensorID);
        sr001.setValue(expectedReadingNumber);

        // Act
        SR001Value actualValue = (SR001Value) sr001.getValue();

        // Assert
        assertNotNull(actualValue);
    }
}