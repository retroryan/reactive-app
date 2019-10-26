package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
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

@Log4j2 // <1>
public class BigQueryWSListner {

    public BigQueryWSListner() {
    }



    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> getStreaming() throws URISyntaxException {

        Flux<String> input = Flux.<String>generate(sink -> sink.next(String.format("{ message: 'got message', date: '%s' }", new Date())))
                .delayElements(Duration.ofSeconds(1));

        WebSocketClient client = new ReactorNettyWebSocketClient();
        EmitterProcessor<String> output = EmitterProcessor.create();

        Mono<Void> sessionMono = client.execute(URI.create("ws://echo.websocket.org"), session -> session.send(input.map(session::textMessage))
                .thenMany(session.receive().map(WebSocketMessage::getPayloadAsText).subscribeWith(output).then()).then());

        return output.doOnSubscribe(s -> sessionMono.subscribe());
    }
}
