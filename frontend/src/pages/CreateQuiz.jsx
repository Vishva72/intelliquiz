// src/pages/CreateQuiz.jsx
import { useState } from "react";
import { createQuizApi } from "../api/adminApi";
import { useNavigate } from "react-router-dom";

export default function CreateQuiz() {
  const [quizTitle, setQuizTitle] = useState("");
  const [subject, setSubject] = useState("");

  const navigate = useNavigate();

  const create = async () => {
    await createQuizApi({ quizTitle, subject });
    navigate("/admin");
  };

  return (
    <div>
      <h2>Create Quiz</h2>

      <input
        placeholder="Title"
        onChange={(e) => setQuizTitle(e.target.value)}
      />

      <input
        placeholder="Subject"
        onChange={(e) => setSubject(e.target.value)}
      />

      <button onClick={create}>Create</button>
    </div>
  );
}