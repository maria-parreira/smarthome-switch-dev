package smartHomeDDD.controllers;

import smartHomeDDD.domain.sensorType.*;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceSensorType;

/**
 * The DefineSensorTypeController class represents a controller responsible for defining
 * new sensor types. It interacts with a factory for creating sensor types and a repository
 * for adding new sensor types.
 */

public class DefineSensorTypeController {

    /**
     * The service for sensor type.
     */
    private final ServiceSensorType sensorTypeService;

    /**
     * Constructor for the US04DefineSensorTypeController class
     *
     * @param sensorTypeService The service for sensor type.
     * @throws IllegalArgumentException If the sensor type service is null.
     */
    public DefineSensorTypeController(ServiceSensorType sensorTypeService) {
        if (sensorTypeService == null) {
            throw new IllegalArgumentException("Sensor Type Service cannot be null");
        } else {
            this.sensorTypeService = sensorTypeService;
        }

    }

    /**
     * Defines a new sensor type based on the provided sensor type data transfer object (DTO).
     * If a sensor type with the same identity already exists, it returns null.
     *
     * @param sensorTypeDTO the sensor type data transfer object containing information about the sensor type.
     * @return the created sensor type data transfer object, or null if a sensor type with the same identity already exists.
     */
    public SensorTypeDTO defineSensorType(SensorTypeDTO sensorTypeDTO) {
        Description description = SensorTypeMapper.createDescription(sensorTypeDTO.getDescription());
        Unit unit = SensorTypeMapper.createUnit(sensorTypeDTO.getUnit());
        SensorTypeID sensorTypeID = SensorTypeMapper.createSensorTypeID(sensorTypeDTO.getSensorTypeID());
        SensorType sensorType = sensorTypeService.createSensorType(sensorTypeID, description, unit);
        return SensorTypeMapper.domainToDTO(sensorType);
    }
}
