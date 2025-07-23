import { useState } from "react";
import { useAuth } from "../AuthContext";
import { useParams, useNavigate } from "react-router-dom";
import { createTransaction } from "../api";
import Menu from "./Menu";
import "./FormBox.css";

/**
 * Form component for creating a new transaction for a specified account.
 */
export default function CreateTransactionForm() {
  const { accountNumber, userId } = useParams();
  const { jwt } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    amount: "",
    type: "DEPOSIT",
    currency: "GBP",
    reference: ""
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  function handleChange(e) {
    const { name, value } = e.target;
    setForm(f => ({ ...f, [name]: value }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setSuccess("");
    try {
      // API expects: { amount, type, reference }
      const payload = {
        amount: parseFloat(form.amount),
        type: form.type,
        currency: form.currency,
        reference: form.reference
      };
      const result = await createTransaction(accountNumber, payload, jwt);
      if (result?.id) {
        setSuccess("Transaction created successfully!");
        setTimeout(() => navigate(`/user/${userId}/accounts/${accountNumber}`), 1200);
      } else {
        setError(result?.message || "Transaction creation failed.");
      }
    } catch (err) {
      setError("Transaction creation failed.");
    }
  }

  return (
    <div className="form-box" role="main" aria-label="Create transaction form">
      <Menu />
      <form onSubmit={handleSubmit} className="form-box__form" aria-labelledby="create-transaction-title">
        <h2 id="create-transaction-title" className="form-box__title">Create New Transaction</h2>
        {error && <div className="form-box__error" role="alert">{error}</div>}
        {success && <div className="form-box__success" role="status">{success}</div>}

        <label htmlFor="currency" className="form-box__label">Currency</label>
        <select
          id="currency"
          name="currency"
          className="form-box__input"
          value={form.currency || "GBP"}
          onChange={handleChange}
          required
        >
          <option value="GBP">GBP</option>
        </select>
        
        <label htmlFor="amount" className="form-box__label">Amount</label>
        <input
          id="amount"
          name="amount"
          type="number"
          className="form-box__input"
          placeholder="e.g. 100.00"
          value={form.amount}
          onChange={handleChange}
          min="0.01"
          max="10000"
          step="0.01"
          required
        />

        <label htmlFor="type" className="form-box__label">Transaction Type</label>
        <select
          id="type"
          name="type"
          className="form-box__input"
          value={form.type}
          onChange={handleChange}
          required
        >
          <option value="DEPOSIT">Deposit</option>
          <option value="WITHDRAWAL">Withdrawal</option>
        </select>

        <label htmlFor="reference" className="form-box__label">Reference (optional)</label>
        <input
          id="reference"
          name="reference"
          type="text"
          className="form-box__input"
          placeholder="e.g. Salary, Rent"
          value={form.reference}
          onChange={handleChange}
          maxLength={128}
        />

        <button type="submit" className="form-box__button">Create Transaction</button>
      </form>
    </div>
  );
}