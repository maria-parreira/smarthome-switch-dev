package smartHomeDDD.controllers;

import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.dto.GetReadingsWithinPeriodDTO;
import smartHomeDDD.dto.SensorReadingDTO;
import smartHomeDDD.dto.SensorReadingMapper;

import smartHomeDDD.services.ServiceSensorReading;

import java.sql.Timestamp;
import java.util.List;

/**
 * Controller class for retrieving sensor measurements from a device within a specified period.
 */
public class GetMeasurementsFromDeviceWithinPeriodController {

    /**
     * Service for retrieving sensor readings.
     */
    private final ServiceSensorReading _serviceSensorReading;

    /**
     * Constructs a new GetMeasurementsFromDeviceWithinPeriodController with the specified ServiceSensorReading.
     * @param serviceSensorReading The ServiceSensorReading instance to use for retrieving sensor readings.
     * @throws IllegalArgumentException if the provided serviceSensorReading is null.
     */
    public GetMeasurementsFromDeviceWithinPeriodController(ServiceSensorReading serviceSensorReading) {
        if (serviceSensorReading == null)
            throw new IllegalArgumentException("Service cannot be null");
        this._serviceSensorReading = serviceSensorReading;
    }

    /**
     * Retrieves sensor measurements from a device within the specified period.
     * @param entryDTO The GetReadingsWithinPeriodDTO object containing the device ID, start time and end time.
     * @return A list of SensorReadingDTO objects representing the sensor measurements within the specified period.
     */
    public List<SensorReadingDTO> getMeasurementsFromDeviceWithinPeriod(GetReadingsWithinPeriodDTO entryDTO) {
        DeviceId id = DeviceMapper.DTOToDeviceId(entryDTO.getDeviceID());
        Timestamp startTime = entryDTO.getStartTime();
        Timestamp endTime = entryDTO.getEndTime();
        List<SensorReading> readingsOfDevice = _serviceSensorReading.getMeasurementsFromDeviceWithinPeriod(id, startTime, endTime);
        return SensorReadingMapper.domainListToDTO(readingsOfDevice);
    }

}
