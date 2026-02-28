// src/pages/ParticipantDashboard.jsx
import { useEffect, useState } from "react";
import { getParticipantQuizzesApi } from "../api/participantApi";
import { useNavigate } from "react-router-dom";

export default function ParticipantDashboard() {
  const [quizzes, setQuizzes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    load();
  }, []);

  const load = async () => {
    const res = await getParticipantQuizzesApi();
    setQuizzes(res.data);
  };

  return (
    <div>
      <h2>Participant Dashboard</h2>

      {quizzes.map((q) => (
        <div key={q.quizId}>
          {q.quizTitle} - {q.state}

          <button onClick={() => navigate(`/quiz/${q.quizId}`)}>
            Open
          </button>
        </div>
      ))}
    </div>
  );
}