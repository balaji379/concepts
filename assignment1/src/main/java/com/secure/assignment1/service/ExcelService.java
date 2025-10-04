package com.secure.assignment1.service;

import com.secure.assignment1.entity.Input;
import com.secure.assignment1.repo.InputRepo;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ExcelService {
    @Autowired
    InputRepo inputRepo;

    public void readData(String sheetName) {
        String data = "";
        try {
            FileInputStream fs = new FileInputStream(new File("C:\\Users\\DELL\\Desktop\\Assessment 2\\testset.xlsx"));
            Workbook wb = WorkbookFactory.create(fs);
            Sheet sheet = wb.getSheet(sheetName);
            int r = sheet.getLastRowNum();
            for (int i = 1; i <= r; i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;
                int c = row.getLastCellNum();
                Input input = new Input();
                for (int j = 0; j < c - 1; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null)
                        continue;
                    switch (j) {
                        case 0:
                            input.setDate(cell.getStringCellValue());
                            break;
                        case 1:
                            input.setConds(cell.getStringCellValue());
                            break;
                        case 2:
                            input.setDewptm(cell.getNumericCellValue());
                            break;
                        case 3:
                            input.setFog(cell.getNumericCellValue());
                            ;
                            break;
                        case 4:
                            input.setHail(cell.getNumericCellValue());
                            break;
                        case 5:
                            input.setHeatindexm(cell.getNumericCellValue());
                            break;
                        case 6:
                            input.setHum(cell.getNumericCellValue());
                            break;
                        case 7:
                            input.setPrecipm(cell.getNumericCellValue());
                            break;
                        case 8:
                            input.setPressurem(cell.getNumericCellValue());
                            break;
                        case 9:
                            input.setRain(cell.getNumericCellValue());
                            break;
                        case 10:
                            input.setSnow(cell.getNumericCellValue());
                            break;
                        case 11:
                            input.setTempm(cell.getNumericCellValue());
                            break;
                        case 12:
                            input.setThunder(cell.getNumericCellValue());
                            break;
                        case 13:
                            input.setTornado(cell.getNumericCellValue());
                            break;
                        case 14:
                            input.setVism(cell.getNumericCellValue());
                            break;
                        case 15:
                            input.setWdird(cell.getNumericCellValue());
                            break;
                        case 16:
                            input.setWdire(cell.getStringCellValue());
                            break;
                        case 17:
                            input.setWgustm(cell.getNumericCellValue());
                            break;
                        case 18:
                            input.setWindchillm(cell.getNumericCellValue());
                            break;
                        case 19:
                            input.setWspdm(cell.getNumericCellValue());
                            break;
                    }
                }
                inputRepo.save(input);
            }
        } catch (Exception e) {
        }

    }

}
