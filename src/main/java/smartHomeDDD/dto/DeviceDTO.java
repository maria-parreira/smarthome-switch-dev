package smartHomeDDD.dto;

/**
 * Data Transfer Object (DTO) for the Device domain object.
 * This class is used to transfer data between different parts of the application.
 */
public class DeviceDTO {
    /**
     * The unique identifier of the device.
     */
    private final String deviceId;
    /**
     * The unique identifier of the room where the device is located.
     */
    private final String roomId;
    /**
     * The name of the device.
     */
    private final String deviceName;
    /**
     * The model of the device.
     */
    private final String deviceModel;
    /**
     * The activation status of the device.
     */
    private final boolean activationStatus;

    /**
     * Constructor for the DeviceDTO class.
     * @param deviceId         The unique identifier of the device.
     * @param roomId           The unique identifier of the room where the device is located.
     * @param deviceName       The name of the device.
     * @param deviceModel      The model of the device.
     * @param activationStatus The activation status of the device.
     */
    public DeviceDTO(String deviceId, String roomId, String deviceName, String deviceModel, boolean activationStatus) {
        this.deviceId = deviceId;
        this.roomId = roomId;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.activationStatus = activationStatus;
    }

    /**
     * Retrieves the unique identifier of the device.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Retrieves the unique identifier of the room where the device is located.
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Retrieves the name of the device.
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Retrieves the model of the device.
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * Retrieves the activation status of the device.
     */
    public boolean getActivationStatus() {
        return activationStatus;
    }

}
