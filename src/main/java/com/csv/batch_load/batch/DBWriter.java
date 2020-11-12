package com.csv.batch_load.batch;

import com.csv.batch_load.model.StockDailyData;
import com.csv.batch_load.repository.StockDailyDataRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<StockDailyData> {
    @Autowired
    private StockDailyDataRepository stockDailyDataRepository;

    @Override
    public void write(List<? extends  StockDailyData> records) throws Exception {

        // Debug info
        // System.out.println("Records Saved: " + records);
        stockDailyDataRepository.saveAll(records);
    }
}
