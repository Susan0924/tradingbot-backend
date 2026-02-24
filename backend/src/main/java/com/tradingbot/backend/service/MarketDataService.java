package com.tradingbot.backend.service;

import com.tradingbot.backend.model.Candle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
@Service

public class MarketDataService {
    @Value("${forex.api.key}")
    private String apiKey;

    @Value("${forex.api.url}")
    private String baseUrl;
    @Autowired
    private RestTemplate restTemplate;
    private final List<Candle> candles = new ArrayList<>();

    public List<Candle> getCandles() {
        return candles;
    }

    public void addCandle(Candle candle) {
        candles.add(candle);
        if (candles.size() > 200) {
            candles.remove(0); // keep only last 200 candles
        }
    }
    @Scheduled(fixedRate = 60000)
    public void fetchMarketData() {

        String url = baseUrl + apiKey;


        String response = restTemplate.getForObject(url, String.class);

        System.out.println("API Response:");
        System.out.println(response);
    }

}
