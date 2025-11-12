package com.gdg.blackjackapi.dto.Player;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class PlayerSaveRequestDto {
    @NotBlank(message = "닉네임은 필수 입력입니다.")
    @Size(min = 2, max = 20, message = "닉네임은 2글자 이상 20글자 이하여야 합니다.")
    private String name;
}