package com.example.stockapi.service;

import com.example.stockapi.enums.ApiProviders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service(ApiProviders.ALPHA_VANTAGE)
public class AlphaServiceImpl implements StockService {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    public String fetchTimeSeriesDaily(String symbol) {
        String baseUrl = "https://www.alphavantage.co/query";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("function", "TIME_SERIES_DAILY")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey);
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
//                .queryParam("function", "TIME_SERIES_DAILY")
//                .queryParam("symbol", "IBM")
//                .queryParam("apikey", "demo");

        ResponseEntity<String> response = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);

        return response.getBody();
    }

    @Override
    public String fetchTimeSeriesIntraday(String symbol, String interval) {
        return null;
    }

    @Override
    public String fetchTimeSeriesWeekly(String symbol) {
        return null;
    }

    @Override
    public String fetchTimeSeriesMonthly(String symbol) {
        return null;
    }

    @Autowired
    public AlphaServiceImpl(RestTemplate restTemplate, @Value("${alpha.vantage.apikey}") String apiKey, @Value("${alpha.vantage.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }
}
