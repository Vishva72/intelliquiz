package org.bits.IntelliQuiz.controller;

import org.bits.IntelliQuiz.dto.CreateAdminRequest;
import org.bits.IntelliQuiz.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // -------------------- CREATE ADMIN --------------------

    @PostMapping
    public ResponseEntity<Void> createAdmin(
            @RequestBody CreateAdminRequest request) {

        adminService.createAdmin(
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
