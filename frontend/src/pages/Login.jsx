import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../api/axios";
import { AuthContext } from "../context/AuthContext";
import "./auth.css";

export default function Login() {

  const navigate = useNavigate();
  const { login } = useContext(AuthContext);

  const [form, setForm] = useState({
    email: "",
    password: ""
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    if (!form.email || !form.password) {
      setError("Please fill all fields");
      return;
    }

    try {

      setLoading(true);
      setError("");

      const res = await axios.post("/api/auth/login", form);

      const data = res.data;

      // Store token + role
      login(data);

      // Role based redirect
      if (data.role === "ROLE_ADMIN") {
        navigate("/admin");
      } else {
        navigate("/participant");
      }

    } catch (err) {

      if (err.response?.status === 401) {
        setError("Invalid credentials");
      } else {
        setError("Login failed. Please try again.");
      }

    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">

      <div className="auth-card">

        <h2>Welcome Back</h2>

        <p className="subtitle">
          Login as Administrator or Participant
        </p>

        <form onSubmit={handleLogin}>

          <input
            type="email"
            name="email"
            placeholder="Email Address"
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
            {loading ? "Signing In..." : "Login"}
          </button>

        </form>

        {error && <p className="message">{error}</p>}

        <div className="footer-text">
          Admin without account?{" "}
          <span onClick={() => navigate("/admin-signup")}>
            Create Account
          </span>
        </div>

      </div>

    </div>
  );
}