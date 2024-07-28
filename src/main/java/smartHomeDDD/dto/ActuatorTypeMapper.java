package smartHomeDDD.dto;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;


import java.util.ArrayList;
import java.util.List;

/**
 * The ActuatorTypeMapper class is responsible for mapping ActuatorType objects to ActuatorTypeDTO objects.
 */
public class ActuatorTypeMapper {
    public static ActuatorTypeDTO actuatorTypeToDTO(ActuatorType actuatorType) {
        if (actuatorType == null)
            return null;
        String strDescription = actuatorType.getDescription().toString();
        String actuatorTypeID = actuatorType.identity().toString();
        return new ActuatorTypeDTO(actuatorTypeID, strDescription);
    }

    /**
     * Converts a list of ActuatorType objects to a list of ActuatorTypeDTO objects.
     * @param actuatorTypes The list of ActuatorType objects to convert.
     * @return A list of ActuatorTypeDTO objects.
     */
    public static List<ActuatorTypeDTO> actuatorTypeListToDTO(Iterable<ActuatorType> actuatorTypes) {
        List<ActuatorTypeDTO> actuatorTypeDTOs = new ArrayList<>();
        for (ActuatorType actuatorType : actuatorTypes) {
            actuatorTypeDTOs.add(actuatorTypeToDTO(actuatorType));
        }
        return actuatorTypeDTOs;
    }

    /**
     * Converts an ActuatorTypeDTO object to an ActuatorType object.
     * @param actuatorTypeID The ActuatorTypeDTO object to convert.
     * @return An ActuatorType object.
     */
    public static ActuatorTypeID DTOToActuatorTypeID(String actuatorTypeID) {
        return new ActuatorTypeID(actuatorTypeID);
    }

    /**
     * Transforms a actuatorType domain object to a ActuatorTypeID Exit Web data transfer object.
     * @param actuatorType the actuator type domain object to be mapped.
     * @return the corresponding ActuatorTypeIDExitWebDTO
     */
    public static ActuatorTypeIDExitWebDTO domainToIDExitWebDTO(ActuatorType actuatorType) {
        String actuatorTypeID = actuatorType.identity().toString();
        return new ActuatorTypeIDExitWebDTO(actuatorTypeID);
    }

    /**
     * Transforms a actuatorType domain object to a ActuatorTypeWeb data transfer object.
     * @param actuatorType the actuator type domain object to be mapped.
     * @return the corresponding ActuatorTypeWebDTO
     */
    public static ActuatorTypeExitWebDTO domainToExitWebDTO(ActuatorType actuatorType) {
        String actuatorTypeID = actuatorType.identity().toString();
        String description = actuatorType.getDescription().toString();
        String unit = actuatorType.getUnit().toString();
        return new ActuatorTypeExitWebDTO(actuatorTypeID, description, unit);
    }
}
