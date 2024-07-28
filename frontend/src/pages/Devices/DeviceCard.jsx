import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/DeviceCard.css';
import deviceImage from '../../assets/device.jpg';
import { getDeviceNameById } from '../../services/DeviceService.jsx';

/**
 * DeviceCard is a functional component that renders a device card.
 * It uses the useState and useEffect hooks to manage the state of the device name.
 * It also uses the useNavigate hook from react-router-dom to navigate to the device details page.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Object} props.device - The device object.
 * @example
 * return (
 *   <DeviceCard device={device} />
 * )
 */
const DeviceCard = ({ device }) => {
    /**
     * deviceName is a state variable that holds the name of the device. setDeviceName is the corresponding setter function.
     * @type {string}
     */
    const [deviceName, setDeviceName] = useState('');
    /**
     * navigate is a function that is used to navigate to different routes.
     * @type {function}
     */
    const navigate = useNavigate();

    useEffect(() => {
        /**
         * fetchDeviceName is an asynchronous function that fetches the name of the device.
         * It sends a request to the server to get the device name by the device id,
         * and then sets the deviceName state variable with the fetched name.
         * If an error occurs, it logs the error to the console.
         */
        const fetchDeviceName = async () => {
            try {
                const name = await getDeviceNameById(device.id);
                setDeviceName(name);
            } catch (error) {
                console.error('Failed to fetch device name:', error);
                // Handle error appropriately in your component
            }
        };

        fetchDeviceName();
    }, [device.id]);

    /**
     * navigateToDeviceDetailsPage is a function that navigates to the device details page.
     */
    const navigateToDeviceDetailsPage = () => {
        navigate(`/devices/${device.id}/details`);
    };

    /**
     * The component returns a div element that contains an image and the device name.
     * When the div is clicked, it navigates to the device details page.
     */
    return (
        <div className="device-card" onClick={navigateToDeviceDetailsPage}>
            <img src={deviceImage} alt="Device" className="device-image" />
            <h3 className="device-name">{deviceName}</h3>
        </div>
    );
};

export default DeviceCard;
