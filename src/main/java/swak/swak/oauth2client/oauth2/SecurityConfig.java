package swak.swak.oauth2client.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    public SecurityConfig(OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .oauth2Login(oAuth2LoginConfig -> oAuth2LoginConfig
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(this.oAuth2UserService()))
                        .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                                .baseUri("/api/auth/{action}/oauth2/code/{registrationId}"))
                        .successHandler(oAuth2AuthenticationSuccessHandler))
                .sessionManagement(sessionManagementConfig -> sessionManagementConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    @Bean
    OAuth2UserService oAuth2UserService() {
        return new OAuth2UserService();
    }
}
