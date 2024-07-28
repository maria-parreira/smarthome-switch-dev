import React from 'react';
import '../styles/DeviceTable.css';
import SensorReadingAttributes from './SensorReadingAttributes';

/**
 * `DeviceReadingsTable` is a React component that renders a table of sensor readings for a device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Array} props.readings - An array of sensor reading objects. Each object should have a `sensorReadingID` property.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <DeviceReadingsTable readings={readings} />
 *
 * @returns {React.Element} Returns a React element that renders a table of sensor readings. Each row in the table represents a sensor reading, with columns for the sensor model, reading value, and timestamp. If there are no readings, it displays a message saying "No readings available".
 */

// DeviceReadingsTable component
const DeviceReadingsTable = ({ readings }) => {
    return (
        <div className="table-wrapper">
            <table className="device-table">
                <thead>
                <tr>
                    <th>Readings</th>
                </tr>
                </thead>
                <tbody>
                {readings.length > 0 ? (
                    readings.map((reading) => (
                        <tr key={reading.sensorReadingID}>
                            <td>
                                Sensor Model: <SensorReadingAttributes
                                sensorReadingId={reading.sensorReadingID}
                                attribute="sensorModelID"
                            />
                                <br/>
                                Value: <SensorReadingAttributes
                                sensorReadingId={reading.sensorReadingID}
                                attribute="reading"
                            />
                                <br/>
                                Timestamp: <SensorReadingAttributes
                                sensorReadingId={reading.sensorReadingID}
                                attribute="timeStamp"
                            />
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="3">No readings available</td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default DeviceReadingsTable;
