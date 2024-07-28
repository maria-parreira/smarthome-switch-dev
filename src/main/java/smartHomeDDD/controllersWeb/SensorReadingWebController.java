package smartHomeDDD.controllersWeb;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorReadingID;
import smartHomeDDD.dto.*;
import smartHomeDDD.services.ServiceSensorReading;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
/**
 * This class is responsible for handling the web requests related to the sensor readings.
 * It contains the methods that handle the GET requests to retrieve sensor data.
 */
@RestController
@RequestMapping("/api/v1/sensor-readings")
public class SensorReadingWebController {
    /**
     * The service that handles the sensor readings.
     */
    private final ServiceSensorReading _serviceSensorReading;

    /**
     * Constructor for the SensorReadingWebController class.
     * @param serviceSensorReading The service that handles the sensor readings.
     */
    public SensorReadingWebController(ServiceSensorReading serviceSensorReading) {
        this._serviceSensorReading = serviceSensorReading;
    }

    /**
     * This method is responsible for handling GET requests to retrieve sensor data.
     * It can either return the maximum temperature difference between two devices within a given period,
     * or all the sensor readings from a device within a given period, based on the provided parameters.
     *
     * @param deviceID The ID of the device for which the sensor readings are to be retrieved.
     * @param startTimeString The start time of the period for which the sensor readings are to be retrieved.
     * @param endTimeString The end time of the period for which the sensor readings are to be retrieved.
     * @param deviceIDIndoorString (Optional) The ID of the indoor device for which the maximum temperature difference is to be calculated.
     * @param deviceIDOutdoorString (Optional) The ID of the outdoor device for which the maximum temperature difference is to be calculated.
     * @param deltaTime (Optional) The time interval for which the maximum temperature difference is to be calculated.
     * @return A ResponseEntity containing either a MaxDifferenceWebDTO object with the maximum temperature difference,
     *         or a list of SensorReadingWebDTO objects with the sensor readings from the device,
     *         and an HTTP status code.
     * @throws DataIntegrityViolationException if there is an issue with the data integrity.
     */
    @GetMapping("")
    public ResponseEntity<?> getSensorReadings(
            @RequestParam(value = "deviceID", required = false) String deviceID,
            @RequestParam(value = "startTimeString", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTimeString,
            @RequestParam(value = "endTimeString", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTimeString,
            @RequestParam(value = "deviceIDIndoor", required = false) String deviceIDIndoorString,
            @RequestParam(value = "deviceIDOutdoor", required = false) String deviceIDOutdoorString,
            @RequestParam(value = "deltaTime", required = false) String deltaTime) {

        try {
            Timestamp startTime = Timestamp.valueOf(startTimeString);
            Timestamp endTime = Timestamp.valueOf(endTimeString);

            if (deviceIDIndoorString != null && deviceIDOutdoorString != null && deltaTime != null) {       // If the request is to get the maximum difference between a device and the outside, in a given period.
                List<SensorReading> allInsideTemperatureReadings = _serviceSensorReading.getTemperatureReadingsFromDevice(deviceIDIndoorString, startTime, endTime);
                List<SensorReading> allOutsideTemperatureReadings = _serviceSensorReading.getTemperatureReadingsFromDevice(deviceIDOutdoorString, startTime, endTime);

                int maxDifference = _serviceSensorReading.getDifferenceBetweenReadings(allInsideTemperatureReadings, allOutsideTemperatureReadings, Integer.parseInt(deltaTime));

                MaxDifferenceWebDTO maxDifferenceWebDTO = MaxDifferenceWebDTO.domainToDTO(maxDifference);

                return new ResponseEntity<>(maxDifferenceWebDTO, HttpStatus.OK);

            } else {    // If the request is to get all the readings of a device in a given period.
                DeviceId id = DeviceMapper.DTOToDeviceId(deviceID);

                List<SensorReading> readingsOfDevice = _serviceSensorReading.getMeasurementsFromDeviceWithinPeriod(id, startTime, endTime);

                List<SensorReadingIDExitWebDTO> readingsOfDeviceWebDTO = new ArrayList<>();

                for (SensorReading dto : readingsOfDevice) {
                    SensorReadingIDExitWebDTO sensorReadingWebDTO = SensorReadingMapper.sensorReadingIDToExitWebDTO(dto);

                    Link selfLink = linkTo(SensorReadingWebController.class).slash(sensorReadingWebDTO.getSensorReadingID()).withSelfRel();
                    sensorReadingWebDTO.add(selfLink);
                    readingsOfDeviceWebDTO.add(sensorReadingWebDTO);
                }

                return new ResponseEntity<>(readingsOfDeviceWebDTO, HttpStatus.OK);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET requests to retrieve a specific sensor reading by its ID.
     *
     * @param id The ID of the sensor reading to be retrieved.
     * @return A ResponseEntity containing a SensorReadingWebDTO object with the sensor reading data,
     *         and an HTTP status code.
     * @throws DataIntegrityViolationException if there is an issue with the data integrity.
     */
    @GetMapping("/{sensor-reading-ID}")
    public ResponseEntity<?> getSensorReading(@PathVariable(value = "sensor-reading-ID") String id) {
        try {
            SensorReadingID sensorReadingID = SensorReadingMapper.DTOToSensorReadingID(id);

            SensorReading sensorReading = _serviceSensorReading.getSensorReadingById(sensorReadingID);

            SensorReadingExitWebDTO sensorReadingWebDTO = SensorReadingMapper.sensorReadingToExitWebDTO(sensorReading);

            Link selfLink = linkTo(SensorReadingWebController.class).slash(sensorReadingWebDTO.getSensorReadingID()).withSelfRel();
            sensorReadingWebDTO.add(selfLink);

            return new ResponseEntity<>(sensorReadingWebDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}