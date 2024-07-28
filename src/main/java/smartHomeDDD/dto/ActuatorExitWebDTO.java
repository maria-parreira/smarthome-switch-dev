package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object for the exit of an Actuator in the Web Interface.
 */
public class ActuatorExitWebDTO extends RepresentationModel<ActuatorExitWebDTO> {

    /**
     * The unique identifier of the actuator.
     */
    private final String _actuatorID;

    /**
     * The unique identifier of the device which the actuator belongs to.
     */
    private final String _deviceID;

    /**
     * The model of the actuator.
     */
    private final String _actuatorModelID;

    /**
     * Constructor Method for the creation of an ActuatorExitWebDTO object.
     *
     * @param actuatorID      The unique identifier of the actuator.
     * @param deviceID        The unique identifier of the device which the actuator belongs to.
     * @param actuatorModelID The model of the actuator.
     */
    public ActuatorExitWebDTO(String actuatorID, String deviceID, String actuatorModelID) {
        _actuatorID = actuatorID;
        _deviceID = deviceID;
        _actuatorModelID = actuatorModelID;
    }

    /**
     * Retrieves the unique identifier of the actuator.
     *
     * @return The unique identifier of the actuator.
     */
    public String getActuatorID() {
        return _actuatorID;
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

