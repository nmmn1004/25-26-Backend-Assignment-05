package com.gdg.blackjackapi.dto.Game;

import com.gdg.blackjackapi.domain.Game;
import com.gdg.blackjackapi.dto.Round.RoundInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class GameInfoResponseDto {
    private Long id;
    private Long chips;
    private LocalDate date;
    private Long playerId;
    private String playerName;
    private List<RoundInfoResponseDto> rounds;

    public static GameInfoResponseDto from(Game game) {
        List<RoundInfoResponseDto> roundsDtos = game.getRounds()
                .stream()
                .map(RoundInfoResponseDto::from)
                .toList();

        return GameInfoResponseDto.builder()
                .id(game.getId())
                .chips(game.getChips())
                .date(game.getDate())
                .playerId(game.getPlayer().getId())
                .playerName(game.getPlayer().getName())
                .rounds(roundsDtos)
                .build();
    }
}
