import { useAuth } from '../AuthContext';
import { getAccounts } from '../api';
import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Menu from './Menu';
import './FormBox.css';

export default function AccountsPage() {
  const { userId } = useParams();
  const { jwt } = useAuth();
  const [accounts, setAccounts] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (jwt) {
      getAccounts(jwt).then(setAccounts);
    }
  }, [jwt]);

  function handleCreateAccount() {
    navigate(`/user/${userId}/accounts/create`);
  }

  function handleAccountClick(accountNumber) {
    navigate(`/user/${userId}/accounts/${accountNumber}`);
  }

  return (
    <div className="form-box" role="main" aria-label="Accounts info">
      <Menu />
      <h2 className="form-box__title" style={{marginBottom: "1rem"}}>Accounts Info</h2>

      {accounts ? (
        <div className="form-box__fieldset">
          <legend className="form-box__legend">Your Accounts</legend>
          {accounts.accounts && accounts.accounts.length > 0 ? (
            <ul style={{paddingLeft: 0, listStyle: "none"}}>
              {accounts.accounts.map(acc => (
                <li key={acc.accountNumber} style={{marginBottom: "1.2rem", borderBottom: "1px solid #e0e0e0", paddingBottom: "0.8rem"}}>
                  <button
                    type="button"
                    className="account-list__item"
                    onClick={() => handleAccountClick(acc.accountNumber)}
                    aria-label={`View details for account ${acc.accountName}`}
                  >
                    <div><strong>Name:</strong> {acc.accountName}</div>
                    <div><strong>Type:</strong> {acc.accountType}</div>
                    <div><strong>Number:</strong> {acc.accountNumber}</div>
                    <div><strong>Sort Code:</strong> {acc.sortCode}</div>
                    <div><strong>Balance:</strong> £{acc.balance.toFixed(2)}</div>
                    <div><strong>Currency:</strong> {acc.currency}</div>
                    <div><strong>Created:</strong> {acc.createdTimestamp}</div>
                    <div><strong>Updated:</strong> {acc.updatedTimestamp}</div>
                  </button>
                </li>
              ))}
            </ul>
          ) : (
            <div>No accounts found.</div>
          )}
        </div>
      ) : (
        <p>Loading...</p>
      )}
      <form onSubmit={handleCreateAccount} className="form-box__form" aria-labelledby="create-account-title">
        <button
          className="form-box__button"
          type="submit"
        >
          + New Account
        </button>
      </form>
    </div>
  );
}
