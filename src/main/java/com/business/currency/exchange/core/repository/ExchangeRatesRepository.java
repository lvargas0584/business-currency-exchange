package com.business.currency.exchange.core.repository;

import com.business.currency.exchange.core.entity.ExchangeRatesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRatesRepository extends CrudRepository<ExchangeRatesEntity,Long> {

    ExchangeRatesEntity findByIsoCurrencySourceAndIsoCurrencyTarget(String isoCurrencySource, String isoCurrencyTarget);
}
