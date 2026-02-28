// src/api/participantApi.js
import api from "./axios";

export const getParticipantQuizzesApi = () =>
  api.get("/participant/quizzes");

export const startQuizApi = (quizId) =>
  api.post(`/participant/${quizId}/start`);

export const submitQuizApi = (quizId, data) =>
  api.post(`/participant/${quizId}/submit`, data);

export const getResultApi = (quizId) =>
  api.get(`/participant/quizzes/${quizId}/result`);