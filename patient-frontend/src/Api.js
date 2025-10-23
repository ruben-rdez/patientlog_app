import axios from "axios";

export const createApiClient = (username, password) => {
  const token = btoa(`${username}:${password}`); // encode Base64 manually

  return axios.create({
    baseURL: "http://localhost:8080/api/v1/patients",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Basic ${token}`,
    },
    withCredentials: true, // important for CORS
  });
};
