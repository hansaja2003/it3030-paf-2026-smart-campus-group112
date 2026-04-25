package com.example.demo.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom resolver that appends {@code prompt=select_account} to every
 * Google OAuth2 authorization request, forcing the account-picker screen
 * to appear even when the user is already signed in to one Google account.
 */
@Component
public class GoogleOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    public GoogleOAuth2AuthorizationRequestResolver(ClientRegistrationRepository repo) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                repo, "/oauth2/authorization");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return customize(defaultResolver.resolve(request));
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        return customize(defaultResolver.resolve(request, clientRegistrationId));
    }

    private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest authRequest) {
        if (authRequest == null) return null;

        Map<String, Object> extraParams = new LinkedHashMap<>(authRequest.getAdditionalParameters());
        // Force Google to always show the account-chooser screen
        extraParams.put("prompt", "select_account");

        return OAuth2AuthorizationRequest.from(authRequest)
                .additionalParameters(extraParams)
                .build();
    }
}
