package com.example.findNthMin;

import com.example.findNthMin.exception.FindNthMinIOException;
import com.example.findNthMin.exception.NOutOfBoundException;
import org.dhatim.fastexcel.reader.*;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FindNthMinService {

    private final PathValidator pathValidator;

    public FindNthMinService(PathValidator pathValidator){
        this.pathValidator = pathValidator;
    }

    public int findNthMin(String path, int n, boolean unique){
        pathValidator.validPath(path);
        if (n < 1) throw new NOutOfBoundException("N < 1");
        try (FileInputStream file = new FileInputStream(path); ReadableWorkbook workbook = new ReadableWorkbook(file)){
            Sheet sheet = workbook.getFirstSheet();

            if(unique) return readFromSheetAndFindUnique(sheet, n);
            else return readFromSheetAndFind(sheet, n);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FindNthMinIOException(e.getMessage());
        }
    }

    public int readFromSheetAndFind(Sheet sheet, int n){
        List<Integer> numbers = new ArrayList<>();
        try(Stream<Row> rows = sheet.openStream()){
            numbers = rows.map(row ->{
                Cell cell = row.getCell(0);
                return Integer.parseInt(cell.getRawValue());
            }).collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
            throw new FindNthMinIOException(e.getMessage());
        }

        if (n > numbers.size()) throw new NOutOfBoundException("N grater than numbers count");
        else {
            SortToNthMin.sort(numbers, n-1);
            return numbers.get(n-1);
        }
    }

    public int readFromSheetAndFindUnique(Sheet sheet, int n){
        final Set<Integer> uniqueNumbers = new TreeSet<>();
        try(Stream<Row> rows = sheet.openStream()){
            rows.forEach(row ->{
                Cell cell = row.getCell(0);
                uniqueNumbers.add(Integer.parseInt(cell.getRawValue()));
            });
        } catch (IOException e){
            e.printStackTrace();
            throw new FindNthMinIOException(e.getMessage());
        }

        if (n > uniqueNumbers.size()) throw new NOutOfBoundException("N grater than unique numbers count");
        else {
            return uniqueNumbers.stream()
                    .skip(n-1)
                    .findFirst().get();
        }
    }
}
