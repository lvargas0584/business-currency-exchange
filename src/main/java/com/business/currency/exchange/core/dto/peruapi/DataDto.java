package com.business.currency.exchange.core.dto.peruapi;


import com.business.currency.exchange.core.enums.IsoCurrencyCode;
import lombok.Value;

import java.time.LocalDate;

@Value
public class DataDto {
    private String buy;
    private String sell;
    private LocalDate date;
    private IsoCurrencyCode currency;
}
