import React from 'react';
import './PatientList.css'; 

const PatientList = ({ patients, onEdit, onDelete, user }) => {
const isAdmin = user?.roles?.includes('ROLE_ADMIN');
	
  return (
    <div className="patient-list-card">
      <h2>Patient List</h2>
      <ul>
        {patients.map((patient) => (
          <li key={patient.id}>
            <span>{patient.firstName} {patient.lastName} (Age: {patient.age})</span>
            {isAdmin && (
              <div className="patient-buttons">
                <button className="edit-btn" onClick={() => onEdit(patient)}>
                  Edit
                </button>
                <button className="delete-btn" onClick={() => onDelete(patient.id)}>
                  Delete
                </button>
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PatientList;
