package swak.swak.oauth2client.configurations;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class KakaoOAuth2User {

    private final Long id;

    private final String code;

    private final String name;

    private final String email;

    private final LocalDateTime createdDate;

    private KakaoOAuth2User(long id, String code, String name, String email, LocalDateTime createdDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
    }

    public static KakaoOAuth2User of(OAuth2User oAuth2User) {
        Map<String, Object> properties = oAuth2User.getAttribute("properties");
        Map<String, Object> kakao_account = oAuth2User.getAttribute("kakao_account");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        return new KakaoOAuth2User(
                (Long) oAuth2User.getAttribute("id"),
                "KAKAO_" + (long) oAuth2User.getAttribute("id"),
                (String) properties.get("nickname"),
                (String) kakao_account.get("email"),
                LocalDateTime.parse(oAuth2User.getAttribute("connected_at"), dateTimeFormatter)
                );
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "KakaoOAuth2User{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
