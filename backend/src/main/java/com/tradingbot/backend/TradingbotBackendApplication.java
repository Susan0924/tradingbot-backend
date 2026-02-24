package com.tradingbot.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//http://localhost:8080/api/test
//http://localhost:8080/api/trading/start
//http://localhost:8080/api/trading/status
//http://localhost:8080/api/trading/stop
//http://localhost:8080/api/trading/balance
//http://localhost:8080/api/trading/trades
@SpringBootApplication
@EnableScheduling
public class TradingbotBackendApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(TradingbotBackendApplication.class, args);
	}

}
