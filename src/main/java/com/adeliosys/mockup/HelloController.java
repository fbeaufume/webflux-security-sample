package com.adeliosys.mockup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * REST endpoint for various utility methods.
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/public")
    public Mono<String> publicMessage(@RequestParam(required = false, defaultValue = "200") long duration) {
        return pause("Hello from a public page", duration);
    }

    @GetMapping("/private")
    public Mono<String> privateMessage(@RequestParam(required = false, defaultValue = "200") long duration) {
        return pause("Hello from a private page", duration);
    }

    private Mono<String> pause(String message, long duration) {
        return Mono.delay(Duration.ofMillis(duration))
                .doFinally(s -> LOGGER.info("Paused for {} msec", duration))
                .then(Mono.just(message));
    }
}
