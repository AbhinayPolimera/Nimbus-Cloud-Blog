package com.phoenix.blog.controller;

import com.phoenix.blog.dto.AuthenticationResponse;
import com.phoenix.blog.dto.LoginRequest;
import com.phoenix.blog.dto.RefreshTokenRequest;
import com.phoenix.blog.dto.RegisterRequest;
import com.phoenix.blog.service.AuthService;
import com.phoenix.blog.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.hibernate.NonUniqueResultException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private  AuthService authService;
    private  RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ModelAndView verifyAccount(@PathVariable String token) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            authService.verifyAccount(token);
            modelAndView.setViewName("success");
        }catch (NonUniqueResultException ex){
            modelAndView.setViewName("error");
            return modelAndView;
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
