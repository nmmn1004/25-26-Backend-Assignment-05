package com.gdg.blackjackapi.service.player;

import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.dto.Player.PlayerInfoResponseDto;
import com.gdg.blackjackapi.dto.Player.PlayerSaveRequestDto;
import com.gdg.blackjackapi.dto.Player.PlayerSignUpDto;
import com.gdg.blackjackapi.dto.Token.TokenDto;
import com.gdg.blackjackapi.repository.PlayerRepository;
import com.gdg.blackjackapi.security.TokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerCreator playerCreator;
    private final PlayerFinder playerFinder;
    private final TokenProvider tokenProvider;

    public TokenDto signUp(PlayerSignUpDto playerSignUpDto){
        Player player = playerCreator.create(playerSignUpDto);

        String accessToken = tokenProvider.createAccessToken(player);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public PlayerInfoResponseDto findPlayerByPrincipal(Principal Principal) {
        return PlayerInfoResponseDto.from(playerFinder.findByIdOrThrow(Long.parseLong(Principal.getName())));
    }

    /**
     * @param playerSaveRequestDto
     * @return
     * */
    @Transactional
    public PlayerInfoResponseDto updatePlayer(Principal principal, PlayerSaveRequestDto playerSaveRequestDto) {
        Player player = playerFinder.findByIdOrThrow(Long.parseLong(principal.getName()));

        player.update(playerSaveRequestDto.getName(), player.getRecord());

        return PlayerInfoResponseDto.from(player);
    }

//  헬퍼 클래스로 옮길 지 고민
    @Transactional
    public PlayerInfoResponseDto updatePlayer(Long playerId, Long record) {
        Player player = playerFinder.findByIdOrThrow(playerId);

        if (record != null) {
            player.update(player.getName(), record);
        }
        return PlayerInfoResponseDto.from(player);
    }

    @Transactional
    public void deletePlayer(Principal principal) {
        playerRepository.delete(playerFinder.findByIdOrThrow(Long.parseLong(principal.getName())));
    }
}
