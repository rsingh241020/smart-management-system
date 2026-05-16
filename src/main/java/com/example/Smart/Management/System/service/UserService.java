package com.example.Smart.Management.System.service;

import com.example.Smart.Management.System.dto.LoginRequest;
import com.example.Smart.Management.System.model.Role;
import com.example.Smart.Management.System.model.User;
import com.example.Smart.Management.System.repository.UserRepository;
import com.example.Smart.Management.System.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.jwtUtil = jwtUtil;
    }

    // =====================================
    // REGISTER
    // =====================================

    public User register(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        // ✅ ADMIN CHECK

        if (
                user.getEmail()
                        .equalsIgnoreCase(
                                "rohitadmin@gmail.com"
                        )
        ) {

            user.setRole(Role.ADMIN);

        } else {

            user.setRole(Role.MEMBER);
        }

        return userRepository.save(user);
    }

    // =====================================
    // LOGIN
    // =====================================

    public String login(
            LoginRequest request
    ) {

        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "User not found"
                                )
                        );

        // ✅ PASSWORD CHECK

        if (
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        // ✅ GENERATE JWT

        return jwtUtil.generateToken(

                user.getEmail(),

                user.getRole().name(),

                // ✅ NAME
                user.getName()
        );
    }
}