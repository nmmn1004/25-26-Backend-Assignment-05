package com.gdg.blackjackapi.controller;

import com.gdg.blackjackapi.dto.Round.RoundInfoResponseDto;
import com.gdg.blackjackapi.dto.Round.RoundSaveRequestDto;
import com.gdg.blackjackapi.service.RoundService;
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
@RequestMapping("/rounds")
public class RoundController {
    private final RoundService roundService;

    @PostMapping("/{gameId}")
    public ResponseEntity<RoundInfoResponseDto> saveRound(Principal principal,
                                                          @PathVariable Long gameId,
                                                          @RequestBody RoundSaveRequestDto roundSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roundService.saveRound(principal, gameId, roundSaveRequestDto));
    }

    @GetMapping("/recent/{gameId}")
    public ResponseEntity<RoundInfoResponseDto> getRound(Principal principal,
                                                         @PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roundService.getLatestRoundByGame(principal, gameId));
    }

    @GetMapping("/all/{gameId}")
    public ResponseEntity<List<RoundInfoResponseDto>> getAllRounds(Principal principal,
                                                                   @PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roundService.getAllRoundsByGame(principal, gameId));
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<RoundInfoResponseDto> updateLatestRound(Principal principal,
                                                                  @PathVariable Long gameId,
                                                                  @RequestBody RoundSaveRequestDto roundSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roundService.updateLatestRound(principal, gameId, roundSaveRequestDto));
    }

    @PatchMapping("/result/{gameId}")
    public ResponseEntity<RoundInfoResponseDto> updateRoundResult(Principal principal,
                                                                  @PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roundService.updateRoundResult(principal, gameId));
    }

    @DeleteMapping("/{roundId}")
    public ResponseEntity<Void> deleteRound(Principal principal,
                                            @PathVariable Long roundId) {
        roundService.deleteRound(principal, roundId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
