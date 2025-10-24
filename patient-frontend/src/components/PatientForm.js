import React, { useState, useEffect } from 'react';
import './PatientForm.css';

const PatientForm = ({ initialPatient, onSubmit }) => {
  const [patient, setPatient] = useState({ firstName: '', lastName: '', age: '' });

  // consider that we are in edit mode ONLY if initialPatient has a valid id
  const isEditing = !!(initialPatient && (initialPatient.id !== undefined && initialPatient.id !== null));

  useEffect(() => {
    if (isEditing) {
      setPatient({
        firstName: initialPatient.firstName ?? '',
        lastName: initialPatient.lastName ?? '',
        age: initialPatient.age ?? ''
      });
    } else {
      setPatient({ firstName: '', lastName: '', age: '' });
    }
  }, [isEditing, initialPatient]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPatient(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // send the object as is to the parent (App.js), which will decide whether to POST or PUT
    onSubmit(patient);
	
	if (!isEditing) {
      setPatient({ firstName: '', lastName: '', age: '' });
    }
  };

  return (
    <div className="patient-form-card">
      <h2>{isEditing ? 'Edit Patient' : 'Add New Patient'}</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="firstName"
          placeholder="First Name"
          value={patient.firstName}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="lastName"
          placeholder="Last Name"
          value={patient.lastName}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="age"
          placeholder="Age"
          value={patient.age}
          onChange={handleChange}
          required
        />
        <button type="submit">
          {isEditing ? 'Update Patient' : 'Add Patient'}
        </button>
      </form>
    </div>
  );
};

export default PatientForm;
