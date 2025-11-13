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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rounds")
public class RoundController {
    private final RoundService roundService;

    @PostMapping
    public ResponseEntity<RoundInfoResponseDto> saveRound(@RequestBody RoundSaveRequestDto roundSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roundService.saveRound(roundSaveRequestDto));
    }

    @GetMapping("/recent/{gameId}")
    public ResponseEntity<RoundInfoResponseDto> getRound(@PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(roundService.getLatestRoundByGame(gameId));
    }

    @GetMapping("/all/{gameId}")
    public ResponseEntity<List<RoundInfoResponseDto>> getAllGame(@PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(roundService.getAllRoundsByGame(gameId));
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<RoundInfoResponseDto> updateLatestRound(@PathVariable Long gameId, @RequestBody RoundSaveRequestDto roundSaveRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(roundService.updateLatestRound(gameId, roundSaveRequestDto));
    }

    @PatchMapping("/result/{gameId}")
    public ResponseEntity<RoundInfoResponseDto> updateRoundResult(@PathVariable Long gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(roundService.updateRoundResult(gameId));
    }

    @DeleteMapping("/{roundId}")
    public ResponseEntity<RoundInfoResponseDto> deleteRound(@PathVariable Long roundId) {
        roundService.deleteRound(roundId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
