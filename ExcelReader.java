package main_project;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {

    public Map<String, String> readExcelData(String filePath) {
        Map<String, String> data = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1); // Assuming data is in the second row

            data.put("FirstName", getCellValue(row.getCell(0)));
            data.put("LastName", getCellValue(row.getCell(1)));
            data.put("Email", getCellValue(row.getCell(2)));
            data.put("Phone", getCellValue(row.getCell(3)));
            data.put("InstitutionType", getCellValue(row.getCell(4)));
            data.put("Company", getCellValue(row.getCell(5)));
            data.put("JobRole", getCellValue(row.getCell(6)));
            data.put("Department", getCellValue(row.getCell(7)));
            data.put("NeedsDescription", getCellValue(row.getCell(8)));
            data.put("Country", getCellValue(row.getCell(9)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return "";
        }
    }
}
