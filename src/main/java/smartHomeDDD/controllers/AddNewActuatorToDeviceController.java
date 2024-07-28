package smartHomeDDD.controllers;

import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.dto.ActuatorDTO;
import smartHomeDDD.dto.ActuatorMapper;
import smartHomeDDD.dto.ActuatorModelMapper;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.services.ServiceActuator;


/**
 * The US12AddNewActuatorToDeviceController class is responsible for handling the addition of new actuators to devices.
 * It uses a ServiceActuator to handle the creation and storage of new actuator objects.
 */

public class AddNewActuatorToDeviceController {
    /**
     * The ServiceActuator instance used by the US12AddNewActuatorToDeviceController to handle the creation and storage of new actuator objects.
     */
    private final ServiceActuator _serviceActuator;

    /**
     * Constructs a new US12AddNewActuatorToDeviceController with the specified ServiceActuator.
     * Throws an IllegalArgumentException if the ServiceActuator is null.
     *
     * @param serviceActuator the serviceActuator to be used by the controller
     * @throws IllegalArgumentException if the serviceActuator is null
     */

    public AddNewActuatorToDeviceController(ServiceActuator serviceActuator) {
        if (serviceActuator == null) {
            throw new IllegalArgumentException("actuator Service cannot be null.");
        }
        _serviceActuator = serviceActuator;
    }

    /**
     * Adds a new actuator to a device.
     * This method takes a ActuatorDTO object as input, which contains the details of the actuator to be added.
     * It converts the actuator details from the ActuatorDTO to domain objects, then uses the ServiceActuator to create and store a new actuator.
     * Finally, it converts the created actuator back to a ActuatorDTO and returns it.
     *
     * @param entryDTO the ActuatorDTO containing the details of the actuator to be added
     * @return a ActuatorDTO representing the actuator that was added
     */

    public ActuatorDTO addNewActuatorToDevice(ActuatorDTO entryDTO) {

        //ActuatorID actuatorID = ActuatorMapper.convertToActuatorID(entryDTO.getActuatorID());
        DeviceId deviceID = DeviceMapper.DTOToDeviceId(entryDTO.getDeviceID());
        ActuatorModelID actuatorModelID = ActuatorModelMapper.convertToActuatorModelID(entryDTO.getActuatorModelID());

        Actuator savedActuator = _serviceActuator.addNewActuator( deviceID, actuatorModelID);

        return ActuatorMapper.convertToActuatorDTO(savedActuator);
    }


}

