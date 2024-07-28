import React, { useEffect, useState } from 'react';
import { getSensorReadingsByDevice } from '../services/DeviceService';
import DeviceReadingsTable from './DeviceReadingsTable';

/**
 * `DeviceReadings` is a React component that fetches and displays the sensor readings of a device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.deviceId - The ID of the device whose sensor readings are to be fetched.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <DeviceReadings deviceId="1234" />
 *
 * @returns {React.Element} Returns a React element that displays the sensor readings of a device. If the readings are still being fetched, it displays a loading message. If there was an error fetching the readings, it displays an error message. Otherwise, it displays a `DeviceReadingsTable` component with the fetched readings.
 */

// DeviceReadings component
const DeviceReadings = ({ deviceId }) => {
    const [readings, setReadings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Fetch sensor readings for the device
        const fetchReadings = async () => {
            try {
                setLoading(true);
                // Call the `getSensorReadingsByDevice` function from the `DeviceService` module
                const response = await getSensorReadingsByDevice(deviceId);
                setReadings(response.data);
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch sensor readings:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchReadings();
    }, [deviceId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return <DeviceReadingsTable title="Device Readings" items={readings} />;
};

export default DeviceReadings;
