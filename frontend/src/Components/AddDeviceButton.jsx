import React from 'react';
import '../styles/AddRoomButton.css';

/**
 * `AddDeviceButton` is a React component that renders a button to add a new device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Function} props.onAddDevice - The function to be called when the button is clicked.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <AddDeviceButton onAddDevice={handleAddDevice} />
 *
 * @returns {React.Element} Returns a React element that renders a button. When this button is clicked, it calls the `onAddDevice` function passed as a prop.
 */

// Functional component AddDeviceButton responsible for rendering the add device button.
const AddDeviceButton = ({ onAddDevice }) => {
    return (
        <button className="add-room-button" onClick={onAddDevice}>
            Add Device
        </button>
    );
};

export default AddDeviceButton;
