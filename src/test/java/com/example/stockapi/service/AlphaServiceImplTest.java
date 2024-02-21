package com.example.stockapi.service;

import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.model.StockDataModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AlphaServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    void fetchTimeSeriesDaily_returnsExpectedData() {
        // given
        String symbol = "IBM";
        String expectedResponse = "{\"Time Series (Daily)\": {\"2021-03-15\": {\"4. close\": \"123.45\"}}}";
        AlphaServiceImpl alphaService = new AlphaServiceImpl(restTemplate, "demo", "https://www.alphavantage.co/query");

        // when
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(expectedResponse));

        // action
        List<StockDataModel> actualResponse = alphaService.fetchTimeSeries(symbol, TimeSeriesType.DAILY);

        // assert
        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.size());
        assertEquals(new BigDecimal("123.45"), actualResponse.getFirst().getValue());
        verify(restTemplate).getForEntity(anyString(), eq(String.class));
    }

    @Test
    void parseResponseForDailyTimeSeries_ReturnsExpectedData() {
        // given
        String jsonResponse = "{\"Time Series (Daily)\": {\"2021-03-15\": {\"4. close\": \"123.45\"}, \"2021-03-14\": {\"4. close\": \"120.00\"}}}";
        AlphaServiceImpl service = new AlphaServiceImpl(null, null, null);

        // action
        List<StockDataModel> result = service.parseResponse(jsonResponse, TimeSeriesType.DAILY);

        // then
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("123.45"), result.get(0).getValue());
        assertEquals(new BigDecimal("120.00"), result.get(1).getValue());
    }
}
