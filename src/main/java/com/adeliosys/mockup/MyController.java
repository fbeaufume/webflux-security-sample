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
public class MyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    @GetMapping("/public")
    public Mono<String> publicPause(@RequestParam(required = false, defaultValue = "200") long duration) {
        return pause(duration);
    }

    @GetMapping("/private")
    public Mono<String> privatePause(@RequestParam(required = false, defaultValue = "200") long duration) {
        return pause(duration);
    }

    private Mono<String> pause(long duration) {
        return Mono.delay(Duration.ofMillis(duration))
                .doFinally(s -> LOGGER.info("Paused for {} msec", duration))
                .then(Mono.just("Done"));
    }
}
