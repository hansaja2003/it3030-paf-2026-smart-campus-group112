package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private static final Set<String> ALLOWED_ROLES = Set.of("ADMIN", "MANAGER", "TECHNICIAN", "LECTURER", "STUDENT");

    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Transactional
    public ResponseEntity<?> updateUserRole(Long id, Map<String, String> body, Authentication authentication) {
        User targetUser = userRepository.findById(id).orElse(null);

        if (targetUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        String requestedRole = (body.getOrDefault("role", "")).trim().toUpperCase();
        if (!ALLOWED_ROLES.contains(requestedRole)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid role"));
        }

        String currentAdminEmail = authentication != null ? authentication.getName() : null;
        if (currentAdminEmail != null
                && currentAdminEmail.equalsIgnoreCase(targetUser.getEmail())
                && !"ADMIN".equals(requestedRole)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You cannot remove your own admin role"));
        }

        String previousRole = targetUser.getRole();

        targetUser.setRole(requestedRole);
        User saved = userRepository.save(targetUser);

        Notification notification = Notification.builder()
            .user(saved)
            .title("Your role has been updated")
            .message("Your account role changed from " + previousRole + " to " + requestedRole + ".")
            .type("ROLE_UPDATE")
            .referenceId(String.valueOf(saved.getId()))
            .isRead(false)
            .build();

        notificationRepository.saveAndFlush(notification);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User role updated");
        response.put("id", saved.getId());
        response.put("role", saved.getRole());
        response.put("email", saved.getEmail());
        return ResponseEntity.ok(response);
    }
}