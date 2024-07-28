import React, { useState } from 'react';
import { addDevice } from '../services/DeviceService.jsx';
import '../styles/PopupForm.css'; // Import the CSS file for popup form
import AddDeviceButton from './AddDeviceButton.jsx';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

/**
 * `AddDeviceForm` is a React component that renders a form to add a new device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.roomId - The ID of the room where the device is to be added.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <AddDeviceForm roomId="1234" />
 *
 * @returns {React.Element} Returns a React element that renders a form. When this form is submitted, it calls the `addDevice` function from the DeviceService with the form data.
 */

// Functional component AddDeviceForm responsible for rendering the form to add a new device.
const AddDeviceForm = ({ roomId }) => {
    // State to hold the device data
    const [device, setDevice] = useState({ roomId: roomId, deviceName: '', deviceModel: '', activationStatus: true });
    const [error, setError] = useState(null); // State to hold any error message
    const [formIsVisible, setFormIsVisible] = useState(false); // State to control the visibility of the form

    // Handle changes in text input fields
// Handle form submission
    const handleSubmit = async (formData) => {
        try {
            const newDevice = await addDevice(formData); // Call the service function to add the device
            console.log('Device added:', newDevice); // Log the newly added device
            toast.success('Device added successfully!', { autoClose: 3000 }); // Display success toast
            window.location.reload(); // Refresh the page so the newly added device is visible
            setFormIsVisible(false); // Hide the form
        } catch (error) {
            setError(error); // Set the error state if there is an error
            toast.error(`Failed to add device: ${error.response ? error.response.data.message : error.message}`, { autoClose: 3000 }); // Display error toast
        }
    };

    // Popup form component to add a new device
    const PopupAddDeviceForm = ({ isOpen, onRequestClose, onSubmit, initialFormData }) => {
        const [formData, setFormData] = useState(initialFormData); // Local state for form data

        // Handle changes in text input fields
        const handleInputChange = (e) => {
            const { name, value } = e.target; // Destructure name and value from the event target
            setFormData(prevFormData => ({ ...prevFormData, [name]: value })); // Update the local state with the new value
        };

        //Handle form submission
        const handleSubmit = (e) => {
        e.preventDefault(); // Prevent the default form submission behavior
        onSubmit(formData); // Call the onSubmit function passed from the parent component with the local form data
        };

        if (!isOpen) return null;
        return (
            <div className="popup-overlay">
                <div className="popup">
                    <h2>Add new Device</h2>
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <div className="popup1"><h3>Device Name</h3></div>
                            <input
                                type="text"
                                name="deviceName" // Corrected name attribute
                                placeholder="Device Name"
                                value={formData.deviceName}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <div className="popup1"><h3>Device Model</h3></div>
                            <input
                                type="text"
                                name="deviceModel" // Corrected name attribute
                                placeholder="Device Model"
                                value={formData.deviceModel}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="button-container">
                            <button type="submit" className="save-button">Save Device</button>
                            <button onClick={onRequestClose} className="close-button">Close</button>
                        </div>
                    </form>
                </div>
            </div>
        );
    };

    return (
        <div>
            <ToastContainer />
            <p></p>
            <AddDeviceButton onAddDevice={() => setFormIsVisible(true)} />
            <PopupAddDeviceForm
                isOpen={formIsVisible}
                onRequestClose={() => setFormIsVisible(false)}
                onSubmit={handleSubmit}
                initialFormData={device}
            />
            {error && <div>Error: {error.message}</div>}
        </div>
    );
};

export default AddDeviceForm;