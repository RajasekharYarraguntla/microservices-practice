package com.raja;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            System.out.println("Request Path: " + exchange.getRequest().getPath());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("Response Status: " + exchange.getResponse().getStatusCode());
            }));
        };
    }
}
