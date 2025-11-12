package com.gdg.blackjackapi.repository;

import com.gdg.blackjackapi.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
