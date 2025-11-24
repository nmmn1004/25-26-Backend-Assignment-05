package com.gdg.blackjackapi.service.round;

import com.gdg.blackjackapi.domain.Game;
import com.gdg.blackjackapi.domain.Round.Round;
import com.gdg.blackjackapi.exception.game.GameNotFoundException;
import com.gdg.blackjackapi.exception.round.RoundNotFoundException;
import com.gdg.blackjackapi.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoundFinder {
    private final RoundRepository roundRepository;

    @Transactional(readOnly = true)
    public Round findByIdOrThrow(Long roundId) {
        return roundRepository.findById(roundId)
                .orElseThrow(() -> new RoundNotFoundException(roundId));
    }
}
