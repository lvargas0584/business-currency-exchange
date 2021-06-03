package com.business.currency.exchange;

import com.business.currency.exchange.core.entity.ExchangeRatesEntity;
import com.business.currency.exchange.core.repository.ExchangeRatesRepository;
import com.business.currency.exchange.security.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableFeignClients
public class BusinessCurrencyExchangeApplication {

    @Value("${spring.datasource.maximum-pool-size}")
    private int connectionPoolSize;

    public static void main(String[] args) {
        SpringApplication.run(BusinessCurrencyExchangeApplication.class, args);
    }

    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers( "/auth",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/api-docs.yaml",
                            "/favicon.ico",
                            "/swagger-ui/**")
                    .permitAll()
                    .anyRequest().authenticated();
        }
    }

    @Bean
    public CommandLineRunner putData(ExchangeRatesRepository exchangeRatesRepository) {
        return (args) -> {
            exchangeRatesRepository.save(new ExchangeRatesEntity("USD","PEN",3.845,1));
            exchangeRatesRepository.save(new ExchangeRatesEntity("PEN","USD",3.838,1));
            exchangeRatesRepository.save(new ExchangeRatesEntity("EUR","PEN",4.945,1));
            exchangeRatesRepository.save(new ExchangeRatesEntity("PEN","EUR",4.689,1));
        };
    }
}
