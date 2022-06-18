package swak.swak.oauth2client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class SpringBootOauth2ClientExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOauth2ClientExampleApplication.class, args);
    }

//    @Bean
//    DateTimeFormatter isoInstantDateTimeFormatter() {
//        return DateTimeFormatter.ofPattern("yyyy-MM-ddThh:mm:ssZ");
//    }
}
