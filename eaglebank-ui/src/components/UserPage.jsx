import { useParams } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { deleteUser, getUser } from '../api';
import { useEffect, useState } from 'react';
import Menu from './Menu';

export default function UserPage() {
  const { userId } = useParams();
  const { jwt, setJwt } = useAuth();
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (jwt) {
      getUser(userId, jwt)
        .then(setUser)
        .catch(setError);
    }
  }, [jwt, userId]);

  async function handleDelete() {
    if (jwt) {
      try {
        await deleteUser(userId, jwt);
        setJwt(null);
        navigate(`/login`);
      } catch (error) {
        console.error("Failed to delete user:", error);
        setError(error);  
      }
    }
  };

  return (
    <div>
      {error && (
        <div className="error">
          <strong>Error:</strong> {JSON.stringify(error, null, 2)}
        </div>
      )}
      <h2>User Info</h2>
      <Menu />
      {user ? <pre>{JSON.stringify(user, null, 2)}</pre> : <p>Loading...</p>}
      <button onClick={handleDelete}>
        Delete User
      </button>
    </div>
  );
}
