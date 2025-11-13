package com.gdg.blackjackapi.service.auth;

import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.domain.Player.Role;
import com.gdg.blackjackapi.dto.Player.PlayerInfoResponseDto;
import com.gdg.blackjackapi.dto.Token.TokenDto;
import com.gdg.blackjackapi.dto.User.UserInfoDto;
import com.gdg.blackjackapi.repository.PlayerRepository;
import com.gdg.blackjackapi.security.TokenProvider;
import com.gdg.blackjackapi.service.player.PlayerCreator;
import com.gdg.blackjackapi.service.player.PlayerFinder;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${google.gcp-project-id}")
    private String gcpProjectId;

    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${google.client-secret}")
    private String googleClientSecret;

    @Value("${google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${google.token-url}")
    private String googleTokenUrl;

    private final PlayerFinder playerFinder;
    private final PlayerCreator playerCreator;
    private final TokenProvider tokenProvider;

    private final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public String getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(googleTokenUrl, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> jsonMap = new Gson().fromJson(response.getBody(), Map.class);
            return (String) jsonMap.get("access_token");
        }

        throw new RuntimeException("구글 액세스 토큰 요청 실패: " + response.getBody());
    }

    public TokenDto loginOrSignUp(String googleAccessToken) {
        UserInfoDto userInfoDto = getUserInfo(googleAccessToken);

        if (Boolean.TRUE.equals(userInfoDto.getVerifiedEmail())) {
            Player player = playerFinder.findByEmail(userInfoDto.getEmail())
                    .orElseGet(() -> playerCreator.create(userInfoDto));
            return tokenProvider.createAccessToken(player);
        }

        throw new RuntimeException("이메일 인증이 되지 않은 유저입니다.");
    }

    private UserInfoDto getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_URL, HttpMethod.GET, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return new Gson().fromJson(response.getBody(), UserInfoDto.class);
        }

        throw new RuntimeException("구글 사용자 정보를 가져오는데 실패했습니다: " + response.getBody());
    }
}
