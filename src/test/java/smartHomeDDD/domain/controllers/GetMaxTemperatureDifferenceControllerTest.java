package smartHomeDDD.domain.controllers;


import smartHomeDDD.controllers.GetMaxTemperatureDifferenceController;
import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.FactoryDevice;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.ImplFactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.GA100K;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.ImplFactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.GetMaxDifferenceDTO;
import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import smartHomeDDD.persistence.mem.RepositoryRoomMem;
import smartHomeDDD.persistence.mem.RepositorySensorMem;
import smartHomeDDD.persistence.mem.RepositorySensorReadingMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceSensor;
import smartHomeDDD.services.ServiceSensorReading;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the GetMaxTemperatureDifferenceController class.
 * - Test the successful retrieval of the maximum temperature difference between sensors.
 * - Test that an exception is thrown when there are no readings in the defined time interval.
 * - Test that returns the maximum difference when several readings with the same value are found.
 * - Test that an exception is thrown when the start time is after the end time.
 * - Test that finds difference when the start time is the same as the end time.
 * - Test that an exception is thrown when there are no readings between the defined delta interval (in minutes).
 * - Test that an exception is thrown when there are only inside readings in the defined time interval.
 * - Test that an exception is thrown when there are only outside readings in the defined time interval.
 */
class GetMaxTemperatureDifferenceControllerTest
{
    /**
     * Test the successful retrieval of the maximum temperature difference between sensors indoor and outdoor.
     */
    @Test
    void getValidReadingsFromDeviceWithinPeriod_shouldReturnMaximumTemperatureDifference(){
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);

        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 5;

        SensorReadingID sensorReadingID1Indoor = new SensorReadingID("sr2");
        SensorReadingID sensorReadingID2Outdoor = new SensorReadingID("sr1");

        DeviceId deviceIDIndoor = new DeviceId(deviceIDIndoorString);
        DeviceId deviceIDOutdoor = new DeviceId(deviceIDOutdoorString);
        SensorID sensorIDIndoors = new SensorID("s3");
        SensorID sensorIDOutdoors = new SensorID("s2");

        Reading reading1Indoor = new Reading("20");
        Reading reading2Outdoor = new Reading("30");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:10:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:30:00");

        Timestamp timeStampIndoor = Timestamp.valueOf("2021-01-01 12:15:00");
        Timestamp timeStampOutdoor = Timestamp.valueOf("2021-01-01 12:17:00");

        SensorReading SensorReading1 = factory.createSensorReading(sensorReadingID1Indoor,reading1Indoor,deviceIDIndoor,sensorIDIndoors,timeStampIndoor);
        SensorReading SensorReading2 = factory.createSensorReading(sensorReadingID2Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        repository.save(SensorReading1);
        repository.save(SensorReading2);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);

        // Act
        Integer result = controller.getMaxTemperatureDifference(getMaxDifferenceDTO);

