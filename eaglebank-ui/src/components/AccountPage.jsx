import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { getAccount, deleteAccount } from '../api';
import { useEffect, useState } from 'react';
import Menu from './Menu';
import './FormBox.css';

export default function AccountPage() {
  const { accountId, userId } = useParams();
  const { jwt } = useAuth();
  const [account, setAccount] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    setError("");
    if (jwt && accountId) {
      getAccount(accountId, jwt)
        .then(setAccount)
        .catch(e => setError("Failed to load account."));
    }
  }, [jwt, accountId]);

  async function handleDelete() {
    setError("");
    setSuccess("");
    if (!window.confirm("Are you sure you want to delete this account? This cannot be undone.")) return;
    try {
      await deleteAccount(accountId, jwt);
      setSuccess("Account deleted successfully.");
      setTimeout(() => navigate(`/user/${userId}/accounts`), 1200);
    } catch (err) {
      setError("Failed to delete account.");
    }
  }

  return (
    <div className="form-box" role="main" aria-label="Account info">
      <Menu />
      <h2 className="form-box__title" style={{marginBottom: "1rem"}}>Account Info</h2>
      {error && <div className="form-box__error" role="alert">{error}</div>}
      {success && <div className="form-box__success" role="status">{success}</div>}
      {account ? (
        <div className="form-box__fieldset" style={{marginBottom: "1.5rem"}}>
          <legend className="form-box__legend">Account Details</legend>
          <div style={{marginBottom: "0.5rem"}}><strong>Name:</strong> {account.accountName}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Type:</strong> {account.accountType}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Number:</strong> {account.accountNumber}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Sort Code:</strong> {account.sortCode}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Balance:</strong> £{account.balance?.toFixed(2)}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Currency:</strong> {account.currency}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Created:</strong> {account.createdTimestamp}</div>
          <div style={{marginBottom: "0.5rem"}}><strong>Updated:</strong> {account.updatedTimestamp}</div>
        </div>
      ) : (
        <p>Loading...</p>
      )}
      <button
        onClick={handleDelete}
        className="form-box__button"
        style={{background: "#c62828"}}
        disabled={!account}
      >
        Delete Account
      </button>
    </div>
  );
}
