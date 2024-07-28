package smartHomeDDD.services;

import org.springframework.stereotype.Service;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.repository.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ServiceDeviceByType class provides services for grouping devices by their associated sensor or actuator types.
 * It uses various repositories to fetch devices, sensors, actuators, and their associated types and models.
 */
@Service
public class ServiceDeviceByType {
    /**
     * The repository containing all Sensors;
     */
    final IRepositorySensor _repositorySensor;
    /**
     * The repository containing all Sensor Types;
     */
    final IRepositorySensorType _repositorySensorType;
    /**
     * The repository containing all Actuators;
     */
    final IRepositoryActuator _repositoryActuator;
    /**
     * The repository containing all actuator Types;
     */
    final IRepositoryActuatorType _I_repositoryActuatorType;
    /**
     * The repository containing all devices;
     */
    final IRepositoryDevice _repositoryDevice;
    /**
     * The repository containing all ActuatorModels;
     */
    final IRepositoryActuatorModel _repositoryActuatorModel;
    /**
     * The repository containing all SensorModels;
     */
    final IRepositorySensorModel _repositorySensorModel;

    /**
     * Constructor for the ServiceDeviceByType class.
     * @param repositorySensor The repository for sensors. Must not be null.
     * @param repositorySensorType The repository for sensor types. Must not be null.
     * @param repositoryActuator The repository for actuators. Must not be null.
     * @param repositoryActuatorType The repository for actuator types. Must not be null.
     * @param repositoryDevice The repository for devices. Must not be null.
     * @param repositoryActuatorModel The repository for actuator models. Must not be null.
     * @param repositorySensorModel The repository for sensor models. Must not be null.
     * @throws IllegalArgumentException If any of the repositories is null.
     */
    public ServiceDeviceByType(IRepositorySensor repositorySensor, IRepositorySensorType repositorySensorType,
                               IRepositoryActuator repositoryActuator, IRepositoryActuatorType repositoryActuatorType,
                               IRepositoryDevice repositoryDevice, IRepositoryActuatorModel repositoryActuatorModel,
                               IRepositorySensorModel repositorySensorModel) {

        if (repositorySensor == null)
            throw new IllegalArgumentException("Sensor Repository cannot be null");
        if (repositorySensorType == null)
            throw new IllegalArgumentException("Sensor Type Repository cannot be null");
        if (repositorySensorModel == null)
            throw new IllegalArgumentException("Sensor Model Repository cannot be null");
        if (repositoryActuator == null)
            throw new IllegalArgumentException("Actuator Repository cannot be null");
        if (repositoryActuatorType == null)
            throw new IllegalArgumentException("Actuator Type Repository cannot be null");
        if (repositoryActuatorModel == null)
            throw new IllegalArgumentException("Actuator Model Repository cannot be null");
        if (repositoryDevice == null)
            throw new IllegalArgumentException("Device Repository cannot be null");

        this._repositorySensor = repositorySensor;
        this._repositorySensorType = repositorySensorType;
        this._repositoryActuator = repositoryActuator;
        this._I_repositoryActuatorType = repositoryActuatorType;
        this._repositoryDevice = repositoryDevice;
        this._repositoryActuatorModel = repositoryActuatorModel;
        this._repositorySensorModel = repositorySensorModel;
    }

    /**
     * Retrieves all devices, groups them by their associated sensor or actuator types, and maps them to their DTO representation.
     * @return returns a Map with a key of types and their respective lists comprised of Device Objects of that type.
     */
    public Map<String, List<Device>> getDevicesByType() {
        Map<String, List<Device>> groupedDevices = new HashMap<>();
        List<Sensor> sensors = new ArrayList<>();
        _repositorySensor.findAll().forEach(sensors::add);
        List<Actuator> actuators = new ArrayList<>();
        _repositoryActuator.findAll().forEach(actuators::add);
        assignDevicesByType(groupedDevices, sensors, actuators);
        return groupedDevices;
    }

    /**
     * Assigns devices to their respective types based on the sensors and actuators they contain.
     * This method updates the provided map by grouping devices according to their sensor or actuator types.
     * @param groupedDevices Map containing Types and the respective List of Devices of that type.
     * @param sensors A list of all sensors.
     * @param actuators A list of all actuators.
     * @return returns an updated Map with the newly inserted Devices in their respective List.
     */
    public Map<String, List<Device>> assignDevicesByType(Map<String, List<Device>> groupedDevices, List<Sensor> sensors, List<Actuator> actuators){
        assignDeviceToSensorGroupType(groupedDevices,sensors);
        assignDeviceToActuatorGroupType(groupedDevices,actuators);
        return groupedDevices;
    }

    /**
     * Given a Map of Devices and a list of Sensors, it stores the latter's corresponding Device in the respective Type List.
     * @param groupedDevices Map containing Types and the respective List of Devices of that type.
     * @param sensors A list of all sensors present in the repository.
     * @return returns an updated Map with the newly inserted Devices in their respective List.
     */
    private boolean assignDeviceToSensorGroupType(Map<String, List<Device>> groupedDevices, List<Sensor> sensors) {
        if (sensors.isEmpty()) {
            return false;
        }
        for (Sensor sensor : sensors) {
            // Retrieve the description from the sensor model if it's present
            _repositorySensorModel.ofIdentity(sensor.getSensorModelID()).flatMap(sensorModel ->
                    _repositorySensorType.ofIdentity(sensorModel.getSensorTypeID())).ifPresent(sensorType -> {
                String typeDescription = String.valueOf(sensorType.getDescription());
                // Retrieve the device if it's present and add it to the map
                _repositoryDevice.ofIdentity(sensor.getDeviceID()).ifPresent(device -> addDeviceToTypeMap(groupedDevices, typeDescription, device));
            });
        }
        return true;
    }

    /**
     * Given a Map of Devices and a list of Actuators, it stores the latter's corresponding Device in the respective Type List.
     * @param groupedDevices Map containing Types and the respective List of Devices of that type.
     * @param actuators A list of all actuators present in the repository.
     * @return returns an updated Map with the newly inserted Devices in their respective List.
     */
    private boolean assignDeviceToActuatorGroupType(Map<String, List<Device>> groupedDevices, List<Actuator> actuators) {
        if (actuators.isEmpty()) {
            return false;
        }
        for (Actuator actuator : actuators) {
            // Retrieve the description from the actuator model if it's present
            _repositoryActuatorModel.ofIdentity(actuator.getActuatorModelID()).flatMap(actuatorModel ->
                    _I_repositoryActuatorType.ofIdentity(actuatorModel.getActuatorTypeID())).ifPresent(actuatorType -> {
                String typeDescription = String.valueOf(actuatorType.getDescription());
                // Retrieve the device if it's present and add it to the map
                _repositoryDevice.ofIdentity(actuator.getDeviceID()).ifPresent(device -> addDeviceToTypeMap(groupedDevices, typeDescription, device));
            });
        }
        return true;
    }

    /**
     * Adds a device to the grouped devices map in its respective List based on Type
     * If the type description does not exist as a key in the map, it creates a new list and associates it with the type.
     * If the type (description) already exists, it adds the device to the existing list, preventing duplicates.
     * @param groupedDevices The map containing grouped devices organized in lists by type.
     * @param typeDescription The description of the device's type, used as a key.
     * @param device The device to be added to the Map.
     */
    private boolean addDeviceToTypeMap(Map<String, List<Device>> groupedDevices, String typeDescription, Device device) {
        groupedDevices.putIfAbsent(typeDescription, new ArrayList<>());
        groupedDevices.get(typeDescription).add(device);
        return true;
    }
}
