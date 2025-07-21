package com.barbirms.hw4_t1.controllers;

import com.barbirms.hw4_t1.persistence.*;
import com.barbirms.hw4_t1.security.JwtUtils;
import com.barbirms.hw4_t1.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.login, user.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        UserEntity newUser = new UserEntity(user.login, passwordEncoder.encode(user.password), user.email);

        List<String> roles = user.roles;
        Set<RoleEntity> actualRoles = new HashSet<>();

        roles.forEach(role -> {
            switch (role) {
                case "admin":
                    RoleEntity adminRole = roleRepository.findByRole(UserRole.admin);
                    actualRoles.add(adminRole);
                    break;
                case "premium_user":
                    RoleEntity premiumRole = roleRepository.findByRole(UserRole.premium_user);
                    actualRoles.add(premiumRole);
                    break;
                case "guest":
                    RoleEntity guestRole = roleRepository.findByRole(UserRole.guest);
                    actualRoles.add(guestRole);
                    break;
                default:
                    throw new RuntimeException("Invalid role");
            }
        });

        newUser.roles = actualRoles;
        userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }
}
