package smartHomeDDD.domain.controllers;

import smartHomeDDD.controllers.GetMeasurementsFromDeviceWithinPeriodController;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.ImplFactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.GetReadingsWithinPeriodDTO;
import smartHomeDDD.dto.SensorReadingDTO;
import smartHomeDDD.persistence.mem.RepositoryDeviceMem;
import smartHomeDDD.persistence.mem.RepositorySensorMem;
import smartHomeDDD.persistence.mem.RepositorySensorReadingMem;
import smartHomeDDD.services.GenerateRandomId;
import smartHomeDDD.services.ServiceSensor;
import smartHomeDDD.services.ServiceSensorReading;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GetMeasurementsFromDeviceWithinPeriodController.
 * It contains test cases related to the retrieval of sensor readings from a device within a specified period.
 * This class tests the following scenarios:
 * - Valid sensor readings are returned for a specified period.
 * - An empty list is returned when no readings are found for the specified period.
 * - An IllegalArgumentException is thrown when the provided time period is invalid.
 * - An IllegalArgumentException is thrown when the service is null.
 */

class GetMeasurementsFromDeviceWithinPeriodControllerTest {

    /**
     * Test case to verify that valid sensor readings are returned for a specified period.
     */
    @Test
    void getValidReadingsFromDeviceWithinPeriod_shouldReturnReadingList(){
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        //create deviceId VO
        String deviceID = "d1";
        DeviceId device = new DeviceId(deviceID);
        //create factory, timeStamp, value and id to create sensorReading object
        FactorySensorReading factory = new ImplFactorySensorReading();
        Timestamp timeStampIn = Timestamp.valueOf("2021-01-01 12:00:00");
        Timestamp timeStampOff = Timestamp.valueOf("2021-01-02 12:00:00");
        Reading value = new Reading("15");
        SensorReadingID sensorReadingID1 = new SensorReadingID("sr1");
        SensorReadingID sensorReadingID2 = new SensorReadingID("sr2");
        SensorID sensorID = new SensorID("s1");
        //create SensorReading object
        SensorReading reading1 = factory.createSensorReading(sensorReadingID1,value,device,sensorID,timeStampIn);
        SensorReading reading2 = factory.createSensorReading(sensorReadingID2,value,device,sensorID,timeStampOff);
        // save readings at repo
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        repository.save(reading1);
        repository.save(reading2);
        //create repository sensor
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        //create repository device
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        //create service sensor
        FactorySensor factorySensor = new ImplFactorySensor();
        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,repositorySensor,repositoryDevice,generateRandomId);
        // create service and controller objects
        ServiceSensorReading service = new ServiceSensorReading(repository,repositorySensor, serviceSensor,factorySensorReading,repositoryDevice);
        GetMeasurementsFromDeviceWithinPeriodController controller = new GetMeasurementsFromDeviceWithinPeriodController(service);
        //time received at controller to get readings from device
        Timestamp start = Timestamp.valueOf("2021-01-01 00:00:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 23:59:59");
        // Create DTO object
        GetReadingsWithinPeriodDTO entryDTO = new GetReadingsWithinPeriodDTO(deviceID,start,end);

        // Act
        List<SensorReadingDTO> result = controller.getMeasurementsFromDeviceWithinPeriod(entryDTO);

