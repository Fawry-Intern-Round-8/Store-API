package org.fawry.storeapi.repositories;

import org.fawry.storeapi.entities.Stock;
import org.fawry.storeapi.entities.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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
    Long isProductAvailableInAnyStore(@Param("productId") Long productId,
                                         @Param("quantity") int quantity);

    List<Stock> findByAvailableTrue();

    @Query("SELECT s.store FROM Stock s WHERE s.id = :stockId")
    Store findStoreByStockId(@Param("stockId") Long stockId);

    @Query("SELECT s FROM Stock s WHERE s.productId = :productId AND s.store.id = :storeId AND s.available = TRUE")
    Optional<Stock> findByProductIdAndStoreIdAndAvailable(@Param("productId") Long productId, @Param("storeId") Long storeId);
}
