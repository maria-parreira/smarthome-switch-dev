package smartHomeDDD.domain.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.controllers.AddNewSensorToDeviceController;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.SensorDTO;
import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import smartHomeDDD.persistence.mem.RepositorySensorMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceSensor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the AddNewSensorToDeviceController class.
 * It encompasses the following scenarios:
 * - Constructor with null ServiceSensor
 * - Add new sensor to device
 * - Add repeated sensor to device
 * - Add sensor with empty sensorID
 * - Add sensor with blank sensorID (spaces)
 * - Add sensor with tab sensorID
 * - Add sensor with carriage return sensorID
 * - Add sensor with null sensorID
 */
class AddNewSensorToDeviceControllerTest {

    public static Device getDevice() {
        DeviceId deviceId = new DeviceId("deviceID");
        DeviceName deviceName = new DeviceName("deviceName");
        DeviceModel deviceModel = new DeviceModel("deviceModel");
        ActivationStatus status = new ActivationStatus(true);
        RoomID roomId = new RoomID("roomID");
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        return factoryDevice.createDevice(deviceId, deviceName, deviceModel, status, roomId);
    }

    /**
     * This test case checks if the constructor of the AddNewSensorToDeviceController class
     * throws an IllegalArgumentException when it is called with a null ServiceSensor.
     * The expected result is an IllegalArgumentException with the message "Sensor Service cannot be null."
     */
    @Test
    void nullServiceSensor_ShouldThrowIllegalArgumentException() {

        // Arrange
        String expectedMessage = "Sensor Service cannot be null.";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AddNewSensorToDeviceController(null));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * This test case checks if a valid sensor is added to the repository.
     * The expected result is that the sensor is added to the repository.
     */
    @Test
    void validSensor_shouldAddSensorToRepository() {

        // Arrange
        IRepositorySensor repoSensor = new RepositorySensorMem();
        IRepositoryDevice repoDevice = new RepositoryDeviceMem();
        ImplFactorySensor factorySensor = new ImplFactorySensor();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repoSensor, repoDevice, mockId);
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(serviceSensor);
        repoDevice.save(getDevice());

        String sensorID = "sensorId";
        String deviceID = "deviceID";
        String sensorModelID = "PC500W";
        SensorID sensorID1 = new SensorID(sensorID);
        SensorDTO sensorDTO = new SensorDTO(sensorID, deviceID, sensorModelID);
        when(mockId.generateID()).thenReturn(sensorID);

        // Act
        SensorDTO result = controller.addNewSensorToDevice(sensorDTO);

        // Assert
        assertEquals(sensorID1, new SensorID(result.getSensorID()));
    }

    /**
     * This test case checks if an exception is thrown when a sensor with a repeated ID is added.
     * The expected result is an IllegalArgumentException with the message "Sensor already exists".
     */
    @Test
    void repeatedSensorID_shouldThrowException() {

        //Arrange
        IRepositorySensor repoSensor = new RepositorySensorMem();
        IRepositoryDevice repoDevice = new RepositoryDeviceMem();
        ImplFactorySensor factorySensor = new ImplFactorySensor();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repoSensor, repoDevice, mockId);
        AddNewSensorToDeviceController myController = new AddNewSensorToDeviceController(serviceSensor);
        repoDevice.save(getDevice());

        String sensorID = "sensorId";
        String deviceID = "deviceID";
        String sensorModelID = "PC500W";
        when(mockId.generateID()).thenReturn(sensorID);
        SensorDTO sensorDTO = new SensorDTO(sensorID, deviceID, sensorModelID);
        myController.addNewSensorToDevice(sensorDTO);

        SensorDTO otherSensorDTO = new SensorDTO(sensorID, deviceID, sensorModelID);
        String expectedMessage = "Sensor already exists";

        // Act & Assert
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () ->
                myController.addNewSensorToDevice(otherSensorDTO));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }



    @Test
    void addNewSensorToDevice_ShouldReturnSensorDTO() {
        // Arrange
        IRepositorySensor repoSensor = new RepositorySensorMem();
        IRepositoryDevice repoDevice = new RepositoryDeviceMem();
        ImplFactorySensor factorySensor = new ImplFactorySensor();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor, repoSensor, repoDevice, mockId);
        AddNewSensorToDeviceController controller = new AddNewSensorToDeviceController(serviceSensor);
        repoDevice.save(AddNewSensorToDeviceControllerTest.getDevice());

        String sensorID = "sensorId";
        String deviceID = "deviceID";
        String sensorModelID = "PC500W";
        when(mockId.generateID()).thenReturn(sensorID);
        SensorDTO sensorDTO = new SensorDTO(sensorID, deviceID, sensorModelID);

        // Act
        SensorDTO result = controller.addNewSensorToDevice(sensorDTO);

        // Assert
        assertNotNull(result);
        assertEquals(sensorID, result.getSensorID());
        assertEquals(deviceID, result.getDeviceID());
        assertEquals(sensorModelID, result.getSensorModelID());
    }
}
