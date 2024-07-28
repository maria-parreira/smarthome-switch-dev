import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from "../axiosInstance.jsx";
import '../styles/RoomsList.css';
import { getRoomNameById } from '../services/RoomService.jsx';
import livingRoomImage from '../assets/livingRoom.jpg';
import kitchenImage from '../assets/kitchen.jpg';
import bedroomImage from '../assets/bedroom.jpg';
import bathroomImage from '../assets/bathroom.jpg';
import gardenImage from '../assets/garden.jpg';
import entranceImage from '../assets/entrance.jpg';
import officeImage from '../assets/office.jpg';

const RoomsList = ({ refreshKey }) => {
    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [isPopupOpen, setPopupOpen] = useState(false);

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        height: '',
        width: '',
        length: ''
    });

    const roomImageMap = {
        'LivingRoom': livingRoomImage,
        'Kitchen': kitchenImage,
        'Bedroom': bedroomImage,
        'Bathroom': bathroomImage,
        'Garden': gardenImage,
        'Entrance': entranceImage,
        'Office': officeImage,
    };

    useEffect(() => {
        const fetchRooms = async () => {
            try {
                setLoading(true);
                const response = await axiosInstance.get(`/houses/H1/rooms`);
                setRooms(response.data);
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch rooms:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchRooms();
    }, [refreshKey]);

    useEffect(() => {
        const fetchRoomNames = async () => {
            const updatedRooms = await Promise.all(
                rooms.map(async (room) => {
                    const roomName = await getRoomNameById(room.roomId);
                    return { ...room, roomName };
                })
            );
            setRooms(updatedRooms);
        };

        if (rooms.length > 0) {
            fetchRoomNames();
        }
    }, [rooms]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    const navigateToDevices = (roomId) => {
        navigate(`/rooms/${roomId}/devices`);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const newRoom = {
            id: rooms.length + 1,
            name: formData.name,
            description: formData.description,
            height: formData.height,
            width: formData.width,
            length: formData.length
        };
        setRooms([...rooms, newRoom]);
        setPopupOpen(false);
        setFormData({
            name: '',
            description: '',
            height: '',
            width: '',
            length: ''
        });
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    return (
        <div>
            <div className="menu">Room Menu</div>
            {rooms.map(room => (
                <div key={room.roomId} className="room-card" onClick={() => navigateToDevices(room.roomId)}>
                    <h3>{room.roomName}</h3>
                    {roomImageMap[room.roomName] && (
                        <img src={roomImageMap[room.roomName]} alt={room.roomName} className="room-image" />
                    )}
                </div>
            ))}

        </div>
    );
};

export default RoomsList;
