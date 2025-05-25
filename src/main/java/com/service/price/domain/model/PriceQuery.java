package com.service.price.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class PriceQuery {

    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
}
