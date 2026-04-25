package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final UserRepository userRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${student.email:}")
    private String studentEmail;

    @Value("${lecturer.email:}")
    private String lecturerEmail;

    @Value("${technician.email:}")
    private String technicianEmail;

    @Value("${manager.email:}")
    private String managerEmail;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(request);

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String providerId = oauthUser.getAttribute("sub");

        String resolvedRole = determineRole(email);

        userRepository.findByEmail(email)
                .ifPresentOrElse(existingUser -> {
                    existingUser.setName(name);
                    existingUser.setProvider("GOOGLE");
                    existingUser.setProviderId(providerId);
                    existingUser.setRole(resolvedRole);
                    userRepository.save(existingUser);
                }, () -> userRepository.save(
                    User.builder()
                            .name(name)
                            .email(email)
                            .role(resolvedRole)
                            .provider("GOOGLE")
                            .providerId(providerId)
                            .build()
                ));

        return oauthUser;
    }

    // Determine role from configured role emails in application.properties
    private String determineRole(String email) {
        if (isSameEmail(email, adminEmail)) return "ADMIN";
        if (isSameEmail(email, studentEmail)) return "STUDENT";
        if (isSameEmail(email, lecturerEmail)) return "LECTURER";
        if (isSameEmail(email, technicianEmail)) return "TECHNICIAN";
        if (isSameEmail(email, managerEmail)) return "MANAGER";
        return "STUDENT";
    }

    private boolean isSameEmail(String email, String configuredEmail) {
        return configuredEmail != null
                && !configuredEmail.isBlank()
                && email != null
                && email.equalsIgnoreCase(configuredEmail.trim());
    }
}