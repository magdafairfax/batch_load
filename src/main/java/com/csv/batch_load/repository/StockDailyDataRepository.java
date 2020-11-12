package com.csv.batch_load.repository;

import com.csv.batch_load.model.StockDailyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDailyDataRepository extends JpaRepository<StockDailyData, Integer> {
}
