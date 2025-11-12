package com.gdg.blackjackapi.security;

import com.gdg.blackjackapi.domain.Player.Player;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class TokenProvider {
    private static final String ROLE_CLAIM = "Role";
    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";

    private final SecretKey key;
    private final long accessTokenValidityTime;

    public TokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.access-token-validity-in-milliseconds}") long accessTokenValidityTime){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityTime = accessTokenValidityTime;
    }

    public String createAccessToken(Player player) {
        long nowTime = (new Date().getTime());
        Date accessTokenExpiredTime = new Date(nowTime + accessTokenValidityTime);

        return Jwts.builder()
                .subject(player.getId().toString())
                .claim(ROLE_CLAIM, player.getRole().name())
                .expiration(accessTokenExpiredTime)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(ROLE_CLAIM) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(ROLE_CLAIM).toString().split(","))
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
        authentication.setDetails(claims);

        return authentication;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring((BEARER + " ").length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException |
                 ExpiredJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (SecurityException e) {
            throw new RuntimeException("토큰 복호화에 실패했습니다.");
        }
    }
}
