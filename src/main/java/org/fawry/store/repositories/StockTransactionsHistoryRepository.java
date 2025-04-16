package org.fawry.store.repositories;

import org.fawry.store.entities.StockTransactionsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionsHistoryRepository extends JpaRepository<StockTransactionsHistory, Long> {
}
