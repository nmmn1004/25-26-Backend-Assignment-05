package com.gdg.blackjackapi.dto.Token;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {
    private String accessToken;
}
