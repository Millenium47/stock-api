package com.example.stockapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AlphaServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    void fetchTimeSeriesDaily_returnsExpectedData() {
        // given
        String symbol = "IBM";
        String expectedResponse = "{\"data\":\"some mock data\"}";
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(expectedResponse));

        AlphaServiceImpl alphaService = new AlphaServiceImpl(restTemplate, "demo", "https://www.alphavantage.co/query");

        // when
        String actualResponse = alphaService.fetchTimeSeriesDaily(symbol);

        // assert
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).getForEntity(anyString(), eq(String.class));
    }
}
