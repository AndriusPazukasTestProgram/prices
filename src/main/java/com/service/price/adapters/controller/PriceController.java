package com.service.price.adapters.controller;

import com.service.price.application.dto.PriceResponse;
import com.service.price.application.ports.in.GetPriceUseCase;
import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") LocalDateTime date,
            @RequestParam Long productId,
            @RequestParam Long brandId) {

        PriceQuery query = new PriceQuery(date, productId, brandId);
        Price price = getPriceUseCase.getPrice(query);

        return ResponseEntity.ok(PriceResponse.fromDomain(price));
    }
}