        //Assert
        assertEquals(10, result);

    }

    /**
     * Test that an exception is thrown when there are no readings in the defined time interval.
     */
    @Test
    void noReadingsFromDeviceWithinPeriod_shouldThrowException(){
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);

        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 5;

        SensorReadingID sensorReadingID1Indoor = new SensorReadingID("sr2");
        SensorReadingID sensorReadingID2Outdoor = new SensorReadingID("sr1");

        DeviceId deviceIDIndoor = new DeviceId(deviceIDIndoorString);
        DeviceId deviceIDOutdoor = new DeviceId(deviceIDOutdoorString);
        SensorID sensorIDIndoors = new SensorID("s3");
        SensorID sensorIDOutdoors = new SensorID("s2");

        Reading reading1Indoor = new Reading("20");
        Reading reading2Outdoor = new Reading("30");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:10:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:30:00");

        Timestamp timeStampIndoor = Timestamp.valueOf("2021-01-01 12:35:00");
        Timestamp timeStampOutdoor = Timestamp.valueOf("2021-01-01 12:37:00");

        SensorReading SensorReading1 = factory.createSensorReading(sensorReadingID1Indoor,reading1Indoor,deviceIDIndoor,sensorIDIndoors,timeStampIndoor);
        SensorReading SensorReading2 = factory.createSensorReading(sensorReadingID2Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        repository.save(SensorReading1);
        repository.save(SensorReading2);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);
        String expectedMessage = "No temperature readings found for the given time period";

        // Act
        Exception exception = assertThrows(Exception.class, () -> controller.getMaxTemperatureDifference(getMaxDifferenceDTO));
        String exceptionMessage = exception.getMessage();

        //Assert
        assertEquals(expectedMessage, exceptionMessage);

    }

    /**
     * Test that returns the maximum difference when several readings with the same value are found.
     */
    @Test
    void severalValidReadingsWithTheSameDifference_shouldReturnMaximumTemperatureDifference(){
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);

        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 5;

        SensorReadingID sensorReadingID1Indoor = new SensorReadingID("sr2");
        SensorReadingID sensorReadingID2Outdoor = new SensorReadingID("sr1");
        SensorReadingID sensorReadingID3Outdoor = new SensorReadingID("sr3");

        DeviceId deviceIDIndoor = new DeviceId(deviceIDIndoorString);
        DeviceId deviceIDOutdoor = new DeviceId(deviceIDOutdoorString);
        SensorID sensorIDIndoors = new SensorID("s3");
        SensorID sensorIDOutdoors = new SensorID("s2");

        Reading reading1Indoor = new Reading("20");
        Reading reading2Outdoor = new Reading("30");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:10:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:30:00");

        Timestamp timeStampIndoor = Timestamp.valueOf("2021-01-01 12:15:00");
        Timestamp timeStampOutdoor = Timestamp.valueOf("2021-01-01 12:17:00");

        SensorReading SensorReading1 = factory.createSensorReading(sensorReadingID1Indoor,reading1Indoor,deviceIDIndoor,sensorIDIndoors,timeStampIndoor);
        SensorReading SensorReading2 = factory.createSensorReading(sensorReadingID2Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        SensorReading SensorReading3 = factory.createSensorReading(sensorReadingID3Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        repository.save(SensorReading1);
        repository.save(SensorReading2);
        repository.save(SensorReading3);

        //GetTemperatureReadingsFromDeviceWithinPeriodController getInstantaneousTemperatureOutsideController = new GetTemperatureReadingsFromDeviceWithinPeriodController(serviceSensor,serviceSensorReading);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);

        // Act
        Integer result = controller.getMaxTemperatureDifference(getMaxDifferenceDTO);

        //Assert
        assertEquals(10, result);

    }

    /**
     * Test that an exception is thrown when the start time is after the end time.
     */
    @Test
    void invalidTimeStampStartIsAfterEnd_shouldThrowException(){
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);

        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 5;

        SensorReadingID sensorReadingID1Indoor = new SensorReadingID("sr2");
        SensorReadingID sensorReadingID2Outdoor = new SensorReadingID("sr1");

        DeviceId deviceIDIndoor = new DeviceId(deviceIDIndoorString);
        DeviceId deviceIDOutdoor = new DeviceId(deviceIDOutdoorString);
        SensorID sensorIDIndoors = new SensorID("s3");
        SensorID sensorIDOutdoors = new SensorID("s2");

        Reading reading1Indoor = new Reading("20");
        Reading reading2Outdoor = new Reading("30");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:40:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:30:00");

        Timestamp timeStampIndoor = Timestamp.valueOf("2021-01-01 12:15:00");
        Timestamp timeStampOutdoor = Timestamp.valueOf("2021-01-01 12:17:00");

        SensorReading SensorReading1 = factory.createSensorReading(sensorReadingID1Indoor,reading1Indoor,deviceIDIndoor,sensorIDIndoors,timeStampIndoor);
        SensorReading SensorReading2 = factory.createSensorReading(sensorReadingID2Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        repository.save(SensorReading1);
        repository.save(SensorReading2);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);

        String expectedMessage = "Invalid time period";

        // Act
        Exception exception = assertThrows(Exception.class, () -> controller.getMaxTemperatureDifference(getMaxDifferenceDTO));
        String exceptionMessage = exception.getMessage();

        //Assert
        assertEquals(expectedMessage, exceptionMessage);

    }

    /**
     * Test that finds difference when the start time is the same as the end time.
     */
    @Test
    void getValidReadingsFromDeviceWithinPeriod_StarTimeIsTheSameAsEndTime_shouldReturnMaximumTemperatureDifference(){
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);


        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 5;

        SensorReadingID sensorReadingID1Indoor = new SensorReadingID("sr2");
        SensorReadingID sensorReadingID2Outdoor = new SensorReadingID("sr1");

        DeviceId deviceIDIndoor = new DeviceId(deviceIDIndoorString);
        DeviceId deviceIDOutdoor = new DeviceId(deviceIDOutdoorString);
        SensorID sensorIDIndoors = new SensorID("s3");
        SensorID sensorIDOutdoors = new SensorID("s2");

        Reading reading1Indoor = new Reading("-20");
        Reading reading2Outdoor = new Reading("-30");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:15:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:15:00");

        Timestamp timeStampIndoor = Timestamp.valueOf("2021-01-01 12:15:00");
        Timestamp timeStampOutdoor = Timestamp.valueOf("2021-01-01 12:15:00");

        SensorReading SensorReading1 = factory.createSensorReading(sensorReadingID1Indoor,reading1Indoor,deviceIDIndoor,sensorIDIndoors,timeStampIndoor);
        SensorReading SensorReading2 = factory.createSensorReading(sensorReadingID2Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        repository.save(SensorReading1);
        repository.save(SensorReading2);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);

        // Act
        Integer result = controller.getMaxTemperatureDifference(getMaxDifferenceDTO);

        //Assert
        assertEquals(10, result);

    }

    /**
     * Test that an exception is thrown when there are no readings between the defined delta interval.
     */
    @Test
    void noReadingsBetweenDelta_shouldThrowException() {
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);


        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 1;

        SensorReadingID sensorReadingID1Indoor = new SensorReadingID("sr2");
        SensorReadingID sensorReadingID2Outdoor = new SensorReadingID("sr1");

        DeviceId deviceIDIndoor = new DeviceId(deviceIDIndoorString);
        DeviceId deviceIDOutdoor = new DeviceId(deviceIDOutdoorString);
        SensorID sensorIDIndoors = new SensorID("s3");
        SensorID sensorIDOutdoors = new SensorID("s2");

        Reading reading1Indoor = new Reading("20");
        Reading reading2Outdoor = new Reading("30");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:10:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:30:00");

        Timestamp timeStampIndoor = Timestamp.valueOf("2021-01-01 12:15:00");
        Timestamp timeStampOutdoor = Timestamp.valueOf("2021-01-01 12:17:00");

        SensorReading SensorReading1 = factory.createSensorReading(sensorReadingID1Indoor,reading1Indoor,deviceIDIndoor,sensorIDIndoors,timeStampIndoor);
        SensorReading SensorReading2 = factory.createSensorReading(sensorReadingID2Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        repository.save(SensorReading1);
        repository.save(SensorReading2);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);

        String expectedMessage = "No readings found within the given time period.";

        // Act
        Exception exception = assertThrows(Exception.class, () -> controller.getMaxTemperatureDifference(getMaxDifferenceDTO));
        String exceptionMessage = exception.getMessage();

        //Assert
        assertEquals(expectedMessage, exceptionMessage);

    }

    /**
     * Test that an exception is thrown when there are only inside readings in the defined time interval.
     */
    @Test
    void onlyInsideReadingsFromDeviceWithinPeriod_shouldReturnMaximumTemperatureDifference(){
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);


        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 5;

        SensorReadingID sensorReadingID1Indoor = new SensorReadingID("sr2");

        DeviceId deviceIDIndoor = new DeviceId(deviceIDIndoorString);
        SensorID sensorIDIndoors = new SensorID("s3");

        Reading reading1Indoor = new Reading("20");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:10:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:30:00");

        Timestamp timeStampIndoor = Timestamp.valueOf("2021-01-01 12:15:00");

        SensorReading SensorReading1 = factory.createSensorReading(sensorReadingID1Indoor,reading1Indoor,deviceIDIndoor,sensorIDIndoors,timeStampIndoor);
        repository.save(SensorReading1);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);
        String expectedMessage = "No temperature readings found for the given time period";

        // Act
        Exception exception = assertThrows(Exception.class, () -> controller.getMaxTemperatureDifference(getMaxDifferenceDTO));
        String exceptionMessage = exception.getMessage();

        //Assert
        assertEquals(expectedMessage, exceptionMessage);

    }

    /**
     * Test that an exception is thrown when there are only outside readings in the defined time interval.
     */
    @Test
    void onlyOutsideReadingsFromDeviceWithinPeriod_shouldReturnMaximumTemperatureDifference(){
        // Arrange
        IRepositoryRoom roomRepository = new RepositoryRoomMem();
        IRepositoryDevice deviceRepository = new RepositoryDeviceMem();
        IRepositorySensor sensorRepository = new RepositorySensorMem();
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        FactorySensorReading factory = new ImplFactorySensorReading();
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();

        FactoryRoom factoryRoom = new ImplFactoryRoom();
        FactoryDevice factoryDevice = new ImplFactoryDevice();
        FactorySensor factorySensor = new ImplFactorySensor();

        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,sensorRepository, deviceRepository, generateRandomId);


        Room roomOutside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r1"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), false, new RoomName("room1"));
        Room roomInside = factoryRoom.createRoom( new HouseId("h1"), new RoomID("r2"), new FloorNumber(1), new Dimensions(new Length(10),new Width(10),new Height(10)), true, new RoomName("room2"));
        roomRepository.save(roomOutside);
        roomRepository.save(roomInside);

        Device deviceWithSensorRoomOutside = factoryDevice.createDevice(new DeviceId("d1"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r1"));
        Device deviceWithSensorRoomInside = factoryDevice.createDevice(new DeviceId("d2"), new DeviceName("name"), new DeviceModel("model"), new ActivationStatus(true), new RoomID("r2"));
        deviceRepository.save(deviceWithSensorRoomInside);
        deviceRepository.save(deviceWithSensorRoomOutside);

        Sensor sensorHumidity = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("TSY01"), new SensorID("s1"));
        Sensor sensorTempDeviceOutside = factorySensor.createSensor(new DeviceId("d1"), new SensorModelID("GA100K"), new SensorID("s2"));
        Sensor sensorTempDeviceInside = factorySensor.createSensor(new DeviceId("d2"), new SensorModelID("GA100K"), new SensorID("s3"));
        GA100K sensorTempOutside = (GA100K) sensorTempDeviceOutside;
        GA100K sensorTempInside = (GA100K) sensorTempDeviceInside;
        sensorRepository.save(sensorHumidity);
        sensorRepository.save(sensorTempOutside);
        sensorRepository.save(sensorTempInside);

        String deviceIDIndoorString = "d2";
        String deviceIDOutdoorString = "d1";
        int deltaTime = 5;

        SensorReadingID sensorReadingID2Outdoor = new SensorReadingID("sr1");

        DeviceId deviceIDOutdoor = new DeviceId(deviceIDOutdoorString);
        SensorID sensorIDOutdoors = new SensorID("s2");

        Reading reading2Outdoor = new Reading("30");

        Timestamp start = Timestamp.valueOf("2021-01-01 12:10:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 12:30:00");

        Timestamp timeStampOutdoor = Timestamp.valueOf("2021-01-01 12:17:00");

        SensorReading SensorReading2 = factory.createSensorReading(sensorReadingID2Outdoor,reading2Outdoor,deviceIDOutdoor,sensorIDOutdoors,timeStampOutdoor);
        repository.save(SensorReading2);

        ServiceSensorReading service = new ServiceSensorReading(repository, repositorySensor,serviceSensor, factorySensorReading, deviceRepository);
        GetMaxTemperatureDifferenceController controller = new GetMaxTemperatureDifferenceController(service);

        GetMaxDifferenceDTO getMaxDifferenceDTO = new GetMaxDifferenceDTO(deviceIDIndoorString, deviceIDOutdoorString, deltaTime, start, end);
        String expectedMessage = "No temperature readings found for the given time period";

        // Act
        Exception exception = assertThrows(Exception.class, () -> controller.getMaxTemperatureDifference(getMaxDifferenceDTO));
        String exceptionMessage = exception.getMessage();

        //Assert
        assertEquals(expectedMessage, exceptionMessage);

    }

    @Test
    void constructor_NullServiceSensorReading_ShouldThrowIllegalArgumentException() {
        // Arrange
        ServiceSensorReading serviceSensorReading = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new GetMaxTemperatureDifferenceController(serviceSensorReading));
    }

}
