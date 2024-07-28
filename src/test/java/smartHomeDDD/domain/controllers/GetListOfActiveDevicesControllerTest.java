package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetListOfActiveDevicesController;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import smartHomeDDD.persistence.mem.RepositoryRoomMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceDevice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the GetListOfActiveDevicesController class.
 * It verifies the behavior of GetListOfActiveDevicesController methods under various scenarios.
 * Scenarios tested include:
 * - Null device service
 * - Non-null device service
 * - Retrieval of a list of active devices
 * - Handling of inactive devices
 */
class GetListOfActiveDevicesControllerTest {

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

    private static Room getRoom2() {
        HouseId houseId = new HouseId("houseId");
        RoomID roomId = new RoomID("roomId2");
        FloorNumber floorNumber = new FloorNumber(1);
        Length length = new Length(10);
        Width width = new Width(10);
        Height height = new Height(10);
        Dimensions dimensions = new Dimensions(length, width, height);
        RoomName roomName = new RoomName("Bedroom");
        FactoryRoom factoryRoom = new ImplFactoryRoom();
        return factoryRoom.createRoom(houseId, roomId, floorNumber, dimensions, true, roomName);
    }

    /**
     * This test case verifies that the GetListOfActiveDevicesController constructor throws an IllegalArgumentException
     * when the device service is null.
     */
    @Test
    void nullDeviceService_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new GetListOfActiveDevicesController(null));
    }

    /**
     * This test case verifies that the GetListOfActiveDevicesController constructor does not throw an IllegalArgumentException
     * when the device service is not null.
     */
    @Test
    void nonNullDeviceService_ShouldNotThrowIllegalArgumentException() {
        // Arrange
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice deviceService = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, generateRandomId);

        // Act and Assert
        new GetListOfActiveDevicesController(deviceService);
    }

    /**
     * This test case verifies that the getListOfActiveDevices method returns a list of active devices.
     */

    @Test
    void getListOfActiveDevices_ShouldReturnActiveDevices() {
        // Arrange
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        FactoryDevice factoryDevice = new ImplFactoryDevice();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, generateRandomId);

        DeviceName deviceName = new DeviceName("deviceXpto1");
        DeviceModel deviceModel = new DeviceModel("deviceModel1");
        ActivationStatus activationStatus = new ActivationStatus(true);
        RoomID roomId = new RoomID("roomId");

        // Create a new room and add it to the repository
        Room room = getRoom();
        repositoryRoom.save(room);

        serviceDevice.addNewDevice(deviceName, deviceModel, activationStatus, roomId);

        GetListOfActiveDevicesController controller = new GetListOfActiveDevicesController(serviceDevice);

        // Act
        List<DeviceDTO> activeDevices = controller.getListOfActiveDevices();

        // Assert
        assertEquals(1, activeDevices.size());
    }

    /**
     * This test case verifies that the getListOfActiveDevices method returns the correct number of active devices.
     */

    @Test
    void getListOfActiveDevices_ShouldReturnTwoActiveDevices() {
        // Arrange
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        FactoryDevice factoryDevice = new ImplFactoryDevice();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, generateRandomId);

        DeviceId deviceId1 = new DeviceId("deviceId1");
        DeviceName deviceName1 = new DeviceName("deviceXpto1");
        DeviceModel deviceModel1 = new DeviceModel("deviceModel1");
        ActivationStatus activationStatus1 = new ActivationStatus(true);
        RoomID roomId1 = new RoomID("roomId");

        // Create a new room and add it to the repository
        Room room = getRoom();
        repositoryRoom.save(room);
        Room room2 = getRoom2();
        repositoryRoom.save(room2);

        DeviceId deviceId2 = new DeviceId("deviceId2");
        DeviceName deviceName2 = new DeviceName("deviceXpto2");
        DeviceModel deviceModel2 = new DeviceModel("deviceModel2");
        ActivationStatus activationStatus2 = new ActivationStatus(true);
        RoomID roomId2 = new RoomID("roomId2");

        factoryDevice.createDevice(deviceId1, deviceName1, deviceModel1, activationStatus1, roomId1);
        factoryDevice.createDevice(deviceId2, deviceName2, deviceModel2, activationStatus2, roomId2);

        // Add devices to the service
        serviceDevice.addNewDevice(deviceName1, deviceModel1, activationStatus1, roomId1);
        serviceDevice.addNewDevice(deviceName2, deviceModel2, activationStatus2, roomId2);

        GetListOfActiveDevicesController controller = new GetListOfActiveDevicesController(serviceDevice);

        // Act
        List<DeviceDTO> activeDevices = controller.getListOfActiveDevices();

        // Assert
        assertEquals(2, activeDevices.size());
    }


    /**
     * This test case verifies that the getListOfActiveDevices method returns an empty list when there are no active devices.
     */

    @Test
    void whenNoActiveDevices_shouldReturnEmptyList() {
        // Arrange
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        IRepositoryRoom repositoryRoom = new RepositoryRoomMem();
        FactoryDevice factoryDevice = new ImplFactoryDevice();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(repositoryDevice, repositoryRoom, factoryDevice, generateRandomId);

        // Create a new room and add it to the repository
        Room room = getRoom();
        repositoryRoom.save(room);

        GetListOfActiveDevicesController controller = new GetListOfActiveDevicesController(serviceDevice);

        // Act
        List<DeviceDTO> activeDevices = controller.getListOfActiveDevices();

        // Assert
        assertTrue(activeDevices.isEmpty());
    }


}