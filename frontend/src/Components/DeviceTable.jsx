import React from 'react';
import '../styles/DeviceTable.css';
import PopupForm from './PopupForm';
import SensorAttributes from './SensorAttributes';
import ActuatorAttributes from './ActuatorAttributes';
import BlindRollerControl from './BlindRollerControl';

/**
 * `DeviceTable` is a React component that renders a table of devices, either sensors or actuators.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.title - The title of the table.
 * @param {Array} props.items - An array of device objects to be displayed in the table.
 * @param {boolean} props.isFormVisible - A boolean indicating whether the form for adding a new device is visible.
 * @param {Function} props.setIsFormVisible - The function to be called to set the visibility of the form.
 * @param {Function} props.addForm - The function to be called to add a new device.
 * @param {Object} props.newFormData - The data for the new device to be added.
 * @param {Function} props.handleInputChange - The function to be called when the input in the form changes.
 * @param {boolean} props.isDisabled - A boolean indicating whether the table is disabled.
 * @param {Function} props.increasePercentage - The function to be called to increase the percentage of a device.
 * @param {Function} props.decreasePercentage - The function to be called to decrease the percentage of a device.
 * @param {Function} props.handlePercentageChange - The function to be called when the percentage changes.
 * @param {Array} props.sensorTypes - An array of sensor types.
 * @param {Array} props.sensorModels - An array of sensor models.
 * @param {Function} props.handleSensorTypeChange - The function to be called when the sensor type changes.
 * @param {Array} props.actuatorTypes - An array of actuator types.
 * @param {Array} props.actuatorModels - An array of actuator models.
 * @param {Function} props.handleActuatorTypeChange - The function to be called when the actuator type changes.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <DeviceTable title="Sensors" items={sensors} isFormVisible={isFormVisible} setIsFormVisible={setIsFormVisible} addForm={addSensor} newFormData={newSensorData} handleInputChange={handleInputChange} isDisabled={isDisabled} increasePercentage={increasePercentage} decreasePercentage={decreasePercentage} handlePercentageChange={handlePercentageChange} sensorTypes={sensorTypes} sensorModels={sensorModels} handleSensorTypeChange={handleSensorTypeChange} actuatorTypes={actuatorTypes} actuatorModels={actuatorModels} handleActuatorTypeChange={handleActuatorTypeChange} />
 *
 * @returns {React.Element} Returns a React element that renders a table of devices. Each row in the table represents a device, with columns for the device attributes and controls. If there are no devices, it displays a message saying "No data available". It also renders a `PopupForm` component for adding a new device.
 */

// DeviceTable component
const DeviceTable = ({
                         title,
                         items,
                         isFormVisible,
                         setIsFormVisible,
                         addForm,
                         newFormData,
                         handleInputChange,
                         isDisabled,
                         increasePercentage,
                         decreasePercentage,
                         handlePercentageChange,
                         sensorTypes,
                         sensorModels,
                         handleSensorTypeChange,
                         actuatorTypes,
                         actuatorModels,
                         handleActuatorTypeChange
                     }) => {
    // Function to render a custom control for a device
    const renderCustomControl = (item) => {
        if (item.type === 'Blind Roller') {
            return (
                <BlindRollerControl
                    percentage={item.percentage}
                    increase={() => increasePercentage && increasePercentage(item.id)}
                    decrease={() => decreasePercentage && decreasePercentage(item.id)}
                    handlePercentageChange={(e) => handlePercentageChange && handlePercentageChange(e, item.id)}
                />
            );
        }
        return null;
    };

    return (
        <div className={`table-wrapper ${isDisabled ? 'disabled' : ''}`}>
            <table className="device-table">
                <thead>
                <tr>
                    <th>{title}
                        <button
                            className="add-button-table"
                            onClick={() => setIsFormVisible && setIsFormVisible(true)}
                            disabled={isDisabled}
                        >
                            +
                        </button>
                    </th>
                </tr>
                </thead>
                <tbody>
                {items.length > 0 ? (
                    items.map((item) => (
                        <tr key={item.sensorId || item.actuatorId || item.id}>
                            <td>
                                {title === 'Sensors' ? (
                                    <SensorAttributes sensorId={item.sensorId} />
                                ) : (
                                    <>
                                        <ActuatorAttributes actuatorId={item.actuatorId} />
                                        {renderCustomControl(item)}
                                    </>
                                )}
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td>No data available</td>
                    </tr>
                )}
                </tbody>
            </table>
            {isFormVisible && (
                <PopupForm
                    isOpen={isFormVisible}
                    onRequestClose={() => setIsFormVisible(false)}
                    type={title.slice(0, -1)} // Assuming title is 'Sensors' or 'Actuators'
                    onSubmit={addForm}
                    formData={newFormData}
                    handleInputChange={handleInputChange}
                    sensorTypes={sensorTypes}
                    sensorModels={sensorModels}
                    handleSensorTypeChange={handleSensorTypeChange}
                    actuatorTypes={actuatorTypes}
                    actuatorModels={actuatorModels}
                    handleActuatorTypeChange={handleActuatorTypeChange}
                />
            )}
        </div>
    );
};

export default DeviceTable;
