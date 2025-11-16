package com.gdg.blackjackapi.service;

import com.gdg.blackjackapi.domain.Card.Card;
import com.gdg.blackjackapi.domain.Card.CardOwner;
import com.gdg.blackjackapi.domain.Card.CardUtil;
import com.gdg.blackjackapi.domain.Game;
import com.gdg.blackjackapi.domain.Round.Round;
import com.gdg.blackjackapi.domain.Round.RoundResult;
import com.gdg.blackjackapi.dto.Round.RoundInfoResponseDto;
import com.gdg.blackjackapi.dto.Round.RoundSaveRequestDto;
import com.gdg.blackjackapi.repository.RoundRepository;
import com.gdg.blackjackapi.service.game.GameFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoundService {
    private final RoundRepository roundRepository;
    private final GameFinder gameFinder;
    private final CardUtil cardUtil = new CardUtil();

    @Transactional
    public RoundInfoResponseDto saveRound(Principal principal, Long gameId, RoundSaveRequestDto roundSaveRequestDto) {
        Game game = gameFinder.findByIdOrThrow(gameId);
        checkPlayerAccess(principal, game);

        if (roundSaveRequestDto.getBettingChips() > game.getChips()) {
            throw new IllegalArgumentException("베팅 칩이 보유 칩보다 많습니다.");
        }

        Round round = new Round(roundSaveRequestDto.getBettingChips(), game);

        round.getCards().add(new Card(CardOwner.PLAYER, round));
        round.getCards().add(new Card(CardOwner.OPPONENT, round));

        game.getRounds().add(round);
        game.update(game.getChips() - roundSaveRequestDto.getBettingChips(), game.getPlayer());

        roundRepository.save(round);
        return RoundInfoResponseDto.from(round);
    }

    @Transactional(readOnly = true)
    public RoundInfoResponseDto getLatestRoundByGame(Principal principal, Long gameId) {
        Game game = gameFinder.findByIdOrThrow(gameId);
        checkPlayerAccess(principal, game);

        Round latest = game.getRounds().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("아직 생성된 라운드가 없습니다."));

        return RoundInfoResponseDto.from(latest);
    }

    @Transactional(readOnly = true)
    public List<RoundInfoResponseDto> getAllRoundsByGame(Principal principal, Long gameId) {
        Game game = gameFinder.findByIdOrThrow(gameId);
        checkPlayerAccess(principal, game);

        return game.getRounds().stream()
                .map(RoundInfoResponseDto::from)
                .toList();
    }

    @Transactional
    public RoundInfoResponseDto updateLatestRound(Principal principal, Long gameId, RoundSaveRequestDto roundSaveRequestDto) {
        Game game = gameFinder.findByIdOrThrow(gameId);
        checkPlayerAccess(principal, game);

        Round latest = game.getRounds().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("수정할 라운드가 존재하지 않습니다."));

        if (roundSaveRequestDto.getBettingChips() != null) {
            latest.updateBettingChips(roundSaveRequestDto.getBettingChips());
        }

        game.update(game.getChips() - latest.getBettingChips(), game.getPlayer());
        return RoundInfoResponseDto.from(latest);
    }

    @Transactional
    public RoundInfoResponseDto updateRoundResult(Principal principal, Long gameId) {
        Game game = gameFinder.findByIdOrThrow(gameId);
        checkPlayerAccess(principal, game);

        Round latest = game.getRounds().stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("수정할 라운드가 존재하지 않습니다."));

        List<Card> cards = latest.getCards();
        int playerScore = cardUtil.calculateHandCard(cards.stream()
                .filter(c -> c.getOwner() == CardOwner.PLAYER)
                .flatMap(c -> List.of(c.getCard1(), c.getCard2()).stream())
                .toList());

        int opponentScore = cardUtil.calculateHandCard(cards.stream()
                .filter(c -> c.getOwner() == CardOwner.OPPONENT)
                .flatMap(c -> List.of(c.getCard1(), c.getCard2()).stream())
                .toList());

        long modifiedChips = game.getChips();
        RoundResult roundResult;

        if (playerScore > 21) {
            modifiedChips -= latest.getBettingChips();
            roundResult = RoundResult.LOSE;
        } else if (opponentScore > 21) {
            modifiedChips += latest.getBettingChips();
            roundResult = RoundResult.WIN;
        } else if (playerScore == opponentScore) {
            roundResult = RoundResult.DRAW;
        } else if (playerScore == 21 || opponentScore > playerScore) {
            modifiedChips -= latest.getBettingChips();
            roundResult = RoundResult.LOSE;
        } else {
            modifiedChips += latest.getBettingChips();
            roundResult = RoundResult.WIN;
        }

        latest.updateResult(roundResult);
        game.update(modifiedChips, game.getPlayer());

        return RoundInfoResponseDto.from(latest);
    }

    @Transactional
    public void deleteRound(Principal principal, Long roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new IllegalArgumentException("라운드를 찾을 수 없습니다. id=" + roundId));
        checkPlayerAccess(principal, round.getGame());

        roundRepository.delete(round);
    }

    private void checkPlayerAccess(Principal principal, Game game) {
        Long playerId = Long.parseLong(principal.getName());
        if (!game.getPlayer().getId().equals(playerId)) {
            throw new SecurityException("접근 권한이 없습니다.");
        }
    }
}
