import React, { useEffect, useState } from 'react';
import { getSensorById} from "../services/SensorService.jsx";

import '../styles/AttributeCard.css';

const SensorAttributes = ({ sensorId }) => {
    const [attributes, setAttributes] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSensorAttributes = async () => {
            try {
                setLoading(true);
                const response = await getSensorById(sensorId);
                setAttributes(response.data);



                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch sensor attributes:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchSensorAttributes();
    }, [sensorId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="attribute-card-sensor-actuator">
            <ul>
                <li>{attributes.sensorModelID?.toString() || 'N/A'}</li>
            </ul>
        </div>
    );
};

export default SensorAttributes;
