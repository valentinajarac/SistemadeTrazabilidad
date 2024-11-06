package com.traceability.service;

import com.traceability.dto.AuthRequest;
import com.traceability.dto.AuthResponse;
import com.traceability.exception.BadRequestException;
import com.traceability.model.User;
import com.traceability.repository.UserRepository;
import com.traceability.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthResponse authenticate(AuthRequest request) {
        try {
            log.debug("Attempting authentication for user: {}", request.getUsuario());

            if (!userRepository.existsByUsuario(request.getUsuario())) {
                throw new BadRequestException("Usuario no encontrado");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsuario(),
                            request.getPassword()
                    )
            );

            User user = (User) userDetailsService.loadUserByUsername(request.getUsuario());
            String token = jwtService.generateToken(user);

            log.debug("Authentication successful for user: {} with role: {}",
                    request.getUsuario(), user.getRole());

            return AuthResponse.builder()
                    .token(token)
                    .user(AuthResponse.UserDTO.builder()
                            .id(user.getId().toString())
                            .nombreCompleto(user.getNombreCompleto())
                            .role(user.getRole())
                            .usuario(user.getUsuario())
                            .municipio(user.getMunicipio())
                            .codigoTrazabilidad(user.getCodigoTrazabilidad())
                            .build())
                    .build();
        } catch (BadCredentialsException e) {
            log.error("Authentication failed for user: {}", request.getUsuario());
            throw new BadRequestException("Credenciales incorrectas");
        }
    }
}
