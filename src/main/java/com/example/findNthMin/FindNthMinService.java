package com.example.findNthMin;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class FindNthMinService {

    //Return -1 when not found
    public int findNthMin(String path, int n){
        List<Integer> numbers = new LinkedList<>();
        numbers.add(Integer.MAX_VALUE);
        try (FileInputStream file = new FileInputStream(path); Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            int k = 0;
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if(cell == null) break;
                int i = 0;
                for (int element : numbers){
                    int value = (int)cell.getNumericCellValue();
                    if (value<element) {numbers.add(i, value); break;}
                    else i++;
                }
                if (numbers.size() > n) numbers.removeLast();
                k++;
            }
            if (k<n) return -1;
            return numbers.getLast();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
