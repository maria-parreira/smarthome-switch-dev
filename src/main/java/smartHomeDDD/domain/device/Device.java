package smartHomeDDD.domain.device;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.*;

/**
 * The Device class represents a device in the smartHomeDDD domain.
 * This class is an aggregate root in the domain-driven design context.
 * <p>
 * A Device instance encapsulates the identity, name, model, activation status, and room ID of a device.
 * The identity of a device is represented by a DeviceId object, which is unique for each device.
 * The name and model of a device are represented by DeviceName and DeviceModel objects respectively.
 * The activation status of a device is represented by an ActivationStatus object.
 * The room in which a device is located is represented by a RoomId object.
 * <p>
 * The class provides methods to get the identity, name, model, activation status, and room ID of a device.
 * It also provides a method to deactivate a device.
 * <p>
 * Two Device objects are considered equal if they have the same identity.
 * The class overrides the equals method to check the equality based on the identity of devices.
 */

public class Device implements AggregateRoot<DeviceId> {
    /**
     * The unique identifier for the device.
     */
    private final DeviceId _deviceId;
    /**
     * The name of the device.
     */
    private final DeviceName _deviceName;
    /**
     * The model of the device.
     */
    private final DeviceModel _deviceModel;
    /**
     * The activation status of the device.
     */
    private ActivationStatus _status;
    /**
     * The unique identifier for the room in which the device is located.
     */
    private final RoomID _roomID;

    /**
     * Constructor for the Device class.
     * @param deviceId    The unique identifier for the device.
     * @param deviceName  The name of the device.
     * @param deviceModel The model of the device.
     * @param status      The activation status of the device.
     * @param roomId      The unique identifier for the room in which the device is located.
     * @throws IllegalArgumentException If any of the arguments are invalid.
     */
    public Device(DeviceId deviceId, DeviceName deviceName, DeviceModel deviceModel, ActivationStatus status, RoomID roomId)
    {

        if (status.toString().contains("false"))
            throw new IllegalArgumentException("Device cannot be inactive");

        this._deviceId = deviceId;
        this._deviceName = deviceName;
        this._deviceModel = deviceModel;
        this._status = status;
        this._roomID = roomId;
    }

    /**
     * Method to get the unique identifier for the device.
     */
    @Override
    public DeviceId identity() {
        return _deviceId;
    }

    /**
     * Method to get the room where the device is located.
     */
    public RoomID getRoomId() {
        return _roomID;
    }

    /**
     * Method to get the name of the device.
     */
    public DeviceName getDeviceName() {
        return _deviceName;
    }

    /**
     * Method to get the model of the device.
     */
    public DeviceModel getDeviceModel() {
        return _deviceModel;
    }

    /**
     * Method to get the activation status of the device.
     */
public ActivationStatus getActivationStatus() {
        return _status;
    }

    /**
     * Method to check if two devices are the same.
     * @param object The object to compare.
     * @return True if the devices are the same, false otherwise.
     */

    @Override
    public boolean sameAs(Object object) {

        if (object instanceof Device device) {

            if(!equals(object))
                return false;

            return this._deviceName.equals(device._deviceName) &&
                    this._status.equals(device._status) &&
                    this._deviceModel.equals(device._deviceModel) &&
                    this._roomID.equals(device._roomID);
        }
        return false;
    }

    /**
     * This method compares the deviceId of the device with the deviceId of the object passed as a parameter.
     * Since the deviceId is unique, it is sufficient to compare the deviceId to determine if two devices are the same.
     * @param object The object to compare.
     * @return True if the devices are the same, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof Device device) {

            return this._deviceId.equals(device._deviceId);
        }
        return false;
    }

    /**
     * Deactivates the device.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean deactivateDevice() {

        // Deactivate the device
        _status = new ActivationStatus(false);
        return true;
    }

    /**
     * Returns the hash code of the device ID.
     *
     * @return The hash code of the device ID.
     */
    @Override
    public int hashCode() {
        return _deviceId.hashCode();
    }
}
