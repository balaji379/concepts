package com.tutorial.excel_batch_application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping("/fetch-data")
    public String fetch_data() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        final JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("start_at", System.currentTimeMillis())
                        .toJobParameters();
        var jobexecution = jobLauncher.run(job, jobParameters);
        return jobexecution.getStatus().toString();
    }

}
