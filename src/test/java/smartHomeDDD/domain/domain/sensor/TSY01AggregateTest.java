package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.TSY01;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.TSY01Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This is the test class for the TSY01 Sensor. It tests the following scenarios:
 * - Test the successful instantiation of the TSY01 object
 * - Test that the sameAs method returns true when the object is the same
 * - Test that the sameAs method returns false when the sensor ID is different
 * - Test that the sameAs method returns false when the Device ID is different
 * - Test that the getValue method returns the correct value for reading 1
 * - Test that the getValue method returns the correct value for reading 2
 * - Test that the getValue method returns the correct value for reading 3
 */
public class TSY01AggregateTest {

    /**
     * Tests a successful instantiation of the TSY01 Sensor Object
     */
    @Test
    void validParameters_shouldCreateNewInstance(){
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("1");
        SensorModelID sensorModelId = new SensorModelID("TSY01");
        SensorID sensorID = new SensorID("1");

        // Act
        TSY01 tsy01 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID);

        // Assert
        assertNotNull(tsy01);
        assertEquals(deviceId, tsy01.getDeviceID());
        assertEquals(sensorModelId, tsy01.getSensorModelID());
        assertEquals(sensorID, tsy01.identity());
    }


    /**
     * Tests that the sameAs method correctly returns true when the object is the same
     */
    @Test
    void sameObject_shouldReturnTrue(){
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("1");
        SensorModelID sensorModelId = new SensorModelID("TSY01");
        SensorID sensorID = new SensorID("1");

        TSY01 tsy01 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID);

        // Act
        boolean isEquals = tsy01.sameAs(tsy01);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests that the sameAs method correctly returns false when the sensor ID is different
     */
    @Test
    void differentIDs_shouldReturnFalse(){
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("1");
        SensorModelID sensorModelId = new SensorModelID("TSY01");
        SensorID sensorID = new SensorID("1");
        SensorID sensorID2 = new SensorID("2");

        TSY01 tsy01 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID);
        TSY01 tsy012 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID2);

        // Act
        boolean isEquals = tsy01.sameAs(tsy012);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests that the sameAs method correctly returns false when the Device ID is different
     */
    @Test
    void differentDeviceIDs_shouldReturnFalse(){
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("1");
        DeviceId deviceId2 = new DeviceId("2");
        SensorModelID sensorModelId = new SensorModelID("TSY01");
        SensorID sensorID = new SensorID("1");

        TSY01 tsy01 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID);
        TSY01 tsy012 = (TSY01) implFactorySensor.createSensor(deviceId2, sensorModelId, sensorID);

        // Act
        boolean isEquals = tsy01.sameAs(tsy012);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 1
     */
    @Test
    void getValueFromReading1_shouldReturnSensorValue(){
        // Arrange
        Integer readingID = 1;
        int value = 15;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("1");
        SensorModelID sensorModelId = new SensorModelID("TSY01");
        SensorID sensorID = new SensorID("1");

        TSY01 tsy01 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID);
        tsy01.setValue(readingID);

        // Act
        TSY01Value tsy01Value = (TSY01Value) tsy01.getValue();

        // Assert
        assertEquals(Integer.toString(value), tsy01Value.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 2
     */
    @Test
    void getValueFromReading2_shouldReturnSensorValue(){
        Integer readingID = 2;
        int value = 85;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("1");
        SensorModelID sensorModelId = new SensorModelID("TSY01");
        SensorID sensorID = new SensorID("1");

        TSY01 tsy01 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID);
        tsy01.setValue(readingID);

        // Act
        TSY01Value tsy01Value = (TSY01Value) tsy01.getValue();

        // Assert
        assertEquals(Integer.toString(value), tsy01Value.toString());
    }

    /**
     * Tests that the getValue method correctly returns the value for reading 3
     */
    @Test
    void getValueFromReading3_shouldReturnSensorValue(){
        Integer readingID = 3;
        int value = 65;
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("1");
        SensorModelID sensorModelId = new SensorModelID("TSY01");
        SensorID sensorID = new SensorID("1");

        TSY01 tsy01 = (TSY01) implFactorySensor.createSensor(deviceId, sensorModelId, sensorID);
        tsy01.setValue(readingID);

        // Act
        TSY01Value tsy01Value = (TSY01Value) tsy01.getValue();

        // Assert
        assertEquals(Integer.toString(value), tsy01Value.toString());
    }
}
