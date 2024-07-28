package smartHomeDDD.controllers;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceActuatorModel;

import java.util.List;

/**
 * Controller class that gives the list of actuator models for a given actuator type.
 */
public class GetActuatorModelsController {
    /**
     * Service of actuator models.
     */
    private final ServiceActuatorModel _serviceActuatorModel;

    /**
     * Constructor for the GetActuatorModelsController class.
     *
     * @param serviceActuatorModel The service used to fetch actuator models. Must not be null.
     * @throws IllegalArgumentException If the provided serviceActuatorModel is null.
     */
    public GetActuatorModelsController(ServiceActuatorModel serviceActuatorModel) {
        if (serviceActuatorModel == null)
            throw new IllegalArgumentException("Service cannot be null");
        this._serviceActuatorModel = serviceActuatorModel;
    }

    /**
     * Get all actuator models for a given actuator type.
     *
     * @param actuatorTypeDTO actuator type.
     * @return List of actuator models.
     */

    public List<ActuatorModelDTO> getActuatorModels(ActuatorTypeDTO actuatorTypeDTO) {
        ActuatorTypeID actuatorTypeID = ActuatorTypeMapper.DTOToActuatorTypeID(actuatorTypeDTO.getID());
        List<ActuatorModel> actuatorModels = _serviceActuatorModel.getModelsByActuatorType(actuatorTypeID);
        return ActuatorModelMapper.actuatorModelListToDTO(actuatorModels);
    }
}


