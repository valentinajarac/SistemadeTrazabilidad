import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Auth
export const login = async (username: string, password: string) => {
  const response = await api.post('/auth/login', { username, password });
  return response.data;
};

// Farms
export const getFarms = async () => {
  const response = await api.get('/farms');
  return response.data;
};

export const createFarm = async (farmData: any) => {
  const response = await api.post('/farms', farmData);
  return response.data;
};

export const updateFarm = async (id: string, farmData: any) => {
  const response = await api.put(`/farms/${id}`, farmData);
  return response.data;
};

export const deleteFarm = async (id: string) => {
  await api.delete(`/farms/${id}`);
};

// Crops
export const getCrops = async () => {
  const response = await api.get('/crops');
  return response.data;
};

export const createCrop = async (cropData: any) => {
  const response = await api.post('/crops', cropData);
  return response.data;
};

export const updateCrop = async (id: string, cropData: any) => {
  const response = await api.put(`/crops/${id}`, cropData);
  return response.data;
};

export const deleteCrop = async (id: string) => {
  await api.delete(`/crops/${id}`);
};

// Shipments
export const getShipments = async () => {
  const response = await api.get('/shipments');
  return response.data;
};

export const getProducerShipments = async (producerId: string) => {
  const response = await api.get(`/shipments/producer/${producerId}`);
  return response.data;
};

export const getAllShipmentsWithProducers = async () => {
  const response = await api.get('/shipments/all-with-producers');
  return response.data;
};

export const createShipment = async (shipmentData: any) => {
  const response = await api.post('/shipments', shipmentData);
  return response.data;
};

export const updateShipment = async (id: string, shipmentData: any) => {
  const response = await api.put(`/shipments/${id}`, shipmentData);
  return response.data;
};

export const deleteShipment = async (id: string) => {
  await api.delete(`/shipments/${id}`);
};

// Users
export const getUsers = async () => {
  const response = await api.get('/users');
  return response.data;
};

export const createUser = async (userData: any) => {
  const response = await api.post('/users', userData);
  return response.data;
};

export const updateUser = async (id: string, userData: any) => {
  const response = await api.put(`/users/${id}`, userData);
  return response.data;
};

export const deleteUser = async (id: string) => {
  await api.delete(`/users/${id}`);
};

// Clients
export const getClients = async () => {
  const response = await api.get('/clients');
  return response.data;
};

export const createClient = async (clientData: any) => {
  const response = await api.post('/clients', clientData);
  return response.data;
};

export const updateClient = async (id: string, clientData: any) => {
  const response = await api.put(`/clients/${id}`, clientData);
  return response.data;
};

export const deleteClient = async (id: string) => {
  await api.delete(`/clients/${id}`);
};

// Add interceptor for token
export const setAuthToken = (token: string) => {
  api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
};

export const clearAuthToken = () => {
  delete api.defaults.headers.common['Authorization'];
};

export default api;