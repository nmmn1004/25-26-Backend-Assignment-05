package com.gdg.blackjackapi.service.auth;

import com.gdg.blackjackapi.domain.Player.Player;
import com.gdg.blackjackapi.domain.Player.Role;
import com.gdg.blackjackapi.dto.Token.TokenDto;
import com.gdg.blackjackapi.dto.User.UserInfoDto;
import com.gdg.blackjackapi.repository.PlayerRepository;
import com.gdg.blackjackapi.security.TokenProvider;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${google.client-id}")
    private String googleClientId;

    @Value("${google.client-secret}")
    private String googleClientSecret;

    @Value("${google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${google_token_url")
    private String googleTokenUrl;

    private final PlayerRepository playerRepository;
    private final TokenProvider tokenProvider;
    private final SecretManagerService secretManagerService;

    private final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";
    private final String GCP_PROJECT_ID = "OAuthTest";

    public TokenDto getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", )
    }

    public String getGoogleClientSecret() {
        return secretManagerService.getSecret(GCP_PROJECT_ID, googleClientSecret, "latest");
    }

    public String getGoogleClientId() {
        return secretManagerService.getSecret(GCP_PROJECT_ID, googleClientId, "latest");
    }

    private UserInfoDto getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(GOOGLE_USERINFO_URL));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
        }

        String json = responseEntity.getBody();
        return new Gson().fromJson(json, UserInfoDto.class);
    }
}
