package com.service.price.application.service;

import com.service.price.application.ports.out.PriceRepository;
import com.service.price.domain.exception.PriceNotFoundException;
import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    // Casos de éxito

    @Test
    void getPrice_WhenSinglePriceExists_ReturnsIt() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        PriceQuery query = new PriceQuery(LocalDateTime.now(), 35455L, 1L);
        Price expectedPrice = new Price(35455L, 1L, 1,startDate, endDate, 35.50, "EUR",0);

        when(priceRepository.findPricesByCriteria(query))
                .thenReturn(List.of(expectedPrice));

        // Act
        Price result = priceService.getPrice(query);

        // Assert
        assertEquals(expectedPrice, result);
    }

    @Test
    void getPrice_WhenMultiplePricesExist_ReturnsHighestPriority() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        PriceQuery query = new PriceQuery(LocalDateTime.now(), 35455L, 1L);
        List<Price> prices = List.of(
                new Price(35455L, 1L, 1,startDate, endDate, 35.50, "EUR",0), // Prioridad baja
        new Price(35455L,1L,1,startDate, endDate, 25.45,"EUR",1)  // Prioridad alta
        );

        when(priceRepository.findPricesByCriteria(query)).thenReturn(prices);

        // Act
        Price result = priceService.getPrice(query);

        // Assert
        assertEquals(25.45, result.getPrice()); // Verifica que tomó el de prioridad 1
    }

    //  Casos de error
    @Test
    void getPrice_WhenNoPricesFound_ThrowsException() {
        // Arrange
        PriceQuery query = new PriceQuery(LocalDateTime.now(), 35455L, 1L);
        when(priceRepository.findPricesByCriteria(query)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(PriceNotFoundException.class, () -> {
            priceService.getPrice(query);
        });
    }

    @Test
    void getPrice_WhenRepositoryFails_ThrowsServiceException() {
        // Arrange
        PriceQuery query = new PriceQuery(LocalDateTime.now(), 35455L, 1L);
        when(priceRepository.findPricesByCriteria(query))
                .thenThrow(new DataAccessResourceFailureException("DB connection failed"));

        // Act & Assert
        assertThrows(ServiceException.class, () -> {
            priceService.getPrice(query);
        });
    }
}


