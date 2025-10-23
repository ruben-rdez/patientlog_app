import React, { useState, useEffect } from 'react';
import './PatientForm.css';

const PatientForm = ({ initialPatient, onSubmit }) => {
  // estado interno del formulario
  const [patient, setPatient] = useState({ firstName: '', lastName: '', age: '' });

  // considerar que estamos en modo edición SOLO si initialPatient tiene id válido
  const isEditing = !!(initialPatient && (initialPatient.id !== undefined && initialPatient.id !== null));

  useEffect(() => {
    if (isEditing) {
      // cargar datos para editar
      setPatient({
        firstName: initialPatient.firstName ?? '',
        lastName: initialPatient.lastName ?? '',
        age: initialPatient.age ?? ''
      });
    } else {
      // limpiar formulario cuando no haya edición
      setPatient({ firstName: '', lastName: '', age: '' });
    }
  }, [isEditing, initialPatient]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPatient(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // enviar el objeto tal cual al parent (App.js) que decidirá POST o PUT
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
