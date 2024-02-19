package com.example.stockapi.controller;

import com.example.stockapi.enums.ApiProviders;
import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.factory.StockServiceFactory;
import com.example.stockapi.model.StockDataModel;
import com.example.stockapi.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockControllerImpl.class)
public class StockControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockServiceFactory serviceFactory;

    @Mock
    private StockService stockService;

    @Test
    void getTimeSeriesDaily_ReturnsExpectedData() throws Exception {
        // given
        String symbol = "IBM";
        StockDataModel mockData = new StockDataModel("2021-03-15", new BigDecimal("12.0"));
        ArrayList<StockDataModel> expectedResponse = new ArrayList<>(List.of(mockData));
        String expectedJsonResponse = "[{\"date\":\"2021-03-15\",\"value\":12.0}]";

        // when
        when(serviceFactory.getService(ApiProviders.ALPHA_VANTAGE)).thenReturn(stockService);
        when(stockService.fetchTimeSeries(symbol, TimeSeriesType.DAILY)).thenReturn(expectedResponse);

        // action
        mockMvc.perform(get("/v1/stocks/daily/{symbol}", symbol))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse));

        // assert
        verify(serviceFactory).getService(ApiProviders.ALPHA_VANTAGE);
        verify(stockService).fetchTimeSeries(symbol, TimeSeriesType.DAILY);
    }
}
