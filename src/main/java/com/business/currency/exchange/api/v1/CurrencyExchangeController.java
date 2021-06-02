package com.business.currency.exchange.api.v1;

import com.business.currency.exchange.core.dto.ExchangeDto;
import com.business.currency.exchange.core.entity.ExchangeRatesEntity;
import com.business.currency.exchange.core.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/exchange")
public class CurrencyExchangeController {

    @Autowired
    ExchangeService exchangeService;

    @GetMapping
    public Flux<ExchangeRatesEntity> listExchange(){
        return exchangeService.findAllExchange();
    }

    @PostMapping
    public Mono<ExchangeDto> exchange(@RequestBody ExchangeDto exchangeDto) {
        return exchangeService.processExchange(exchangeDto);
    }

    @PutMapping({"/{idExchange}"})
    public Mono<ExchangeDto> updateExchange(@RequestBody ExchangeDto exchangeDto, @PathVariable("idExchange")Long idExchange) {
        return exchangeService.updateExchange(exchangeDto,idExchange);
    }
}
