package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

public class ActuatorWebDTO extends RepresentationModel<ActuatorWebDTO> {

    private final String actuatorId;
    private String deviceId;
    private String actuatorModelId;
    private String actuatorValue;

    /**
     * Basic constructor for ActuatorWebDTO.
     *
     * @param actuatorId      The ID of the actuator.
     * @param deviceId        The ID of the device associated with the actuator.
     * @param actuatorModelId The ID of the actuator model.
     */
    public ActuatorWebDTO(String actuatorId, String deviceId, String actuatorModelId, String actuatorValue) {
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorModelId = actuatorModelId;
        this.actuatorValue = actuatorValue;
    }

    /**
     * Constructor for ActuatorWebDTO when a list is to be retrieved
     * @param actuatorId the ID of the actuator
     */
    public ActuatorWebDTO(String actuatorId) {
        this.actuatorId = actuatorId;
    }

    /**
     * Getter method for actuatorID
     *
     * @return String describing actuatorID
     */
    public String getActuatorId() {
        return this.actuatorId;
    }

    /**
     * Getter method for deviceID
     *
     * @return String describing deviceID
     */
    public String getDeviceId() {
        return this.deviceId;
    }

    /**
     * Getter method for actuatorModelID
     *
     * @return String describing actuatorModelID
     */
    public String getActuatorModelId() {
        return this.actuatorModelId;
    }

    /**
     * Getter method for actuatorValue
     *
     * @return String describing actuatorValue
     */
    public String getActuatorValue() {
        return this.actuatorValue;
    }




}
