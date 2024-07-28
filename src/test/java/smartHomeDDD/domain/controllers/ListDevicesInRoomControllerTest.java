package smartHomeDDD.domain.controllers;

import jakarta.persistence.EntityNotFoundException;
import smartHomeDDD.controllers.ListDevicesInRoomController;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.ImplFactoryDevice;
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
 * Test class for ListDevicesInRoomController.
 * It tests the conditions for:
 * - returning a list of devices in a room with two devices
 * - returning an empty list when there are no devices in the room
 * - throwing an exception when the room does not exist
 * - throwing an exception when the room ID is null
 * - throwing an exception when the device service is null
 */

class ListDevicesInRoomControllerTest {

    /**
     * Tests the listDevicesInRoom() method when there are two devices in the room.
     * It verifies that the method returns a list of two elements in this scenario.
     */
    @Test
    void twoDevicesInRoom_ShouldReturnListOfTwoElements() {
        // Arrange
        DeviceName name = new DeviceName("Device");
        DeviceId id = new DeviceId("Device1");
        DeviceId id2 = new DeviceId("Device2");
        DeviceId id3 = new DeviceId("Device3");
        RoomID roomId = new RoomID("Room1");
        RoomID roomID2 = new RoomID("Room2");
        ActivationStatus status = new ActivationStatus(true);
        RoomName roomName1 = new RoomName("Living Room");
        RoomName roomName2 = new RoomName("Bedroom");
        DeviceModel model = new DeviceModel("model");

        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        Device device1 = factoryDevice.createDevice(id, name, model, status, roomId);
        Device device2 = factoryDevice.createDevice(id2, name, model, status, roomId);
        Device device3 = factoryDevice.createDevice(id3, name, model, status, roomID2);

        RepositoryDeviceMem deviceRepo = new RepositoryDeviceMem();
        deviceRepo.save(device1);
        deviceRepo.save(device2);
        deviceRepo.save(device3);

        ImplFactoryRoom factoryRoom = new ImplFactoryRoom();
        Room room1 = factoryRoom.createRoom(new HouseId("House1"), roomId, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName1);
        Room room2 = factoryRoom.createRoom(new HouseId("House1"), roomID2, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName2);

        RepositoryRoomMem roomRepo = new RepositoryRoomMem();
        roomRepo.save(room1);
        roomRepo.save(room2);

        String roomID = "Room1";

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(deviceRepo, roomRepo, factoryDevice, generateRandomId);
        ListDevicesInRoomController controller = new ListDevicesInRoomController(serviceDevice);

        int expectedValue = 2;

        // Act
        List<DeviceDTO> devicesInRoom = controller.listDevicesInARoom(roomID);

        // Assert
        assertEquals(expectedValue, devicesInRoom.size());
        assertTrue(devicesInRoom.stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomID)));
    }

    /**
     * Tests the listDevicesInRoom() method when there are no devices in the room.
     * It verifies that the method returns an empty list in this scenario.
     */
    @Test
    void noDevicesInRoom_ShouldReturnEmptyList() {
        // Arrange
        DeviceName name = new DeviceName("Device1");
        DeviceId id = new DeviceId("Device1");
        DeviceId id2 = new DeviceId("Device2");
        DeviceId id3 = new DeviceId("Device3");
        ActivationStatus status = new ActivationStatus(true);
        DeviceModel model = new DeviceModel("model");
        RoomID roomID1 = new RoomID("Room1");
        RoomID roomID2 = new RoomID("Room2");
        RoomID roomID3 = new RoomID("Room3");
        RoomName roomName1 = new RoomName("Living Room");
        RoomName roomName2 = new RoomName("Bedroom");
        RoomName roomName3 = new RoomName("Kitchen");

        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        Device device1 = factoryDevice.createDevice(id, name, model, status, roomID1);
        Device device2 = factoryDevice.createDevice(id2, name, model, status, roomID1);
        Device device3 = factoryDevice.createDevice(id3, name, model, status, roomID2);

        RepositoryDeviceMem deviceRepo = new RepositoryDeviceMem();
        deviceRepo.save(device1);
        deviceRepo.save(device2);
        deviceRepo.save(device3);

        ImplFactoryRoom factoryRoom = new ImplFactoryRoom();
        Room room1 = factoryRoom.createRoom(new HouseId("House1"), roomID1, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName1);
        Room room2 = factoryRoom.createRoom(new HouseId("House1"), roomID2, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName2);
        Room room3 = factoryRoom.createRoom(new HouseId("House1"), roomID3, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName3);

        RepositoryRoomMem roomRepo = new RepositoryRoomMem();
        roomRepo.save(room1);
        roomRepo.save(room2);
        roomRepo.save(room3);

        String roomID = "Room3";

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(deviceRepo, roomRepo, factoryDevice, generateRandomId);
        ListDevicesInRoomController controller = new ListDevicesInRoomController(serviceDevice);

        // Act
        List<DeviceDTO> devicesInRoom = controller.listDevicesInARoom(roomID);

        // Assert
        assertTrue(devicesInRoom.isEmpty());
        assertFalse(devicesInRoom.stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomID)));
    }

    /**
     * Tests the listDevicesInRoom() method when the room does not exist.
     * It verifies that the method throws an IllegalArgumentException in this scenario.
     */
    @Test
    void nonExistentRoom_shouldThrowException() {
        // Arrange
        DeviceName name = new DeviceName("Device1");
        DeviceId id = new DeviceId("Device1");
        DeviceId id2 = new DeviceId("Device2");
        DeviceId id3 = new DeviceId("Device3");
        ActivationStatus status = new ActivationStatus(true);
        DeviceModel model = new DeviceModel("model");
        RoomID roomID1 = new RoomID("Room1");
        RoomID roomID2 = new RoomID("Room2");
        RoomName roomName1 = new RoomName("Living Room");
        RoomName roomName2 = new RoomName("Bedroom");

        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        Device device1 = factoryDevice.createDevice(id, name, model, status, roomID1);
        Device device2 = factoryDevice.createDevice(id2, name, model, status, roomID1);
        Device device3 = factoryDevice.createDevice(id3, name, model, status, roomID2);

        RepositoryDeviceMem deviceRepo = new RepositoryDeviceMem();
        deviceRepo.save(device1);
        deviceRepo.save(device2);
        deviceRepo.save(device3);

        ImplFactoryRoom factoryRoom = new ImplFactoryRoom();
        Room room1 = factoryRoom.createRoom(new HouseId("House1"), roomID1, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName1);
        Room room2 = factoryRoom.createRoom(new HouseId("House1"), roomID2, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName2);

        RepositoryRoomMem roomRepo = new RepositoryRoomMem();
        roomRepo.save(room1);
        roomRepo.save(room2);

        String roomID = "Room3";

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(deviceRepo, roomRepo, factoryDevice, generateRandomId);
        ListDevicesInRoomController controller = new ListDevicesInRoomController(serviceDevice);
        String expectedMessage = "Room not found";

        // Act
        Exception exception = assertThrows(EntityNotFoundException.class, () -> controller.listDevicesInARoom(roomID));
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Tests the listDevicesInRoom() method when the room ID is null.
     * It verifies that the method throws an IllegalArgumentException in this scenario.
     */
    @Test
    void nullRoomId_shouldThrowException() {
        // Arrange
        DeviceName name = new DeviceName("Device1");
        DeviceId id = new DeviceId("Device1");
        DeviceId id2 = new DeviceId("Device2");
        DeviceId id3 = new DeviceId("Device3");
        ActivationStatus status = new ActivationStatus(true);
        DeviceModel model = new DeviceModel("model");
        RoomID roomID1 = new RoomID("Room1");
        RoomID roomID2 = new RoomID("Room2");
        RoomName roomName1 = new RoomName("Living Room");
        RoomName roomName2 = new RoomName("Bedroom");

        ImplFactoryDevice factoryDevice = new ImplFactoryDevice();
        Device device1 = factoryDevice.createDevice(id, name, model, status, roomID1);
        Device device2 = factoryDevice.createDevice(id2, name, model, status, roomID1);
        Device device3 = factoryDevice.createDevice(id3, name, model, status, roomID2);

        RepositoryDeviceMem deviceRepo = new RepositoryDeviceMem();
        deviceRepo.save(device1);
        deviceRepo.save(device2);
        deviceRepo.save(device3);

        ImplFactoryRoom factoryRoom = new ImplFactoryRoom();
        Room room1 = factoryRoom.createRoom(new HouseId("House1"), roomID1, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName1);
        Room room2 = factoryRoom.createRoom(new HouseId("House1"), roomID2, new FloorNumber(0), new Dimensions(new Length(15.0), new Width(7.5), new Height(2.5)), true, roomName2);

        RepositoryRoomMem roomRepo = new RepositoryRoomMem();
        roomRepo.save(room1);
        roomRepo.save(room2);

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceDevice serviceDevice = new ServiceDevice(deviceRepo, roomRepo, factoryDevice, generateRandomId);
        ListDevicesInRoomController controller = new ListDevicesInRoomController(serviceDevice);

        String expectedMessage = "RoomId cannot be null or empty";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.listDevicesInARoom(null));
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * This test verifies that the ListDevicesInRoomController constructor throws an IllegalArgumentException
     * when it is provided with a null Device Service.
     */
    @Test
    void nullDeviceService_shouldThrowException(){
        // Arrange
        String expectedMessage = "Device Service cannot be null";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ListDevicesInRoomController(null));
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }


}
