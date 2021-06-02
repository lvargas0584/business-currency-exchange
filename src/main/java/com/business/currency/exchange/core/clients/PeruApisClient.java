package com.business.currency.exchange.core.clients;

import com.business.currency.exchange.core.config.PeruApisFeignClientConfiguration;
import com.business.currency.exchange.core.dto.peruapi.PeruApiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "peru-api", url = "${feign.peru-api.url}", configuration = PeruApisFeignClientConfiguration.class)
public interface PeruApisClient {

    @PostMapping(value = "/v1/exchange",produces = "application/json",consumes ="application/json" )
    PeruApiDto exchange(PeruApiDto peruApiDto);
}
