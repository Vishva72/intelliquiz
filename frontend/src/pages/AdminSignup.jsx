import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../api/axios";
import "./auth.css";

export default function AdminSignup() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSignup = async (e) => {
    e.preventDefault();

    if (!form.email || !form.password) {
      setMessage("Please fill all fields");
      return;
    }

    try {
      setLoading(true);

      await axios.post("/api/admins", form);

      setMessage("Admin account created successfully!");

      setTimeout(() => {
        navigate("/login");
      }, 1500);

    } catch (error) {
      if (error.response?.status === 409) {
        setMessage("Admin already exists");
      } else {
        setMessage("Signup failed. Try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">

      <div className="auth-card">

        <h2>Create Admin Account</h2>

        <p className="subtitle">
          Only administrators can register. Participants are invited by admins.
        </p>

        <form onSubmit={handleSignup}>

          <input
            type="email"
            name="email"
            placeholder="Admin Email"
            value={form.email}
            onChange={handleChange}
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={form.password}
            onChange={handleChange}
          />

          <button disabled={loading}>
            {loading ? "Creating..." : "Create Account"}
          </button>

        </form>

        {message && <p className="message">{message}</p>}

        <div className="footer-text">
          Already have an account?{" "}
          <span onClick={() => navigate("/login")}>
            Login
          </span>
        </div>

      </div>

    </div>
  );
}