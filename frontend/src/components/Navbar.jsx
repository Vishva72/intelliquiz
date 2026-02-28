// src/components/Navbar.jsx

import { useNavigate } from "react-router-dom";

export default function Navbar() {

  const navigate = useNavigate();

  const role = localStorage.getItem("role");

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const goDashboard = () => {
    if (role === "ROLE_ADMIN") {
      navigate("/admin");
    } else {
      navigate("/participant");
    }
  };

  return (
    <div className="navbar">

      <div className="nav-left" onClick={goDashboard}>
        AssessAI
      </div>

      <div className="nav-right">

        {role === "ROLE_ADMIN" && (
          <button onClick={() => navigate("/admin")}>
            Dashboard
          </button>
        )}

        {role === "ROLE_PARTICIPANT" && (
          <button onClick={() => navigate("/participant")}>
            My Quizzes
          </button>
        )}

        <button className="logout-btn" onClick={logout}>
          Logout
        </button>

      </div>

    </div>
  );
}