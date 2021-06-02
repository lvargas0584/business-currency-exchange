package com.business.currency.exchange.core.service;

import com.business.currency.exchange.core.dto.ExchangeDto;
import com.business.currency.exchange.core.entity.ExchangeRatesEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeService {

    Mono<ExchangeDto> processExchange(ExchangeDto exchangeDto);

    Mono<ExchangeDto> processOnlineExchange(ExchangeDto exchangeDto);

    Mono<ExchangeDto> updateExchange(ExchangeDto exchangeDto, Long idExchange);

    Flux<ExchangeRatesEntity> findAllExchange();
}
