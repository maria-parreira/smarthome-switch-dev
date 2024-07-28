package smartHomeDDD.dto;

public class OPNCLEntryDTO {

    private final String _actuatorID;
    /**
     * The unique identifier of the device which the actuator belongs to.
     */
    private final String _sensorID;
    /**
     * The model of the actuator.
     */
    private final String _inputValue;


    /**
     * Constructor Method for the creation of an ActuatorDTO object, without the model name or value.
     *
     * @param actuatorID      The unique identifier of the actuator.
     * @param sensorID        The unique identifier of the device which the actuator belongs to.
     * @param inputValue The model of the actuator.
     */
    public OPNCLEntryDTO(String actuatorID, String sensorID, String inputValue) {
        _actuatorID = actuatorID;
        _sensorID = sensorID;
        _inputValue = inputValue;
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
    public String getSensorID() {
        return _sensorID;
    }

    /**
     * Retrieves the model of the actuator.
     *
     * @return The model of the actuator.
     */
    public String getInputValue() {
        return _inputValue;
    }

}

