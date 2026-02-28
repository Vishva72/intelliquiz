package org.bits.IntelliQuiz.service;

import org.bits.IntelliQuiz.entities.Admin;
import org.bits.IntelliQuiz.entities.Quiz;
import org.bits.IntelliQuiz.exceptions.EmailAlreadyRegisteredException;
import org.bits.IntelliQuiz.exceptions.QuizNotFoundException;
import org.bits.IntelliQuiz.repository.AdminRepository;
import org.bits.IntelliQuiz.repository.QuizRepository;
import org.bits.IntelliQuiz.security.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository,
                            QuizRepository quizRepository,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.quizRepository = quizRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // signup
    @Override
    public Admin createAdmin(String email, String rawPassword) {
        if (adminRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException(email);
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        Admin admin = new Admin(email, encodedPassword);
        return adminRepository.save(admin);
    }

    // get current admin from security context
    @Override
    public Admin getCurrentAdmin() {
        Long adminId = SecurityUtils.getCurrentUserId();

        return adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new IllegalStateException("Authenticated admin not found"));
    }

    // ownership validation without adminId parameter
    @Override
    public void validateAdminOwnership(Long quizId) {

        Admin admin = getCurrentAdmin();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() ->
                        new QuizNotFoundException(quizId));

        if (!quiz.getAdmin().getId().equals(admin.getId())) {
            throw new SecurityException(
                    "Admin does not have permission to access this quiz");
        }
    }
}

