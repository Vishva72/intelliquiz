// src/pages/QuizAttempt.jsx
import { useParams, useNavigate } from "react-router-dom";
import { submitQuizApi } from "../api/participantApi";

export default function QuizAttempt() {
  const { id } = useParams();
  const navigate = useNavigate();

  const submit = async () => {
    await submitQuizApi(id, {
      answers: {
        1: 2,
        2: 1,
      },
    });

    navigate(`/result/${id}`);
  };

  return (
    <div>
      <h2>Quiz Attempt</h2>
      <button onClick={submit}>Submit</button>
    </div>
  );
}