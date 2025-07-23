import { useParams, useNavigate } from 'react-router-dom';
import { updateUser, getUser } from "../api";
import { useAuth } from '../AuthContext';
import { useEffect, useState } from 'react';
import Menu from './Menu';
import './FormBox.css';

export default function UpdateUserForm() {
    const { userId } = useParams();
    const { jwt } = useAuth();
    const navigate = useNavigate();

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [dbUser, setDbUser] = useState(null);

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

    useEffect(() => {
        if (!jwt) {
            navigate("/", { replace: true });
            return;
        }
        getUser(userId, jwt).then(setDbUser).catch(e => setError("Failed to load user."));
    }, [jwt, userId]);

    useEffect(() => {
        if (dbUser) {
            const { id, createdTimestamp, updatedTimestamp, ...rest } = dbUser;
            setForm({
                ...rest,
                password: "", // leave password blank
                address: {
                    line1: rest.address?.line1 || "",
                    line2: rest.address?.line2 || "",
                    line3: rest.address?.line3 || "",
                    town: rest.address?.town || "",
                    county: rest.address?.county || "",
                    postcode: rest.address?.postcode || ""
                }
            });
        }
    }, [dbUser]);

    function handleChange(e) {
        const { name, value } = e.target;
        if (name.startsWith("address.")) {
            const key = name.split(".")[1];
            setForm(prev => ({
                ...prev,
                address: { ...prev.address, [key]: value }
            }));
        } else {
            setForm(prev => ({ ...prev, [name]: value }));
        }
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setError("");
        setSuccess("");
        try {
            const result = await updateUser(userId, form, jwt);
            if (result?.id) {
                setSuccess("User updated successfully!");
                setTimeout(() => navigate("/user/" + result.id), 1200);
            } else {
                setError(result?.message || "Update failed.");
            }
        } catch (err) {
            setError("Update failed.");
        }
    }

    return (
        <div className="form-box" role="main" aria-label="Update user form">
            <Menu />
            <form onSubmit={handleSubmit} className="form-box__form" aria-labelledby="update-title">
                <h2 id="update-title" className="form-box__title">Update User</h2>
                {error && (
                    <div className="form-box__error" role="alert">
                        <strong>Error:</strong> {typeof error === "string" ? error : JSON.stringify(error, null, 2)}
                    </div>
                )}
                {success && (
                    <div className="form-box__success" role="status">
                        {success}
                    </div>
                )}

                <label htmlFor="name" className="form-box__label">Name</label>
                <input
                    id="name"
                    name="name"
                    type="text"
                    className="form-box__input"
                    placeholder="Name"
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
                    placeholder="Phone Number"
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
                />

                <fieldset className="form-box__fieldset">
                    <legend className="form-box__legend">Address</legend>
                    <label htmlFor="line1" className="form-box__label">Line 1</label>
                    <input
                        id="line1"
                        name="address.line1"
                        type="text"
                        className="form-box__input"
                        placeholder="Line 1"
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
                        placeholder="Line 2"
                        value={form.address.line2}
                        onChange={handleChange}
                    />
                    <label htmlFor="line3" className="form-box__label">Line 3</label>
                    <input
                        id="line3"
                        name="address.line3"
                        type="text"
                        className="form-box__input"
                        placeholder="Line 3"
                        value={form.address.line3}
                        onChange={handleChange}
                    />
                    <label htmlFor="town" className="form-box__label">Town</label>
                    <input
                        id="town"
                        name="address.town"
                        type="text"
                        className="form-box__input"
                        placeholder="Town"
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

                <button type="submit" className="form-box__button">Update User</button>
            </form>
        </div>
    );
}
