// src/api/adminApi.js
import api from "./axios";

export const createQuizApi = (data) =>
  api.post("/admin/quizzes", data);

export const getAdminQuizzes = () =>
  api.get("/admin/quizzes");

export const addQuestionsApi = (quizId, data) =>
  api.post(`/admin/quizzes/${quizId}/questions`, data);

export const addParticipantsApi = (quizId, data) =>
  api.post(`/admin/quizzes/${quizId}/participants`, data);

export const scheduleQuizApi = (quizId, data) =>
  api.put(`/admin/quizzes/${quizId}/schedule`, data);

export const publishResultsApi = (quizId) =>
  api.post(`/admin/quizzes/${quizId}/publish-results`);