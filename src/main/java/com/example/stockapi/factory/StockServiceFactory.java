package com.example.stockapi.factory;

import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StockServiceFactory {
    private final Map<String, StockService> stockServiceMap;

    @Autowired
    public StockServiceFactory(Map<String, StockService> stockServiceMap) {
        this.stockServiceMap = stockServiceMap;
    }

    public StockService getService(String type) {
        StockService stockService = stockServiceMap.get(type);
        if (stockService == null) {
            throw new RuntimeException("Unsupported type");
        }

        return stockService;
    }
}
