package smartHomeDDD.domain.services;

import org.junit.jupiter.api.Test;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.repository.*;
import smartHomeDDD.services.ServiceDeviceByType;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


/**
 * Test class for ServiceDeviceByType.
 * Test cases:
 * -testServiceDeviceByTypeConstructor_shouldConstructServiceObject
 * -testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositorySensorIsNull
 * -testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositorySensorTypeIsNull
 * -testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryActuatorIsNull
 * -testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryActuatorTypeIsNull
 * -testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryDeviceIsNull
 * -testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryActuatorModelIsNull
 * -testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositorySensorModelIsNull
 * -testGetDevicesByType_shouldReturnMapOfDevices
 */
public class ServiceDeviceByTypeTest {

    /**
     * This method tests the successful creation of a ServiceDeviceByType object.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldConstructServiceObject() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);

        // Act
        ServiceDeviceByType serviceDeviceByType = new ServiceDeviceByType(repositorySensor, repositorySensorType, repositoryActuator, repositoryActuatorType, repositoryDevice, repositoryActuatorModel, repositorySensorModel);

        // Assert
        assertNotNull(serviceDeviceByType);
    }

    /**
     * This method tests the exception thrown when the sensor repository is null.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositorySensorIsNull() {
        // Arrange
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);

        // Act
        String actual = assertThrows(IllegalArgumentException.class, () ->
                new ServiceDeviceByType(null, repositorySensorType, repositoryActuator, repositoryActuatorType, repositoryDevice, repositoryActuatorModel, repositorySensorModel)).getMessage();

        // Assert
        String expected = "Sensor Repository cannot be null";
        assertEquals(expected, actual);
    }

    /**
     * This method tests the exception thrown when the sensor type repository is null.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositorySensorTypeIsNull() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);

        // Act
        String actual = assertThrows(IllegalArgumentException.class, () ->
                new ServiceDeviceByType(repositorySensor, null, repositoryActuator, repositoryActuatorType, repositoryDevice, repositoryActuatorModel, repositorySensorModel)).getMessage();

        // Assert
        String expected = "Sensor Type Repository cannot be null";
        assertEquals(expected, actual);
    }

    /**
     * This method tests the exception thrown when the actuator repository is null.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryActuatorIsNull() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);

        // Act
        String actual = assertThrows(IllegalArgumentException.class, () ->
                new ServiceDeviceByType(repositorySensor, repositorySensorType, null, repositoryActuatorType, repositoryDevice, repositoryActuatorModel, repositorySensorModel)).getMessage();

        // Assert
        String expected = "Actuator Repository cannot be null";
        assertEquals(expected, actual);
    }

    /**
     * This method tests the exception thrown when the actuator type repository is null.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryActuatorTypeIsNull() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);

        // Act
        String actual = assertThrows(IllegalArgumentException.class, () ->
                new ServiceDeviceByType(repositorySensor, repositorySensorType, repositoryActuator, null, repositoryDevice, repositoryActuatorModel, repositorySensorModel)).getMessage();

        // Assert
        String expected = "Actuator Type Repository cannot be null";
        assertEquals(expected, actual);
    }

    /**
     * This method tests the exception thrown when the device repository is null.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryDeviceIsNull() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);

        // Act
        String actual = assertThrows(IllegalArgumentException.class, () ->
                new ServiceDeviceByType(repositorySensor, repositorySensorType, repositoryActuator, repositoryActuatorType, null, repositoryActuatorModel, repositorySensorModel)).getMessage();

        // Assert
        String expected = "Device Repository cannot be null";
        assertEquals(expected, actual);
    }

    /**
     * This method tests the exception thrown when the actuator model repository is null.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositoryActuatorModelIsNull() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);

        // Act
        String actual = assertThrows(IllegalArgumentException.class, () ->
                new ServiceDeviceByType(repositorySensor, repositorySensorType, repositoryActuator, repositoryActuatorType, repositoryDevice, null, repositorySensorModel)).getMessage();

        // Assert
        String expected = "Actuator Model Repository cannot be null";
        assertEquals(expected, actual);
    }

    /**
     * This method tests the exception thrown when the sensor model repository is null.
     */
    @Test
    void testServiceDeviceByTypeConstructor_shouldThrowExceptionWhenRepositorySensorModelIsNull() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);

        // Act
        String actual = assertThrows(IllegalArgumentException.class, () ->
                new ServiceDeviceByType(repositorySensor, repositorySensorType, repositoryActuator, repositoryActuatorType, repositoryDevice, repositoryActuatorModel, null)).getMessage();

        // Assert
        String expected = "Sensor Model Repository cannot be null";
        assertEquals(expected, actual);
    }


    /**
     * This method tests the successful retrieval of a map of devices.
     */
    @Test
    void testGetDevicesByType_shouldReturnMapOfDevices() {
        // Arrange
        IRepositorySensor repositorySensor = mock(IRepositorySensor.class);
        IRepositorySensorType repositorySensorType = mock(IRepositorySensorType.class);
        IRepositoryActuator repositoryActuator = mock(IRepositoryActuator.class);
        IRepositoryActuatorType repositoryActuatorType = mock(IRepositoryActuatorType.class);
        IRepositoryDevice repositoryDevice = mock(IRepositoryDevice.class);
        IRepositoryActuatorModel repositoryActuatorModel = mock(IRepositoryActuatorModel.class);
        IRepositorySensorModel repositorySensorModel = mock(IRepositorySensorModel.class);
        ServiceDeviceByType serviceDeviceByType = new ServiceDeviceByType(repositorySensor, repositorySensorType, repositoryActuator, repositoryActuatorType, repositoryDevice, repositoryActuatorModel, repositorySensorModel);

        // Act
         Map<String, List<Device>> actual = serviceDeviceByType.getDevicesByType();

        // Assert
        assertNotNull(actual);
    }


}
