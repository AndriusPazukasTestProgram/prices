package com.service.price.adapters.config;

import com.service.price.adapters.repository.PriceEntity;
import com.service.price.adapters.repository.jpa.JpaPriceRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@DependsOn("entityManagerFactory")
public class H2Config {

    @Bean
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CommandLineRunner initDatabase(JpaPriceRepository repository) {
        return args -> {
            repository.save(PriceEntity.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                    .priceList(1)
                    .productId(35455L)
                    .priority(0)
                    .price(35.50)
                    .currency("EUR")
                    .build());

            repository.save(PriceEntity.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
                    .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
                    .priceList(2)
                    .productId(35455L)
                    .priority(1)
                    .price(25.45)
                    .currency("EUR")
                    .build());

            repository.save(PriceEntity.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0))
                    .endDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0))
                    .priceList(3)
                    .productId(35455L)
                    .priority(1)
                    .price(30.50)
                    .currency("EUR")
                    .build());

            repository.save(PriceEntity.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                    .priceList(4)
                    .productId(35455L)
                    .priority(1)
                    .price(38.95)
                    .currency("EUR")
                    .build());


        };
    }
}
