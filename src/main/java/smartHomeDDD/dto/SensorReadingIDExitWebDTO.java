package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * SensorReadingExitWebDTO is a DTO (Data Transfer Object) class that represents a SensorReading object in a format that can be used by the web layer.
 */
public class SensorReadingIDExitWebDTO extends RepresentationModel<SensorReadingIDExitWebDTO> {

    /**
     * The identity of the sensorReading.
     */
    private final String _sensorReadingID;

    /**
     * Constructor for the SensorReadingExitWebDTO class.
     * @param sensorReadingID The identity of the sensorReading.
     */
    public SensorReadingIDExitWebDTO(String sensorReadingID) {
        this._sensorReadingID = sensorReadingID;
    }

    /**
     * Gets the sensorReadingID.
     * @return The sensorReadingID.
     */
    public String getSensorReadingID() {
        return _sensorReadingID;
    }

}
