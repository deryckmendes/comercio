package com.comercio.comercio_auth.auth;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.comercio.comercio_auth.auth.dto.LoginRequestDTO;
import com.comercio.comercio_auth.auth.dto.RabbitDTO;
import com.comercio.comercio_auth.auth.dto.RegisterDTO;
import com.comercio.comercio_auth.infra.security.TokenService;
import com.comercio.comercio_auth.rabbitmq.RabbitProducer;
import com.comercio.comercio_auth.user.User;
import com.comercio.comercio_auth.user.UserRepository;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RabbitProducer rabbitProducer;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            TokenService tokenService,
            RabbitProducer rabbitProducer) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.rabbitProducer = rabbitProducer;
    }

    public Map<String, ResponseCookie> login(LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        ResponseCookie accessCookie = ResponseCookie.from("token", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Strict")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .sameSite("Strict")
                .build();

        return Map.of(
                "accessCookie", accessCookie,
                "refreshCookie", refreshCookie);
    }

    public ResponseCookie refreshAccessToken(String refreshToken) {
        String email = tokenService.validateToken(refreshToken);
        if (email.isBlank()) {
            throw new RuntimeException("Refresh token inválido");
        }

        UserDetails userDetails = userRepository.findByEmail(email);

        if (!(userDetails instanceof User)) {
            throw new IllegalStateException("UserDetails instance is not of type User");
        }

        User user = (User) userDetails;
        String newAccessToken = tokenService.generateAccessToken(user);

        return ResponseCookie.from("token", newAccessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Strict")
                .build();
    }

    public List<ResponseCookie> logout() {
        ResponseCookie access = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        ResponseCookie refresh = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return List.of(access, refresh);
    }

    public void register(RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.email(), encryptedPassword, data.role());
        this.userRepository.save(user);

        RabbitDTO rabbitDTO = new RabbitDTO(user.getId().toString());
        rabbitProducer.sendUserId(rabbitDTO);
    }
}
