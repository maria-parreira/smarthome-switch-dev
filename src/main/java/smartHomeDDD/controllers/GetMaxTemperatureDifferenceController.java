package smartHomeDDD.controllers;


import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.dto.GetMaxDifferenceDTO;
import smartHomeDDD.services.ServiceSensorReading;

import java.util.List;

/**
 * Controller class to get the maximum temperature difference
 * between the indoor and outdoor temperature within a given time period
 * and within a delta time (in minutes).
 */
public class GetMaxTemperatureDifferenceController
{

    /**
     * The service for sensor readings.
     */
    private final ServiceSensorReading _sensorReadingService;

    /**
     * The controller for getting the instantaneous temperature.
     */
    //private final GetTemperatureReadingsFromDeviceWithinPeriodController _getTempController;


    /**
     * Constructor for the GetMaxTemperatureDifferenceController class.
     * @param sensorReadingService The service for sensor readings.
     */
    public GetMaxTemperatureDifferenceController(ServiceSensorReading sensorReadingService) {
        if (sensorReadingService == null)
            throw new IllegalArgumentException("Service cannot be null");
        this._sensorReadingService = sensorReadingService;
        //this._getTempController = getTempController;

    }

    /**
     * Retrieves the maximum temperature difference between the indoor and outdoor temperature within a given time period.
     * @param getMaxDifferenceDTO The sensor reading DTO object.
     * deltaTime - the time difference (in minutes) that is considered
     * instantaneous between the indoor and outdoor temperature readings.
     * @return The maximum temperature difference.
     */
    public Integer getMaxTemperatureDifference(GetMaxDifferenceDTO getMaxDifferenceDTO) {

        List<SensorReading> allInsideTemperatureReadings = _sensorReadingService.getTemperatureReadingsFromDevice(getMaxDifferenceDTO.getDeviceIDIndoor(), getMaxDifferenceDTO.getStartTime(), getMaxDifferenceDTO.getEndTime());
        //List<SensorReading> insideReadings = SensorReadingMapper.DTOsToSensorReading(allInsideTemperatureReadingsDTO);

        List<SensorReading> allOutsideTemperatureReadings = _sensorReadingService.getTemperatureReadingsFromDevice(getMaxDifferenceDTO.getDeviceIDOutdoor(), getMaxDifferenceDTO.getStartTime(), getMaxDifferenceDTO.getEndTime());
        //List<SensorReading> outsideReadings = SensorReadingMapper.DTOsToSensorReading(allOutsideTemperatureReadingsDTO);

        int deltaTime = getMaxDifferenceDTO.getDeltaTime();

        return _sensorReadingService.getDifferenceBetweenReadings(allInsideTemperatureReadings, allOutsideTemperatureReadings, deltaTime);

    }


}
