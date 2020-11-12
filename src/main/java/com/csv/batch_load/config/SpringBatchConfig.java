package com.csv.batch_load.config;

import com.csv.batch_load.model.StockDailyData;
import com.csv.batch_load.util.BeanWrapperFieldSetMapperCustom;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Value("${sourceDataFile}")
    private String csvFileSource;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<StockDailyData> itemReader,
                   ItemProcessor<StockDailyData, StockDailyData> itemProcessor,
                   ItemWriter<StockDailyData> itemWriter
    ) {

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<StockDailyData, StockDailyData>chunk(50000)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<StockDailyData> itemReader() {

        FlatFileItemReader<StockDailyData> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(csvFileSource));
        flatFileItemReader.setName("CSV-Reader");
        // no need to skip the first line becase there is no header
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<StockDailyData> lineMapper() {

        DefaultLineMapper<StockDailyData> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});

        BeanWrapperFieldSetMapperCustom<StockDailyData> fieldSetMapper = new BeanWrapperFieldSetMapperCustom<>();
        fieldSetMapper.setTargetType(StockDailyData.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
