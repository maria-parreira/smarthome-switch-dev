package smartHomeDDD.dto;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class that contains methods for the conversion of Domain actuatorModel entities to DTOs.
 */
public class ActuatorModelMapper {
    public static ActuatorModelDTO actuatorModelToDTO(ActuatorModel actuatorModel) {
        if (actuatorModel == null)
            return null;
        String actuatorModelID = actuatorModel.identity().toString();
        String actuatorTypeID = actuatorModel.getActuatorTypeID().toString();
        return new ActuatorModelDTO(actuatorModelID, actuatorTypeID);
    }

    /**
     *
     * Method that converts a ActuatorModel entity to a ActuatorModelWebDTO.
     * @param actuatorModel The actuatorModel entity to convert.
     * @return  ActuatorModelWebDTO.
     */

    public static ActuatorModelExitWebDTO domainToExitWebDTO(ActuatorModel actuatorModel) {
        if (actuatorModel == null) {
            return null;
        }
        String actuatorModelID = actuatorModel.identity().toString();
        String actuatorTypeID = actuatorModel.getActuatorTypeID().toString();
        return new ActuatorModelExitWebDTO(actuatorModelID, actuatorTypeID);
    }

    /**
     * Given a actuatorModelID String, Retrieves a actuatorModelID Value Object.
     *
     * @param actuatorModelID A String actuatorModelID.
     * @return returns an actuatorModelID VO.
     */
    public static ActuatorModelID convertToActuatorModelID(String actuatorModelID) {
        return new ActuatorModelID(actuatorModelID);
    }

    /**
     * Converts a list of actuatorModel entities to a list of ActuatorModelDTOs.
     *
     * @param actuatorModels The list of actuatorModel entities to convert.
     * @return A list of ActuatorModelDTOs.
     */
    public static List<ActuatorModelDTO> actuatorModelListToDTO(List<ActuatorModel> actuatorModels) {
        List<ActuatorModelDTO> actuatorModelDTOs = new ArrayList<>();
        for (ActuatorModel actuatorModel : actuatorModels) {
            actuatorModelDTOs.add(actuatorModelToDTO(actuatorModel));
        }
        return actuatorModelDTOs;
    }


}
