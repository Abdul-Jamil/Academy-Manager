package com.AjAkrampoor.Academy.shared.security;

import com.AjAkrampoor.Academy.roles.domain.model.Permission;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.shared.dto.UserResponse;
import com.AjAkrampoor.Academy.users.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    @Value("${jwt.access-token-expiration}") // 3600000 (20 Minutes)
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}") // 1800000 (30 Minutes)
    private long refreshTokenExpiration;

    public LoginController(AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();
        Role role = userDetails.getRole();

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        ResponseCookie accessCookie = createAccessTokenCookie(accessToken);
        ResponseCookie refreshCookie = createRefreshTokenCookie(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(buildUserResponse(user, role));
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserResponse> refresh(
            @CookieValue(name = "refresh_token") String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        CustomUserDetails userDetails =
                (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new IllegalArgumentException("Refresh token expired or invalid");
        }

        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        ResponseCookie accessCookie = createAccessTokenCookie(newAccessToken);
        ResponseCookie refreshCookie = createRefreshTokenCookie(newRefreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(buildUserResponse(userDetails.getUser(), userDetails.getRole()));
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        ResponseCookie accessCookie = ResponseCookie
                .from("access_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie refreshCookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }

    private UserResponse buildUserResponse(User user, Role role) {
        Set<Permission> permissions = role.getPermissions();

        return new UserResponse(
                user.getUserId().asString(),
                user.getUserName().getValue(),
                user.getLanguage(),
                role.getName().getValue(),
                permissions
        );
    }

    private ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(accessTokenExpiration / 1000)
                .sameSite("Strict")
                .build();
    }

    private ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh")
                .maxAge(refreshTokenExpiration / 1000)
                .sameSite("Strict")
                .build();
    }
}