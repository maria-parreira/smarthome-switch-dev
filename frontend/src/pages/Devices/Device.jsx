import { useNavigate, useParams } from "react-router-dom";
import React, { useState } from "react";
import '../../styles/Devices.css';
import DevicesList from "../../Components/DevicesList.jsx";
import Card from '../../Components/Card.jsx';
import BackButton from '../../Components/BackButton.jsx';
import bathroomImage from '../../assets/bathroom.jpg';
import officeImage from '../../assets/office.jpg';
import livingRoomImage from '../../assets/livingRoom.jpg';
import bedroomImage from '../../assets/bedroom.jpg';
import kitchenImage from '../../assets/kitchen.jpg';
import gardenImage from '../../assets/garden.jpg';
import entranceImage from '../../assets/entrance.jpg';
import RoomAttributes from '../../Components/RoomAttributes.jsx';
import MenuButton from "../../Components/MenuButton.jsx";
import AddDeviceForm from "../../Components/AddDeviceForm.jsx";

/**
 * roomImages is an object that maps room names to their corresponding images.
 * @type {Object.<string, string>}
 * @property {string} bathroom - The image for the bathroom.
 * @property {string} office - The image for the office.
 * @property {string} livingroom - The image for the living room.
 * @property {string} bedroom - The image for the bedroom.
 * @property {string} kitchen - The image for the kitchen.
 * @property {string} garden - The image for the garden.
 * @property {string} entrance - The image for the entrance.
 */
const roomImages = {
    'bathroom': bathroomImage,
    'office': officeImage,
    'livingroom': livingRoomImage,
    'bedroom': bedroomImage,
    'kitchen': kitchenImage,
    'garden': gardenImage,
    'entrance': entranceImage,
};

/**
 * Device is a functional component that renders a device page.
 * It uses the useState hook to manage the state of the room name.
 * It also uses the useParams hook from react-router-dom to get the room id from the URL parameters.
 *
 * @component
 * @example
 * return (
 *   <Device />
 * )
 */
const Device = () => {
    /**
     * roomId is a variable that holds the id of the room from the URL parameters.
     * @type {string}
     */
    const { roomId } = useParams();
    /**
     * roomName is a state variable that holds the name of the room. setRoomName is the corresponding setter function.
     * @type {string}
     */
    const [roomName, setRoomName] = useState('');

    /**
     * handleRoomDataLoaded is a function that is called when the room data has been loaded.
     * It sets the roomName state variable with the loaded room name, with the first letter capitalized.
     * @param {string} name - The loaded room name.
     */
    const handleRoomDataLoaded = (name) => {
        setRoomName(name.charAt(0).toUpperCase() + name.slice(1));
    };

    if (!roomId) {
        return <p>Loading...</p>;
    }
    /**
     * The component returns a div element that contains a BackButton component, a MenuButton component, a Card component,
     * a RoomAttributes component, a DevicesList component, and an AddDeviceForm component.
     * If the room id is not yet loaded, it displays a loading message.
     */
    return (
        <div className='device'>
            <BackButton path="/rooms" label="Back to RoomMenu" className="back-button"/>
            <h1><br/></h1>
            <h1>Smart Home</h1>
            <div className="menu-button-container">
                <MenuButton/>
            </div>
            <Card
                title={`Devices in ${roomName || roomId.charAt(0).toUpperCase() + roomId.slice(1)}`}
                image={roomImages[roomName.toLowerCase()]}
            />
            <RoomAttributes roomId={roomId} onRoomDataLoaded={handleRoomDataLoaded}/>
            <DevicesList roomId={roomId}/>
            <AddDeviceForm roomId={roomId}/>
        </div>
    );
};

export default Device;
