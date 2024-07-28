import React, { useState, useEffect } from 'react';
import '../styles/DeviceTable.css';
import { updateRollerBlindValue } from '../services/ActuatorService';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

/**
 * `BlindRollerTable` is a React component that renders a table for controlling a blind roller.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Array} props.actuators - An array of actuator objects. Each object should have an `actuatorId` property.
 * @param {Array} props.sensors - An array of sensor objects. Each object should have a `sensorId` property.
 * @param {Array} props.sensorReadings - An array of sensor reading objects. Each object should have a `sensorID`, `reading`, and `timestamp` property.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <BlindRollerTable actuators={actuators} sensors={sensors} sensorReadings={sensorReadings} />
 *
 * @returns {React.Element} Returns a React element that renders a table for controlling a blind roller. The table includes a dropdown for selecting an actuator and a sensor, a slider for setting the percentage, and a submit button for sending the updated value to the server. When the submit button is clicked, it calls the `updateRollerBlindValue` function from the ActuatorService with the selected actuator ID, sensor ID, and percentage.
 */

// BlindRollerTable component
const BlindRollerTable = ({ actuators, sensors, sensorReadings }) => {
    const [selectedActuatorID, setSelectedActuatorID] = useState(actuators[0]?.actuatorId || '');
    const [selectedSensorID, setSelectedSensorID] = useState(sensors[0]?.sensorId || '');
    const [percentage, setPercentage] = useState(50); // Default value
    const [isLoading, setIsLoading] = useState(false);

    // Update percentage based on latest reading for the selected sensor
    useEffect(() => {
        const updatePercentage = () => {
            if (sensorReadings && selectedSensorID) {
                const latestReading = sensorReadings
                    .filter(reading => reading.sensorID === selectedSensorID)
                    .sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp))[0];
                if (latestReading && typeof latestReading.reading === 'number') {
                    setPercentage(latestReading.reading);
                } else {
                    setPercentage(50); // Default value if no reading found
                }
            }
        };

        updatePercentage();
    }, [selectedSensorID, sensorReadings]);

    // Handle slider change
    const handleSliderChange = (e) => {
        setPercentage(parseInt(e.target.value, 10));
    };

    // Handle submit
    const handleSubmit = async () => {
        setIsLoading(true);
        try {
            // Sending patch request to update the roller blind value
            await updateRollerBlindValue(selectedActuatorID, {
                actuatorID: selectedActuatorID,
                sensorID: selectedSensorID,
                inputValue: percentage,
            });
            toast.success('Value updated successfully!', {
                onClose: () => window.location.reload() // Refresh the page when the toast closes
            });
        } catch (error) {
            console.error('Failed to update roller blind value:', error);
            toast.error('Failed to update value');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="table-wrapper">
            <ToastContainer />
            <table className="device-table">
                <thead>
                <tr>
                    <th>Blind Roller Control</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <label>Select Actuator:</label>
                        <select
                            value={selectedActuatorID}
                            onChange={(e) => setSelectedActuatorID(e.target.value)}
                        >
                            {actuators.map((actuator) => (
                                <option key={actuator.actuatorId} value={actuator.actuatorId}>
                                    {'OPNCL0100'} {/* Display the actuator ID */}
                                </option>
                            ))}
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Select Sensor:</label>
                        <select
                            value={selectedSensorID}
                            onChange={(e) => setSelectedSensorID(e.target.value)}
                        >
                            {sensors.map((sensor) => (
                                <option key={sensor.sensorId} value={sensor.sensorId}>
                                    {'CAP200'}
                                </option>
                            ))}
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Percentage:</label>
                        <input
                            type="range"
                            min="0"
                            max="100"
                            value={percentage}
                            onChange={handleSliderChange}
                        />
                        <span>{percentage}%</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button onClick={handleSubmit} disabled={isLoading}>
                            {isLoading ? 'Submitting...' : 'Submit'}
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );
};

export default BlindRollerTable;
