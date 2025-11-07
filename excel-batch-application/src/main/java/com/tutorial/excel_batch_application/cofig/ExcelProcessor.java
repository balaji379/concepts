package com.tutorial.excel_batch_application.cofig;

import com.tutorial.excel_batch_application.entity.WeatherReport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExcelProcessor implements ItemProcessor<WeatherReport, WeatherReport> {
    @Override
    public WeatherReport process(WeatherReport item) throws Exception {
        return item;
    }
}
