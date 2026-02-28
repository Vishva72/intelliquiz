// src/routes/AppRoutes.jsx
import { Routes, Route } from "react-router-dom";

import Landing from "../pages/Landing";
import Login from "../pages/Login";
import AdminDashboard from "../pages/AdminDashboard";
import CreateQuiz from "../pages/CreateQuiz";
import QuizDetails from "../pages/QuizDetails";
import ParticipantDashboard from "../pages/ParticipantDashboard";
import QuizAttempt from "../pages/QuizAttempt";
import Result from "../pages/Result";
import ProtectedRoute from "../components/ProtectedRoute";
import AdminSignup from "../pages/AdminSignup";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Landing />} />
      <Route path="/login" element={<Login />} />

      <Route
        path="/admin"
        element={
          <ProtectedRoute>
            <AdminDashboard />
          </ProtectedRoute>
        }
      />

      <Route path="/admin/create" element={<CreateQuiz />} />
      <Route path="/admin/quiz/:id" element={<QuizDetails />} />

      <Route path="/participant" element={<ParticipantDashboard />} />
      <Route path="/quiz/:id" element={<QuizAttempt />} />
      <Route path="/result/:id" element={<Result />} />
      <Route path="/admin-signup" element={<AdminSignup />} />
      
    </Routes>
  );
}