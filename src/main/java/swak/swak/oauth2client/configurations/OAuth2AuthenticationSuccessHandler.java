package swak.swak.oauth2client.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger log;

    public OAuth2AuthenticationSuccessHandler() {
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        log.info("[onAuthenticationSuccess] principal={}", principal);

        OAuth2User oAuth2User = principal;
        KakaoOAuth2User kakaoOAuth2User = KakaoOAuth2User.of(principal);
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        kakaoOAuth2User,
                        "",
                        oAuth2User.getAuthorities()
                ));

        response.setContentType("application/json;charset=utf-8");
        request.getRequestDispatcher("/")
                .forward(request, response);
    }
}
