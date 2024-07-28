package smartHomeDDD.dto;
import org.springframework.hateoas.RepresentationModel;


/**
 * DTO (Data Transfer Object) representing a sensor in the web context.
 * Extends RepresentationModel for HATEOAS support.
 */
public class SensorWebDTO extends RepresentationModel<SensorWebDTO> {

    private  String sensorId;
    private  String deviceId;
    private   String sensorModelId;

    /**
     * Default constructor for SensorWebDTO.
     */
    public SensorWebDTO(){

    }

    /**
     * Constructor for SensorWebDTO with parameters.
     * @param deviceId The ID of the device associated with the sensor.
     * @param sensormodelId The ID of the sensor model.
     * @param sensorId The ID of the sensor.
     */
    public SensorWebDTO(String deviceId, String sensormodelId, String sensorId){
        this.deviceId = deviceId;
        this.sensorId = sensorId;
        this.sensorModelId = sensormodelId;
    }

    /**
     * Get the ID of the sensor.
     * @return The sensor ID.
     */
    public String getSensorId(){
        return this.sensorId;
    }

    /**
     * Get the ID of the device associated with the sensor.
     * @return The device ID.
     */
    public String getDeviceId() {
        return this.deviceId;
    }

    /**
     * Get the ID of the sensor model.
     * @return The sensor model ID.
     */
    public String getSensorModelId(){
        return this.sensorModelId;
    }
}
