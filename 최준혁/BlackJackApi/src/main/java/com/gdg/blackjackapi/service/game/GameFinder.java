package com.gdg.blackjackapi.service.game;

import com.gdg.blackjackapi.domain.Game;
import com.gdg.blackjackapi.exception.game.GameNotFoundException;
import com.gdg.blackjackapi.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameFinder {
    private final GameRepository gameRepository;

    @Transactional(readOnly = true)
    public Game findByIdOrThrow(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    @Transactional(readOnly = true)
    public List<Game> findAll() {
        return gameRepository.findAll();
    }
}
