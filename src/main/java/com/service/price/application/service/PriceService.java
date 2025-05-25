package com.service.price.application.service;

import com.service.price.application.ports.in.GetPriceUseCase;
import com.service.price.application.ports.out.PriceRepository;
import com.service.price.domain.exception.PriceNotFoundException;
import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PriceService implements GetPriceUseCase {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getPrice(PriceQuery query) {
        List<Price> prices = priceRepository.findPricesByCriteria(query);

        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(
                        "Price not found for productId: " + query.getProductId() +
                                ", brandId: " + query.getBrandId() +
                                ", applicationDate: " + query.getApplicationDate()));
    }
}
