package swak.swak.oauth2client.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserService extends DefaultOAuth2UserService {
    private final Logger log;

    public OAuth2UserService() {
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("[loadUser] userRequest={}", userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("[loadUser] oAuth2User={}", oAuth2User);
        return oAuth2User;
    }
}
