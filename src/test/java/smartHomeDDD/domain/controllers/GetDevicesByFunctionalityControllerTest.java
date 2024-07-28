package smartHomeDDD.domain.controllers;
import smartHomeDDD.controllers.GetDevicesByFunctionalityController;
import smartHomeDDD.domain.actuator.OPNCL0100;
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.actuatorModel.ImplFactoryActuatorModel;
import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.sensor.PC500W;
import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.ImplFactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.sensorType.ImplFactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.ImplFactoryActuatorType;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.repository.*;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.persistence.mem.*;
import smartHomeDDD.services.ServiceDeviceByType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the GetDevicesByTypeController.
 * It verifies the functionality of grouping devices by their type. The tests include the following scenarios:
 * Where the house has multiple rooms and devices, no devices, or when the house or catalogue is null.
 */
class GetDevicesByFunctionalityControllerTest {
    /**
     * This test verifies the functionality of grouping devices by their type in a house with 2 rooms and multiple devices.
     */
    @Test
    void devicesWithHumiditySensorTypeAndPercentageActuatorType_ShouldReturnFalseForEmptyAndTrueForHumidityAndPercentageTypes() {
        //Arrange
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        IRepositorySensorType repositorySensorType = new RepositorySensorTypeMem();
        IRepositorySensorModel repositorySensorModel = new RepositorySensorModelMem();
        IRepositoryActuator repositoryActuatorMem = new RepositoryActuatorMem();
        IRepositoryActuatorType RepositoryActuatorTypeMem = new RepositoryActuatorTypeMem();
        IRepositoryActuatorModel repositoryActuatorModel = new RepositoryActuatorModelMem();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();

        DeviceName deviceName = new DeviceName("Device");
        DeviceId deviceID = new DeviceId("Device1");
        ActivationStatus deviceStatus = new ActivationStatus(true);
        DeviceModel model = new DeviceModel("Dyson");
        RoomID roomId = new RoomID("SmallRoom");

        DeviceName secondDeviceName = new DeviceName("DifferentDevice");
        DeviceId secondDeviceID = new DeviceId("Device2");
        ActivationStatus secondDeviceStatus = new ActivationStatus(true);
        DeviceModel secondModel = new DeviceModel("Sony");
        RoomID secondRoomID = new RoomID("BigRoom");

        FactoryDevice factoryDevice = new ImplFactoryDevice();
        Device device1 = factoryDevice.createDevice(deviceID, deviceName, model, deviceStatus, roomId);
        Device device2 = factoryDevice.createDevice(secondDeviceID, secondDeviceName, secondModel, secondDeviceStatus, secondRoomID);
        repositoryDevice.save(device1);
        repositoryDevice.save(device2);

        String anotherSensorType = "Humidity";

        Unit sensorUnit = new Unit("Celsius");
        SensorTypeID sensorTypeID = new SensorTypeID("SType01");
        SensorModelID sensorModelID = new SensorModelID("PC500W");
        SensorID sensorID = new SensorID("Sensor01");
        Description sensorDescription = new Description("PowerConsumption");

        Unit secondSensorUnit = new Unit("AbsoluteHumidity");
        SensorTypeID sensorTypeID2 = new SensorTypeID("SType02");
        SensorModelID sensorModelID2 = new SensorModelID("GA100K");
        SensorID sensorID2 = new SensorID("Sensor02");
        Description anotherSensorDescription = new Description(anotherSensorType);

        FactoryActuatorType factoryActuatorType = new ImplFactoryActuatorType();
        FactoryActuatorModel factoryActuatorModel = new ImplFactoryActuatorModel();
        FactorySensorType factorySensorType = new ImplFactorySensorType();
        FactorySensorModel factorySensorModel = new ImplFactorySensorModel();

        Unit actuatorUnit = new Unit("unitOfMeasure");
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("AType01");
        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");

        ActuatorID actuatorID = new ActuatorID("Actuator01");
        Description actuatorDescription = new Description("Percentage");
        ActuatorType actuatorType1 = factoryActuatorType.createActuatorType(actuatorUnit, actuatorDescription, actuatorTypeID);
        RepositoryActuatorTypeMem.save(actuatorType1);

        ActuatorModel actuatorModel = factoryActuatorModel.createActuatorModel(actuatorModelID, actuatorTypeID);
        repositoryActuatorModel.save(actuatorModel);

        SensorType sensorType1 = factorySensorType.createSensorType(sensorTypeID, sensorDescription, sensorUnit);
        SensorType sensorType2 = factorySensorType.createSensorType(sensorTypeID2, anotherSensorDescription, secondSensorUnit);
        SensorModel sensorModel = factorySensorModel.createSensorModel(sensorModelID, sensorTypeID);
        SensorModel sensorModel2 = factorySensorModel.createSensorModel(sensorModelID2, sensorTypeID2);

        repositorySensorType.save(sensorType1);
        repositorySensorType.save(sensorType2);
        repositorySensorModel.save(sensorModel);
        repositorySensorModel.save(sensorModel2);
        repositorySensor.save(new PC500W(secondDeviceID, sensorModelID2, sensorID2));
        repositorySensor.save(new PC500W(deviceID, sensorModelID, sensorID));
        repositoryActuatorMem.save(new OPNCL0100(actuatorID, deviceID, actuatorModelID));

        ServiceDeviceByType serviceDeviceByType = new ServiceDeviceByType(repositorySensor, repositorySensorType,
                repositoryActuatorMem, RepositoryActuatorTypeMem, repositoryDevice, repositoryActuatorModel, repositorySensorModel);

        GetDevicesByFunctionalityController us09Controller = new GetDevicesByFunctionalityController(serviceDeviceByType);

        // Act
        Map<String, List<DeviceDTO>> result = us09Controller.retrieveDevicesByType();

        // Assert
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("Humidity"));
        assertTrue(result.containsKey("Percentage"));
    }

    /**
     * Test for a repository with no sensors in the repository.
     */
    @Test
    void noTemperatureTypeInRepository_ShouldReturnFalseForTemperatureAndTrueForOtherTypes() {
        //Arrange
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        IRepositorySensorType repositorySensorType = new RepositorySensorTypeMem();
        IRepositorySensorModel repositorySensorModel = new RepositorySensorModelMem();
        IRepositoryActuator repositoryActuatorMem = new RepositoryActuatorMem();
        IRepositoryActuatorType RepositoryActuatorTypeMem = new RepositoryActuatorTypeMem();
        IRepositoryActuatorModel repositoryActuatorModel = new RepositoryActuatorModelMem();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();

        DeviceName deviceName = new DeviceName("Device");
        DeviceId deviceID = new DeviceId("Device1");
        ActivationStatus deviceStatus = new ActivationStatus(true);
        DeviceModel model = new DeviceModel("Dyson");
        RoomID roomId = new RoomID("SmallRoom");

        DeviceName secondDeviceName = new DeviceName("DifferentDevice");
        DeviceId secondDeviceID = new DeviceId("Device2");
        ActivationStatus secondDeviceStatus = new ActivationStatus(true);
        DeviceModel secondModel = new DeviceModel("Sony");
        RoomID secondRoomID = new RoomID("BigRoom");

        FactoryDevice factoryDevice = new ImplFactoryDevice();
        Device device1 = factoryDevice.createDevice(deviceID, deviceName, model, deviceStatus, roomId);
        Device device2 = factoryDevice.createDevice(secondDeviceID, secondDeviceName, secondModel, secondDeviceStatus, secondRoomID);
        repositoryDevice.save(device1);
        repositoryDevice.save(device2);

        FactoryActuatorModel factoryActuatorModel = new ImplFactoryActuatorModel();
        FactoryActuatorType factoryActuatorType = new ImplFactoryActuatorType();
        Unit actuatorUnit = new Unit("unitOfMeasure");
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID("AType01");

        ActuatorModelID actuatorModelID = new ActuatorModelID("ONF01A");
        ActuatorID actuatorID = new ActuatorID("Actuator01");
        Description actuatorDescription = new Description("Percentage");

        ActuatorType actuatorType1 = factoryActuatorType.createActuatorType(actuatorUnit, actuatorDescription, actuatorTypeID);
        RepositoryActuatorTypeMem.save(actuatorType1);


        ActuatorModel actuatorModel = factoryActuatorModel.createActuatorModel(actuatorModelID, actuatorTypeID);
        repositoryActuatorModel.save(actuatorModel);
        repositoryActuatorMem.save(new OPNCL0100(actuatorID, deviceID, actuatorModelID));

        ServiceDeviceByType serviceDeviceByType = new ServiceDeviceByType(repositorySensor, repositorySensorType,
                repositoryActuatorMem, RepositoryActuatorTypeMem, repositoryDevice, repositoryActuatorModel, repositorySensorModel);

        GetDevicesByFunctionalityController us09Controller = new GetDevicesByFunctionalityController(serviceDeviceByType);

        // Act
        Map<String, List<DeviceDTO>> result = us09Controller.retrieveDevicesByType();

        // Assert
        assertFalse(result.isEmpty());
        assertFalse(result.containsKey("Temperature"));
        assertTrue(result.containsKey("Percentage"));
    }

    /**
     * Test for a repository with no actuators in the repository.
     */
    @Test
    void noHumidityTypeInRepository_ShouldReturnFalseForHumidityAndTrueForOtherTypes() {
        //Arrange
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        IRepositorySensorType repositorySensorType = new RepositorySensorTypeMem();
        IRepositorySensorModel repositorySensorModel = new RepositorySensorModelMem();
        IRepositoryActuator repositoryActuatorMem = new RepositoryActuatorMem();
        IRepositoryActuatorType RepositoryActuatorTypeMem = new RepositoryActuatorTypeMem();
        IRepositoryActuatorModel repositoryActuatorModel = new RepositoryActuatorModelMem();
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();

        DeviceName deviceName = new DeviceName("Device");
        DeviceId deviceID = new DeviceId("Device1");
        ActivationStatus deviceStatus = new ActivationStatus(true);
        DeviceModel model = new DeviceModel("Dyson");
        RoomID roomId = new RoomID("SmallRoom");

        DeviceName secondDeviceName = new DeviceName("DifferentDevice");
        DeviceId secondDeviceID = new DeviceId("Device2");
        ActivationStatus secondDeviceStatus = new ActivationStatus(true);
        DeviceModel secondModel = new DeviceModel("Sony");
        RoomID secondRoomID = new RoomID("BigRoom");

        FactoryDevice factoryDevice = new ImplFactoryDevice();
        Device device1 = factoryDevice.createDevice(deviceID, deviceName, model, deviceStatus, roomId);
        Device device2 = factoryDevice.createDevice(secondDeviceID, secondDeviceName, secondModel, secondDeviceStatus, secondRoomID);
        repositoryDevice.save(device1);
        repositoryDevice.save(device2);

        String anotherSensorType = "Humidity";

        Unit sensorUnit = new Unit("Celsius");
        SensorTypeID sensorTypeID = new SensorTypeID("SType01");
        SensorModelID sensorModelID = new SensorModelID("PC500W");
        SensorID sensorID = new SensorID("Sensor01");
        Description sensorDescription = new Description("PowerConsumption");

        Unit secondSensorUnit = new Unit("AbsoluteHumidity");
        SensorTypeID sensorTypeID2 = new SensorTypeID("SType02");
        SensorModelID sensorModelID2 = new SensorModelID("GA100K");
        SensorID sensorID2 = new SensorID("Sensor02");
        Description anotherSensorDescription = new Description(anotherSensorType);


        FactorySensorType factorySensorType = new ImplFactorySensorType();
        FactorySensorModel factorySensorModel = new ImplFactorySensorModel();
        SensorType sensorType1 = factorySensorType.createSensorType(sensorTypeID, sensorDescription, sensorUnit);
        SensorType sensorType2 = factorySensorType.createSensorType(sensorTypeID2, anotherSensorDescription, secondSensorUnit);
        SensorModel sensorModel = factorySensorModel.createSensorModel(sensorModelID, sensorTypeID);
        SensorModel sensorModel2 = factorySensorModel.createSensorModel(sensorModelID2, sensorTypeID2);

        repositorySensorType.save(sensorType1);
        repositorySensorType.save(sensorType2);
        repositorySensorModel.save(sensorModel);
        repositorySensorModel.save(sensorModel2);
        repositorySensor.save(new PC500W(secondDeviceID, sensorModelID2, sensorID2));
        repositorySensor.save(new PC500W(deviceID, sensorModelID, sensorID));

        ServiceDeviceByType serviceDeviceByType = new ServiceDeviceByType(repositorySensor, repositorySensorType,
                repositoryActuatorMem, RepositoryActuatorTypeMem, repositoryDevice, repositoryActuatorModel, repositorySensorModel);

        GetDevicesByFunctionalityController us09Controller = new GetDevicesByFunctionalityController(serviceDeviceByType);

        // Act
        Map<String, List<DeviceDTO>> result = us09Controller.retrieveDevicesByType();

        // Assert
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("Humidity"));
        assertFalse(result.containsKey("OPNCL0100"));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////// Spring Data ///////////////////////////////////////////////

}
