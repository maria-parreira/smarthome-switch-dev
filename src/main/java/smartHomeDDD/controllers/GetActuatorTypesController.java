package smartHomeDDD.controllers;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.dto.ActuatorTypeDTO;
import smartHomeDDD.dto.ActuatorTypeMapper;
import smartHomeDDD.services.ServiceActuatorType;

import java.util.List;

/**
 * Controller class that gives the list of actuator types.
 */
public class GetActuatorTypesController {
    /**
     * Service of actuator types.
     */

    private final ServiceActuatorType _serviceActuatorType;

    /**
     * Constructor for the GetActuatorTypesController class.
     *
     * @param serviceActuatorType Service of actuator types.
     * @throws IllegalArgumentException If the service is null.
     */

    public GetActuatorTypesController(ServiceActuatorType serviceActuatorType) {
        if (serviceActuatorType == null)
            throw new IllegalArgumentException("Service cannot be null");
        this._serviceActuatorType = serviceActuatorType;

    }

    /**
     * List all actuator types.
     *
     * @return List of actuator types.
     */
    public List<ActuatorTypeDTO> getActuatorTypes() {
        Iterable<ActuatorType> actuatorTypes = _serviceActuatorType.getActuatorTypes();
        return ActuatorTypeMapper.actuatorTypeListToDTO(actuatorTypes);
    }
}
