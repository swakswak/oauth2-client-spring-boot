package swak.swak.oauth2client.oauth2;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
class JwtTokenProvider {
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 30L;
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60L;

    private final Algorithm algorithm;

    private final String jwtSecret;

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
        this.algorithm = Algorithm.HMAC256(this.jwtSecret);
    }

    String createAccessToken(KakaoOAuth2User user) {
        return this.createToken(user.getCode(), ACCESS_TOKEN_EXPIRATION_TIME);
    }

    String createRefreshToken(KakaoOAuth2User user) {
        return this.createToken(user.getCode(), REFRESH_TOKEN_EXPIRATION_TIME);
    }

    boolean verify(String token) {
        try {
            JWT.require(algorithm)
                    .build()
                    .verify(token);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private String createToken(String subject, long time) {
        return JWT.create()
                .withSubject(subject)
                .withClaim("exp", Instant.now().getEpochSecond() + time)
                .sign(algorithm);
    }


}
