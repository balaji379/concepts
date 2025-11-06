package com.spring_batch.nvd_task.cofig;

import com.spring_batch.nvd_task.entity.NvdEntity;
import com.spring_batch.nvd_task.repo.NvdRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NvdBatchConfig {

    @Autowired
    NvdRepo nvdRepo;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RepositoryItemWriter<NvdEntity> nvdEntityRepositoryItemWriter() {
        RepositoryItemWriter<NvdEntity> nvdEntityRepositoryItemWriter = new RepositoryItemWriter<>();
        nvdEntityRepositoryItemWriter.setRepository(nvdRepo);
        nvdEntityRepositoryItemWriter.setMethodName("save");
        return nvdEntityRepositoryItemWriter;
    }

    @Bean
    public ItemProcessor<NvdEntity, NvdEntity> nvdEntityNvdEntityItemProcessor() {
        return new NvdProcessor();
    }

    @Bean
    public ItemReader<NvdEntity> nvdEntityItemReader() {
        return new NvdReader();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("get_data_from_nvdAPi", jobRepository)
                .<NvdEntity, NvdEntity>chunk(50, platformTransactionManager)
                .reader(nvdEntityItemReader())
                .processor(nvdEntityNvdEntityItemProcessor())
                .writer(nvdEntityRepositoryItemWriter())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("nvd_job", jobRepository)
                .start(step)
                .build();
    }
}
