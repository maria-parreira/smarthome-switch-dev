import React, { useEffect, useState } from 'react';
import { getDevicesByRoom } from '../services/RoomService.jsx';
import DeviceCard from "../pages/Devices/DeviceCard.jsx";
import '../styles/DevicesList.css';

/**
 * `DevicesList` is a React component that fetches and displays a list of devices in a specific room.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.roomId - The ID of the room whose devices are to be fetched.
 * @param {string} props.refreshKey - A key that when changed, triggers a re-fetch of the devices.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <DevicesList roomId="1234" refreshKey={refreshKey} />
 *
 * @returns {React.Element} Returns a React element that displays a list of devices in a specific room. If the devices are still being fetched, it displays a loading message. If there was an error fetching the devices, it displays an error message. Otherwise, it displays a `DeviceCard` component for each device in the room.
 */

// DevicesList component
const DevicesList = ({ roomId, refreshKey }) => {
    // State variables to store the devices, loading state, and error state
    const [devices, setDevices] = useState([]);
    // const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    // const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Fetch devices for the room
        const fetchDevices = async () => {
            try {
                setLoading(true);
                const response = await getDevicesByRoom(roomId);
                setDevices(response.data); // Assuming the response.data contains the devices array
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch devices:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchDevices(); // Fetch devices on component mount and when refreshKey changes
    }, [roomId, refreshKey]); // Re-fetch when roomId or refreshKey changes

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="devices-list-container">
            {devices.map(device => (
                <DeviceCard key={device.id} device={device} />
            ))}
        </div>
    );
};

export default DevicesList;
