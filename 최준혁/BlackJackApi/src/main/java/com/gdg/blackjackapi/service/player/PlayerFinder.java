package com.gdg.blackjackapi.service.player;

import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.exception.player.PlayerNotFoundException;
import com.gdg.blackjackapi.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerFinder {
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public Player findByIdOrThrow(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
}
