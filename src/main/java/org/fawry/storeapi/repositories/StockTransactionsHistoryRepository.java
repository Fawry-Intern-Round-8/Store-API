package org.fawry.storeapi.repositories;

import org.fawry.storeapi.entities.StockTransactionsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionsHistoryRepository extends JpaRepository<StockTransactionsHistory,Long> {
}
