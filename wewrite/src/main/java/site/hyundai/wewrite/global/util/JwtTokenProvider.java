package site.hyundai.wewrite.global.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.hyundai.wewrite.global.exeception.service.BadVariableRequestException;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.exeception.service.UnAuthorizedException;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.access-token-expire}")
    private long accessTokenExpireTime;
    @Value("${security.jwt.refresh-token-expire}")
    private long refreshTokenExpireTime;

    @PostConstruct
    protected void init() {
        //secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(String accessToken, String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenExpireTime);

        return Jwts.builder()
                .setSubject("access-token")
                .claim("token", accessToken)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new UnAuthorizedException("적합하지 않은 Signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new BadVariableRequestException("JWT가 훼손되었습니다.");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new UnAuthorizedException("JWT 시간 만료");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new UnAuthorizedException("지원하지 않는 JWT 종류");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new IllegalArgumentException("JWT 가 비어있습니다.");
        } catch (NullPointerException ex) {
            log.error("JWT RefreshToken is empty");
            throw new EntityNullException("JWT 가 NULL 입니다.");
        }


    }
}
