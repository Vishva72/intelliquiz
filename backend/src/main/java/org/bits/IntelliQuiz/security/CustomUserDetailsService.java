package org.bits.IntelliQuiz.security;

import org.bits.IntelliQuiz.enums.Role;
import org.bits.IntelliQuiz.repository.AdminRepository;
import org.bits.IntelliQuiz.repository.ParticipantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final ParticipantRepository participantRepository;

    public CustomUserDetailsService(
            AdminRepository adminRepository,
            ParticipantRepository participantRepository) {
        this.adminRepository = adminRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        return adminRepository.findByEmail(email)
                .map(a -> new CustomUserDetails(
                        a.getId(), a.getEmail(), a.getPassword(), Role.ADMIN))
                .orElseGet(() ->
                        participantRepository.findByEmail(email)
                                .map(p -> new CustomUserDetails(
                                        p.getId(), p.getEmail(), p.getPassword(), Role.PARTICIPANT))
                                .orElseThrow(() ->
                                        new UsernameNotFoundException("User not found"))
                );
    }
}

