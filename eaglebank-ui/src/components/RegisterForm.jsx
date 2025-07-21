import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { createUser } from "../api";
import "./FormBox.css";

export default function RegisterForm() {
    const [form, setForm] = useState({
        name: "",
        email: "",
        phoneNumber: "",
        password: "",
        address: {
            line1: "",
            line2: "",
            line3: "",
            town: "",
            county: "",
            postcode: ""
        }
    });
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    function handleChange(e) {
        const { name, value } = e.target;
        if (name.startsWith("address.")) {
            const addressField = name.split(".")[1];
            setForm(f => ({
                ...f,
                address: { ...f.address, [addressField]: value }
            }));
        } else {
            setForm(f => ({ ...f, [name]: value }));
        }
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setSuccess("");
        try {
            const result = await createUser(form);
            if (result?.id) {
                setSuccess("Registration successful! You can now log in.");
                setTimeout(() => navigate("/", { state: { prefillUsername: result.id } }), 1200);
            } else {
                setError(result?.message || "Registration failed. Please check your details.");
            }
        } catch (err) {
            setError(err?.message || "Registration failed. Please check your details.");
        }
    }

    return (
        <div className="form-box" role="main" aria-label="Register form">
            <form onSubmit={handleSubmit} className="form-box__form" aria-labelledby="register-title">
                <h2 id="register-title" className="form-box__title">Register</h2>
                {error && <div className="form-box__error" role="alert">{error}</div>}
                {success && <div className="form-box__success" role="status">{success}</div>}

                <label htmlFor="name" className="form-box__label">Name</label>
                <input
                    id="name"
                    name="name"
                    type="text"
                    className="form-box__input"
                    placeholder="Full Name"
                    value={form.name}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="email" className="form-box__label">Email</label>
                <input
                    id="email"
                    name="email"
                    type="email"
                    className="form-box__input"
                    placeholder="Email"
                    value={form.email}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="phoneNumber" className="form-box__label">Phone Number</label>
                <input
                    id="phoneNumber"
                    name="phoneNumber"
                    type="tel"
                    className="form-box__input"
                    placeholder="+441234567890"
                    value={form.phoneNumber}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="password" className="form-box__label">Password</label>
                <input
                    id="password"
                    name="password"
                    type="password"
                    className="form-box__input"
                    placeholder="Password"
                    value={form.password}
                    onChange={handleChange}
                    required
                />

                <fieldset className="form-box__fieldset">
                    <legend className="form-box__legend">Address</legend>
                    <label htmlFor="line1" className="form-box__label">Line 1</label>
                    <input
                        id="line1"
                        name="address.line1"
                        type="text"
                        className="form-box__input"
                        placeholder="Address line 1"
                        value={form.address.line1}
                        onChange={handleChange}
                        required
                    />
                    <label htmlFor="line2" className="form-box__label">Line 2</label>
                    <input
                        id="line2"
                        name="address.line2"
                        type="text"
                        className="form-box__input"
                        placeholder="Address line 2"
                        value={form.address.line2}
                        onChange={handleChange}
                    />
                    <label htmlFor="line3" className="form-box__label">Line 3</label>
                    <input
                        id="line3"
                        name="address.line3"
                        type="text"
                        className="form-box__input"
                        placeholder="Address line 3"
                        value={form.address.line3}
                        onChange={handleChange}
                    />
                    <label htmlFor="town" className="form-box__label">Town/City</label>
                    <input
                        id="town"
                        name="address.town"
                        type="text"
                        className="form-box__input"
                        placeholder="Town or City"
                        value={form.address.town}
                        onChange={handleChange}
                        required
                    />
                    <label htmlFor="county" className="form-box__label">County</label>
                    <input
                        id="county"
                        name="address.county"
                        type="text"
                        className="form-box__input"
                        placeholder="County"
                        value={form.address.county}
                        onChange={handleChange}
                        required
                    />
                    <label htmlFor="postcode" className="form-box__label">Postcode</label>
                    <input
                        id="postcode"
                        name="address.postcode"
                        type="text"
                        className="form-box__input"
                        placeholder="Postcode"
                        value={form.address.postcode}
                        onChange={handleChange}
                        required
                    />
                </fieldset>

                <button type="submit" className="form-box__button">Register</button>
            </form>
            <div className="form-box__footer">
                <span>Already have an account? </span>
                <Link to="/" className="form-box__link">Login</Link>
            </div>
        </div>
    );
}