// src/pages/Result.jsx
import { useEffect, useState } from "react";
import { getResultApi } from "../api/participantApi";
import { useParams } from "react-router-dom";

export default function Result() {
  const { id } = useParams();
  const [result, setResult] = useState(null);

  useEffect(() => {
    load();
  }, []);

  const load = async () => {
    const res = await getResultApi(id);
    setResult(res.data);
  };

  if (!result) return <div>Loading...</div>;

  return (
    <div>
      <h2>Result</h2>
      <p>Score: {result.score}</p>
      <p>Total: {result.totalQuestions}</p>
    </div>
  );
}