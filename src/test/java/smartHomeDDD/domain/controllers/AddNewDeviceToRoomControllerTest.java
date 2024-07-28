package smartHomeDDD.domain.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import smartHomeDDD.persistence.mem.RepositoryRoomMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceDevice;
import org.junit.jupiter.api.Test;
import smartHomeDDD.controllers.AddNewDeviceToRoomController;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the US05AddNewDeviceToRoomController class. It lists the following scenarios:
 * - Instantiation of the controller with null ServiceDevice.
 * - Successful creation and saving of a new device with valid arguments.
 * - Successful creation and saving of two new devices with valid arguments.
 * - Attempt to create and save a device with an existent DeviceId.
 * - Attempt to create and save a device with a null DeviceId.
 * - Attempt to create and save a device with a null RoomId.
 * - Attempt to create and save a device with a null DeviceName.
 * - Attempt to create and save a device with a null DeviceModel.
 * - Attempt to create and save a device with an inactive ActivationStatus.
 * It also tests the same scenarios using JPA and Spring repositories.
 */
class AddNewDeviceToRoomControllerTest {

    private static Room getRoom() {
        HouseId houseId = new HouseId("houseId");
        RoomID roomId = new RoomID("roomId");
        FloorNumber floorNumber = new FloorNumber(1);
        Length length = new Length(10);
        Width width = new Width(10);
        Height height = new Height(10);
        Dimensions dimensions = new Dimensions(length, width, height);
        RoomName roomName = new RoomName("Living Room");
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        return factoryRoom.createRoom(houseId, roomId, floorNumber, dimensions, true, roomName);
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when the ServiceDevice is null.
     */
    @Test
    void nullServiceDevice_ShouldThrowIllegalArgumentException() {

        // Arrange
        String expectedMessage = "ServiceDevice cannot be null.";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AddNewDeviceToRoomController(null));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that a new device is created and saved in a Memory Repository when valid arguments are provided.
     * The input and output DTOs attributes are compared to assure that the device was saved correctly.
     * The size of the repository is then checked, to verify that one device was saved.
     */
    @Test
    void validArguments_ShouldCreateAndSaveDevice(){
        // Arrange
        FactoryDevice factoryDevice = new ImplFactoryDevice();

        // Creation of Repositories Room and Device
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();

        // Creation of Service
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, mockId);

        // Creation of DTO
        DeviceDTO deviceDTO = new DeviceDTO("deviceId", "roomId", "deviceName",
                "deviceModel", true);

        // Saving a room in the repository
        Room room = getRoom();
        repositoryRoom.save(room);

        // Creation of Controller
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(serviceDevice);

        int expectedSize = 1;

        when(mockId.generateID()).thenReturn("deviceId");

        // Act
        DeviceDTO result = controller.addNewDeviceToRoom(deviceDTO);

        // Assert
        assertEquals(deviceDTO.getDeviceId(), result.getDeviceId());
        assertEquals(deviceDTO.getRoomId(), result.getRoomId());
        assertEquals(deviceDTO.getDeviceName(), result.getDeviceName());
        assertEquals(deviceDTO.getDeviceModel(), result.getDeviceModel());
        assertEquals(deviceDTO.getActivationStatus(), result.getActivationStatus());
        assertEquals(expectedSize, repositoryDevice.findAll().spliterator().getExactSizeIfKnown());
    }

    /**
     * Test to verify that two new devices are created and saved when valid arguments are provided.
     * The input and output DTOs attributes are compared to assure that the devices were saved correctly.
     * The size of the repository is then checked, to verify that two devices were saved.
     */
    @Test
    void validArguments_ShouldCreateAndSaveTwoDevices() {

        // Arrange
        //Creation of Factory Device
        FactoryDevice factoryDevice = new ImplFactoryDevice();

        // Creation of Repositories Room and Device
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();

        // Creation of Service
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, mockId);

        // Creation of DTOs
        DeviceDTO deviceDTO1 = new DeviceDTO("deviceId1", "roomId",
                "deviceName1", "deviceModel1", true);
        DeviceDTO deviceDTO2 = new DeviceDTO("deviceId2", "roomId",
                "deviceName2", "deviceModel2", true);

        // Saving a room in the repository
        Room room = getRoom();
        repositoryRoom.save(room);

