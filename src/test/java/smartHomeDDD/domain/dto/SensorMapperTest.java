package smartHomeDDD.domain.dto;

import smartHomeDDD.domain.sensor.PC500W;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.dto.SensorDTO;
import smartHomeDDD.dto.SensorMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the SensorMapper class.
 * Each test case focuses on a single method in the SensorMapper class.
 * It encompasses the following scenarios:
 * Test case for the DTOToSensorId method in the SensorMapper class.
 * Test case for the DTOToSensorModelId method in the SensorMapper class.
 * Test case for the convertSensorToDTO method in the SensorMapper class.
 */
class SensorMapperTest {

    /**
     * Test case for the DTOToSensorId method in the SensorMapper class.
     * The test verifies that the method correctly converts a sensor ID string to a SensorID object.
     */
    @Test
    void dTOToSensorId_ShouldReturnSensorID() {

        // Arrange
        String sensorId = "sensorId";
        // Act
        SensorID result = SensorMapper.convertToSensorId(sensorId);
        // Assert
        assertEquals(sensorId, result.toString());
    }

    /**
     * Test case for the DTOToSensorModelId method in the SensorMapper class.
     * The test verifies that the method correctly converts a sensor model ID string to a SensorModelID object.
     */

    @Test
    void dTOToSensorModelId_ShouldReturnSensorModelID() {

        // Arrange
        String sensorModelId = "PC500W";
        // Act
        SensorModelID result = SensorMapper.convertToSensorModelId(sensorModelId);
        // Assert
        assertEquals(sensorModelId, result.toString());
    }

    /**
     * Test case for the convertSensorToDTO method in the SensorMapper class.
     * The test verifies that the method correctly converts a Sensor object to a SensorDTO object.
     */

    @Test
    void validSensor_ReturnsDTO() {

        // Arrange
        PC500W sensor = mock(PC500W.class);
        when(sensor.getDeviceID()).thenReturn(new DeviceId("deviceID"));
        when(sensor.getSensorModelID()).thenReturn(new SensorModelID("PC500W"));
        when(sensor.identity()).thenReturn(new SensorID("sensorID"));

        // Act
        SensorDTO dto = SensorMapper.convertSensorToDTO(sensor);

        // Assert
        assertEquals("deviceID", dto.getDeviceID());
        assertEquals("PC500W", dto.getSensorModelID());
        assertEquals("sensorID", dto.getSensorID());
    }

}