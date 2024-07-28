package smartHomeDDD.controllers;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.services.ServiceDevice;

import java.util.List;

/**
 * Controller class responsible for retrieving a list of active devices.
 */
public class GetListOfActiveDevicesController {

    /**
     * The service that provides device-related operations.
     */
    private final ServiceDevice _serviceDevice;

    /**
     * Constructs a new GetListOfActiveDevicesController with the specified service.
     * @param deviceService the service that provides device-related operations
     * @throws IllegalArgumentException if the provided service is null
     */
    public GetListOfActiveDevicesController(ServiceDevice deviceService){
        if (deviceService == null) {
            throw new IllegalArgumentException("Service Device cannot be null");
        }
        this._serviceDevice = deviceService;
    }

    /**
     * Retrieves a list of active devices from the service and converts them to DTOs.
     * @return A list of DeviceDTO objects representing the active devices.
     */
    public List<DeviceDTO> getListOfActiveDevices() {
        List<Device> activeDevices = _serviceDevice.listOfActiveDevices();
        return DeviceMapper.activeDeviceListToDTOList(activeDevices);
    }

}