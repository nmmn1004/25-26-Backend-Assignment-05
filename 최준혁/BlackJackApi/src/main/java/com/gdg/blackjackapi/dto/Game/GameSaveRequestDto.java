package com.gdg.blackjackapi.dto.Game;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GameSaveRequestDto {
    @NotNull(message = "플레이어 ID는 필수 입력입니다.")
    private Long playerId;
}
