package com.gdg.blackjackapi.repository;

import com.gdg.blackjackapi.domain.Round.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
}
