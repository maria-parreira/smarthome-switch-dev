package smartHomeDDD.controllers;

import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.dto.SensorMapper;
import smartHomeDDD.dto.SensorDTO;
import smartHomeDDD.dto.SensorModelMapper;
import smartHomeDDD.services.ServiceSensor;

/**
 * The AddNewSensorToDeviceController class is responsible for handling the addition of new sensors to devices.
 * It uses a ServiceSensor to handle the creation and storage of new Sensor objects.
 */
public class AddNewSensorToDeviceController {

    /**
     * The ServiceSensor instance used by the AddNewSensorToDeviceController to handle the creation and storage of new Sensor objects.
     */
    private final ServiceSensor _serviceSensor;


    /**
     * Constructs a new AddNewSensorToDeviceController with the specified ServiceSensor.
     * Throws an IllegalArgumentException if the ServiceSensor is null.
     *
     * @param serviceSensor the ServiceSensor to be used by the controller
     * @throws IllegalArgumentException if the ServiceSensor is null
     */

    public AddNewSensorToDeviceController(ServiceSensor serviceSensor) throws IllegalArgumentException {
        if (serviceSensor == null) {
            throw new IllegalArgumentException("Sensor Service cannot be null.");
        }
        this._serviceSensor = serviceSensor;
    }

    /**
     * Adds a new sensor to a device.
     * This method takes a SensorDTO object as input, which contains the details of the sensor to be added.
     * It converts the sensor details from the SensorDTO to domain objects, then uses the ServiceSensor to create and store a new Sensor.
     * Finally, it converts the created Sensor back to a SensorDTO and returns it.
     *
     * @param sensorDTO the SensorDTO containing the details of the sensor to be added
     * @return a SensorDTO representing the sensor that was added
     */
    public SensorDTO addNewSensorToDevice(SensorDTO sensorDTO) {

        DeviceId deviceID = DeviceMapper.DTOToDeviceId(sensorDTO.getDeviceID());
        SensorModelID sensorModelID = SensorModelMapper.DTOToSensorModelId(sensorDTO.getSensorModelID());

        Sensor sensor = _serviceSensor.createNewSensor(deviceID, sensorModelID);
        return SensorMapper.convertSensorToDTO(sensor);
    }



}