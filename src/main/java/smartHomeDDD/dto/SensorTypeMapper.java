package smartHomeDDD.dto;

import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The SensorTypeMapper class provides static methods for converting between sensorType domain objects and SensorTypeDTO objects.
 */
public class SensorTypeMapper {

    /**
     * Creates a Description domain object from the specified description string.
     *
     * @param description the description string.
     * @return the created Description object.
     */
    public static Description createDescription(String description) {
        return new Description(description);
    }

    /**
     * Creates a Unit domain object from the specified unit string.
     *
     * @param unit the unit string.
     * @return the created Unit object.
     */
    public static Unit createUnit(String unit) {
        return new Unit(unit);
    }


    /**
     * Creates a SensorTypeID domain object from the specified sensor type ID string.
     *
     * @param sensorTypeID the sensor type ID string.
     * @return the created SensorTypeID object.
     */
    public static SensorTypeID createSensorTypeID(String sensorTypeID) {
        return new SensorTypeID(sensorTypeID);
    }

    /**
     * Transforms a sensorType domain object to a SensorTypeDTO data transfer object.
     *
     * @param sensorType the sensor type domain object to be mapped.
     * @return the corresponding sensor type DTO, or null if the input is null.
     */
    public static SensorTypeDTO domainToDTO(SensorType sensorType) {
        if (sensorType == null)
            return null;
        String strDescription = sensorType.getDescription().toString();
        String unit = sensorType.getUnit().toString();
        String sensorTypeID = sensorType.identity().toString();
        return new SensorTypeDTO(sensorTypeID, strDescription, unit);
    }

    /**
     * Transforms a sensorType domain object to a SensorTypeExitWebDTO data transfer object.
     * @param sensorType the sensor type domain object to be mapped.
     * @return the corresponding sensor type DTO, or null if the input is null.
     */
    public static SensorTypeExitWebDTO domainToExitWebDTO(SensorType sensorType) {
        if (sensorType == null) {
            return null;
        }
        String sensorTypeID = sensorType.identity().toString();
        return new SensorTypeExitWebDTO(sensorTypeID);
    }

    /**
     * Transforms a list of sensorType domain objects to a list of SensorTypeDTO data transfer objects.
     *
     * @param sensorTypes the list of sensor type domain objects to be mapped.
     * @return the corresponding list of sensor type DTOs.
     */
    public static List<SensorTypeDTO> sensorTypeListToDTO(Iterable<SensorType> sensorTypes){
        List<SensorTypeDTO> sensorTypesDTO = new ArrayList<>();
        for (SensorType sensorType : sensorTypes){
            sensorTypesDTO.add(domainToDTO(sensorType));
        }
        return sensorTypesDTO;
    }

    /**
     * Transforms a sensorType object to a SensorType Web data transfer object.
     *
     * @param sensorType the sensor type domain object to be mapped.
     * @return the corresponding sensor type web DTO.
     */
    public static SensorTypeWebDTO domainToWebDTO(SensorType sensorType) {
        String strDescription = sensorType.getDescription().toString();
        String unit = sensorType.getUnit().toString();
        String sensorTypeID = sensorType.identity().toString();
        return new SensorTypeWebDTO(sensorTypeID, strDescription, unit);
    }

}
