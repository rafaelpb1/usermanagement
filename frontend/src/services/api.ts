import axios from "axios";

const envBaseUrl = import.meta.env.VITE_API_URL?.trim();

const resolvedBaseUrl = (envBaseUrl || "http://localhost:8090").replace(/\/$/, "");

export const api = axios.create({
    baseURL: resolvedBaseUrl,
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    if(token && token !== "undefined" && token !== null) {
        config.headers.Authorization = `Bearer ${token}`;
    } else {
        delete config.headers.Authorization;
    }
    return config;

});