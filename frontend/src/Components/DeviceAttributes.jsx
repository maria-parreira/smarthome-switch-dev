import React, { useEffect, useState } from 'react';
import { getDeviceById } from '../services/DeviceService.jsx';

/**
 * `DeviceAttributes` is a React component that fetches and displays the attributes of a device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.deviceId - The ID of the device whose attributes are to be fetched.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <DeviceAttributes deviceId="1234" />
 *
 * @returns {React.Element} Returns a React element that displays the attributes of a device. If the attributes are still being fetched, it displays a loading message. If there was an error fetching the attributes, it displays an error message. Otherwise, it displays a list of the device's attributes, including the device ID, room ID, device name, device model, and activation status.
 */

// DeviceAttributes component
const DeviceAttributes = ({ deviceId }) => {
    const [attributes, setAttributes] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDeviceAttributes = async () => {
            try {
                setLoading(true);
                const response = await getDeviceById(deviceId);
                setAttributes(response.data);
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch device attributes:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchDeviceAttributes();
    }, [deviceId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="attribute-card">
            <h3>Device Attributes</h3>
            <ul>
                <li><strong>Device ID:</strong> {attributes.deviceId?.toString() || 'N/A'}</li>
                <li><strong>Room ID:</strong> {attributes.roomId?.toString() || 'N/A'}</li>
                <li><strong>Device Name:</strong> {attributes.deviceName?.toString() || 'N/A'}</li>
                <li><strong>Device Model:</strong> {attributes.deviceModel?.toString() || 'N/A'}</li>
                <li><strong>Activation Status:</strong> {attributes.activationStatus?.toString() || 'N/A'}</li>
            </ul>
        </div>
    );
};

export default DeviceAttributes;
