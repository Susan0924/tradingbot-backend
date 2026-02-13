package com.tradingbot.backend.model;


import java.time.LocalDateTime;

public class Candle {

        private LocalDateTime time;
        private double open;
        private double high;
        private double low;
        private double close;

        public Candle(LocalDateTime time, double open, double high, double low, double close) {
            this.time = time;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public double getOpen() {
            return open;
        }

        public double getHigh() {
            return high;
        }

        public double getLow() {
            return low;
        }

        public double getClose() {
            return close;
        }
    }

