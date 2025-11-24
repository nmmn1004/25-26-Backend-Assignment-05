package com.gdg.blackjackapi.domain.Card;

import com.gdg.blackjackapi.domain.Round.Round;
import com.gdg.blackjackapi.global.util.CardUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer card1;
    private Integer card2;

    @Enumerated(EnumType.STRING)
    private CardOwner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;

    public Card(CardOwner owner, Round round) {
        this.owner = owner;
        this.round = round;

        this.card1 = CardUtil.generateRandomCards();
        this.card2 = CardUtil.generateRandomCards();
    }
}
