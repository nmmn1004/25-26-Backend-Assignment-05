package com.gdg.blackjackapi.controller;

import com.gdg.blackjackapi.dto.Player.PlayerInfoResponseDto;
import com.gdg.blackjackapi.dto.Player.PlayerSaveRequestDto;
import com.gdg.blackjackapi.dto.Player.PlayerSignUpDto;
import com.gdg.blackjackapi.dto.Token.TokenDto;
import com.gdg.blackjackapi.service.player.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("signup")
    public ResponseEntity<TokenDto> signUp(@Valid @RequestBody PlayerSignUpDto playerSignUpDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.signUp(playerSignUpDto));
    }

    @GetMapping
    public ResponseEntity<PlayerInfoResponseDto> getPlayer(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.findPlayerByPrincipal(principal));
    }

    @PatchMapping
    public ResponseEntity<PlayerInfoResponseDto> updatePlayer(Principal principal, @Valid @RequestBody PlayerSaveRequestDto playerRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.updatePlayer(principal, playerRequestDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlayer(Principal principal) {
        playerService.deletePlayer(principal);
        return ResponseEntity.noContent().build();
    }
}
