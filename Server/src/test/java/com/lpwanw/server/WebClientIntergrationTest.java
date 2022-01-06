package com.lpwanw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class WebClientIntergrationTest {
    @Test
    void shoudbe(){
        WebClientPL webClient = new WebClientPL();
        Flux<StockPice> prices =  webClient.priceFor("SYMBOL");
        Assertions.assertNotNull(prices);
        Assertions.assertTrue(prices.take(5).count().block() > 0);

    }
}