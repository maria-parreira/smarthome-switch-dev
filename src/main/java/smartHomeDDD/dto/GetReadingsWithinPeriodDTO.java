package smartHomeDDD.dto;

import java.sql.Timestamp;

/**
 * This class represents a GetReadingsWithinPeriodDTO Data Transfer Object.
 * It contains the unique identifier of the device, the start time and the end time of the readings.
 */
public class GetReadingsWithinPeriodDTO {

    /**
     * The unique identifier of the device.
     */
    private final String _deviceID;

    /**
     * The start time of the readings.
     */
    private final Timestamp _startTime;

    /**
     * The end time of the readings.
     */
    private final Timestamp _endTime;


    /**
     * Constructor for the GetReadingsWithinPeriodDTO class
     * @param deviceID  The unique identifier of the device.
     * @param startTime The start time of the sensor reading.
     * @param endTime The end time of the sensor reading.
     */
    public GetReadingsWithinPeriodDTO(String deviceID, Timestamp startTime, Timestamp endTime)
    {
        this._deviceID = deviceID;
        this._startTime = startTime;
        this._endTime = endTime;
    }

    /**
     * Retrieves the unique identifier of the device.
     */

    public String getDeviceID()
    {
        return _deviceID;
    }

    /**
     * Retrieves the start time of the readings.
     */
    public Timestamp getStartTime()
    {
        return _startTime;
    }

    /**
     * Retrieves the end time of the readings.
     */
    public Timestamp getEndTime()
    {
        return _endTime;
    }
}
