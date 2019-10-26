package com.example.demo;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
class ProfileRepository  {

    Map<String, Profile> profilesByName = new HashMap<>();

    public <S extends Profile> Mono<S> save(S s) {
        Profile put = profilesByName.put(s.getId(), s);
        return Mono.just(s);
    }

    public <S extends Profile> Flux<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    public <S extends Profile> Flux<S> saveAll(Publisher<S> publisher) {
        return null;
    }

    public Mono<Profile> findById(String s) {
        Profile profile = profilesByName.get(s);
        return Mono.just(profile);
    }

    public Mono<Profile> findById(Publisher<String> publisher) {
        return null;
    }

    public Mono<Boolean> existsById(String s) {
        return null;
    }

    public Mono<Boolean> existsById(Publisher<String> publisher) {
        return null;
    }

    public Flux<Profile> findAll() {
        Flux<Profile> profileFlux = Flux.fromIterable(profilesByName.values());
        return profileFlux;
    }

    public Flux<Profile> findAllById(Iterable<String> iterable) {
        return null;
    }

    public Flux<Profile> findAllById(Publisher<String> publisher) {
        return null;
    }

    public Mono<Long> count() {
        return null;
    }

    public Mono<Void> deleteById(String s) {
        return null;
    }

    public Mono<Void> deleteById(Publisher<String> publisher) {
        return null;
    }

    public Mono<Void> delete(Profile profile) {
        return null;
    }

    public Mono<Void> deleteAll(Iterable<? extends Profile> iterable) {
        return null;
    }

    public Mono<Void> deleteAll(Publisher<? extends Profile> publisher) {
        return null;
    }

    public Mono<Void> deleteAll() {
        return Mono.empty();
    }
}
