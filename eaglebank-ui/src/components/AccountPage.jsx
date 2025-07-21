import { useParams } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { getAccount } from '../api';
import { useEffect, useState } from 'react';

export default function AccountPage() {
  const { accountId } = useParams();
  const { jwt } = useAuth();
  const [account, setAccount] = useState(null);

  useEffect(() => {
    if (jwt) {
      getAccount(accountId, jwt).then(setAccount);
    }
  }, [jwt, accountId]);

  return (
    <div>
      <h2>Account Info</h2>
      {account ? <pre>{JSON.stringify(account, null, 2)}</pre> : <p>Loading...</p>}
    </div>
  );
}
