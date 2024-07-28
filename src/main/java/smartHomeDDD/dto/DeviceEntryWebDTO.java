package smartHomeDDD.dto;

/**
 * Entry Data Transfer Object (DTO) for the device domain object, for REST controller use.
 */
public class DeviceEntryWebDTO {

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
     * Constructor for the DeviceEntryWebDTO class.
     * @param roomId           The unique identifier of the room where the device is located.
     * @param deviceName       The name of the device.
     * @param deviceModel      The model of the device.
     * @param activationStatus The activation status of the device.
     */
    public DeviceEntryWebDTO(String roomId, String deviceName, String deviceModel, boolean activationStatus) {
        this.roomId = roomId;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.activationStatus = activationStatus;
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
