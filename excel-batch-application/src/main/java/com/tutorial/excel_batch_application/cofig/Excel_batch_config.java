package com.tutorial.excel_batch_application.cofig;

import com.tutorial.excel_batch_application.entity.WeatherReport;
import com.tutorial.excel_batch_application.repo.WeatherReportRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.Writer;
import java.util.concurrent.Executors;

@Configuration
public class Excel_batch_config {

    @Autowired
    WeatherReportRepo weatherReportRepo;

    @Autowired
    ExcelWriter excelWriter;

    @Autowired
    ExcelReader excelReader;

    @Autowired
    ItemProcessor<WeatherReport, WeatherReport> excelProcessor;


    @Bean
    public TaskExecutor virtualThreadExecutor() {
        return new ConcurrentTaskExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }


    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("excel", jobRepository)
                .<WeatherReport, WeatherReport>chunk(100, platformTransactionManager)
                .reader(excelReader)
                .processor(excelProcessor)
                .writer(excelWriter)
                .taskExecutor(virtualThreadExecutor())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("excel_job", jobRepository)
                .start(step)
                .build();
    }

}
