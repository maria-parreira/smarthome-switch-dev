import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/EditDevice.css';

/**
 * EditDevice is a functional component that allows the user to edit a device's details.
 * It uses React's useState hook to manage the device's state and the useNavigate hook from react-router-dom to navigate between pages.
 *
 * @component
 * @example
 * return (
 *   <EditDevice />
 * )
 */
const EditDevice = () => {
    /**
     * useNavigate hook from react-router-dom is used for navigation.
     */
    const navigate = useNavigate();
    /**
     * useState hook is used to manage the state of the device.
     * The initial state is an object with empty strings for name, description, and image.
     */
    const [device, setDevice] = useState({ name: '', description: '', image: '' });

    /**
     * handleInputChange is a function that updates the device's state when the input fields change.
     * It uses the event object (e) to get the name and value of the input field that triggered the change.
     *
     * @param {Object} e - The event object
     */
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setDevice({ ...device, [name]: value });
    };

    /**
     * handleSubmit is a function that handles the form submission.
     * It prevents the default form submission, logs the updated device to the console, and navigates back to the previous page.
     * @param {Object} e - The event object
     */
    const handleSubmit = (e) => { // volta para a pagina anterior mas nao atualiza o estado
        e.preventDefault();
        console.log('Device updated:', device);
        navigate(-1);
    };

    /**
     * The component returns a form that allows the user to edit the device's details.
     * The form includes input fields for the device's name and description, and a submit button.
     */
    return (
        <div className="edit-device">
            <h1>Edit Device</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="name"
                    placeholder="Device Name"
                    value={device.name}
                    onChange={handleInputChange}
                    required
                />
                <p></p>
                <input
                    type="text"
                    name="description"
                    placeholder="Device Description"
                    value={device.description}
                    onChange={handleInputChange}
                    required
                />
                <p></p>
                <button type="submit" className="edit-device-button">Update Device</button>
            </form>
        </div>
    );
};

export default EditDevice;
