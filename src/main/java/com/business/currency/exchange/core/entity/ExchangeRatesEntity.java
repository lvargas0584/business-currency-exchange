package com.business.currency.exchange.core.entity;

import javax.persistence.*;

@Entity
@Table(name = "EXCHANGE_RATES")
public class ExchangeRatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isoCurrencySource;
    private String isoCurrencyTarget;
    private Double exchangeRate;
    private Integer status;

    public ExchangeRatesEntity(String isoCurrencySource, String isoCurrencyTarget, Double exchangeRate, Integer status) {
        this.isoCurrencySource = isoCurrencySource;
        this.isoCurrencyTarget = isoCurrencyTarget;
        this.exchangeRate = exchangeRate;
        this.status = status;
    }

    public ExchangeRatesEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoCurrencySource() {
        return isoCurrencySource;
    }

    public void setIsoCurrencySource(String isoCurrencySource) {
        this.isoCurrencySource = isoCurrencySource;
    }

    public String getIsoCurrencyTarget() {
        return isoCurrencyTarget;
    }

    public void setIsoCurrencyTarget(String isoCurrencyTarget) {
        this.isoCurrencyTarget = isoCurrencyTarget;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}