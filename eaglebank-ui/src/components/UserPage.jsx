import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { deleteUser, getUser } from '../api';
import { useEffect, useState } from 'react';
import Menu from './Menu';
import './FormBox.css';

export default function UserPage() {
  const { userId } = useParams();
  const { jwt, setJwt } = useAuth();
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

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
        const response = await deleteUser(userId, jwt);
        if(!response) {
          setJwt(null);
          navigate(`/`);
        }else {
          setError(response);
        }
       
      } catch (error) {
        console.error("Failed to delete user:", error);
        setError(error);  
      }
    }
  };

  return (
    <div className="form-box" role="main" aria-label="User info">
      <Menu />
      <h2 className="form-box__title" style={{marginBottom: "1rem"}}>User Info</h2>
      {error && (
        <div className="form-box__error" role="alert">
          <strong>Error:</strong> {typeof error === "string" ? error : JSON.stringify(error, null, 2)}
        </div>
      )}
      {user ? (
        <div className="form-box__fieldset" style={{marginBottom: "1.5rem"}}>
          <div style={{marginBottom: "0.5rem"}}><strong>Name:</strong> {user.name}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Email:</strong> {user.email}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Phone:</strong> {user.phoneNumber}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Address:</strong>
            <div style={{marginLeft: "1rem"}}>
              {user.address?.line1 && <div>{user.address.line1}</div>}
              {user.address?.line2 && <div>{user.address.line2}</div>}
              {user.address?.line3 && <div>{user.address.line3}</div>}
              {user.address?.town && <div>{user.address.town}</div>}
              {user.address?.county && <div>{user.address.county}</div>}
              {user.address?.postcode && <div>{user.address.postcode}</div>}
            </div>
          </div>
          <div style={{marginBottom: "0.5rem"}}><strong>ID:</strong> {user.id}</div>
        </div>
      ) : (
        <p>Loading...</p>
      )}
      <button onClick={handleDelete} className="form-box__button" style={{background: "#c62828"}}>
        Delete User
      </button>
    </div>
  );
}
