package com.tradingbot.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//http://localhost:8080/api/test
//http://localhost:8080/api/trading/start
//http://localhost:8080/api/trading/status
//http://localhost:8080/api/trading/stop
//http://localhost:8080/api/trading/balance
//http://localhost:8080/api/trading/trades
@SpringBootApplication
@EnableScheduling
public class TradingbotBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingbotBackendApplication.class, args);
	}

}
