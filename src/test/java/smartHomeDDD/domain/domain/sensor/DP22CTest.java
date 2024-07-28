package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.DP22C;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DP22CValue;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;


import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;



/**
 * This is aggregate test class for the DP22C Sensor.
 * It encompasses the following scenarios:
 * - valid sensor object creation
 * - valid identity retrieval
 * - valid comparison of two sensor objects
 * - invalid comparison of two sensor objects
 * - valid comparison of a sensor object with a non-sensor object
 * - valid retrieval of the device ID
 * - valid retrieval of the sensor model ID
 */
class DP22CTest {

    /**
     * Tests a successful instantiation of the DP22C Sensor Object
     */
    @Test
    void validSensor_shouldReturnNonNullObject() {
        // Arrange
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        // Act
        DP22C mySensor = new DP22C(deviceID, sensorModelID, sensorID);
        // Assert
        assertNotNull(mySensor);
    }

    /**
     * Tests that the identity method correctly retrieves the SensorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {
        // Arrange
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        // Act
        DP22C mySensor = new DP22C(deviceID, sensorModelID, sensorID);
        SensorID actualId = mySensor.identity();

        // Assert
        assertEquals(sensorID, actualId);
    }

    /**
     * Test case for the sameAs method in the DP22C class when comparing a DP22C object with a non-DP22C object.
     * The test verifies that the sameAs method returns false when a DP22C object is compared with a non-DP22C object.
     */
    @Test
    public void nonDP22CObject_ShouldReturnFalse() {

        // Arrange
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        DP22C mySensor = new DP22C(deviceID, sensorModelID, sensorID);
        Object obj = new Object();

        //Act + Assert
        assertFalse(mySensor.sameAs(obj));
    }

    /**
     * Test case for the sameAs method in the DP22C class when comparing the same object.
     * The test verifies that the sameAs method returns true when the same object is compared.
     */
    @Test
    void sameAs_SameObject_ShouldReturnTrue() {

        // Arrange
        SensorID sensorId = mock(SensorID.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        DP22C dp22c1 = new DP22C(deviceID, sensorModelID, sensorId);
        DP22C dp22c2 = new DP22C(deviceID, sensorModelID, sensorId);

        // Act and Assert
        assertTrue(dp22c1.sameAs(dp22c2));
    }


    /**
     * Test case for the sameAs method in the DP22C class when comparing a DP22C object with a non-DP22C object.
     * The test verifies that the sameAs method returns false when a DP22C object is compared with a non-DP22C object.
     */
    @Test
    void sameAs_DifferentType_ShouldReturnFalse() {

        // Arrange
        SensorID sensorId = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);


        DP22C dp22c = new DP22C(deviceId, sensorModelID, sensorId);

        // Act and Assert
        assertFalse(dp22c.sameAs(new Object()));
    }

    /**
     * Test case for the getDeviceID method in the DP22C class.
     * This test verifies that the method returns the correct DeviceId
     * that was set during the construction of the DP22C object.
     */
    @Test
    void getDeviceID_ShouldReturnCorrectDeviceID() {
        // Arrange
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        DP22C sensor = new DP22C(deviceID, sensorModelID, sensorID);

        // Act
        DeviceId actualDeviceId = sensor.getDeviceID();

        // Assert
        assertEquals(deviceID, actualDeviceId);
    }

    /**
     * Test case for the getSensorModelID method in the DP22C class.
     * This test verifies that the method returns the correct SensorModelID
     * that was set during the construction of the DP22C object.
     */
    @Test
    void getSensorModelID_ShouldReturnCorrectSensorModelID() {
        // Arrange
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        DP22C sensor = new DP22C(deviceID, sensorModelID, sensorID);

        // Act
        SensorModelID actualSensorModelID = sensor.getSensorModelID();

        // Assert
        assertEquals(sensorModelID, actualSensorModelID);
    }


    /**
     * The following test if the values are being set correctly for the CAP200 sensor while using the MockConstruction
     * feature of Mockito.
     */

    @Test
    void mockConstructGetValueForAllReadings_shouldReturnCorrectValues() {
        // Arrange
        Integer readingID1 = 1;
        double expectedValue1 = 30.0;
        Integer readingID2 = 2;
        double expectedValue2 = -12.2;
        Integer readingID3 = 3;
        double expectedValue3 = 300.1;
        Integer readingID4 = 4;
        double expectedValue4 = -275.0;

        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<DP22CValue> DP22CValueDouble = mockConstruction(DP22CValue.class, (mock, context) -> {
            Double valueReading = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {
            DP22C DP22C = new DP22C(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            DP22C.setValue(readingID1);
            DP22C.setValue(readingID2);
            DP22C.setValue(readingID3);
            DP22C.setValue(readingID4);

            // Act
            List<DP22CValue> values = DP22CValueDouble.constructed();
            assertEquals(4, values.size());

            DP22CValue mockedValue1 = values.get(0);
            DP22CValue mockedValue2 = values.get(1);
            DP22CValue mockedValue3 = values.get(2);
            DP22CValue mockedValue4 = values.get(3);
            //Assert
            assertEquals(Double.toString(expectedValue1), mockedValue1.toString());
            assertEquals(Double.toString(expectedValue2), mockedValue2.toString());
            assertEquals(expectedValue3, Double.parseDouble(String.valueOf(mockedValue3)));
            assertEquals(expectedValue4, Double.parseDouble(String.valueOf(mockedValue4)));
            assertEquals(expectedValue4, Double.parseDouble(String.valueOf(DP22C.getValue())));
        }
    }
    /**
     * Tests if the equals method of the DP22C sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        DP22C mySensor = new DP22C(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
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

        DP22C mySensor = new DP22C(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests that the equals method returns true when comparing a Sensor object to its duplicate.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareSensorWithDifferentSensorID() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        DP22C mySensor = new DP22C(deviceId, sensorModelID, sensorIdDouble);
        DP22C mySensor2 = new DP22C(deviceId, sensorModelID, sensorIdDouble2);
        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests that the sameAs method returns true when comparing a Sensor object to its duplicate.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareSensorWithDifferentSensorID() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        DP22C mySensor = new DP22C(deviceId, sensorModelID, sensorIdDouble);
        DP22C mySensor2 = new DP22C(deviceId, sensorModelID, sensorIdDouble2);
        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

}
