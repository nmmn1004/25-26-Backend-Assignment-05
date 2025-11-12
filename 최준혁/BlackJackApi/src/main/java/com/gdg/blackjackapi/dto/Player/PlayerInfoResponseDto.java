package com.gdg.blackjackapi.dto.Player;

import com.gdg.blackjackapi.domain.Player.Player;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Builder
@Getter
public class PlayerInfoResponseDto {
    private Long id;
    private String name;
    private String email;
    private Long record;
    private String role;
    private LocalDate date;
    private String oauthProvider;

    public static PlayerInfoResponseDto from(Player player) {
        return PlayerInfoResponseDto.builder()
                .id(player.getId())
                .name(player.getName())
                .email(player.getEmail())
                .record(player.getRecord())
                .role(player.getRole().name())
                .date(player.getDate())
                .oauthProvider(player.getOauthProvider())
                .build();
    }
}
