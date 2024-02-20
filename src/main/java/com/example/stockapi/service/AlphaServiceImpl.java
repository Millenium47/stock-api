package com.example.stockapi.service;

import com.example.stockapi.enums.ApiProviders;
import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.model.StockDataModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map.Entry;

@Service(ApiProviders.ALPHA_VANTAGE)
public class AlphaServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(AlphaServiceImpl.class);

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    @Autowired
    public AlphaServiceImpl(RestTemplate restTemplate, @Value("${alpha.vantage.apikey}") String apiKey, @Value("${alpha.vantage.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Cacheable(value = "timeSeries", key = "#symbol.concat('-').concat(#timeSeriesType.getApiFunction())")
    public ArrayList<StockDataModel> fetchTimeSeries(String symbol, TimeSeriesType timeSeriesType) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("function", timeSeriesType.getApiFunction())
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey);

        logger.info("LOG: Fetching time series data from API for symbol: {} and timeSeries: {}", symbol, timeSeriesType.getApiFunction());
        ResponseEntity<String> response = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);
        return parseResponse(response.getBody(), timeSeriesType);
    }

    ArrayList<StockDataModel> parseResponse(String responseBody, TimeSeriesType timeSeriesType) {
        final String PARSED_VALUE = "4. close";
        ArrayList<StockDataModel> response = new ArrayList<>();

        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        // depends on fetch type
        JsonObject timeSeries = jsonObject.getAsJsonObject(timeSeriesType.getKey());

        for (Entry<String, JsonElement> entry : timeSeries.entrySet()) {
            String date = entry.getKey();
            String value = entry.getValue().getAsJsonObject().get(PARSED_VALUE).getAsString();

            StockDataModel stockData = new StockDataModel(date, new BigDecimal(value).setScale(2, RoundingMode.HALF_UP));
            response.add(stockData);
        }
        return response;
    }
}
