import { useState } from "react";
import { useAuth } from '../AuthContext';
import { login } from "../api";
import { useNavigate, useLocation, Link } from 'react-router-dom';
import './FormBox.css'; // Reusable form styles

export default function LoginForm() {
  const location = useLocation();
  const prefillUsername = location.state?.prefillUsername || "";

  const { setJwt } = useAuth();
  const [username, setUsername] = useState(prefillUsername);
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    try {
      const result = await login(username, password);
      
      if(result.jwt) {
        setJwt(result.jwt);
        navigate(`/user/${username}`);
      } else {
        setError(result?.message || "Login failed. Please check your credentials.");
        return;
      }
      
    } catch (err) {
      setError("Login failed. Please check your credentials.");
    }
  }

  return (
    <div className="form-box" role="main" aria-label="Login form">
      <form onSubmit={handleSubmit} className="form-box__form" aria-labelledby="login-title">
        <h2 id="login-title" className="form-box__title">Login</h2>
        {prefillUsername && (
          <div className="form-box__success" role="status">
            Account created. Please use your password to log in.
          </div>
        )}
        {error && <div className="form-box__error" role="alert">{error}</div>}
        <label htmlFor="username" className="form-box__label">Username</label>
        <input
          id="username"
          name="username"
          type="text"
          autoComplete="username"
          className="form-box__input"
          placeholder="Username"
          value={username}
          onChange={e => setUsername(e.target.value)}
          required
        />
        <label htmlFor="password" className="form-box__label">Password</label>
        <input
          id="password"
          name="password"
          type="password"
          autoComplete="current-password"
          className="form-box__input"
          placeholder="Password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          required
        />
        <button type="submit" className="form-box__button">Login</button>
      </form>
      <div className="form-box__footer">
        <span>Don't have an account? </span>
        <Link to="/register" className="form-box__link">Register</Link>
      </div>
    </div>
  );
}
