package com.lpwanw.server;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebClientPL {
    private WebClient webClient;
    public Flux<StockPice> priceFor(String symbol) {
        return Flux.fromArray(new StockPice[0]);
    }
}
