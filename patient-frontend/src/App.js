import React, { useState, useEffect, useCallback } from 'react';
import { createApiClient } from './api/axiosConfig';
import Login from './components/Login';
import PatientList from './components/PatientList';
import PatientForm from './components/PatientForm';
import './App.css';

function App() {
  const [user, setUser] = useState(null);  // { username, roles, apiClient }
  const [patients, setPatients] = useState([]);
  const [editingPatient, setEditingPatient] = useState(null);

  const handleLogin = async (username, password) => {
    const apiClient = createApiClient(username, password);
    try {
      const response = await apiClient.get('/auth/login');
      const userInfo = response.data;
      setUser({ ...userInfo, apiClient }); // store api client for future calls
    } catch (error) {
      console.error('Login failed', error);
      alert('Invalid credentials');
    }
  };

	const fetchPatients = useCallback(async () => {
	  if (!user) return;
	  try {
		  const response = await user.apiClient.get('/patients');
		  setPatients(response.data);
	  } catch (error) {
		  console.error('Error fetching patients', error);
	  }
	}, [user]);

  useEffect(() => {
    if (user) fetchPatients();
  }, [user, fetchPatients]);

  const handleLogout = () => {
    setUser(null);
    setPatients([]);
    setEditingPatient(null);
  };

  const handleCreateOrUpdatePatient = async (patientData) => {
    if (!user) return;
    try {
      if (editingPatient) {
        await user.apiClient.put(`/patients/${editingPatient.id}`, patientData);
		    setEditingPatient(null);
		    alert("Patient updated successfully ✅");
      } else {
		    await user.apiClient.post('/patients', patientData);
		    setEditingPatient({}); 
		    setTimeout(() => setEditingPatient(null), 0);
		    alert("Patient added successfully ✅");
      }
      await fetchPatients();
      setEditingPatient(null);
    } catch (error) {
      console.error('Error saving patient', error);
    }
  };

  const handleEditPatient = (patient) => setEditingPatient(patient);
  
  const handleDeletePatient = async (id) => {
    if (!user) return;
	  const confirmed = window.confirm("Are you sure you want to delete this patient?");
	  if (!confirmed) return;
    try {
      await user.apiClient.delete(`/patients/${id}`);
      fetchPatients();
    } catch (error) {
      console.error('Error deleting patient', error);
    }
  };

  if (!user) {
    return <Login onLogin={handleLogin} />;
  }

  const isAdmin = user.roles.includes('ROLE_ADMIN');

  return (
    <div className="app-container">
      <h1 className="app-title">Patient Management System</h1>
		<div className="main-content">
			
		{isAdmin && (
          <PatientForm
			key={editingPatient ? editingPatient.id : 'new'}
            className="patient-form-card"
            initialPatient={editingPatient}
            onSubmit={handleCreateOrUpdatePatient}
          />
        )}
		  <PatientList
			className="patient-list-card"
			patients={patients}
			onEdit={handleEditPatient}
			onDelete={handleDeletePatient}
			user={user}
		  />
		</div>
		{user && (
    <button className="logout-button" onClick={handleLogout}>
      Logout
    </button>
  )}
    </div>
  );
}

export default App;
