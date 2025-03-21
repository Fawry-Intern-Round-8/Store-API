package org.fawry.storeapi.repositories;

import org.fawry.storeapi.dtos.StockDTO;
import org.fawry.storeapi.entities.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Page<Stock> findByStoreId(Long storeId, Pageable pageable);
    @Query(value = "SELECT COALESCE(SUM(quantity), 0) FROM stock WHERE store_id = :storeId", nativeQuery = true)
    int getTotalStockCountByStoreId(@Param("storeId") Long storeId);

    @Query(value = """
    SELECT COUNT(*) > 0 FROM stock st
    WHERE st.product_id = :productId
    AND st.quantity >= :quantity
    """, nativeQuery = true)
    boolean isProductAvailableInAnyStore(@Param("productId") Long productId,
                                         @Param("quantity") int quantity);
}
