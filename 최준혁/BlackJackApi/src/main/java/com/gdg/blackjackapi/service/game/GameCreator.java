package com.gdg.blackjackapi.service.game;

import com.gdg.blackjackapi.domain.Game;
import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.dto.Game.GameSaveRequestDto;
import com.gdg.blackjackapi.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameCreator {
    private final GameRepository gameRepository;

    public Game create(Player player, GameSaveRequestDto gameSaveRequestDto) {
        Game game = Game.builder()
                .player(player)
                .build();

        return gameRepository.save(game);
    }
}
