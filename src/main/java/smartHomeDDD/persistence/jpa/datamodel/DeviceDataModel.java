package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.valueobject.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Model for Device
 * This class is used to map the Device domain object to the database
 */

@Entity
@Table(name = "DEVICE")
public class DeviceDataModel {

    /**
     * The device id, which is the primary key
     */
    @Id
    @Column(name = "device_id")
    private String deviceId;

    /**
     * The device name
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * The device model
     */
    @Column(name = "device_model")
    private String deviceModel;

    /**
     * The device status
     */
    @Column(name = "status")
    private boolean status;

    /**
     * The room id where the device is located
     */
    @Column(name = "room_id")
    private String roomId;

    /**
     * Default constructor with no parameters.
     */
    public DeviceDataModel() {
    }

    /**
     * Constructor with parameters
     * @param device The device domain object
     */
    public DeviceDataModel(Device device) {
        deviceId = device.identity().toString();
        deviceName = device.getDeviceName().toString();
        deviceModel = device.getDeviceModel().toString();
        status = Boolean.parseBoolean(device.getActivationStatus().toString());
        roomId = device.getRoomId().toString();
    }

    /**
     * method to convert a device data model object to a device domain object
     * @param factoryDevice the factory device to instantiate the device domain object
     * @param deviceDataModel the device data model
     * @return the device domain object
     */
    public static Device toDomain(FactoryDevice factoryDevice, DeviceDataModel deviceDataModel) {
        DeviceId deviceId = new DeviceId(deviceDataModel.deviceId);
        DeviceName deviceName = new DeviceName(deviceDataModel.deviceName);
        DeviceModel deviceModel = new DeviceModel(deviceDataModel.deviceModel);
        ActivationStatus status = new ActivationStatus(deviceDataModel.status);
        RoomID roomId = new RoomID(deviceDataModel.roomId);

        if (status.toString().contains("true")) {
            return factoryDevice.createDevice(deviceId, deviceName, deviceModel, status, roomId);
        }
        else {
            ActivationStatus activationStatus2 = new ActivationStatus(true);
            Device deviceDomain = factoryDevice.createDevice(deviceId, deviceName, deviceModel, activationStatus2, roomId);
            deviceDomain.deactivateDevice();
            return deviceDomain;
        }
    }

    /**
     * method to convert a list of device data model objects to a list of device domain objects
     * @param factoryDevice the factory device to instantiate the device domain object
     * @param listDataModel the list of device data model objects to be converted to domain objects
     * @return a list of device domain objects
     */
    public static List<Device> toDomain(FactoryDevice factoryDevice, Iterable<DeviceDataModel> listDataModel) {
        List<Device> listDomain = new ArrayList<>();

        listDataModel.forEach(deviceDataModel -> {
            Device deviceDomain = toDomain(factoryDevice, deviceDataModel);
            listDomain.add(deviceDomain);
        });

        return listDomain;
    }

    /**
     * method to update the device data model based on the device domain object
     * @param device the device domain object
     * @return true if the device data model is updated from the domain object
     */
    public boolean updateFromDomain (Device device){
        this.deviceId = device.identity().toString();
        this.deviceName = device.getDeviceName().toString();
        this.deviceModel = device.getDeviceModel().toString();
        this.status = Boolean.parseBoolean(device.getActivationStatus().toString());
        this.roomId = device.getRoomId().toString();
        return true;
    }

}
