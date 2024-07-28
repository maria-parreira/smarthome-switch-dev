package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The SensorModelWebDTO class represents a Data Transfer Object (DTO) of type Web for a sensor model.
 */
public class SensorModelExitWebDTO extends RepresentationModel<SensorModelExitWebDTO> {

    /** The unique identifier of the sensor model. */
    private String sensorModelID;

    public SensorModelExitWebDTO() {
        // Default constructor
    }

    /**
     * Constructs a new SensorModel Web DTO with the specified sensor model information.
     * @param sensorModelID the unique identifier of the sensor model.
     */
    public SensorModelExitWebDTO(String sensorModelID) {
        this.sensorModelID = sensorModelID;
    }

    /**
     * Returns the unique identifier of the sensor model.
     * @return the unique identifier of the sensor model.
     */
    public String getSensorModelID() {
        return sensorModelID;
    }

}
