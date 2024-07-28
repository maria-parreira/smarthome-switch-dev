import React from 'react';
import '../styles/DeactivateDeviceButton.css';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { deactivateDevice } from '../services/DeviceService.jsx'; // Import the deactivateDevice function

/**
 * `DeactivateDeviceButton` is a React component that renders a button which can deactivate a device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {boolean} props.isDeviceOn - A boolean indicating whether the device is on or off.
 * @param {Function} props.toggleDeviceStatus - The function to be called when the button is clicked.
 * @param {string} props.deviceId - The ID of the device.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <DeactivateDeviceButton isDeviceOn={true} toggleDeviceStatus={handleToggleDeviceStatus} deviceId="1234" />
 *
 * @returns {React.Element} Returns a React element that renders a button. When this button is clicked, it calls the `toggleDeviceStatus` function passed as a prop and deactivates the device in the backend.
 */

// DeactivateDeviceButton component
const DeactivateDeviceButton = ({ isDeviceOn, toggleDeviceStatus, deviceId }) => { // Add deviceId as a prop

    // Handle slide
    const handleSlide = () => {
        if (isDeviceOn) {
            toast((t) => (
                <div>
                    <div>
                        Are you sure you want to deactivate the device?
                    </div>
                    <div>
                        <button onClick={async () => {
                            toast.dismiss(t.id);
                            toggleDeviceStatus(); // Call the function to change the device's status
                            await deactivateDevice(deviceId); // Call the function to deactivate the device in the backend
                        }}>
                            Yes
                        </button>
                        <button onClick={() => {
                            toast.dismiss(t.id); // Dismiss the toast notification
                        }}>
                            No
                        </button>
                    </div>
                </div>
            ), {
                position: "top-center", // Center the toast horizontally
                autoClose: false,
                closeOnClick: false,
                pauseOnHover: true,
                draggable: true,
                toastId: "confirmDeactivate",
            });
        }
    };

    return (
        <div className="slide-button-container">
            <ToastContainer/>
            {isDeviceOn && <span className="activated-label">Activated</span>}
            <label className="switch">
                <input type="checkbox" checked={isDeviceOn} onChange={handleSlide}/>
                <span className="slider round"></span>
            </label>
            {!isDeviceOn && <span className="deactivated-label">Deactivated</span>}
        </div>
    );
}

export default DeactivateDeviceButton;
