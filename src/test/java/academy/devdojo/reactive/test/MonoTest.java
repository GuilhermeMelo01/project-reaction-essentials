package academy.devdojo.reactive.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
/* Reactive Streams
 * 1. Asynchronous
 * 2. Non-blocking
 * 3. Backpressure
 * Publisher <- (subscribe) Subscriber
 * Subscription is created
 * Publisher (onSubseribe with the subscription) -> Subscriber
 * Subscription - (request N) Subseriber
 * Publisher -> (onNext) Subscriber
 * until:
 * 1. Publisher sends ell the objects raquested.
 * 2, Publisher sends all the objects it has. (onCosplete) sobscriber and subscription wf12 be canceled
 * 3. There is an error. (onEror) -> subseriber and subseription will be canceled
 */
public class MonoTest {

    @Test
    public void monoSubscriber() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe();
        log.info("---------------------");
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();

    }

    @Test
    public void monoSubscriberConsumer() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe(s -> log.info("value {}", s));
        log.info("---------------------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();

    }

    @Test
    public void monoSubscriberConsumerError() {
        String name = "William Suane";
        Mono<String> mono = Mono.just(name)
                .map(s -> {throw new RuntimeException("testing mono with error");});


        mono.subscribe(s -> log.info("name {}", s), s -> log.error("Something bad happened"));
        mono.subscribe(s -> log.info("name {}", s), Throwable::printStackTrace);

        log.info("---------------------");

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();

    }

}

