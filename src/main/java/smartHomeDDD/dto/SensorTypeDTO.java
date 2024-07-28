package smartHomeDDD.dto;

public class SensorTypeDTO {

    /** The description of the sensor type. */
    private final String description;

    /** The unit of measurement associated with the sensor type. */
    private final String unit;

    /** The unique identifier of the sensor type. */
    private final String sensorTypeID;

    /**
     * Constructs a new {@code SensorTypeDTO} with the specified sensor type information.
     *
     * @param sensorTypeID the unique identifier of the sensor type.
     * @param description the description of the sensor type.
     * @param unit the unit of measurement associated with the sensor type.
     */
    public SensorTypeDTO(String sensorTypeID, String description, String unit) {
        this.description = description;
        this.unit = unit;
        this.sensorTypeID = sensorTypeID;
    }

    /**
     * Returns the description of the sensor type.
     *
     * @return the description of the sensor type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the unit of measurement associated with the sensor type.
     *
     * @return the unit of measurement associated with the sensor type.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Returns the unique identifier of the sensor type.
     *
     * @return the unique identifier of the sensor type.
     */
    public String getSensorTypeID() {
        return sensorTypeID;
    }

    /**
     * Checks if this SensorTypeDTO is equal to another object.
     *
     * @param obj The object to compare with this SensorTypeDTO.
     * @return true if the provided object is an instance of SensorTypeDTO and its sensorTypeID, description, and unit match with this SensorTypeDTOs respective fields. Returns false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SensorTypeDTO sensorTypeDTO) {
            return this.sensorTypeID.equals(sensorTypeDTO.sensorTypeID) &&
                    this.description.equals(sensorTypeDTO.description) &&
                    this.unit.equals(sensorTypeDTO.unit);
        }
        return false;
    }

    /**
     * Returns the hash code value for this SensorTypeDTO object.
     * @return the hash code value for this SensorTypeDTO object.
     */
    @Override
    public int hashCode() {
        return sensorTypeID.hashCode();
    }
}