        //Assert
        assertFalse(result.isEmpty());

    }

    /**
     * Test case to verify that an empty list is returned when no readings are found for the specified period.
     */
    @Test
    void getReadingsFromDeviceWithinPeriod_shouldReturnEmptyList(){
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        //create deviceId VO
        String deviceID = "d1";
        DeviceId device = new DeviceId(deviceID);
        //create factory, timeStamp, value and id to create sensorReading object
        FactorySensorReading factory = new ImplFactorySensorReading();
        Timestamp timeStampIn = Timestamp.valueOf("2021-01-02 12:00:00");
        Timestamp timeStampOff = Timestamp.valueOf("2021-01-02 12:00:00");
        Reading value = new Reading("15");
        SensorReadingID sensorReadingID1 = new SensorReadingID("sr1");
        SensorReadingID sensorReadingID2 = new SensorReadingID("sr2");
        SensorID sensorID = new SensorID("s1");
        //create SensorReading object
        SensorReading reading1 = factory.createSensorReading(sensorReadingID1,value,device,sensorID,timeStampIn);
        SensorReading reading2 = factory.createSensorReading(sensorReadingID2,value,device,sensorID,timeStampOff);
        // save readings at repo
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        repository.save(reading1);
        repository.save(reading2);
        //create repository sensor
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        //create repository device
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        //create service sensor
        FactorySensor factorySensor = new ImplFactorySensor();
        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,repositorySensor,repositoryDevice,generateRandomId);
        // create service and controller objects
        ServiceSensorReading service = new ServiceSensorReading(repository,repositorySensor, serviceSensor,factorySensorReading,repositoryDevice);
        GetMeasurementsFromDeviceWithinPeriodController controller = new GetMeasurementsFromDeviceWithinPeriodController(service);
        //time received at controller to get readings from device
        Timestamp start = Timestamp.valueOf("2021-01-01 00:00:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 23:59:59");
        // Create DTO object
        GetReadingsWithinPeriodDTO entryDTO = new GetReadingsWithinPeriodDTO(deviceID,start,end);

        // Act
        List<SensorReadingDTO> result = controller.getMeasurementsFromDeviceWithinPeriod(entryDTO);

        //Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the provided time period is invalid.
     */
    @Test
    void invalidTimePeriod_shouldReturnThrowException(){
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        //create device id VO
        String deviceID = "d1";
        DeviceId device = new DeviceId(deviceID);
        //create factory, timeStamp, value and id to create sensorReading object
        FactorySensorReading factory = new ImplFactorySensorReading();
        Timestamp timeStampIn = Timestamp.valueOf("2021-01-02 12:00:00");
        Timestamp timeStampOff = Timestamp.valueOf("2021-01-02 12:00:00");
        Reading value = new Reading("15");
        SensorID sensorID = new SensorID("s1");
        SensorReadingID sensorReadingID1 = new SensorReadingID("sr1");
        SensorReadingID sensorReadingID2 = new SensorReadingID("sr2");
        //create SensorReading object
        SensorReading reading1 = factory.createSensorReading(sensorReadingID1,value,device,sensorID,timeStampIn);
        SensorReading reading2 = factory.createSensorReading(sensorReadingID2,value,device,sensorID,timeStampOff);
        // save readings at repo
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        repository.save(reading1);
        repository.save(reading2);
        //create repository sensor
        IRepositorySensor repositorySensor = new RepositorySensorMem();
        //create repository device
        IRepositoryDevice repositoryDevice = new RepositoryDeviceMem();
        //create service sensor
        FactorySensor factorySensor = new ImplFactorySensor();
        GenerateRandomId generateRandomId = new GenerateRandomId();
        ServiceSensor serviceSensor = new ServiceSensor(factorySensor,repositorySensor,repositoryDevice,generateRandomId);
        // create service and controller objects
        ServiceSensorReading service = new ServiceSensorReading(repository,repositorySensor, serviceSensor,factorySensorReading,repositoryDevice);
        GetMeasurementsFromDeviceWithinPeriodController controller = new GetMeasurementsFromDeviceWithinPeriodController(service);
        //time receive at controller to get readings from device
        Timestamp start = Timestamp.valueOf("2021-01-02 00:00:00");
        Timestamp end = Timestamp.valueOf("2021-01-01 23:59:59");
        // Create DTO object
        GetReadingsWithinPeriodDTO entryDTO = new GetReadingsWithinPeriodDTO(deviceID,start,end);
        String expected = "Invalid time period";

        // Act
        String result = assertThrows(IllegalArgumentException.class, () -> controller.getMeasurementsFromDeviceWithinPeriod(entryDTO)).getMessage();

        //Assert
        assertTrue(result.contains(expected));
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the service is null.
     */
    @Test
    void validTimePeriod_NullService_shouldReturnThrowException() {
        // Arrange
        //create deviceId VO
        String deviceID = "d1";
        DeviceId device = new DeviceId(deviceID);
        //create factory, timeStamp, value and id to create sensorReading object
        FactorySensorReading factory = new ImplFactorySensorReading();
        Timestamp timeStampIn = Timestamp.valueOf("2021-01-02 12:00:00");
        Timestamp timeStampOff = Timestamp.valueOf("2021-01-02 12:00:00");
        Reading value = new Reading("15");
        SensorReadingID sensorReadingID1 = new SensorReadingID("sr1");
        SensorReadingID sensorReadingID2 = new SensorReadingID("sr2");
        SensorID sensorID = new SensorID("s1");
        //create SensorReading object
        SensorReading reading1 = factory.createSensorReading(sensorReadingID1, value, device, sensorID, timeStampIn);
        SensorReading reading2 = factory.createSensorReading(sensorReadingID2, value, device, sensorID, timeStampOff);
        // save readings at repo
        IRepositorySensorReading repository = new RepositorySensorReadingMem();
        repository.save(reading1);
        repository.save(reading2);
        //time controller receive to get readings from device
        String expected = "Service cannot be null";

        // Act
        String result = assertThrows(IllegalArgumentException.class, () -> new GetMeasurementsFromDeviceWithinPeriodController(null)).getMessage();

        //Assert
        assertTrue(result.contains(expected));

    }
}
