package com.gdg.blackjackapi.domain;

import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.domain.Round.Round;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    private Long chips;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Round> rounds = new ArrayList<>();

    @Builder
    public Game(Player player) {
        this.chips = 10000L;
        this.date = LocalDate.now();
        this.player = player;

        Round round = new Round(0L, this);
        this.rounds.add(round);
    }

    public void update(long chips, Player player) {
        this.chips = chips;
        this.player = player;
    }

    public Round getLatestRound() {
        if (rounds.isEmpty()) return null;
        return rounds.get(rounds.size() - 1);
    }
}
