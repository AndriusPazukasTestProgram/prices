package com.service.price.adapters.controller;

import com.service.price.application.dto.PriceResponse;
import com.service.price.application.ports.in.GetPriceUseCase;
import com.service.price.domain.exception.InvalidParameterException;
import com.service.price.domain.exception.PriceNotFoundException;
import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(
           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") String date,
           @RequestParam Long productId,
           @RequestParam Long brandId) {

        if (date == null) {
            throw new InvalidParameterException("date", "Es obligatorio");
        }

        if (productId == null || productId <= 0) {
            throw new InvalidParameterException("productId", "Debe ser un número positivo");
        }

        if (brandId == null || brandId <= 0) {
            throw new InvalidParameterException("brandId", "Debe ser un número positivo");
        }


        LocalDateTime parsedDate;
        try {
            parsedDate = LocalDateTime.parse(date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("date",
                    "Formato inválido. Use yyyy-MM-dd-HH.mm.ss");
        }

        PriceQuery query = new PriceQuery(parsedDate, productId, brandId);
        Price price = getPriceUseCase.getPrice(query);

        if (price == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("PRICE_NOT_FOUND",
                            "No price found for the specified criteria"));
        }

        return ResponseEntity.ok(PriceResponse.fromDomain(price));

        }
        record ErrorResponse(String code, String message) {}

    }




