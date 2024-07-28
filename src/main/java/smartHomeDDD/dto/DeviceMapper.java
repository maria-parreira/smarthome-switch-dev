package smartHomeDDD.dto;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.valueobject.ActivationStatus;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.DeviceModel;
import smartHomeDDD.domain.valueobject.DeviceName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapper class for converting Device domain objects to DeviceDTO objects.
 */
public class DeviceMapper {

    /**
     * Converts a DeviceDTO DeviceId String to a DeviceId value object.
     *
     * @param deviceId The unique identifier of the device.
     * @return The converted DeviceId value object.
     */
    public static DeviceId DTOToDeviceId(String deviceId){
        return new DeviceId(deviceId);
    }

    /**
     * Converts a DeviceDTO DeviceName String to a DeviceName value object.
     *
     * @param deviceName The name of the device.
     * @return The converted DeviceName value object.
     */
    public static DeviceName DTOToDeviceName(String deviceName){
        return new DeviceName(deviceName);
    }

    /**
     * Converts a DeviceDTO DeviceModel String to a DeviceModel value object.
     *
     * @param deviceModel The model of the device.
     * @return The converted DeviceModel value object.
     */
    public static DeviceModel DTOToDeviceModel(String deviceModel){
        return new DeviceModel(deviceModel);
    }

    /**
     * Converts a DeviceDTO ActivationStatus String to an ActivationStatus value object.
     *
     * @param activationStatus The activation status of the device.
     * @return The converted ActivationStatus value object.
     */
    public static ActivationStatus DTOToActivationStatus(boolean activationStatus){
        return new ActivationStatus(activationStatus);
    }

    /**
     * Converts a Device domain object to a DeviceDTO object.
     */

    public static DeviceDTO deviceToDTO(Device device) {
        return new DeviceDTO(
                device.identity().toString(),
                device.getRoomId().toString(),
                device.getDeviceName().toString(),
                device.getDeviceModel().toString(),
                Boolean.parseBoolean(device.getActivationStatus().toString()));
    }

    /**
     * Converts a list of Device domain objects to a list of DeviceDTO objects.
     */

    public static List<DeviceDTO> deviceListToDTOList(List<Device> devices){
        List <DeviceDTO> deviceDTOList = new ArrayList<>();
        for (Device device : devices){
            deviceDTOList.add(deviceToDTO(device));
        }
        return deviceDTOList;
    }

    /**
     * Converts a list of active Device domain objects to a list of DeviceDTO objects.
     * @param activeDevices A list of active Device domain objects.
     * @return A list of DeviceDTO objects representing the active devices.
     */
    public static List<DeviceDTO> activeDeviceListToDTOList(List<Device> activeDevices){
        return deviceListToDTOList(activeDevices);
    }
    /**
     * Converts the devices of a Map into devices DTO
     * @param groupedDevicesByType the Map containing the types and the respective lists of devices of that type
     * @return returns a Map with DevicesDTO grouped by Sensor and actuator Type.
     */
    public static Map<String, List<DeviceDTO>> devicesMapDomainToDTO(Map<String, List<Device>> groupedDevicesByType) {
        Map<String, List<DeviceDTO>> mapOfDevicesByTypeDTO = new HashMap<>();
        for (Map.Entry<String, List<Device>> entry : groupedDevicesByType.entrySet()) {
            String functionality = entry.getKey();
            List<Device> devices = entry.getValue();
            List<DeviceDTO> deviceDTOList = new ArrayList<>();
            for (Device device : devices) {
                DeviceDTO deviceDTO = deviceToDTO(device);
                deviceDTOList.add(deviceDTO);
            }
            mapOfDevicesByTypeDTO.put(functionality, deviceDTOList);
        }
        return mapOfDevicesByTypeDTO;
    }

    /**
     * This method is used to convert a Map of Devices into a Map of DeviceWebDTOs.
     * It iterates over the input Map, and for each entry, it converts the List of Devices into a List of DeviceWebDTOs.
     * The converted List of DeviceWebDTOs is then put into the output Map with the same key as the input Map.
     *
     * @param groupedDevicesByType A Map where the key is a String representing the type of devices,
     *                             and the value is a List of Devices of that type.
     * @return A Map where the key is a String representing the type of devices,
     *         and the value is a List of DeviceWebDTOs of that type.
     */
    public static Map<String, List<DeviceIDExitWebDTO>> domainMapToExitWebDTO(Map<String, List<Device>> groupedDevicesByType) {
        Map<String, List<DeviceIDExitWebDTO>> mapOfDevicesByTypeDTO = new HashMap<>();
        for (Map.Entry<String, List<Device>> entry : groupedDevicesByType.entrySet()) {
            String functionality = entry.getKey();
            List<Device> devices = entry.getValue();
            List<DeviceIDExitWebDTO> deviceDTOList = new ArrayList<>();
            for (Device device : devices) {
                DeviceIDExitWebDTO deviceDTO = domainToIDExitWebDTO(device);
                deviceDTOList.add(deviceDTO);
            }
            mapOfDevicesByTypeDTO.put(functionality, deviceDTOList);
        }
        return mapOfDevicesByTypeDTO;
    }

    /**
     * This method is used to convert a Device domain object into a DeviceWebDTO object.
     * It first checks if the input Device is null, and if so, returns null.
     * Otherwise, it retrieves the identity, name, model, activation status, and room ID from the Device,
     * converts them to Strings, and uses them to create a new DeviceWebDTO.
     * @param device The Device domain object to be converted.
     * @return A DeviceWebDTO object with the same identity, name, model, activation status, and room ID as the input Device,
     *         or null if the input Device is null.
     */
    public static DeviceIDExitWebDTO domainToIDExitWebDTO(Device device) {
        if (device == null) {
            return null;
        }
        String deviceID = device.identity().toString();
        return new DeviceIDExitWebDTO(deviceID);
    }

    public static List<DeviceIDExitWebDTO> domainListToWebDTO(List<Device> devices){
        List <DeviceIDExitWebDTO> deviceDTOList = new ArrayList<>();
        for (Device device : devices){
            deviceDTOList.add(domainToIDExitWebDTO(device));
        }
        return deviceDTOList;
    }

    /**
     * This method is used to convert a Device domain object into a DeviceExitWebDTO object.
     * @param device The Device domain object to be converted.
     * @return A DeviceWebDTO object with the same identity, name, model, activation status, and room ID as the input Device.
     */
    public static DeviceExitWebDTO domainToExitWebDTO(Device device) {
        String deviceID = device.identity().toString();
        String roomID = device.getRoomId().toString();
        String deviceName = device.getDeviceName().toString();
        String model = device.getDeviceModel().toString();
        boolean activationStatus = Boolean.parseBoolean(device.getActivationStatus().toString());
        return new DeviceExitWebDTO(deviceID, roomID, deviceName, model, activationStatus);
    }

}
