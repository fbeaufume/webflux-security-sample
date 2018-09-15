package com.adeliosys.mockup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.Duration;

/**
 * REST endpoint for various utility methods.
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/public")
    public Mono<String> publicHello(
            @RequestParam(required = false, defaultValue = "200") long duration,
            Principal principal) {
        return sayHello(principal, "public", duration);
    }

    @GetMapping("/private")
    public Mono<String> privateHello(
            @RequestParam(required = false, defaultValue = "200") long duration,
            Principal principal) {
        return sayHello(principal, "private", duration);
    }

    private Mono<String> sayHello(Principal principal, String pageType, long duration) {
        return Mono.delay(Duration.ofMillis(duration))
                .doFinally(s -> LOGGER.info("Paused for {} msec", duration))
                .then(Mono.just("Hello " + getPrincipalName(principal) + ", from a " + pageType + " page"));
    }

    private String getPrincipalName(Principal principal) {
        return principal == null ? "anonymous" : principal.getName();
    }
}
