package com.service.price.application.ports.in;

import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;

public interface GetPriceUseCase {

    Price getPrice(PriceQuery query);
}
