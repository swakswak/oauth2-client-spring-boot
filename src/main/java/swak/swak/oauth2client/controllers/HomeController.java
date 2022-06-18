package swak.swak.oauth2client.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final Logger log;

    public HomeController() {
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public Object greeting(@AuthenticationPrincipal Object user) {
        return user;
    }
}
