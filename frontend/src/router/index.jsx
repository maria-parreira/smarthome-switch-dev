import { createBrowserRouter } from "react-router-dom";
import Home from "../pages/Home/Home";
import ErrorPage from "../pages/Error/Error";
import RoomMenu from "../pages/Rooms/RoomMenu";
import EditDevice from "../pages/Devices/EditDevice.jsx";
import DeviceDetails from "../pages/Devices/DeviceDetails.jsx";
import Device from "../pages/Devices/Device.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Home />,
        errorElement: <ErrorPage />
    },
    {
        path: "/rooms",
        element: <RoomMenu />,
        errorElement: <ErrorPage />
    },

    {
        path: "/rooms/:roomId/devices",
        element: <Device />
    },
    {
        path: "/details/:deviceId/edit-device",
        element: <EditDevice />
    },

    {
        path: "/devices/:deviceId/details",
        element: <DeviceDetails />
    },
    {
        path: "*",
        element: <ErrorPage />
    }
]);

export default router;
