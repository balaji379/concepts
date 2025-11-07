package com.tutorial.excel_batch_application.cofig;

import com.tutorial.excel_batch_application.entity.WeatherReport;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelReader implements ItemReader<WeatherReport> {

    private Iterator<Row> rowIterator;

    @PostConstruct
    public void construcingBean() throws IOException {
        InputStream inputStream = new FileInputStream(new File("C:\\Users\\IT LAB-1\\Downloads\\testset.xlsx"));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet testset = workbook.getSheet("testset");
        rowIterator = testset.rowIterator();
        rowIterator.next(); //skip the headers
    }

    @Override
    public WeatherReport read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!rowIterator.hasNext())
            return null;
        Row row = rowIterator.next();

        Iterator<Cell> cellIterator = row.cellIterator();

        int index = 1;
        WeatherReport weatherReport = new WeatherReport();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            setWeatherDataByIndex(weatherReport, index, cell);
            index += 1;
        }
        return weatherReport;
    }

    public void setWeatherDataByIndex(WeatherReport data, int index, Cell cell) {
        if (cell == null) return;

        Object value = getCellValue(cell);
        if (value == null) return;

        switch (index) {
            case 1 -> data.setDatetimeUtc(String.valueOf(value));
            case 2 -> data.setConds(String.valueOf(value));
            case 3 -> data.setDewptm(toDouble(value));
            case 4 -> data.setFog(toInt(value));
            case 5 -> data.setHail(toInt(value));
            case 6 -> data.setHeatindexm(toDouble(value));
            case 7 -> data.setHum(toInt(value));
            case 8 -> data.setPrecipm(toDouble(value));
            case 9 -> data.setPressurem(toDouble(value));
            case 10 -> data.setRain(toInt(value));
            case 11 -> data.setSnow(toInt(value));
            case 12 -> data.setTempm(toDouble(value));
            case 13 -> data.setThunder(toInt(value));
            case 14 -> data.setTornado(toInt(value));
            case 15 -> data.setVism(toDouble(value));
            case 16 -> data.setWdird(toInt(value));
            case 17 -> data.setWdire(String.valueOf(value));
            case 18 -> data.setWgustm(toDouble(value));
            case 19 -> data.setWindchillm(toDouble(value));
            case 20 -> data.setWspdm(toDouble(value));
            default -> System.out.println("⚠ Unknown column index: " + index);
        }
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) return null;

        CellType type = cell.getCellType();

        return switch (type) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case FORMULA -> {
                try {
                    yield cell.getNumericCellValue();
                } catch (Exception e) {
                    yield cell.getStringCellValue();
                }
            }
            default -> null;
        };
    }

    private Double toDouble(Object value) {
        if (value instanceof Number num) return num.doubleValue();
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private Integer toInt(Object value) {
        if (value instanceof Number num) return num.intValue();
        try {
            return Integer.parseInt(value.toString().split("\\.")[0]); // handles "12.0"
        } catch (Exception e) {
            return null;
        }
    }
}
