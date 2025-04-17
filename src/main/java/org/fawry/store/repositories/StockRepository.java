package org.fawry.store.repositories;

import org.fawry.store.entities.Stock;
import org.fawry.store.entities.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Page<Stock> findByStoreId(Long storeId, Pageable pageable);
    @Query(value = "SELECT COALESCE(SUM(quantity), 0) FROM stock WHERE store_id = :storeId", nativeQuery = true)
    int getTotalStockCountByStoreId(@Param("storeId") Long storeId);

    @Query(value = """
    SELECT SUM(st.quantity)
    FROM stock st
    WHERE st.product_id = :productId
    """, nativeQuery = true)
    Long getTotalProductQuantity(@Param("productId") Long productId);

    @Query("SELECT s.store FROM Stock s WHERE s.id = :stockId")
    Store findStoreByStockId(@Param("stockId") Long stockId);

    @Query("SELECT s FROM Stock s WHERE s.productId = :productId AND s.store.id = :storeId")
    Optional<Stock> findStockByProductIdAndStoreId(@Param("productId") Long productId, @Param("storeId") Long storeId);

    @Modifying
    @Query(value = """
    UPDATE stock
    SET quantity = quantity - :amount
    WHERE store_id = :storeId AND product_id = :productId AND quantity >= :amount
    """, nativeQuery = true)
    int decreaseQuantity(@Param("storeId") Long storeId,
                         @Param("productId") Long productId,
                         @Param("amount") int amount);
}
