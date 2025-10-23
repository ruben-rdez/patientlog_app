import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1';

export const createApiClient = (username, password) => {
  return axios.create({
    baseURL: API_URL,
    auth: { username, password },
    withCredentials: true,
  });
};