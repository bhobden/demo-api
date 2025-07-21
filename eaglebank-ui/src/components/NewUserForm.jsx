import { useState } from "react";
import { createUser } from "../api";
import { useNavigate } from 'react-router-dom';

export default function NewUserForm() {
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
    const [error, setError] = useState(null);
    const navigate = useNavigate();

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
        const result = await createUser(form);
        console.log("Created user:", result);

        if (result?.id) {
            navigate("/", { state: { prefillUsername: result.id } });
        } else {
            setError(result);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            {error && (
                <div style={{ color: "red", marginBottom: "1rem" }}>
                    <strong>Error:</strong> {JSON.stringify(error, null, 2)}
                </div>
            )}
            <h2>Create New User</h2>
            <input name="name" placeholder="Name" value={form.name} onChange={handleChange} />
            <input name="email" placeholder="Email" value={form.email} onChange={handleChange} />
            <input name="phoneNumber" placeholder="Phone Number" value={form.phoneNumber} onChange={handleChange} />
            <input name="password" type="password" placeholder="Password" value={form.password} onChange={handleChange} />

            <fieldset>
                <legend>Address</legend>
                <input name="address.line1" placeholder="Line 1" value={form.address.line1} onChange={handleChange} />
                <input name="address.line2" placeholder="Line 2" value={form.address.line2} onChange={handleChange} />
                <input name="address.line3" placeholder="Line 3" value={form.address.line3} onChange={handleChange} />
                <input name="address.town" placeholder="Town" value={form.address.town} onChange={handleChange} />
                <input name="address.county" placeholder="County" value={form.address.county} onChange={handleChange} />
                <input name="address.postcode" placeholder="Postcode" value={form.address.postcode} onChange={handleChange} />
            </fieldset>

            <button type="submit">Create User</button>
        </form>
    );
}
