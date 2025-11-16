package com.gdg.blackjackapi.repository;

import com.gdg.blackjackapi.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayerId(Long playerId);
}
