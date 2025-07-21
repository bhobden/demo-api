import { useState } from "react";
import { useAuth } from "../AuthContext";
import { useParams, useNavigate } from "react-router-dom";
import { createAccount } from "../api";
import Menu from "./Menu";
import "./FormBox.css";

export default function CreateAccountForm() {
    const { userId } = useParams();
  const { jwt } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    accountName: "",
    accountType: "SAVINGS"
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
      const result = await createAccount(form, jwt);
      if (result?.accountNumber) {
        setSuccess("Account created successfully!");
        setTimeout(() => navigate(`/user/${userId}/accounts`), 1200);
      } else {
        setError(result?.message || "Account creation failed.");
      }
    } catch (err) {
      setError("Account creation failed.");
    }
  }

  return (
    <div className="form-box" role="main" aria-label="Create account form">
      <Menu />
      <form onSubmit={handleSubmit} className="form-box__form" aria-labelledby="create-account-title">
        <h2 id="create-account-title" className="form-box__title">Create New Account</h2>
        {error && <div className="form-box__error" role="alert">{error}</div>}
        {success && <div className="form-box__success" role="status">{success}</div>}

        <label htmlFor="accountName" className="form-box__label">Account Name</label>
        <input
          id="accountName"
          name="accountName"
          type="text"
          className="form-box__input"
          placeholder="e.g. Holiday Fund"
          value={form.accountName}
          onChange={handleChange}
          required
        />

        <label htmlFor="accountType" className="form-box__label">Account Type</label>
        <select
          id="accountType"
          name="accountType"
          className="form-box__input"
          value={form.accountType}
          onChange={handleChange}
          required
        >
          <option value="SAVINGS">Savings</option>
          <option value="PERSONAL">Personal</option>
        </select>

        <button type="submit" className="form-box__button">Create Account</button>
      </form>
    </div>
  );
}