package com.gdg.blackjackapi.domain.Round;

import com.gdg.blackjackapi.domain.Card.CardOwner;
import com.gdg.blackjackapi.domain.Card.Card;
import com.gdg.blackjackapi.domain.Game;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bettingChips;

    private RoundResult result;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder
    public Round(long bettingChips, Game game) {
        this.bettingChips = bettingChips;
        this.result = RoundResult.PENDING;
        this.game = game;

        Card playerCard = new Card(CardOwner.PLAYER, this);
        Card opponentCard = new Card(CardOwner.OPPONENT, this);
        this.cards.add(playerCard);
        this.cards.add(opponentCard);
    }

    public void updateBettingChips(long bettingChips) {
        this.bettingChips = bettingChips;
    }

    public void updateResult(RoundResult result) {
        this.result = result;
    }

    public Card getPlayerCard() {
        return cards.stream()
                .filter(c -> c.getOwner() == CardOwner.PLAYER)
                .findFirst()
                .orElse(null);
    }

    public Card getOpponentCard() {
        return cards.stream()
                .filter(c -> c.getOwner() == CardOwner.OPPONENT)
                .findFirst()
                .orElse(null);
    }
}
