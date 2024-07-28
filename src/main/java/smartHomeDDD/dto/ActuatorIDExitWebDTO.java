package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

public class ActuatorIDExitWebDTO extends RepresentationModel<ActuatorIDExitWebDTO> {

    private final String actuatorId;

    /**
     * Basic constructor for ActuatorWebDTO.
     * @param actuatorId The ID of the actuator.
     */
    public ActuatorIDExitWebDTO(String actuatorId) {
        this.actuatorId = actuatorId;
    }

    /**
     * Getter method for actuatorID
     * @return String describing actuatorID
     */
    public String getActuatorId() {
        return this.actuatorId;
    }

}
