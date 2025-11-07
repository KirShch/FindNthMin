package com.example.findNthMin;

import com.example.findNthMin.exception.FindNthMinIOException;
import com.example.findNthMin.exception.NOutOfBoundException;
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

    public int findNthMin(String path, int n, boolean unique){
        pathValidator.validPath(path);
        if (n < 1) throw new NOutOfBoundException("N < 1");
        try (FileInputStream file = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(file)){
            Sheet sheet = workbook.getSheetAt(0);
            if(unique) return readFromSheetAndFindUnique(sheet, n);
            else return readFromSheetAndFind(sheet, n);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FindNthMinIOException(e.getMessage());
        }
    }

    public int readFromSheetAndFind(Sheet sheet, int n){
        var numbers = new ArrayList<Integer>();
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if(cell == null || cell.getCellType() != CellType.NUMERIC) break;
            numbers.add((int)cell.getNumericCellValue());
        }
        if (n > numbers.size()) throw new NOutOfBoundException("N grater than numbers count");
        else {
            SortToNthMin.sort(numbers, n-1);
            return numbers.get(n-1);
        }
    }

    public int readFromSheetAndFindUnique(Sheet sheet, int n){
        var uniqueNumbers = new TreeSet<Integer>();
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if(cell == null || cell.getCellType() != CellType.NUMERIC) break;
            uniqueNumbers.add((int)cell.getNumericCellValue());
        }
        if (n > uniqueNumbers.size()) throw new NOutOfBoundException("N grater than unique numbers count");
        else {
            return uniqueNumbers.stream()
                    .skip(n-1)
                    .findFirst().get();
        }
    }
}
