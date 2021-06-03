package com.example.businesscurrencyexchange;

import com.business.currency.exchange.core.service.ExchangeService;
import com.business.currency.exchange.security.api.AuthController;
import com.business.currency.exchange.security.filter.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {AuthController.class, ExchangeService.class, JwtProvider.class})
@WebMvcTest
public class BusinessCurrencyExchangeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/exchange")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/v2/exchange")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/exchange")).andReturn();
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/exchange")).andReturn();
    }

}

