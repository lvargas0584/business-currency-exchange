package com.business.currency.exchange.core.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class PeruApisFeignClientConfiguration {

    @Value("${peru.apis.app.oauth.accessToken}")
    public String OauthAccessToken;

    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("Authorization",
                        String.format("Bearer %s", OauthAccessToken));
            }
        };
    }
}