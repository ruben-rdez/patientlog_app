import React, { useState } from 'react';
import './Login.css';

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!username || !password) {
      setError('Please enter username and password');
      return;
    }
      setError('');
      onLogin(username, password);
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1>Patient Log</h1>
        <p className="login-subtitle">Sign in to continue</p>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {error && <p className="login-error">{error}</p>}
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
