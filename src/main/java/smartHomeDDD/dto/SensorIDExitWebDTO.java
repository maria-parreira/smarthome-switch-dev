package smartHomeDDD.dto;
import org.springframework.hateoas.RepresentationModel;


/**
 * DTO (Data Transfer Object) representing a sensor in the web context.
 * Extends RepresentationModel for HATEOAS support.
 */
public class SensorIDExitWebDTO extends RepresentationModel<SensorIDExitWebDTO> {

    /**
     * The ID of the sensor.
     */
    private final String sensorId;


    /**
     * Constructor for SensorWebDTO with parameters.
     * @param sensorId The ID of the sensor.
     */
    public SensorIDExitWebDTO(String sensorId){
        this.sensorId = sensorId;
    }

    /**
     * Get the ID of the sensor.
     * @return The sensor ID.
     */
    public String getSensorId(){
        return this.sensorId;
    }

}
