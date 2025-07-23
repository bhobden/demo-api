import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import { getAccount, deleteAccount, getTransactionsForAccount } from '../api';
import { useEffect, useState } from 'react';
import Menu from './Menu';
import './FormBox.css';

export default function AccountPage() {
  const { accountId, userId } = useParams();
  const { jwt } = useAuth();
  const [account, setAccount] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [transactionsError, setTransactionsError] = useState("");
  const [loadingAccount, setLoadingAccount] = useState(true);
  const [loadingTransactions, setLoadingTransactions] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    setError("");
    setTransactionsError("");
    setLoadingAccount(true);
    setLoadingTransactions(true);

    if (!jwt) {
      navigate("/", { replace: true });
      return;
    }
    if (accountId) {
      getAccount(accountId, jwt)
        .then(acc => {
          if (acc?.accountNumber) {
            setAccount(acc);
          } else if (acc?.message) {
            setError(acc.message);
            setAccount(null);
          } else {
            setError("Failed to load account.");
            setAccount(null);
          }
        })
        .catch(() => {
          setError("Failed to load account.");
          setAccount(null);
        }).finally(() => {
          setLoadingAccount(false);
        });

      getTransactionsForAccount(accountId, jwt)
        .then(res => {
          if (Array.isArray(res.transactions)) {
            setTransactions(
              [...res.transactions].sort(
                (a, b) => new Date(b.createdTimestamp) - new Date(a.createdTimestamp)
              )
            );
            setTransactionsError("");
          } else if (res && res.message) {
            setTransactions([]);
            setTransactionsError(res.message);
          } else {
            setTransactions([]);
            setTransactionsError("Failed to load transactions.");
          }
        })
        .catch(() => {
          setTransactions([]);
          setTransactionsError("Failed to load transactions.");
        }).finally(() => {
          setLoadingTransactions(false);
        });
    } else {
      setLoadingAccount(false);
      setLoadingTransactions(false);
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
      <h2 className="form-box__title" style={{ marginBottom: "1rem" }}>Account Info</h2>
      {error && <div className="form-box__error" role="alert">{error}</div>}
      {success && <div className="form-box__success" role="status">{success}</div>}

      {/* Only show account details if loaded and no error */}
      {!error && !loadingAccount && account ? (
        <div className="form-box__fieldset" style={{ marginBottom: "1.5rem" }}>
          <div style={{ marginBottom: "0.5rem" }}><strong>Name:</strong> {account.accountName}</div>
          <div style={{ marginBottom: "0.5rem" }}><strong>Type:</strong> {account.accountType}</div>
          <div style={{ marginBottom: "0.5rem" }}><strong>Number:</strong> {account.accountNumber}</div>
          <div style={{ marginBottom: "0.5rem" }}><strong>Sort Code:</strong> {account.sortCode}</div>
          <div style={{ marginBottom: "0.5rem" }}><strong>Balance:</strong> £{account.balance?.toFixed(2)}</div>
          <div style={{ marginBottom: "0.5rem" }}><strong>Currency:</strong> {account.currency}</div>
          <div style={{ marginBottom: "0.5rem" }}><strong>Created:</strong> {account.createdTimestamp}</div>
          <div style={{ marginBottom: "0.5rem" }}><strong>Updated:</strong> {account.updatedTimestamp}</div>
        </div>
      ) : loadingAccount && !error ? (
        <p>Loading account...</p>
      ) : null}

      <h3 className="form-box__legend" style={{marginTop: "2rem"}}>Transactions</h3>
      {transactionsError && (
        <div className="form-box__error" role="alert">{transactionsError}</div>
      )}
      {!transactionsError && !loadingTransactions && transactions.length > 0 ? (
        <ul style={{paddingLeft: 0, listStyle: "none", marginBottom: "2rem"}}>
          {transactions.map(tx => (
            <li key={tx.id} style={{marginBottom: "1rem", borderBottom: "1px solid #e0e0e0", paddingBottom: "0.8rem"}}>
              <div><strong>Id:</strong> {tx.id}</div>
              <div><strong>Date:</strong> {new Date(tx.createdTimestamp).toLocaleString()}</div>
              <div><strong>Type:</strong> {tx.type}</div>
              <div><strong>Amount:</strong> £{Number(tx.amount).toFixed(2)}</div>
              <div><strong>Reference:</strong> {tx.reference || <span style={{color:"#888"}}>—</span>}</div>
              <div><strong>User:</strong> {tx.userId}</div>
            </li>
          ))}
        </ul>
      ) : !transactionsError && !loadingTransactions ? (
        <div style={{marginBottom: "2rem"}}>No transactions found.</div>
      ) : loadingTransactions ? (
        <p>Loading transactions...</p>
      ) : null}

      <button
        className="form-box__button"
        type="button"
        onClick={() => navigate(`/user/${userId}/accounts/${account?.accountNumber}/transactions/create`)}
        disabled={!!error || loadingAccount}
      >
        + New Transaction
      </button>
      <button
        onClick={handleDelete}
        className="form-box__button"
        style={{ background: "#c62828" }}
        disabled={!!error || loadingAccount || !account}
      >
        Delete Account
      </button>
    </div>
  );
}
