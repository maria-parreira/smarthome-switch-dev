package smartHomeDDD.domain.domain.device;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the functionality of the Device class.
 * This class provides tests for the following scenarios:
 * - create a valid instance of Device
 * - return the DeviceId
 * - return true when the sameAs method is given the same object as parameter
 * - return false when the sameAs method is given two equal devices with different ids
 * - return false when the sameAs method is given two devices with different names
 * - return false when the sameAs method is given two devices with different models
 * - return false when the sameAs method is given two devices with different status
 * - return false when the sameAs method is given two devices with different room ids
 * - verify that the sameAs and equals method return false when given a device and a different object type
 * - verify if the deactivateDevice method correctly deactivates a device
 * - verify that the same object has the same hash code
 */

class DeviceTest {

    /**
     * Test to ensure that the Device constructor creates a Device object when given valid arguments.
     */
    @Test
    void validArguments_shouldCreateADevice(){
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        when(statusDouble.toString()).thenReturn("true");
        RoomID roomIDDouble = mock(RoomID.class);

        // Act
        Device device = new Device(deviceIdDouble, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);

        // Assert
        assertNotNull(device);
        assertEquals(device.getRoomId(), roomIDDouble);
        assertEquals(device.identity(), deviceIdDouble);
        assertEquals(device.getDeviceName(), deviceNameDouble);
        assertEquals(device.getDeviceModel(), deviceModelDouble);
        assertEquals(device.getActivationStatus(), statusDouble);
    }


    /**
     * Test to ensure that the identity method returns the device id.
     */
    @Test
    void shouldReturnDeviceId() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device = new Device(deviceIdDouble, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);

        // Act
        DeviceId deviceId = device.identity();

        // Assert
        assertEquals(deviceIdDouble, deviceId);
    }

    /**
     * Test to ensure that the sameAs method returns true when given the same object.
     */
    @Test
    void comparingTheSameDevice_shouldReturnTrue(){
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device = new Device(deviceIdDouble, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);

        // Act
        boolean sameObject = device.sameAs(device);

        // Assert
        assertTrue(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns true when given two equal devices, with different ids.
     */
    @Test
    void comparingTwoDevicesWithDifferentIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device1 = new Device(deviceIdDouble1, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);
        Device device2 = new Device(deviceIdDouble2, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different names.
     */
    @Test
    void comparingTwoDevicesWithDifferentNames_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        DeviceName deviceNameDouble1 = mock(DeviceName.class);
        DeviceName deviceNameDouble2 = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device1 = new Device(deviceIdDouble1, deviceNameDouble1, deviceModelDouble, statusDouble, roomIDDouble);
        Device device2 = new Device(deviceIdDouble2, deviceNameDouble2, deviceModelDouble, statusDouble, roomIDDouble);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different models.
     */
    @Test
    void comparingTwoDevicesWithDifferentModels_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble1 = mock(DeviceModel.class);
        DeviceModel deviceModelDouble2 = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device1 = new Device(deviceIdDouble1, deviceNameDouble, deviceModelDouble1, statusDouble, roomIDDouble);
        Device device2 = new Device(deviceIdDouble2, deviceNameDouble, deviceModelDouble2, statusDouble, roomIDDouble);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different status.
     */
    @Test
    void comparingTwoDevicesWithDifferentStatus_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble1 = mock(ActivationStatus.class);
        ActivationStatus statusDouble2 = mock(ActivationStatus.class);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device1 = new Device(deviceIdDouble1, deviceNameDouble, deviceModelDouble, statusDouble1, roomIDDouble);
        Device device2 = new Device(deviceIdDouble2, deviceNameDouble, deviceModelDouble, statusDouble2, roomIDDouble);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given two devices with different room ids.
     */
    @Test
    void comparingTwoDevicesWithDifferentRoomIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        RoomID roomIDDouble1 = mock(RoomID.class);
        RoomID roomIDDouble2 = mock(RoomID.class);
        Device device1 = new Device(deviceIdDouble1, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble1);
        Device device2 = new Device(deviceIdDouble2, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble2);

        // Act
        boolean sameObject = device1.sameAs(device2);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to ensure that the sameAs method returns false when given a device and a different object type.
     */
    @Test
    void comparingADeviceWithADifferentObjectWithSameAs_shouldReturnFalse(){

        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        when(statusDouble.toString()).thenReturn("true");
        RoomID roomIDDouble = mock(RoomID.class);
        Device device = new Device(deviceIdDouble, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);
        Room roomDouble = mock(Room.class);

        // Act
        boolean sameObject = device.sameAs(roomDouble);

        // Assert
        assertFalse(sameObject);
    }

    /**
     * Test to verify if the deactivateDevice method correctly deactivates a device.
     */
    @Test
    void deactivateDevice_shouldDeactivateDevice() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        when(statusDouble.toString()).thenReturn("true");
        RoomID roomIDDouble = mock(RoomID.class);
        Device device = new Device(deviceIdDouble, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);

        // Act
        boolean result = device.deactivateDevice();

        // Assert
        assertTrue(result);
    }

    /**
     * Test to verify that same object has the same hash code.
     */
    @Test
    void hashCode_sameDevice_shouldReturnSameHashCode() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        DeviceName deviceNameDouble = mock(DeviceName.class);
        DeviceModel deviceModelDouble = mock(DeviceModel.class);
        ActivationStatus statusDouble = mock(ActivationStatus.class);
        RoomID roomIDDouble = mock(RoomID.class);
        Device device1 = new Device(deviceIdDouble, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);
        Device device2 = new Device(deviceIdDouble, deviceNameDouble, deviceModelDouble, statusDouble, roomIDDouble);

        // Act
        int hashCode1 = device1.hashCode();
        int hashCode2 = device2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testEqualsWithNonDeviceObject() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        ActivationStatus status = mock(ActivationStatus.class);
        RoomID roomID = mock(RoomID.class);

        Device device = new Device(deviceId, deviceName, deviceModel, status, roomID);

        // Act & Assert
        // Scenario: Comparing with a non-Device object
        assertNotEquals(device, new Object());
    }

    @Test
    void testHashCode() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        ActivationStatus status = mock(ActivationStatus.class);
        RoomID roomID = mock(RoomID.class);

        Device device = new Device(deviceId, deviceName, deviceModel, status, roomID);

        // Act
        int hashCode = device.hashCode();

        // Assert
        assertNotEquals(0, hashCode);
    }

}
