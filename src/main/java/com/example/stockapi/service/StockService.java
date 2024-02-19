package com.example.stockapi.service;

public interface StockService {
    String fetchTimeSeriesDaily(String symbol);
    String fetchTimeSeriesIntraday(String symbol, String interval);
    String fetchTimeSeriesWeekly(String symbol);
    String fetchTimeSeriesMonthly(String symbol);
}