package com.gdg.blackjackapi.repository;

import com.gdg.blackjackapi.domain.Card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
