import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getTransaction } from "../api";
import { useAuth } from "../AuthContext";

export default function TransactionDetail() {
  const { accountNumber, userId, transactionId } = useParams();
  const navigate = useNavigate();
  const { jwt } = useAuth();
  const [transaction, setTransaction] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!jwt) {
      navigate("/", { replace: true });
      return;
    }
    getTransaction(accountNumber, transactionId, jwt)
      .then(setTransaction)
      .catch((err) => setError(err.message));
  }, [transactionId, jwt, navigate]);

  if (error) {
    return (
      <div className="panel error-panel">
        <h2>Error</h2>
        <div>{error}</div>
      </div>
    );
  }

  if (!transaction) {
    return (
      <div className="panel loading-panel">
        <h2>Loading Transaction...</h2>
      </div>
    );
  }

  return (
    <div className="panel transaction-panel">
      <h2>Transaction Details</h2>
      <div className="panel-content">
        <div><strong>ID:</strong> {transaction.id}</div>
        <div><strong>Amount:</strong> {transaction.amount}</div>
        <div><strong>Date:</strong> {transaction.date}</div>
        <div><strong>Description:</strong> {transaction.description}</div>
        <div><strong>Status:</strong> {transaction.status}</div>
      </div>
    </div>
  );
}