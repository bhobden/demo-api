import { useParams } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { getAccounts } from '../api';
import { useEffect, useState } from 'react';

export default function UserPage() {
  const { jwt } = useAuth();
  const [accounts, setAccounts] = useState(null);

  useEffect(() => {
    if (jwt) {
      getAccounts(jwt).then(setAccounts);
    }
  }, [jwt]);

  return (
    <div>
      <h2>Accounts Info</h2>
      {accounts ? <pre>{JSON.stringify(accounts, null, 2)}</pre> : <p>Loading...</p>}
    </div>
  );
}
