package com.gdg.blackjackapi.controller;

import com.gdg.blackjackapi.dto.Token.TokenDto;
import com.gdg.blackjackapi.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/callback/google")
    public TokenDto googleCallback(@RequestParam("code") String code) {
        String googleAccessToken = authService.getGoogleAccessToken(code);
    }
}
