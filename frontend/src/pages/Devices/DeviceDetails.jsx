import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import '../../styles/DeviceDetails.css';
import '../../styles/Devices.css';
import Card from '../../Components/Card';
import AttributeCardDevice from '../../Components/AttributeCardDevice';
import deviceImage from '../../assets/device.jpg';
import DeviceTable from '../../Components/DeviceTable';
import DeviceReadingsTable from '../../Components/DeviceReadingsTable';
import '../../styles/ReactiveButton.css';
import '../../styles/AttributeCard.css';
import MenuButton from '../../Components/MenuButton.jsx';
import { getDeviceById, getSensorsByDevice, getActuatorsByDevice, getSensorReadingsByDevice } from '../../services/DeviceService';
import { getRoomById } from '../../services/RoomService.jsx';
import { getSensorTypes, getSensorTypeByID } from '../../services/SensorTypeService.jsx';
import { getSensorModelsByType } from '../../services/SensorModelService.jsx';
import { getActuatorTypes, getActuatorTypeById } from '../../services/ActuatorTypeService.jsx';
import { getActuatorModelsByType } from '../../services/ActuatorModelService.jsx';
import PopupForm from '../../Components/PopupForm';
import BlindRollerTable from '../../Components/BlindRollerTable';

/**
 * DeviceDetails is a functional component that displays detailed information about a specific device.
 * It fetches data about the device, its associated room, sensors, and actuators from the server.
 * It also allows the user to add new sensors and actuators to the device, and to control the device's activation status.
 * @component
 * @example
 * return (
 *   <DeviceDetails />
 * )
 */
