package com.example.findNthMin;

import com.example.findNthMin.exception.FindNthMinIOException;
import com.example.findNthMin.exception.NOutOfBound;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Component
public class FindNthMinService {

    private final PathValidator pathValidator;

    public FindNthMinService(PathValidator pathValidator){
        this.pathValidator = pathValidator;
    }

    public int findNthMin(String path, int n){
        pathValidator.validPath(path);
        if (n < 1) throw new NOutOfBound("N < 1");
        var numbers = new ArrayList<Integer>();
        try (FileInputStream file = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(file)){
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if(cell == null || cell.getCellType() != CellType.NUMERIC) break;
                numbers.add((int)cell.getNumericCellValue());
            }
            if (n > numbers.size()) throw new NOutOfBound("N grater than numbers count");
            else {
                ParallelQuickSort.sortAsync(numbers, 50);
                return numbers.get(n-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FindNthMinIOException(e.getMessage());
        }
    }
}
