package com.example.stockapi.controller;

import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.model.StockDataModel;
import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public ResponseEntity<List<StockDataModel>> getTimeSeries(@PathVariable String symbol, @RequestParam("interval") String interval) {
        TimeSeriesType seriesType = getTimeSeriesType(interval);
        List<StockDataModel> data = stockService.fetchTimeSeries(symbol, seriesType);
        return ResponseEntity.ok(data);
    }

    private TimeSeriesType getTimeSeriesType(String interval) {
        try {
            return TimeSeriesType.valueOf(interval.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid time series interval: " + interval);
        }
    }
}

