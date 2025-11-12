package com.gdg.blackjackapi.dto.Round;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RoundSaveRequestDto {
    @NotNull(message = "베팅 칩은 필수 입력입니다.")
    @Min(value = 100, message = "베팅 칩은 최소 100 이상이어야 합니다.")
    private Long bettingChips;

    @NotNull(message = "게임 ID는 필수 입력입니다.")
    private Long gameId;
}
