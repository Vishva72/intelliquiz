import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAdminQuizzes } from "../api/adminApi";
import Navbar from "../components/Navbar";
import "./admin.css";

export default function AdminDashboard() {

  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    loadQuizzes();
  }, []);

  const loadQuizzes = async () => {
    try {
      const res = await getAdminQuizzes();
      setQuizzes(res.data);
    } catch (err) {
      alert("Failed to load quizzes");
    } finally {
      setLoading(false);
    }
  };

  const getStateColor = (state) => {
    switch (state) {
      case "DRAFT":
        return "badge draft";
      case "SCHEDULED":
        return "badge scheduled";
      case "LIVE":
        return "badge live";
      case "CLOSED":
        return "badge closed";
      default:
        return "badge";
    }
  };

  return (
    <div className="admin-page">

      <Navbar />

      <div className="container">

        <div className="header">

          <div>
            <h2>Admin Dashboard</h2>
            <p>Manage your quizzes and participants</p>
          </div>

          <button
            className="btn-primary"
            onClick={() => navigate("/admin/create")}
          >
            + Create Quiz
          </button>

        </div>


        {loading ? (
          <p>Loading quizzes...</p>
        ) : quizzes.length === 0 ? (
          <p>No quizzes created yet.</p>
        ) : (

          <div className="quiz-grid">

            {quizzes.map((quiz) => (
              <div key={quiz.id} className="quiz-card">

                <h3>{quiz.quizTitle}</h3>

                <p>Subject: {quiz.subject}</p>

                <span className={getStateColor(quiz.state)}>
                  {quiz.state}
                </span>

                <button
                  className="btn-outline"
                  onClick={() =>
                    navigate(`/admin/quiz/${quiz.id}`)
                  }
                >
                  Manage
                </button>

              </div>
            ))}

          </div>
        )}

      </div>

    </div>
  );
}