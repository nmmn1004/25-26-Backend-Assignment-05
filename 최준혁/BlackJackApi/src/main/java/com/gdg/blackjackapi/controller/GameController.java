package com.gdg.blackjackapi.controller;

import com.gdg.blackjackapi.dto.Game.GameSaveRequestDto;
import com.gdg.blackjackapi.dto.Game.GameInfoResponseDto;
import com.gdg.blackjackapi.service.game.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<GameInfoResponseDto> saveGame(Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.saveGame(principal));
    }

    @GetMapping("/{gameId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<GameInfoResponseDto> getGame(Principal principal, @PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGame(principal, gameId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<GameInfoResponseDto>> getAllGame(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGame(principal));
    }

    @GetMapping("/result/{gameId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<GameInfoResponseDto> getResult(Principal principal, @PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameResult(principal, gameId));
    }

    @DeleteMapping("/{gameId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<GameInfoResponseDto> deleteGame(Principal principal, @PathVariable Long gameId) {
        gameService.deleteGame(principal, gameId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
