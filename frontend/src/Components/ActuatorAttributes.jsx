import React, { useEffect, useState } from 'react';
import { getActuatorById} from "../services/ActuatorService.jsx";
import '../styles/AttributeCard.css';

/**
 * `ActuatorAttributes` is a React component that fetches and displays the attributes of a specific actuator.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.actuatorId - The ID of the actuator whose attributes are to be fetched and displayed.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <ActuatorAttributes actuatorId="1234" />
 *
 * @returns {React.Element} Returns a React element that displays the attributes of the actuator.
 * If the attributes are still being fetched, it displays a loading message.
 * If there was an error in fetching the attributes, it displays an error message.
 */
const ActuatorAttributes = ({ actuatorId }) => {
    const [attributes, setAttributes] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!actuatorId) {
            console.error('No actuatorId provided');
            return;
        }

        const fetchActuatorAttributes = async () => {
            try {
                setLoading(true);
                const response = await getActuatorById(actuatorId);
                setAttributes(response.data);
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch actuator attributes:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchActuatorAttributes();
    }, [actuatorId]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="attribute-card-sensor-actuator">
            <ul>
                <li>{attributes.actuatorModelID?.toString() || 'N/A'}</li>
            </ul>
        </div>
    );
};

export default ActuatorAttributes;
