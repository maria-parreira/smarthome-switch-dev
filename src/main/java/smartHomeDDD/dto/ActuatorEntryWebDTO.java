package smartHomeDDD.dto;

/**
 * Entry Data Transfer Object (DTO) for the actuator domain object, for REST controller use.
 */
public class ActuatorEntryWebDTO {

    /**
     * The unique identifier of the actuator.
     */
    // private final String _actuatorID;

    /**
     * The unique identifier of the device which the actuator belongs to.
     */
    private final String _deviceID;

    /**
     * The model of the actuator.
     */
    private final String _actuatorModelID;

    /**
     * Constructor Method for the creation of an ActuatorDTO object, without the model name or value.
     *
     * @param deviceID        The unique identifier of the device which the actuator belongs to.
     * @param actuatorModelID The model of the actuator.
     */
    public ActuatorEntryWebDTO(String deviceID, String actuatorModelID) {
        _deviceID = deviceID;
        _actuatorModelID = actuatorModelID;
    }

    /**
     * Retrieves the unique identifier of the device which the actuator belongs to.
     *
     * @return The unique identifier of the device which the actuator belongs to.
     */
    public String getDeviceID() {
        return _deviceID;
    }

    /**
     * Retrieves the model of the actuator.
     *
     * @return The model of the actuator.
     */
    public String getActuatorModelID() {
        return _actuatorModelID;
    }

}
