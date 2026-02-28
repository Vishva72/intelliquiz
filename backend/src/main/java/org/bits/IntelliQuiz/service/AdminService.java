package org.bits.IntelliQuiz.service;

import org.bits.IntelliQuiz.entities.Admin;

public interface AdminService {

    Admin createAdmin(String email, String rawPassword);

    Admin getCurrentAdmin();

    void validateAdminOwnership(Long quizId);
}

