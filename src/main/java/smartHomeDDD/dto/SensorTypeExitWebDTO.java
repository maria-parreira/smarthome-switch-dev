package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The SensorTypeWebDTO class represents a Data Transfer Object (DTO) of type Web for a sensor type.
 */
public class SensorTypeExitWebDTO extends RepresentationModel<SensorTypeExitWebDTO> {

    /** The unique identifier of the sensor type. */
    private String sensorTypeID;

    public SensorTypeExitWebDTO() {
        // Default constructor
    }

    /**
     * Constructs a new SensorType Web DTO with the specified sensor type information.
     * @param sensorTypeID the unique identifier of the sensor type.
     */
    public SensorTypeExitWebDTO(String sensorTypeID) {
        this.sensorTypeID = sensorTypeID;
    }

    /**
     * Returns the unique identifier of the sensor type.
     * @return the unique identifier of the sensor type.
     */
    public String getSensorTypeID() {
        return sensorTypeID;
    }


}
