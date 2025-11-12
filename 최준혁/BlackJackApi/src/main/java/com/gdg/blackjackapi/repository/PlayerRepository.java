package com.gdg.blackjackapi.repository;

import com.gdg.blackjackapi.domain.Player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    public Optional<Player> findByEmail(String email);
}
