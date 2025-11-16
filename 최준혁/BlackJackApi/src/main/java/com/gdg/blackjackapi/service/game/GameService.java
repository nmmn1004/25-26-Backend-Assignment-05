package com.gdg.blackjackapi.service.game;

import com.gdg.blackjackapi.domain.Game;
import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.dto.Game.GameInfoResponseDto;
import com.gdg.blackjackapi.dto.Game.GameSaveRequestDto;
import com.gdg.blackjackapi.repository.GameRepository;
import com.gdg.blackjackapi.service.player.PlayerFinder;
import com.gdg.blackjackapi.service.player.PlayerService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameCreator gameCreator;
    private final GameFinder gameFinder;
    private final PlayerFinder playerFinder;
    private final PlayerService playerService;

    @Transactional
    public GameInfoResponseDto saveGame(Principal principal) {
        Player player = playerFinder.findByIdOrThrow(Long.parseLong(principal.getName()));

        return GameInfoResponseDto.from(gameCreator.create(player));
    }

    @Transactional(readOnly = true)
    public GameInfoResponseDto getGame(Principal principal, Long gameId) {
        Long playerId = Long.parseLong(principal.getName());
        playerFinder.findByIdOrThrow(playerId);

        Game game = gameFinder.findByIdOrThrow(gameId);

        if (!game.getPlayer().getId().equals(playerId)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        return GameInfoResponseDto.from(game);
    }

    @Transactional(readOnly = true)
    public List<GameInfoResponseDto> getAllGame(Principal principal) {
        Player player = playerFinder.findByIdOrThrow(Long.parseLong(principal.getName()));

        List<Game> playerGames = gameRepository.findByPlayerId(player.getId());

        return playerGames.stream()
                .map(GameInfoResponseDto::from)
                .toList();
    }

    @Transactional
    public GameInfoResponseDto getGameResult(Principal principal, Long gameId) {
        Game game = gameFinder.findByIdOrThrow(gameId);

        if (!game.getPlayer().getId().equals(Long.parseLong(principal.getName()))) {
            throw new AccessDeniedException("잘못된 접근입니다.");
        }

        playerService.updatePlayer(game.getPlayer().getId(), game.getChips());

        return GameInfoResponseDto.from(game);
    }

    @Transactional
    public void deleteGame(Principal principal, Long gameId) {
        Long playerId = Long.parseLong(principal.getName());
        playerFinder.findByIdOrThrow(playerId);

        Game game = gameFinder.findByIdOrThrow(gameId);

        if (!game.getPlayer().getId().equals(playerId)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        gameRepository.delete(game);
    }
}
