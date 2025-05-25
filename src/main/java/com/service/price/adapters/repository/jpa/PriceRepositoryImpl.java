package com.service.price.adapters.repository.jpa;

import com.service.price.adapters.repository.PriceEntity;
import com.service.price.application.ports.out.PriceRepository;
import com.service.price.domain.model.Price;
import com.service.price.domain.model.PriceQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    public PriceRepositoryImpl(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public List<Price> findPricesByCriteria(PriceQuery query) {
        List<PriceEntity> entities = jpaPriceRepository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        query.getBrandId(),
                        query.getProductId(),
                        query.getApplicationDate(),
                        query.getApplicationDate());

        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getProductId(),
                entity.getBrandId(),
                entity.getPriceList(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPrice(),
                entity.getCurrency(),
                entity.getPriority());
    }
}
