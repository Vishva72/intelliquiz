package org.bits.IntelliQuiz.controller;

import org.bits.IntelliQuiz.dto.LoginRequest;
import org.bits.IntelliQuiz.dto.LoginResponse;
import org.bits.IntelliQuiz.security.CustomUserDetails;
import org.bits.IntelliQuiz.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        String token = jwtUtil.generateToken(user);

        String role = user.getAuthorities()
                .stream()
                .findFirst()
                .map(a -> a.getAuthority())
                .orElse("UNKNOWN");


        return ResponseEntity.ok(new LoginResponse(token, role, user.getUsername()));

    }


}
