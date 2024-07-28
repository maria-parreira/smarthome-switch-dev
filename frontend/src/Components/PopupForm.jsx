import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Import axios
import '../styles/PopupForm.css';
import { addActuatorToDevice } from '../services/ActuatorService.jsx';
import { addSensorToDevice } from '../services/SensorService.jsx';
import {toast, ToastContainer} from 'react-toastify';

/**
 * `PopupForm` is a React component that renders a form in a popup for adding a new sensor or actuator to a device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {boolean} props.isOpen - A boolean indicating whether the popup is open.
 * @param {Function} props.onRequestClose - The function to be called to close the popup.
 * @param {string} props.type - The type of the device to be added ('Sensor' or 'Actuator').
 * @param {string} props.deviceId - The ID of the device to which a sensor or actuator is to be added.
 * @param {Object} props.formData - The data for the new sensor or actuator to be added.
 * @param {Function} props.handleInputChange - The function to be called when the input in the form changes.
 * @param {Array} props.sensorTypes - An array of sensor types.
 * @param {Array} props.sensorModels - An array of sensor models.
 * @param {Function} props.handleSensorTypeChange - The function to be called when the sensor type changes.
 * @param {Array} props.actuatorTypes - An array of actuator types.
 * @param {Array} props.actuatorModels - An array of actuator models.
 * @param {Function} props.handleActuatorTypeChange - The function to be called when the actuator type changes.
 * @param {Function} props.refreshSensorsAndActuators - The function to be called to refresh the sensors and actuators.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <PopupForm isOpen={isPopupOpen} onRequestClose={closePopup} type="Sensor" deviceId={deviceId} formData={newSensorData} handleInputChange={handleInputChange} sensorTypes={sensorTypes} sensorModels={sensorModels} handleSensorTypeChange={handleSensorTypeChange} actuatorTypes={actuatorTypes} actuatorModels={actuatorModels} handleActuatorTypeChange={handleActuatorTypeChange} refreshSensorsAndActuators={refreshSensorsAndActuators} />
 *
 * @returns {React.Element} Returns a React element that renders a form in a popup for adding a new sensor or actuator to a device. The form includes select inputs for the type and model of the sensor or actuator, and submit and close buttons. When the form is submitted, it calls the appropriate function to add the sensor or actuator to the device, and then refreshes the sensors and actuators. When the close button is clicked, it calls the `onRequestClose` function passed as a prop.
 */

// PopupForm component
const PopupForm = ({
                       isOpen,
                       onRequestClose,
                       type,
                       deviceId,
                       formData,
                       handleInputChange,
                       sensorTypes = [],
                       sensorModels = [],
                       handleSensorTypeChange,
                       actuatorTypes = [],
                       actuatorModels = [],
                       handleActuatorTypeChange,
                       refreshSensorsAndActuators
                   }) => {
    // State variables
    const [sensorTypeDescriptions, setSensorTypeDescriptions] = useState([]);
    // State variable to store the descriptions of actuator types
    const [actuatorTypeDescriptions, setActuatorTypeDescriptions] = useState([]);

    useEffect(() => {
        if (type === 'Sensor') {
            // Function to fetch the descriptions of sensor types
            const fetchSensorTypeDescriptions = async () => {
                // Fetch the descriptions of sensor types
                const descriptions = await Promise.all(
                    sensorTypes.map(async (type) => {
                        // Fetch the description of a sensor type
                        const response = await axios.get(`http://localhost:8080/api/v1/sensor-types/${type.sensorTypeID}`);
                        return response.data.description;
                    })
                );
                setSensorTypeDescriptions(descriptions);
            };
            fetchSensorTypeDescriptions();
        } else if (type === 'Actuator') {
            // Function to fetch the descriptions of actuator types
            const fetchActuatorTypeDescriptions = async () => {
                // Fetch the descriptions of actuator types
                const descriptions = await Promise.all(
                    actuatorTypes.map(async (type) => {
                        // Fetch the description of an actuator type
                        const response = await axios.get(`http://localhost:8080/api/v1/actuator-types/${type.actuatorTypeID}`);
                        return response.data.description;
                    })
                );
                setActuatorTypeDescriptions(descriptions);
            };
            fetchActuatorTypeDescriptions();
        }
    }, [sensorTypes, actuatorTypes, type]);

    if (!isOpen) return null;

    // Function to handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (type === 'Sensor') {
                // Add the sensor to the device
                const response = await addSensorToDevice({
                    sensorModelID: formData.model,
                    deviceID: deviceId
                });
                toast.success('Sensor added successfully!', { autoClose: 3000 } );
            } else if (type === 'Actuator') {
                // Add the actuator to the device
                const response = await addActuatorToDevice({
                    actuatorModelID: formData.model,
                    deviceID: deviceId
                });
                toast.success('Actuator added successfully!', { autoClose: 3000 });
            }
            await refreshSensorsAndActuators();
            onRequestClose();
        } catch (error) {
            console.error(`Failed to add ${type.toLowerCase()}:`, error);
            toast.error(`Failed to add ${type}`, { autoClose: 3000 });
        }
    };

    return (
        <div className="popup-overlay">
            <ToastContainer autoClose={3000} />
            <div className="popup">
                <h2>Add new {type}</h2>
                <form onSubmit={handleSubmit}>
                    <select name="type" value={formData.type}
                            onChange={type === 'Sensor' ? handleSensorTypeChange : handleActuatorTypeChange} required>
                        <option value="">Select {type} Type</option>
                        {type === 'Sensor'
                            ? sensorTypes.map((type, index) => (
                                <option key={type.sensorTypeID}
                                        value={type.sensorTypeID}>{sensorTypeDescriptions[index]}</option>
                            ))
                            : actuatorTypes.map((type, index) => (
                                <option key={type.actuatorTypeID}
                                        value={type.actuatorTypeID}>{actuatorTypeDescriptions[index]}</option>
                            ))}
                    </select>
                    <select name="model" value={formData.model} onChange={handleInputChange} required>
                        <option value="">Select {type} Model</option>
                        {type === 'Sensor'
                            ? sensorModels.map((model) => (
                                <option key={model.sensorModelID}
                                        value={model.sensorModelID}>{model.sensorModelID}</option>
                            ))
                            : actuatorModels.map((model) => (
                                <option key={model.actuatorModelID}
                                        value={model.actuatorModelID}>{model.actuatorModelID}</option>
                            ))}
                    </select>
                    <div className="button-container">
                        <button type="submit" className="save-device-button">Save {type}</button>
                        <button type="button" onClick={onRequestClose} className="close-button">Close</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default PopupForm;