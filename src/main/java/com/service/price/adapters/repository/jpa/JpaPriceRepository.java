package com.service.price.adapters.repository.jpa;

import com.service.price.adapters.repository.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate,
            LocalDateTime endDate);

    // Method 2: Custom JPQL Query with explicit parameters
    @Query("SELECT p FROM PriceEntity p " +
            "WHERE p.brandId = :brandId " +
            "AND p.productId = :productId " +
            "AND p.startDate <= :applicationDate " +
            "AND p.endDate >= :applicationDate " +
            "ORDER BY p.priority DESC")
    List<PriceEntity> findApplicablePrices(
            @Param("brandId") Long brandId,
            @Param("productId") Long productId,
            @Param("applicationDate") LocalDateTime applicationDate);

    // Method 3: Native SQL Query (for complex cases)
    @Query(value = "SELECT * FROM PRICES " +
            "WHERE BRAND_ID = :brandId " +
            "AND PRODUCT_ID = :productId " +
            "AND START_DATE <= :applicationDate " +
            "AND END_DATE >= :applicationDate " +
            "ORDER BY PRIORITY DESC LIMIT 1",
            nativeQuery = true)
    PriceEntity findTopApplicablePrice(
            @Param("brandId") Long brandId,
            @Param("productId") Long productId,
            @Param("applicationDate") LocalDateTime applicationDate);
}
