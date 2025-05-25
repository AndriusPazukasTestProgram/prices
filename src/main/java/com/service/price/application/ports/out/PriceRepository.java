package com.service.price.application.ports.out;

import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;

import java.util.List;

public interface PriceRepository {

    List<Price> findPricesByCriteria(PriceQuery query);
}
