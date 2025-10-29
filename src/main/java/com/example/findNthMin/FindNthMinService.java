package com.example.findNthMin;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

@Component
public class FindNthMinService {

    //Return -1 when not found (must throw custom exception)
    public int findNthMin(String path, int n){
        if (n < 1) return -1;
        var numbers = new ArrayList<Integer>();
        //numbers.add(Integer.MAX_VALUE); // for LinkedList<>
        try (FileInputStream file = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(file)){
            /*SXSSFWorkbook wb = new SXSSFWorkbook(workbook);
            Iterator<Sheet> sheetIterator = wb.sheetIterator();
            Sheet sheet;
            if (sheetIterator.hasNext())
                sheet = sheetIterator.next();
            else return -1;*/

            //using linked list for saving curent n min values
            /*int k = 0;
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
            return numbers.getLast();*/

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if(cell == null || cell.getCellType() != CellType.NUMERIC) break;
                numbers.add((int)cell.getNumericCellValue());
            }
            if (n > numbers.size()) return -1;
            else {
                quickSort(numbers);
                return numbers.get(n-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void quickSort(ArrayList<Integer> arr) {
        if (arr == null || arr.isEmpty()) {
            return;
        }
        quickSort(arr, 0, arr.size() - 1);
    }

    private static void quickSort(ArrayList<Integer> arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(ArrayList<Integer> arr, int low, int high) {
        randomPivot(arr, low, high);
        int pivot = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr.get(j) <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void randomPivot(ArrayList<Integer> arr, int low, int high) {
        Random random = new Random();
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(arr, randomIndex, high);
    }

    private static void swap(ArrayList<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }
}
