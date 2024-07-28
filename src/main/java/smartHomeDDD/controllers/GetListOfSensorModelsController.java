package smartHomeDDD.controllers;

import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceSensorModel;

import java.util.List;
/**
 * Controller class that gives the list of sensor models for a given sensor type.
 */

public class GetListOfSensorModelsController {
    /**
     * Service of sensor models.
     */
    private final ServiceSensorModel _serviceSensorModel;

    /**
     * Constructor for the GetListOfSensorModelsController class.
     *
     * @param serviceSensorModel The service used to fetch sensor models. Must not be null.
     * @throws IllegalArgumentException If the provided serviceSensorModel is null.
     */
    public GetListOfSensorModelsController(ServiceSensorModel serviceSensorModel) {
        if (serviceSensorModel == null)
            throw new IllegalArgumentException("Service cannot be null");
        this._serviceSensorModel = serviceSensorModel;
    }

    /**
     * Get all sensor models for a given sensor type.
     *
     * @param sensorTypeDTO actuator type.
     * @return List of actuator models.
     */

    public List<SensorModelDTO> getSensorModels(SensorTypeDTO sensorTypeDTO) {
        SensorTypeID sensorTypeID = SensorTypeMapper.createSensorTypeID(sensorTypeDTO.getSensorTypeID());
        List<SensorModel> sensorModels = _serviceSensorModel.getModelsBySensorType(sensorTypeID);
        return SensorModelMapper.sensorModelsListToDTO(sensorModels);
    }
}
