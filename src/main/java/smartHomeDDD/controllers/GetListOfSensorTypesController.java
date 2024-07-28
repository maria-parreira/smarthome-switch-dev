package smartHomeDDD.controllers;

import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.dto.SensorTypeDTO;
import smartHomeDDD.dto.SensorTypeMapper;
import smartHomeDDD.services.ServiceSensorType;

import java.util.List;
/**
 * Controller class that gives the list of sensor types.
 */

public class GetListOfSensorTypesController
{
    /**
     * Service of sensor types.
     */

    private final ServiceSensorType _serviceSensorType;

    /**
     * Constructor for the GetSensorTypesController class.
     *
     * @param serviceSensorType Service of sensor types.
     * @throws IllegalArgumentException If the service is null.
     */

    public GetListOfSensorTypesController(ServiceSensorType serviceSensorType) {
        if (serviceSensorType == null)
            throw new IllegalArgumentException("Service cannot be null");
        this._serviceSensorType = serviceSensorType;

    }

    /**
     * List all sensor types.
     *
     * @return List of sensor types.
     */
    public List<SensorTypeDTO> getSensorTypes() {
        Iterable<SensorType> sensorTypes = _serviceSensorType.getSensorTypes();
        return SensorTypeMapper.sensorTypeListToDTO(sensorTypes);
    }
}
