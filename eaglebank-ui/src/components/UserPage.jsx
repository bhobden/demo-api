import { useParams } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { getUser } from '../api';
import { useEffect, useState } from 'react';

export default function UserPage() {
  const { userId } = useParams();
  const { jwt } = useAuth();
  const [user, setUser] = useState(null);

  useEffect(() => {
    if (jwt) {
      getUser(userId, jwt).then(setUser);
    }
  }, [jwt, userId]);

  return (
    <div>
      <h2>User Info</h2>
      {user ? <pre>{JSON.stringify(user, null, 2)}</pre> : <p>Loading...</p>}
    </div>
  );
}
