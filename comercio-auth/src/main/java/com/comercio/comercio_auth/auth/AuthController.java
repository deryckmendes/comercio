package com.comercio.comercio_auth.auth;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comercio.comercio_auth.auth.dto.LoginRequestDTO;
import com.comercio.comercio_auth.auth.dto.RegisterDTO;
import com.comercio.comercio_auth.user.User;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    AuthController(
            AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody @Valid LoginRequestDTO data,
            HttpServletResponse response) {

        var cookies = authService.login(data);

        response.addHeader("Set-Cookie", cookies.get("accessCookie").toString());
        response.addHeader("Set-Cookie", cookies.get("refreshCookie").toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;

        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    System.out.println(cookie.getValue());
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
        }

        try {
            var newAccessCookie = authService.refreshAccessToken(refreshToken);
            response.addHeader("Set-Cookie", newAccessCookie.toString());
            return ResponseEntity.ok().build();

        } catch (Exception e) {

            ResponseCookie expiredRefresh = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0)
                    .sameSite("Strict")
                    .build();

            ResponseCookie expiredToken = ResponseCookie.from("accessToken", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0)
                    .sameSite("Strict")
                    .build();

            response.addHeader("Set-Cookie", expiredRefresh.toString());
            response.addHeader("Set-Cookie", expiredToken.toString());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/logged")
    public ResponseEntity<?> logged() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User user) {
                return ResponseEntity.ok().body(Map.of(
                        "email", user.getUsername(),
                        "roles", user.getAuthorities()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        var cookies = authService.logout();

        cookies.forEach(cookie -> response.addHeader("Set-Cookie", cookie.toString()));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        try {
            authService.register(data);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
