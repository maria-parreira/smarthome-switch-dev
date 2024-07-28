package smartHomeDDD.controllers;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.services.ServiceDeviceByType;

import java.util.*;

/**
 * This class represents the Controller of the User Story 09, which aims to retrieve a list of all devices in a house,
 * grouped by their functionality type, with the location (room) of each device.
 */
public class GetDevicesByFunctionalityController {

    private final ServiceDeviceByType _serviceDeviceByType;

    /**
     * Constructor method for the instantiation of an US09GetDevicesByTypeController object.
     * @param serviceDeviceByType the Service with the responsibility to execute the Grouping of Devices
     */
    public GetDevicesByFunctionalityController(ServiceDeviceByType serviceDeviceByType) {
        this._serviceDeviceByType = serviceDeviceByType;
    }

    /**
     * Retrieves all devices, groups them by their associated sensor or actuator types, and maps them to their DTO representation.
     * @return returns a Map with a key of types and their respective lists comprised of DeviceDTOs of that type.
     */
    public Map<String, List<DeviceDTO>> retrieveDevicesByType() {
        Map<String, List<Device>> groupedDevices = _serviceDeviceByType.getDevicesByType();
        return DeviceMapper.devicesMapDomainToDTO(groupedDevices);
    }


}

