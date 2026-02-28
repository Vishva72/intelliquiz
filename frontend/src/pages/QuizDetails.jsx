import { useParams } from "react-router-dom";
import { useState } from "react";
import {
  addQuestionsApi,
  addParticipantsApi,
  scheduleQuizApi,
  publishResultsApi
} from "../api/adminApi";

import Navbar from "../components/Navbar";
import "./quizDetails.css";

export default function QuizDetails() {

  const { id } = useParams();

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  /* ---------------- QUESTION STATE ---------------- */

  const [question, setQuestion] = useState({
    questionTitle: "",
    option1: "",
    option2: "",
    option3: "",
    option4: "",
    correctOption: 1
  });

  /* ---------------- PARTICIPANTS STATE ---------------- */

  const [emails, setEmails] = useState("");

  /* ---------------- SCHEDULE STATE ---------------- */

  const [schedule, setSchedule] = useState({
    startTime: "",
    endTime: ""
  });

  /* ---------------- HELPER: FORMAT DATETIME ---------------- */

  const formatDateTime = (value) => {
    if (!value) return "";
    return value.length === 16 ? value + ":00" : value;
  };

  /* ---------------- ADD QUESTION ---------------- */

  const handleAddQuestion = async () => {

    setMessage("");
    setError("");

    try {

      await addQuestionsApi(id, [
        {
          questionTitle: question.questionTitle,
          option1: question.option1,
          option2: question.option2,
          option3: question.option3,
          option4: question.option4,
          correctOption: Number(question.correctOption)
        }
      ]);

      setMessage("Question added successfully.");

      setQuestion({
        questionTitle: "",
        option1: "",
        option2: "",
        option3: "",
        option4: "",
        correctOption: 1
      });

    } catch (err) {
      setError(err.response?.data?.message || "Failed to add question.");
    }
  };

  /* ---------------- ADD PARTICIPANTS ---------------- */

  const handleAddParticipants = async () => {

    setMessage("");
    setError("");

    try {

      const emailList = emails
        .split(",")
        .map(e => e.trim())
        .filter(e => e.length > 0);

      await addParticipantsApi(id, {
        emails: emailList
      });

      setMessage("Participants added successfully.");
      setEmails("");

    } catch (err) {
      setError(err.response?.data?.message || "Failed to add participants.");
    }
  };

  /* ---------------- SCHEDULE QUIZ ---------------- */

  const handleScheduleQuiz = async () => {

    setMessage("");
    setError("");

    try {

      await scheduleQuizApi(id, {
        startTime: formatDateTime(schedule.startTime),
        endTime: formatDateTime(schedule.endTime)
      });

      setMessage("Quiz scheduled successfully.");

    } catch (err) {
      setError(err.response?.data?.message || "Failed to schedule quiz.");
    }
  };

  /* ---------------- PUBLISH RESULTS ---------------- */

  const handlePublishResults = async () => {

    setMessage("");
    setError("");

    try {

      await publishResultsApi(id);
      setMessage("Results published successfully.");

    } catch (err) {
      setError(err.response?.data?.message || "Failed to publish results.");
    }
  };

  return (
    <div>

      <Navbar />

      <div className="quiz-container">

        <h2>Manage Quiz</h2>

        {message && <p className="success">{message}</p>}
        {error && <p className="error">{error}</p>}

        {/* ---------------- ADD QUESTION ---------------- */}

        <div className="card">

          <h3>Add Question</h3>

          <input
            placeholder="Question Title"
            value={question.questionTitle}
            onChange={(e) =>
              setQuestion({ ...question, questionTitle: e.target.value })
            }
          />

          <input
            placeholder="Option 1"
            value={question.option1}
            onChange={(e) =>
              setQuestion({ ...question, option1: e.target.value })
            }
          />

          <input
            placeholder="Option 2"
            value={question.option2}
            onChange={(e) =>
              setQuestion({ ...question, option2: e.target.value })
            }
          />

          <input
            placeholder="Option 3"
            value={question.option3}
            onChange={(e) =>
              setQuestion({ ...question, option3: e.target.value })
            }
          />

          <input
            placeholder="Option 4"
            value={question.option4}
            onChange={(e) =>
              setQuestion({ ...question, option4: e.target.value })
            }
          />

          <select
            value={question.correctOption}
            onChange={(e) =>
              setQuestion({
                ...question,
                correctOption: Number(e.target.value)
              })
            }
          >
            <option value={1}>Correct Option 1</option>
            <option value={2}>Correct Option 2</option>
            <option value={3}>Correct Option 3</option>
            <option value={4}>Correct Option 4</option>
          </select>

          <button onClick={handleAddQuestion}>
            Add Question
          </button>

        </div>


        {/* ---------------- ADD PARTICIPANTS ---------------- */}

        <div className="card">

          <h3>Add Participants</h3>

          <textarea
            placeholder="Enter emails separated by commas"
            value={emails}
            onChange={(e) => setEmails(e.target.value)}
          />

          <button onClick={handleAddParticipants}>
            Add Participants
          </button>

        </div>


        {/* ---------------- SCHEDULE QUIZ ---------------- */}

        <div className="card">

          <h3>Schedule Quiz</h3>

          <label>Start Time</label>
          <input
            type="datetime-local"
            value={schedule.startTime}
            onChange={(e) =>
              setSchedule({ ...schedule, startTime: e.target.value })
            }
          />

          <label>End Time</label>
          <input
            type="datetime-local"
            value={schedule.endTime}
            onChange={(e) =>
              setSchedule({ ...schedule, endTime: e.target.value })
            }
          />

          <button onClick={handleScheduleQuiz}>
            Schedule Quiz
          </button>

        </div>


        {/* ---------------- PUBLISH RESULTS ---------------- */}

        <div className="card">

          <h3>Publish Results</h3>

          <button onClick={handlePublishResults}>
            Publish Results
          </button>

        </div>

      </div>

    </div>
  );
}