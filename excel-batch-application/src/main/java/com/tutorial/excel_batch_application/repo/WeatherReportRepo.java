package com.tutorial.excel_batch_application.repo;

import com.tutorial.excel_batch_application.entity.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherReportRepo extends JpaRepository<WeatherReport,Integer> {

}
