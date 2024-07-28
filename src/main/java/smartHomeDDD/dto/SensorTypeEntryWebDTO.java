package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * The SensorTypeEntryWebDTO class represents a Data Transfer Object (DTO) of type Web for a sensor type.
 */
public class SensorTypeEntryWebDTO extends RepresentationModel<SensorTypeWebDTO> {

    /** The description of the sensor type. */
    private final String description;

    /** The unit of measurement associated with the sensor type. */
    private final String unit;

    /** The unique identifier of the sensor type. */
    private final String sensorTypeID;


    /**
     * Constructs a new SensorType EntryWeb DTO with the specified sensor type information.
     * @param sensorTypeID the unique identifier of the sensor type.
     * @param description the description of the sensor type.
     * @param unit the unit of measurement associated with the sensor type.
     */
    public SensorTypeEntryWebDTO (String sensorTypeID, String description, String unit) {
        this.sensorTypeID = sensorTypeID;
        this.description = description;
        this.unit = unit;
    }

    /**
     * Returns the description of the sensor type.
     * @return the description of the sensor type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the unit of measurement associated with the sensor type.
     * @return the unit of measurement associated with the sensor type.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Returns the unique identifier of the sensor type.
     * @return the unique identifier of the sensor type.
     */
    public String getSensorTypeID() {
        return sensorTypeID;
    }

}
