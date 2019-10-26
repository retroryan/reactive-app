package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Date;

@Log4j2
@RestController // <1>
@RequestMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)  // <2>
@org.springframework.context.annotation.Profile("classic")
class ProfileRestController {

    private final MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
    private final ProfileService profileRepository;

    ProfileRestController(ProfileService profileRepository) {
        this.profileRepository = profileRepository;
    }

    // <3>
    @GetMapping
    Publisher<Profile> getAll() {
        return this.profileRepository.all();
    }

    // <4>
    @GetMapping("/{id}")
    Publisher<Profile> getById(@PathVariable("id") String id) {
        return this.profileRepository.get(id);
    }

    // <5>
    @PostMapping
    Publisher<ResponseEntity<Profile>> create(@RequestBody Profile profile) {
        return this.profileRepository
            .create(profile.getEmail())
            .map(p -> ResponseEntity.created(URI.create("/profiles/" + p.getId()))
                .contentType(mediaType)
                .build());
    }

    @DeleteMapping("/{id}")
    Publisher<Profile> deleteById(@PathVariable String id) {
        return this.profileRepository.delete(id);
    }

    @PutMapping("/{id}")
    Publisher<ResponseEntity<Profile>> updateById(@PathVariable String id, @RequestBody Profile profile) {
        return Mono
            .just(profile)
            .flatMap(p -> this.profileRepository.update(id, p.getEmail()))
            .map(p -> ResponseEntity
                .ok()
                .contentType(this.mediaType)
                .build());
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> getStreaming() throws URISyntaxException {

        Flux<String> input = Flux.<String>generate(sink -> sink.next(String.format("{ message: 'got message', date: '%s' }", new Date())))
                .delayElements(Duration.ofSeconds(1));

        log.info("PART THREE");

        WebSocketClient client = new ReactorNettyWebSocketClient();
        EmitterProcessor<String> output = EmitterProcessor.create();

        log.info("PART FOUR");

        Mono<Void> sessionMono = client.execute(URI.create("ws://echo.websocket.org"), session -> session.send(input.map(session::textMessage))
                .thenMany(session.receive().map(WebSocketMessage::getPayloadAsText).subscribeWith(output).then()).then());

        log.info("PART FIVE");


        return output.doOnSubscribe(s -> sessionMono.subscribe());
    }
}
