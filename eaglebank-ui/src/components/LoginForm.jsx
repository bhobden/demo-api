import { useState } from "react";
import { useAuth } from '../AuthContext';
import { login } from "../api";
import { useNavigate } from 'react-router-dom';
import { useLocation } from "react-router-dom";

export default function LoginForm() {
const location = useLocation();
  const prefillUsername = location.state?.prefillUsername || "";

  const { setJwt } = useAuth();
  const [username, setUsername] = useState(prefillUsername);
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    const result = await login(username, password);
    setJwt(result.jwt);
    navigate(`/user/${username}`);
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>Login</h2>
      <input placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} />
      <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} />
      <button type="submit">Login</button>
    </form>
  );
}
