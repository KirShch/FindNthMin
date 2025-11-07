package com.example.findNthMin;

import java.util.List;
import java.util.Random;

public class SortToNthMin {

    public static void sort(List<Integer> list, int n){
        if (list == null || list.size() <= 1) {
            return;
        }
        startSort(list, n);
    }

    private static void startSort(List<Integer> list, int n)
    {
        int low = 0, high = list.size() - 1;
        while (low < high){
            int pivotIndex = partition(list, low, high);
            int pivotLowIndex = findPivotLowIndex(list, low, pivotIndex);
            if (n < pivotLowIndex) high = pivotLowIndex-1;
            else if (n > pivotIndex) low = pivotIndex + 1;
            else break;
        }
    }

    private static int findPivotLowIndex(List<Integer> list, int low, int pivotIndex){
        int pli = pivotIndex;
        if (pli > low){
            int j = -1;
            for (int i = low; i < pivotIndex; i++){
                if (j < 0 && list.get(i).equals(list.get(pivotIndex))) j = i;
                else if (j >= 0 && !list.get(i).equals(list.get(pivotIndex))){
                    swap(list, i, j);
                    j++;
                }
            }
            if (j >= 0) pli = j;
        }
        return pli;
    }


    private static void recursiveTask(List<Integer> list, int n, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            if (n < pivotIndex) recursiveTask(list, n, low, pivotIndex - 1);
            else if (n > pivotIndex) recursiveTask(list, n, pivotIndex + 1, high);
        }
    }

    private static int partition(List<Integer> list, int low, int high) {
        randomPivot(list, low, high);
        int pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j) <= pivot) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static void randomPivot(List<Integer> list, int low, int high) {
        Random random = new Random();
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(list, randomIndex, high);
    }

    private static void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
