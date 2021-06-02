package com.business.currency.exchange.core.dto;

import com.business.currency.exchange.core.enums.IsoCurrencyCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeDto {

    private Double amount;
    private Double exchange;
    private IsoCurrencyCode currencySource;
    private IsoCurrencyCode currencyTarget;
    private Double exchangeRate;
}
