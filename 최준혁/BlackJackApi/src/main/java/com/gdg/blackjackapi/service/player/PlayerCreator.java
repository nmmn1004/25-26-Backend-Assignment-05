package com.gdg.blackjackapi.service.player;

import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.domain.Player.Role;
import com.gdg.blackjackapi.dto.Player.PlayerSaveRequestDto;
import com.gdg.blackjackapi.dto.Player.PlayerSignUpDto;
import com.gdg.blackjackapi.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PlayerCreator {
    private final PlayerRepository playerRepository;

    public Player create(PlayerSignUpDto playerSignUpDto) {
        Player player = Player.builder()
                .name(playerSignUpDto.getName())
                .email(playerSignUpDto.getEmail())
                .record(0L)
                .role(Role.ROLE_USER)
                .date(LocalDate.now())
                .build();

        return playerRepository.save(player);
    }
}
