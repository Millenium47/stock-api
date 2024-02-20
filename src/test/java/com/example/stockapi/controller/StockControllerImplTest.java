package com.example.stockapi.controller;

import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.model.StockDataModel;
import com.example.stockapi.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockControllerImpl.class)
public class StockControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Test
    void getTimeSeries_ReturnsExpectedData_WhenIntervalIsDaily() throws Exception {
        // given
        String symbol = "IBM";
        String interval = "daily"; // This now comes as a request parameter
        StockDataModel mockData = new StockDataModel("2021-03-15", new BigDecimal("12.0"));
        ArrayList<StockDataModel> expectedResponse = new ArrayList<>();
        expectedResponse.add(mockData);
        String expectedJsonResponse = "[{\"date\":\"2021-03-15\",\"value\":12.0}]";

        // when
        when(stockService.fetchTimeSeries(symbol, TimeSeriesType.DAILY)).thenReturn(expectedResponse);

        // action
        mockMvc.perform(get("/v1/stocks/{symbol}", symbol)
                        .param("interval", interval)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse));

        // verify
        verify(stockService).fetchTimeSeries(symbol, TimeSeriesType.DAILY);
    }
}