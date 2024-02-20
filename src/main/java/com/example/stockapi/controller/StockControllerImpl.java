package com.example.stockapi.controller;

import com.example.stockapi.enums.ApiProviders;
import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.model.StockDataModel;
import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/stocks")
@CrossOrigin("http://localhost:3000")
public class StockControllerImpl {
    private final StockService stockService;

    @Autowired
    public StockControllerImpl(StockService service) {
        this.stockService = service;
    }

    @GetMapping("/{symbol}")
    public ArrayList<StockDataModel> getTimeSeries(@PathVariable String symbol, @RequestParam("interval") String interval) {
        TimeSeriesType seriesType = getTimeSeriesType(interval);
        return stockService.fetchTimeSeries(symbol, seriesType);
    }

    private TimeSeriesType getTimeSeriesType(String interval) {
        try {
            return TimeSeriesType.valueOf(interval.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid time series interval: " + interval);
        }
    }

}
