package com.business.currency.exchange.core.dto.peruapi;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PeruApiDto {
    private String success;
    private DataDto data;
}
