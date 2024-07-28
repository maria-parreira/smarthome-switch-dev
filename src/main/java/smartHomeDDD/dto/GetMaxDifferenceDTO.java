package smartHomeDDD.dto;

import java.sql.Timestamp;

/**
 * This class represents a GetMaxDifferenceDTO Data Transfer Object.
 */
public class GetMaxDifferenceDTO {

    /**
     * The unique identifier of the indoor device.
     */
    private final String _deviceIDIndoor;

    /**
     * The unique identifier of the outdoor device.
     */
    private final String _deviceIDOutdoor;
    /**
     * The time interval between readings.
     */
    private final int _deltaTime;

    /**
     * The start time of the readings.
     */
    private final Timestamp _startTime;

    /**
     * The end time of the readings.
     */
    private final Timestamp _endTime;

    /**
     * Constructor for the GetMaxDifferenceDTO class
     * @param deviceIDIndoor The unique identifier of the indoor device.
     * @param deviceIDOutdoor The unique identifier of the outdoor device.
     * @param deltaTime The time interval between readings.
     * @param startTime The start time of the sensor reading.
     * @param endTime The end time of the sensor reading.
     */
    public GetMaxDifferenceDTO(String deviceIDIndoor, String deviceIDOutdoor, int deltaTime, Timestamp startTime, Timestamp endTime)
    {
        this._deviceIDIndoor = deviceIDIndoor;
        this._deviceIDOutdoor = deviceIDOutdoor;
        this._deltaTime = deltaTime;
        this._startTime = startTime;
        this._endTime = endTime;
    }


    public String getDeviceIDIndoor()
    {
        return _deviceIDIndoor;
    }


    public String getDeviceIDOutdoor()
    {
        return _deviceIDOutdoor;
    }

    /**
     * Retrieves the time interval between readings.
     */
    public int getDeltaTime() {return _deltaTime;}


    /**
     * Retrieves the start time of the sensor reading.
     */
    public Timestamp getStartTime()
    {
        return _startTime;
    }

    /**
     * Retrieves the end time of the sensor reading.
     */
    public Timestamp getEndTime()
    {
        return _endTime;
    }



}
