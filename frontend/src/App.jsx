import React, { useState } from 'react';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';
import './index.css'; // Import global styles
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RoomsList from './components/RoomsList';
import DeviceList from './components/DevicesList';
import DeviceDetails from './pages/Devices/DeviceDetails.jsx';
import EditDevice from './pages/Devices/EditDevice.jsx';

function App() {
    const [count, setCount] = useState(0);

    return (
        <div id="root">
            <div>
                <a href="https://vitejs.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="logo react" alt="React logo" />
                </a>
            </div>
            <h1>Vite + React</h1>
            <div className="card">
                <button onClick={() => setCount((count) => count + 1)}>
                    count is {count}
                </button>
                <p>
                    Edit <code>src/App.jsx</code> and save to test HMR
                </p>
            </div>
            <p className="read-the-docs">
                Click on the Vite and React logos to learn more
            </p>
            <Router>
                <Routes>
                    <Route path="/" element={<RoomsList />} />
                    <Route path="/rooms/:roomId/devices" element={<DeviceList />} />
                    <Route path="/devices/:deviceId/edit-device" element={<EditDevice />} />
                    <Route path="/devices/details" element={<DeviceDetails />} />
                    <Route path="/devices/:deviceId/details" element={<DeviceDetails />} />
                </Routes>
            </Router>
        </div>
    );
}

export default App;
