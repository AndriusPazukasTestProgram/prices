package com.service.price.adapters.controller;

import com.service.price.application.ports.in.GetPriceUseCase;
import com.service.price.domain.exception.PriceNotFoundException;
import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPriceUseCase getPriceUseCase;

    @Test
    void getPrice_At10amOn14th_ReturnsPrice1() throws Exception {

        Price mockPrice = new Price(
                35455L,
                1L,
                1,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35.50,
                "EUR",
                0
        );

        when(getPriceUseCase.getPrice(any(PriceQuery.class)))
                .thenReturn(mockPrice);

        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-14-10.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }
    @Test
    void getPrice_At3pmOn14th_ReturnsPrice2() throws Exception {

        Price mockPrice = new Price(
                35455L,
                1L,
                2,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                25.45,
                "EUR",
                1
        );

        when(getPriceUseCase.getPrice(any(PriceQuery.class)))
                .thenReturn(mockPrice);

        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-14-16.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }
    @Test
    void getPrice_At9pmOn14th_ReturnsPrice3() throws Exception {

        Price mockPrice = new Price(
                35455L,
                1L,
                1,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35.50,
                "EUR",
                0
        );

        when(getPriceUseCase.getPrice(any(PriceQuery.class)))
                .thenReturn(mockPrice);

        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-14-21.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void getPrice_At10amOn15th_ReturnsPrice4() throws Exception {

        Price mockPrice = new Price(
                35455L,
                1L,
                3,
                LocalDateTime.of(2020, 6, 15, 0, 0),  // startDate
                LocalDateTime.of(2020, 6, 15, 0, 0), // endDate
                30.5,
                "EUR",
                1
        );

        when(getPriceUseCase.getPrice(any(PriceQuery.class)))
                .thenReturn(mockPrice);

        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-15-10.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.5));
    }
    @Test
    void getPrice_At9pmOn16th_ReturnsPrice5() throws Exception {

        Price mockPrice = new Price(
                35455L,
                1L,
                4,
                LocalDateTime.of(2020, 6, 1, 16, 0),  // startDate
                LocalDateTime.of(2020, 12, 31, 23, 59, 59), // endDate
                38.95,
                "EUR",
                1
        );

        when(getPriceUseCase.getPrice(any(PriceQuery.class)))
                .thenReturn(mockPrice);

        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-16-21.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }



    //Casos de error


    @Test
    void getPrice_WhenMissingParameters() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", "2020-06-14T10:00:00")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPrice_WhenInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", "14-06-2020")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPrice_WhenPriceNotFound_ShouldReturn404() throws Exception {
        // Arrange
        LocalDateTime testDate = LocalDateTime.of(2023, 1, 1, 12, 0);
        PriceQuery query = new PriceQuery(testDate, 35455L, 1L);
        String errorMessage = "No price found for criteria";

        Mockito.when(getPriceUseCase.getPrice(any(PriceQuery.class)))
                .thenThrow(new PriceNotFoundException(errorMessage));

        // Act & Assert
        mockMvc.perform(get("/api/prices")
                        .param("date", "2023-01-01-12.00.00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }
}
