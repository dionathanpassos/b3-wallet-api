package com.github.dionathanpassos.b3_wallet_api.config.security;

import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import com.github.dionathanpassos.b3_wallet_api.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedUser {

    private final UserRepository userRepository;

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("usuário nao autenticado");
        }

        User user = (User) authentication.getPrincipal();

        return userRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
