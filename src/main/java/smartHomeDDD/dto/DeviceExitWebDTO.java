package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * Exit Data Transfer Object (DTO) for the Device domain object.
 */
public class DeviceExitWebDTO extends RepresentationModel<DeviceExitWebDTO> {
    /**
     * The unique identifier of the device.
     */
    private final String deviceIdWebDTO;
    /**
     * The unique identifier of the room where the device is located.
     */
    private final String roomIdWebDTO;
    /**
     * The name of the device.
     */
    private final String deviceNameWebDTO;
    /**
     * The model of the device.
     */
    private final String deviceModelWebDTO;
    /**
     * The activation status of the device.
     */
    private final boolean activationStatusWebDTO;

    /**
     * Constructor for the DeviceExitWebDTO class.
     * @param deviceId         The unique identifier of the device.
     * @param roomId           The unique identifier of the room where the device is located.
     * @param deviceName       The name of the device.
     * @param deviceModel      The model of the device.
     * @param activationStatus The activation status of the device.
     */
    public DeviceExitWebDTO(String deviceId, String roomId, String deviceName, String deviceModel, boolean activationStatus) {
        this.deviceIdWebDTO = deviceId;
        this.roomIdWebDTO = roomId;
        this.deviceNameWebDTO = deviceName;
        this.deviceModelWebDTO = deviceModel;
        this.activationStatusWebDTO = activationStatus;
    }

    /**
     * Retrieves the unique identifier of the device.
     */
    public String getDeviceId() {
        return deviceIdWebDTO;
    }

    /**
     * Retrieves the unique identifier of the room where the device is located.
     */
    public String getRoomId() {
        return roomIdWebDTO;
    }

    /**
     * Retrieves the name of the device.
     */
    public String getDeviceName() {
        return deviceNameWebDTO;
    }

    /**
     * Retrieves the model of the device.
     */
    public String getDeviceModel() {
        return deviceModelWebDTO;
    }

    /**
     * Retrieves the activation status of the device.
     */
    public boolean getActivationStatus() {
        return activationStatusWebDTO;
    }

}
