package smartHomeDDD.dto;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO (Data Transfer Object) representing a sensor in the web context.
 * Extends RepresentationModel for HATEOAS support.
 */
public class SensorExitWebDTO extends RepresentationModel<SensorExitWebDTO> {

    /**
     * The unique identifier of the sensor.
     */
    private final String sensorID;

    /**
     * The unique identifier of the sensor model.
     */
    private final String sensorModelID;

    /**
     * The unique identifier of the device that the sensor is associated with.
     */
    private final String deviceID;

    /**
     * Constructor Method for the creation of an SensorExitWebDTO object.
     * @param sensorID The unique identifier of the sensor.
     * @param deviceID The unique identifier of the device which the sensor belongs to.
     * @param sensorModelID The unique identifier of the sensor model.
     */
    public SensorExitWebDTO(String sensorID, String deviceID, String sensorModelID) {
        this.sensorID = sensorID;
        this.sensorModelID = sensorModelID;
        this.deviceID = deviceID;
    }

    /**
     * Returns the unique identifier of the sensor.
     *
     * @return the unique identifier of the sensor
     */
    public String getSensorID() {
        return sensorID;
    }

    /**
     * Returns the unique identifier of the device that the sensor is associated with.
     *
     * @return the unique identifier of the device
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * Returns the unique identifier of the sensor model.
     *
     * @return the unique identifier of the sensor model
     */
    public String getSensorModelID() {
        return sensorModelID;
    }
}
