package com.csv.batch_load.batch;

import com.csv.batch_load.model.StockDailyData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<StockDailyData, StockDailyData> {

    @Override
    public StockDailyData process(StockDailyData record) throws Exception {
        // debug info
        System.out.println(String.format("Process record:  [%s] ", record.getDate()));
        record.setSymbol("UKX");
        return record;
    }
}
