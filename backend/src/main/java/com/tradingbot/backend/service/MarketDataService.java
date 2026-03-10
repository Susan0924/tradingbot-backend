package com.tradingbot.backend.service;

import com.tradingbot.backend.broker.MockBroker;
import com.tradingbot.backend.model.Candle;
import com.tradingbot.backend.strategy.Strategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
@Service

public class MarketDataService {
    @Value("${forex.api.key}")
    private String apiKey;

    @Value("${forex.api.url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Strategy strategy;

    @Autowired
    private MockBroker broker;

    private final List<Candle> candles = new ArrayList<>();

    private Candle parseResponseToCandle(String response) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            JsonNode values = root.get("values");

            if (values != null && values.size() > 0) {

                JsonNode latest = values.get(0);

                String datetimeStr = latest.get("datetime").asText();
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime datetime = LocalDateTime.parse(datetimeStr, formatter);

                double open = latest.get("open").asDouble();
                double high = latest.get("high").asDouble();
                double low = latest.get("low").asDouble();
                double close = latest.get("close").asDouble();

                return new Candle(datetime, open, high, low, close);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Candle> getCandles() {
        return candles;
    }
    private void runTradingLogic(Candle candle) {

        if (candles.size() < 20) return;

        String signal = strategy.evaluate(candles);

        // Always use the latest candle
        double price = candle.getClose();

        System.out.printf(
                "Trading decision -> Signal: %s | Candle Price: %.5f%n",
                signal, price
        );

        if (broker.hasOpenPosition()) {

            if (!broker.getOpenPosition().getType().equals(signal)) {
                broker.closePosition(price);
                broker.openPosition(signal, price, 1);
            }

        } else {
            broker.openPosition(signal, price, 1);
        }
    }

    public void addCandle(Candle candle) {
        candles.add(candle);
        if (candles.size() > 200) {
            candles.remove(0); // keep only last 200 candles
        }
    }
    @Scheduled(fixedRate = 300000)
    public void fetchMarketData() {

        String url = baseUrl + apiKey;

        String response = restTemplate.getForObject(url, String.class);

        Candle candle = parseResponseToCandle(response);

        if (candle == null) return;


        System.out.println("Duplicate candle ignored");
        if (!candles.isEmpty()) {
            Candle last = candles.get(candles.size() - 1);
            if (last.getTime().equals(candle.getTime())) {
                return;
            }
        }

        addCandle(candle);

        System.out.println("EUR/USD Candle | Time: " + candle.getTime() +
                " | Close: " + candle.getClose());

        runTradingLogic(candle);
    }

}
