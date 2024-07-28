// AttributeCardDevice.jsx
import React from 'react';
import DeactivateDeviceButton from './DeactivateDeviceButton.jsx'; // Import the device deactivation button component.
import '../styles/AttributeCard.css'; // Import the CSS file for styling the device attribute card.

/**
 * `AttributeCardDevice` is a React component that renders a card with a list of device attributes and a button to deactivate the device.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Array} props.attributes - An array of attribute objects. Each object should have an `id`, `name`, and `value` property.
 * @param {Function} props.onEdit - The function to be called when the edit button is clicked.
 * @param {boolean} props.isDeviceOn - A boolean indicating whether the device is on or off.
 * @param {Function} props.toggleDeviceStatus - The function to be called when the deactivate button is clicked.
 * @param {string} props.deviceId - The ID of the device.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <AttributeCardDevice attributes={[{ id: 1, name: 'Temperature', value: '25°C' }, { id: 2, name: 'Humidity', value: '60%' }]} onEdit={handleEdit} isDeviceOn={true} toggleDeviceStatus={handleToggleDeviceStatus} deviceId="1234" />
 *
 * @returns {React.Element} Returns a React element that renders a card with a list of device attributes and a deactivate button. Each attribute is displayed with its name and value. When the deactivate button is clicked, it calls the `toggleDeviceStatus` function passed as a prop.
 */

// Functional component AttributeCardDevice responsible for rendering the device attribute card.
const AttributeCardDevice = ({ attributes, onEdit, isDeviceOn, toggleDeviceStatus, deviceId }) => { // Add deviceId as a prop
    return (
        <div className="attribute-card"> {/* Main container for the attribute card */}
            <div className="attributes"> {/* Container to display device attributes */}
                {attributes.map(attr => (
                    <div key={attr.id} className="attribute"> {/* Render each device attribute */}
                        <span>{attr.name}:</span> {/* Attribute name */}
                        <span>{attr.value}</span> {/* Attribute value */}
                    </div>
                ))}
            </div>
            <div className="buttons"> {/* Container for edit and deactivate buttons */}
                <div className="deactivate">
                    <DeactivateDeviceButton isDeviceOn={isDeviceOn} toggleDeviceStatus={toggleDeviceStatus} deviceId={deviceId} /> {/* Pass deviceId as a prop */}
                </div>
            </div>
        </div>
    );
};

export default AttributeCardDevice; // Export the AttributeCardDevice component for use in other files.