        // Creation of Controller
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(serviceDevice);

        int expectedSize = 2;

        when(mockId.generateID()).thenReturn("deviceId1").thenReturn("deviceId2");

        // Act
        DeviceDTO result1 = controller.addNewDeviceToRoom(deviceDTO1);
        DeviceDTO result2 = controller.addNewDeviceToRoom(deviceDTO2);

        // Assert
        assertEquals(deviceDTO1.getDeviceId(), result1.getDeviceId());
        assertEquals(deviceDTO1.getRoomId(), result1.getRoomId());
        assertEquals(deviceDTO1.getDeviceName(), result1.getDeviceName());
        assertEquals(deviceDTO1.getDeviceModel(), result1.getDeviceModel());
        assertEquals(deviceDTO1.getActivationStatus(), result1.getActivationStatus());

        assertEquals(deviceDTO2.getDeviceId(), result2.getDeviceId());
        assertEquals(deviceDTO2.getRoomId(), result2.getRoomId());
        assertEquals(deviceDTO2.getDeviceName(), result2.getDeviceName());
        assertEquals(deviceDTO2.getDeviceModel(), result2.getDeviceModel());
        assertEquals(deviceDTO2.getActivationStatus(), result2.getActivationStatus());

        assertEquals(expectedSize, repositoryDevice.findAll().spliterator().getExactSizeIfKnown());
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when the RoomId is null.
     */
    @Test
    void nullRoomId_ShouldThrowIllegalArgumentException() {

        // Arrange
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        GenerateRandomId mockId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, mockId);
        DeviceDTO deviceDTO = new DeviceDTO("deviceId", null, "deviceName", "deviceModel", true);
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(serviceDevice);

        String expectedMessage = "RoomId cannot be null or empty";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                controller.addNewDeviceToRoom(deviceDTO));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when the DeviceName is null.
     */
    @Test
    void nullDeviceName_ShouldThrowIllegalArgumentException() {

        // Arrange
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        GenerateRandomId mockId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, mockId);
        DeviceDTO deviceDTO = new DeviceDTO("deviceId", "roomId", null, "deviceModel", true);
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(serviceDevice);

        String expectedMessage = "Device Name cannot be null or empty.";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                controller.addNewDeviceToRoom(deviceDTO));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when the DeviceModel is null.
     */
    @Test
    void nullDeviceModel_ShouldThrowIllegalArgumentException() {

        // Arrange
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        GenerateRandomId mockId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, mockId);
        DeviceDTO deviceDTO = new DeviceDTO("deviceId", "roomId", "deviceName", null, true);
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(serviceDevice);

        String expectedMessage = "Device Model cannot be null or empty.";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                controller.addNewDeviceToRoom(deviceDTO));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when the ActivationStatus is false.
     */
    @Test
    void inactiveActivationStatus_ShouldThrowIllegalArgumentException() {

        // Arrange
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        GenerateRandomId mockId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, mockId);
        DeviceDTO deviceDTO = new DeviceDTO("deviceId", "roomId", "deviceName", "deviceModel", false);
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(serviceDevice);

        // Saving a room in the repository
        Room room = getRoom();
        repositoryRoom.save(room);

        String expectedMessage = "Device cannot be inactive";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                controller.addNewDeviceToRoom(deviceDTO));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown when the DeviceId is already in the repository.
     */
    @Test
    void existingDevice_ShouldThrowIllegalArgumentException() {

        // Arrange
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        GenerateRandomId mockId = mock(GenerateRandomId.class);
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, mockId);
        DeviceDTO deviceDTO1 = new DeviceDTO("deviceId", "roomId", "deviceName", "deviceModel", true);
        DeviceDTO deviceDTO2 = new DeviceDTO("deviceId", "roomId", "deviceName", "deviceModel", true);
        AddNewDeviceToRoomController controller = new AddNewDeviceToRoomController(serviceDevice);

        when(mockId.generateID()).thenReturn("deviceId");
        // Saving a room in the repository
        Room room = getRoom();
        repositoryRoom.save(room);

        controller.addNewDeviceToRoom(deviceDTO1);

        String expectedMessage = "Device already exists";

        // Act & Assert
        Exception exception = assertThrows(DataIntegrityViolationException.class, () ->
                controller.addNewDeviceToRoom(deviceDTO2));

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
