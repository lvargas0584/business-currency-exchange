package com.business.currency.exchange.api.v2;

import com.business.currency.exchange.core.dto.ExchangeDto;
import com.business.currency.exchange.core.entity.ExchangeRatesEntity;
import com.business.currency.exchange.core.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v2/exchange")
public class CurrencyOnlineExchangeController {

    @Autowired
    ExchangeService exchangeService;

    @PostMapping
    public Mono<ExchangeDto> exchange(@RequestBody ExchangeDto exchangeDto) {
        return exchangeService.processOnlineExchange(exchangeDto);
    }


}
