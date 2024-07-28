import React, { useEffect, useState } from 'react';
import { getSensorReadingByID } from '../services/SensorReadingService.jsx';
import { getSensorById } from '../services/SensorService.jsx';
import '../styles/DeviceDetails.css';

const SensorReadingAttributes = ({ sensorReadingId, attribute }) => {
    const [value, setValue] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSensorReadingAttributes = async () => {
            try {
                setLoading(true);
                const response = await getSensorReadingByID(sensorReadingId);
                const sensorID = response.data.sensorID;

                if (attribute === 'sensorModelID') {
                    const sensorResponse = await getSensorById(sensorID);
                    setValue(sensorResponse.data.sensorModelID);
                } else {
                    setValue(response.data[attribute]);
                }

                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch sensor reading attributes:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchSensorReadingAttributes();
    }, [sensorReadingId, attribute]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return <>{value || 'N/A'}</>;
};

export default SensorReadingAttributes;