const DeviceDetails = () => {
    /**
     * navigate is a function from the useNavigate hook from react-router-dom used for navigation.
     */
    const navigate = useNavigate();
    /**
     * deviceId is a variable that holds the id of the device. It is obtained from the useParams hook from react-router-dom.
     */
    const { deviceId } = useParams();
    /**
     * device is a state variable that holds the details of the device. setDevice is the corresponding setter function.
     */
    const [device, setDevice] = useState(null);
    /**
     * room is a state variable that holds the details of the room associated with the device. setRoom is the corresponding setter function.
     */
    const [room, setRoom] = useState(null);
    /**
     * sensors is a state variable that holds the list of sensors associated with the device. setSensors is the corresponding setter function.
     */
    const [sensors, setSensors] = useState([]);
    /**
     * actuators is a state variable that holds the list of actuators associated with the device. setActuators is the corresponding setter function.
     */
    const [actuators, setActuators] = useState([]);
    /**
     * sensorReadings is a state variable that holds the list of sensor readings associated with the device. setSensorReadings is the corresponding setter function.
     */
    const [sensorReadings, setSensorReadings] = useState([]);
    /**
     * newSensor is a state variable that holds the details of a new sensor to be added to the device. setNewSensor is the corresponding setter function.
     */
    const [newSensor, setNewSensor] = useState({ model: '', type: '' });
    /**
     * newActuator is a state variable that holds the details of a new actuator to be added to the device. setNewActuator is the corresponding setter function.
     */
    const [newActuator, setNewActuator] = useState({ model: '', type: '' });
    /**
     * isSensorFormVisible is a state variable that determines whether the sensor form is visible or not. setIsSensorFormVisible is the corresponding setter function.
     */
    const [isSensorFormVisible, setIsSensorFormVisible] = useState(false);
    /**
     * isActuatorFormVisible is a state variable that determines whether the actuator form is visible or not. setIsActuatorFormVisible is the corresponding setter function.
     */
    const [isActuatorFormVisible, setIsActuatorFormVisible] = useState(false);
    /**
     * isDeviceOn is a state variable that holds the activation status of the device. setIsDeviceOn is the corresponding setter function.
     */
    const [isDeviceOn, setIsDeviceOn] = useState(true);
    /**
     * sensorTypes is a state variable that holds the list of sensor types. setSensorTypes is the corresponding setter function.
     */
    const [sensorTypes, setSensorTypes] = useState([]);
    /**
     * sensorModels is a state variable that holds the list of sensor models. setSensorModels is the corresponding setter function.
     */
    const [sensorModels, setSensorModels] = useState([]);
    /**
     * actuatorTypes is a state variable that holds the list of actuator types. setActuatorTypes is the corresponding setter function.
     */
    const [actuatorTypes, setActuatorTypes] = useState([]);
    /**
     * actuatorModels is a state variable that holds the list of actuator models. setActuatorModels is the corresponding setter function.
     */
    const [actuatorModels, setActuatorModels] = useState([]);
    /**
     * latestReading is a state variable that holds the latest sensor reading. setLatestReading is the corresponding setter function.
     */
    const [latestReading, setLatestReading] = useState(null);
    /**
     * isBlindRollerTableVisible is a state variable that determines whether the Blind Roller Table is visible or not. setIsBlindRollerTableVisible is the corresponding setter function.
     */
    const [isBlindRollerTableVisible, setIsBlindRollerTableVisible] = useState(false);

    useEffect(() => {

        /**
         * fetchDeviceData is an asynchronous function that fetches data about the device, its associated room, sensors, and actuators from the server.
         * It also fetches the latest sensor readings for the device.
         */
        const fetchDeviceData = async () => {
            try {
                const deviceResponse = await getDeviceById(deviceId);
                setDevice(deviceResponse.data);
                setIsDeviceOn(deviceResponse.data.activationStatus);

                const roomResponse = await getRoomById(deviceResponse.data.roomId);
                setRoom(roomResponse.data);

                const sensorsResponse = await getSensorsByDevice(deviceId);
                setSensors(sensorsResponse.data);

                const actuatorsResponse = await getActuatorsByDevice(deviceId);
                setActuators(actuatorsResponse.data);

                const sensorReadingsResponse = await getSensorReadingsByDevice(deviceId);
                setSensorReadings(sensorReadingsResponse.data);

                if (sensorReadingsResponse.data.length > 0) {
                    setLatestReading(sensorReadingsResponse.data[sensorReadingsResponse.data.length - 1]);
                }
            } catch (error) {
                console.error('Failed to fetch device data:', error);
            }
        };


        /**
         * fetchSensorTypes is an asynchronous function that fetches data about all available sensor types from the server.
         */
        const fetchSensorTypes = async () => {
            try {
                const sensorTypesResponse = await getSensorTypes();
                const sensorTypeDetailsPromises = sensorTypesResponse.data.map(async (type) => {
                    const typeDetailsResponse = await getSensorTypeByID(type.sensorTypeID);
                    return { ...typeDetailsResponse.data, description: type.description };
                });
                const sensorTypeDetails = await Promise.all(sensorTypeDetailsPromises);
                setSensorTypes(sensorTypeDetails);
            } catch (error) {
                console.error('Failed to fetch sensor types:', error);
            }
        };

        /**
         * fetchActuatorTypes is an asynchronous function that fetches data about all available actuator types from the server.
         */
        const fetchActuatorTypes = async () => {
            try {
                const actuatorTypesResponse = await getActuatorTypes();
                const actuatorTypeDetailsPromises = actuatorTypesResponse.data.map(async (type) => {
                    const typeDetailsResponse = await getActuatorTypeById(type.actuatorTypeID);
                    return typeDetailsResponse.data;
                });
                const actuatorTypeDetails = await Promise.all(actuatorTypeDetailsPromises);
                setActuatorTypes(actuatorTypeDetails);
            } catch (error) {
                console.error('Failed to fetch actuator types:', error);
            }
        };

        fetchDeviceData();
        fetchSensorTypes();
        fetchActuatorTypes();
    }, [deviceId]);

    /**
     * toggleDeviceStatus is a function that toggles the activation status of the device.
     */
    const toggleDeviceStatus = () => {
        setIsDeviceOn(!isDeviceOn);
    };

    /**
     * handleSensorInputChange is a function that updates the state of the new sensor form when the input fields change.
     * It uses the event object (e) to get the name and value of the input field that triggered the change.
     * @param {Object} e - The event object
     */
    const handleSensorInputChange = (e) => {
        const { name, value } = e.target;
        setNewSensor({ ...newSensor, [name]: value });
    };


    /**
     * handleSensorTypeChange is an asynchronous function that updates the sensor type in the new sensor form and fetches the corresponding sensor models from the server.
     *
     * @param {Object} e - The event object
     */
    const handleSensorTypeChange = async (e) => {
        const sensorTypeID = e.target.value;
        setNewSensor({ ...newSensor, type: sensorTypeID });
        try {
            const sensorModelsResponse = await getSensorModelsByType(sensorTypeID);
            setSensorModels(sensorModelsResponse.data);
        } catch (error) {
            console.error('Failed to fetch sensor models:', error);
        }
    };


    /**
     * handleActuatorInputChange is a function that updates the state of the new actuator form when the input fields change.
     * It uses the event object (e) to get the name and value of the input field that triggered the change.
     *
     * @param {Object} e - The event object
     */
    const handleActuatorInputChange = (e) => {
        const { name, value } = e.target;
        setNewActuator({ ...newActuator, [name]: value });
    };

    /**
     * handleActuatorTypeChange is an asynchronous function that updates the actuator type in the new actuator form and fetches the corresponding actuator models from the server.
     * @param {Object} e - The event object
     */
    const handleActuatorTypeChange = async (e) => {
        const actuatorTypeID = e.target.value;
        setNewActuator({ ...newActuator, type: actuatorTypeID });
        try {
            const actuatorModelsResponse = await getActuatorModelsByType(actuatorTypeID);
            setActuatorModels(actuatorModelsResponse.data);
        } catch (error) {
            console.error('Failed to fetch actuator models:', error);
        }
    };

    /**
     * addSensor is an asynchronous function that adds a new sensor to the device.
     * It prevents the default form submission, checks if the new sensor has a model and type,
     * and then sends a request to the server to add the sensor to the device.
     * If the request is successful, it updates the state of the sensors and resets the new sensor form.
     * @param {Object} e - The event object
     */
    const addSensor = async (e) => {
        e.preventDefault();
        if (newSensor.model && newSensor.type) {
            try {
                await addSensorToDevice({
                    model: newSensor.model,
                    type: newSensor.type,
                    deviceId: deviceId
                });
                setSensors([...sensors, { ...newSensor, id: sensors.length + 1 }]);
                setNewSensor({ model: '', type: '' });
                setIsSensorFormVisible(false);
            } catch (error) {
                console.error('Failed to add sensor:', error);
            }
        }
    };


    /**
     * addActuator is an asynchronous function that adds a new actuator to the device.
     * It prevents the default form submission, checks if the new actuator has a model and type,
     * and then sends a request to the server to add the actuator to the device.
     * If the request is successful, it updates the state of the actuators and resets the new actuator form.
     * @param {Object} e - The event object
     */
    const addActuator = async (e) => {
        e.preventDefault();
        if (newActuator.model && newActuator.type) {
            const newActuatorWithPercentage = newActuator.type === 'Blind Roller'
                ? { ...newActuator, id: actuators.length + 1, percentage: 0 }
                : { ...newActuator, id: actuators.length + 1 };
            try {
                await addActuatorToDevice({
                    model: newActuator.model,
                    type: newActuator.type,
                    deviceId: deviceId
                });
                setActuators([...actuators, newActuatorWithPercentage]);
                setNewActuator({ model: '', type: '' });
                setIsActuatorFormVisible(false);
            } catch (error) {
                console.error('Failed to add actuator:', error);
            }
        }
    };


    /**
     * increaseBlindRollerPercentage is a function that increases the percentage of the Blind Roller actuator by 10, up to a maximum of 100.
     * It updates the state of the actuators.
     * @param {number} id - The id of the actuator
     */
    const increaseBlindRollerPercentage = (id) => {
        setActuators(actuators.map(actuator =>
            actuator.id === id
                ? { ...actuator, percentage: Math.min(actuator.percentage + 10, 100) }
                : actuator
        ));
    };

    /**
     * decreaseBlindRollerPercentage is a function that decreases the percentage of the Blind Roller actuator by 10, down to a minimum of 0.
     * It updates the state of the actuators.
     * @param {number} id - The id of the actuator
     */
    const decreaseBlindRollerPercentage = (id) => {
        setActuators(actuators.map(actuator =>
            actuator.id === id
                ? { ...actuator, percentage: Math.max(actuator.percentage - 10, 0) }
                : actuator
        ));
    };
    /**
     * navigateBackToDevices is a function that navigates back to the previous page.
     */
    const navigateBackToDevices = () => {
        navigate(-1);
    };

    /**
     * formatTitle is a function that capitalizes the first letter of a string.
     * @param {string} title - The string to be formatted
     * @returns {string} The formatted string
     */
    const formatTitle = (title) => {
        return title.charAt(0).toUpperCase() + title.slice(1);
    };

    /**
     * refreshSensorsAndActuators is an asynchronous function that fetches the latest data about the sensors and actuators of the device from the server.
     * It updates the state of the sensors and actuators.
     */
    const refreshSensorsAndActuators = async () => {
        try {
            const sensorsResponse = await getSensorsByDevice(deviceId);
            setSensors(sensorsResponse.data);
            const actuatorsResponse = await getActuatorsByDevice(deviceId);
            setActuators(actuatorsResponse.data);
        } catch (error) {
            console.error('Failed to refresh sensors and actuators:', error);
        }
    };

    /**
     * The component returns a detailed view of the device, including its associated room, sensors, and actuators.
     * It also includes forms for adding new sensors and actuators to the device, and a button for controlling the device's activation status.
     */
    return (
        <div className={`device ${!isDeviceOn ? 'disabled-device' : ''}`}>
            <button className="back-button" onClick={navigateBackToDevices}>
                Back to Devices
            </button>

            <h1> <br/>Smart Home</h1>

            <div className="top-bar">
                <div className="menu-button-container">
                    <MenuButton/>
                </div>
            </div>
            <div className="image-container">

                {device && (
                    <Card
                        title={<div style={{textAlign: "right"}}>{formatTitle(device.deviceName) || formatTitle(deviceId)}</div>}
                        image={deviceImage}
                    />
                )}
                {device && room && (
                    <AttributeCardDevice
                        attributes={[
                            {id: 1, name: 'Device Name', value: formatTitle(device.deviceName)},
                            {id: 2, name: 'Room Name', value: formatTitle(room.roomName)},
                            {id: 3, name: 'Device Model', value: device.deviceModel},
                            {id: 4, name: 'Activation Status', value: device.activationStatus.toString()},
                        ]}
                        isDeviceOn={isDeviceOn}
                        toggleDeviceStatus={toggleDeviceStatus}
                        deviceId={device.deviceId}
                    />
                )}
            </div>
            <div className="tables-container">
                <div className="table-container">
                    <DeviceTable
                        title="Sensors"
                        items={sensors}
                        isFormVisible={isSensorFormVisible && isDeviceOn}
                        setIsFormVisible={setIsSensorFormVisible}
                        addForm={addSensor}
                        newFormData={newSensor}
                        handleInputChange={handleSensorInputChange}
                        itemTypes={sensorTypes}
                        isDisabled={!isDeviceOn}
                        sensorTypes={sensorTypes}
                        sensorModels={sensorModels}
                        handleSensorTypeChange={handleSensorTypeChange}
                    />
                </div>
                <div className="table-container">
                    <DeviceTable
                        title="Actuators"
                        items={actuators}
                        isFormVisible={isActuatorFormVisible && isDeviceOn}
                        setIsFormVisible={setIsActuatorFormVisible}
                        addForm={addActuator}
                        newFormData={newActuator}
                        handleInputChange={handleActuatorInputChange}
                        itemTypes={actuatorTypes}
                        isDisabled={!isDeviceOn}
                        actuatorTypes={actuatorTypes}
                        actuatorModels={actuatorModels}
                        handleActuatorTypeChange={handleActuatorTypeChange}
                        increasePercentage={increaseBlindRollerPercentage}
                        decreasePercentage={decreaseBlindRollerPercentage}
                    />
                </div>
            </div>
            <div className="table-container">
                <DeviceReadingsTable
                    readings={sensorReadings}
                />
            </div>
            <PopupForm
                isOpen={isSensorFormVisible || isActuatorFormVisible}
                onRequestClose={() => {
                    setIsSensorFormVisible(false);
                    setIsActuatorFormVisible(false);
                }}
                type={isSensorFormVisible ? 'Sensor' : 'Actuator'}
                deviceId={deviceId}
                formData={isSensorFormVisible ? newSensor : newActuator}
                handleInputChange={isSensorFormVisible ? handleSensorInputChange : handleActuatorInputChange}
                sensorTypes={sensorTypes}
                sensorModels={sensorModels}
                handleSensorTypeChange={handleSensorTypeChange}
                actuatorTypes={actuatorTypes}
                actuatorModels={actuatorModels}
                handleActuatorTypeChange={handleActuatorTypeChange}
                refreshSensorsAndActuators={refreshSensorsAndActuators}
            />
            {device && device.deviceName.toLowerCase() === 'blindroller' && (
                <button
                    onClick={() => setIsBlindRollerTableVisible(true)}
                    disabled={!isDeviceOn}
                    className={`save-device-button ${!isDeviceOn ? 'disabled-button' : ''}`}
                >
                    Control Blind Roller
                </button>
            )}
            {isBlindRollerTableVisible && (
                <div className="popup-overlay">
                    <div className="popup">
                        <BlindRollerTable
                            actuators={actuators}
                            sensors={sensors}
                            latestReading={latestReading}
                        />
                        <button onClick={() => setIsBlindRollerTableVisible(false)}>Close</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default DeviceDetails;
