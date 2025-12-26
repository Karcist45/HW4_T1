package com.barbirms.hw4_t1.controllers;

import com.barbirms.hw4_t1.persistence.*;
import com.barbirms.hw4_t1.security.JwtUtils;
import com.barbirms.hw4_t1.security.RefreshTokenService;
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
    RevokeRepository revokeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

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

        TokenEntity tokenEntity = refreshTokenService.refreshToken(userDetails.getUsername());


        return ResponseEntity.ok(new JwtResponse(token, tokenEntity.token, userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        UserEntity newUser = new UserEntity(user.login, passwordEncoder.encode(user.password), user.email);

        List<String> roles = user.roles;
        Set<RoleEntity> actualRoles = new HashSet<>();

        roles.forEach(role -> {
            switch (role) {
                case "ROLE_ADMIN":
                    RoleEntity adminRole = roleRepository.findByUserRole(UserRole.ROLE_ADMIN);
                    actualRoles.add(adminRole);
                    break;
                case "ROLE_PREMIUM_USER":
                    RoleEntity premiumRole = roleRepository.findByUserRole(UserRole.ROLE_PREMIUM_USER);
                    actualRoles.add(premiumRole);
                    break;
                case "ROLE_GUEST":
                    RoleEntity guestRole = roleRepository.findByUserRole(UserRole.ROLE_GUEST);
                    actualRoles.add(guestRole);
                    break;
                default:
                    throw new RuntimeException("Invalid role");
            }
        });

        newUser.setRoles(actualRoles);
        userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        String token = refreshRequest.refresh_token;

        var idk = refreshTokenService.findByToken(token);
        return refreshTokenService.findByToken(token)
                .map(refreshTokenService::checkExpired)
                .map(tokenEntity -> tokenEntity.user)
                .map(userEntity -> {
                    String newToken = jwtUtils.generateTokenByLogin(userEntity.login);
                    return ResponseEntity.ok(new RefreshResponse(token, newToken));
                }).get();
    }

    @DeleteMapping("/signout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        String accessToken = request.accessToken;
        String refreshToken = request.refreshToken;

        refreshTokenService.deleteToken(refreshToken);
        var revoke = new RevokeEntity();
        revoke.token = accessToken;

        revokeRepository.save(revoke);
        return ResponseEntity.ok(revoke);
    }
}
