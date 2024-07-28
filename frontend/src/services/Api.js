import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1';

const smartHomeApi = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

export default smartHomeApi;