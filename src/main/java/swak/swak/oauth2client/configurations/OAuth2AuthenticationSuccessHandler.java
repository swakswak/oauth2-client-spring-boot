package swak.swak.oauth2client.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger log;

    private final JwtTokenProvider jwtTokenProvider;

    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        KakaoOAuth2User kakaoOAuth2User = KakaoOAuth2User.of(principal);
        log.info("[onAuthenticationSuccess] principal={}", principal);

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        kakaoOAuth2User,
                        "",
                        principal.getAuthorities()
                ));

        response.addCookie(
                CookieBuilder.builder("ACCESS_TOKEN", jwtTokenProvider.createAccessToken(kakaoOAuth2User))
                        .path("/")
                        .httpOnly(true)
                        .maxAge((int) JwtTokenProvider.ACCESS_TOKEN_EXPIRATION_TIME)
                        .build()
        );

        response.addCookie(
                CookieBuilder.builder("REFRESH_TOKEN", jwtTokenProvider.createRefreshToken(kakaoOAuth2User))
                        .path("/")
                        .httpOnly(true)
                        .maxAge(60 * 60 * 24 * 30)
                        .build()
        );

        response.sendRedirect("/");
    }
}
