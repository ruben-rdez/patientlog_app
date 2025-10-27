import axios from 'axios';

const API_URL = 'http://patientlogapp-env.eba-mvdxr92m.us-east-2.elasticbeanstalk.com/api/v1';

export const createApiClient = (username, password) => {
  return axios.create({
    baseURL: API_URL,
    auth: { username, password },
    withCredentials: true,
  });
};