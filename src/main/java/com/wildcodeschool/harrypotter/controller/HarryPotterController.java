package com.wildcodeschool.harrypotter.controller;

import com.wildcodeschool.harrypotter.model.HPCharacter;
import com.wildcodeschool.harrypotter.model.HPHouseCharacter;
import com.wildcodeschool.harrypotter.model.HousesContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HarryPotterController {

    private static final String API_KEY = "$2a$10$oMDJFTV2t2AFEHdGHhuXrO6a3XKbG0RciST1WItU5r01Ya1M/7BOO";
    private static final String API_URL = "https://www.potterapi.com/v1";
    private WebClient webClient = WebClient.create(API_URL);

    /***
     * Erreur : webclient exedeed limit on max bytes to buffer
     ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
     .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 10)).build();
     WebClient webClient = WebClient.builder().baseUrl(API_URL)
     .exchangeStrategies(exchangeStrategies).build();
     */

    @GetMapping("/sortingHat")
    @ResponseBody
    public String sortingHat() {

        Mono<String> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/sortingHat")
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        return call.block();
    }

    @GetMapping("/character/{id}")
    @ResponseBody
    public HPCharacter findCharacter(@PathVariable String id) {

        Mono<HPCharacter> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/characters/{id}")
                        .queryParam("key", API_KEY)
                        .build(id))
                .retrieve()
                .bodyToMono(HPCharacter.class);

        return call.block();
    }

    @GetMapping("/characters")
    @ResponseBody
    public List<HPCharacter> findCharacters() {

        Flux<HPCharacter> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/characters")
                        .queryParam("key", API_KEY)
                        .build())
                .retrieve()
                .bodyToFlux(HPCharacter.class);

        return call.collectList().block();
    }

    @GetMapping("/members/{id}")
    @ResponseBody
    public List<HPCharacter> findMembers(@PathVariable String id) {

        Flux<HousesContainer> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/houses/{id}")
                        .queryParam("key", API_KEY)
                        .build(id))
                .retrieve()
                .bodyToFlux(HousesContainer.class);

        List<HousesContainer> houses = call.collectList().block();
        HousesContainer house = houses.get(0);
        List<HPCharacter> characters = new ArrayList<>();
        for (HPHouseCharacter member : house.getMembers()) {
            characters.add(findCharacter(member.getId()));
        }

        return characters;
    }
}
