package com.business.currency.exchange.core.service.impl;

import com.business.currency.exchange.core.clients.PeruApisClient;
import com.business.currency.exchange.core.dto.ExchangeDto;
import com.business.currency.exchange.core.dto.peruapi.PeruApiDto;
import com.business.currency.exchange.core.entity.ExchangeRatesEntity;
import com.business.currency.exchange.core.enums.IsoCurrencyCode;
import com.business.currency.exchange.core.repository.ExchangeRatesRepository;
import com.business.currency.exchange.core.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    PeruApisClient peruApisClient;

    ExchangeRatesRepository exchangeRatesRepository;

    @Autowired
    @Qualifier("jdbcScheduler")
    private Scheduler jdbcScheduler;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    public ExchangeServiceImpl(PeruApisClient peruApisClient, ExchangeRatesRepository exchangeRatesRepository) {
        this.peruApisClient = peruApisClient;
        this.exchangeRatesRepository = exchangeRatesRepository;
    }

    @Override
    public Mono<ExchangeDto> processOnlineExchange(ExchangeDto exchangeDto) {
        PeruApiDto onLineExchange = peruApisClient.exchange(new PeruApiDto());

        exchangeDto.setExchangeRate(exchangeDto.getCurrencySource().compareTo(IsoCurrencyCode.PEN) == 0 ?
                Double.parseDouble(onLineExchange.getData().getBuy()) :
                Double.parseDouble(onLineExchange.getData().getSell()));

        exchangeDto.setExchange(exchangeDto.getCurrencySource().compareTo(IsoCurrencyCode.PEN) == 0 ?
                exchangeDto.getAmount() / Double.parseDouble(onLineExchange.getData().getBuy()) :
                exchangeDto.getAmount() * Double.parseDouble(onLineExchange.getData().getSell()));

        return Mono.defer(() -> Mono.just(exchangeDto))
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<ExchangeDto> processExchange(ExchangeDto exchangeDto) {

        ExchangeRatesEntity exchangeRatesEntity = exchangeRatesRepository.findByIsoCurrencySourceAndIsoCurrencyTarget(exchangeDto.getCurrencySource().name(), exchangeDto.getCurrencyTarget().name());
        exchangeDto.setExchangeRate(exchangeRatesEntity.getExchangeRate());
        exchangeDto.setExchange(exchangeDto.getCurrencySource().compareTo(IsoCurrencyCode.PEN) == 0 ?
                exchangeDto.getAmount() / exchangeRatesEntity.getExchangeRate() :
                exchangeDto.getAmount() * exchangeRatesEntity.getExchangeRate());

        return Mono.defer(() -> Mono.just(exchangeDto))
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<ExchangeDto> updateExchange(ExchangeDto exchangeDto, Long idExchange) {
        return Mono.fromCallable(() -> transactionTemplate.execute(status -> {
            exchangeRatesRepository.findById(idExchange).ifPresent(s -> {
                s.setExchangeRate(exchangeDto.getExchangeRate());
                exchangeRatesRepository.save(s);
            });
            return exchangeDto;
        })).subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<ExchangeRatesEntity> findAllExchange() {
        return Flux.defer(() -> Flux.fromIterable(exchangeRatesRepository.findAll())).subscribeOn(jdbcScheduler);
    }
}
