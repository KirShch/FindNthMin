package com.example.findNthMin;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort {

    public static void sort(List<Integer> list) {
        if (list == null || list.size() <= 1) {
            return;
        }
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(new QuickSortTask(list, 0, list.size() - 1));
        } finally {
            pool.shutdown();
        }
    }

    public static CompletableFuture<Void> sortAsync(List<Integer> list) {
        return CompletableFuture.runAsync(() -> sort(list));
    }

    private static class QuickSortTask extends RecursiveAction {
        private final List<Integer> list;
        private final int low;
        private final int high;
        private static final int THRESHOLD = 1000; // Порог для последовательной сортировки

        public QuickSortTask(List<Integer> list, int low, int high) {
            this.list = list;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (high - low <= THRESHOLD) {
                sequentialQuickSort(list, low, high);
                return;
            }

            if (low < high) {
                int pivotIndex = partition(list, low, high);

                QuickSortTask leftTask = new QuickSortTask(list, low, pivotIndex - 1);
                QuickSortTask rightTask = new QuickSortTask(list, pivotIndex + 1, high);

                invokeAll(leftTask, rightTask);
            }
        }
    }

    private static void sequentialQuickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            sequentialQuickSort(list, low, pivotIndex - 1);
            sequentialQuickSort(list, pivotIndex + 1, high);
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

    private static void swap(List<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    public static void sort(List<Integer> list, int threshold) {
        if (list == null || list.size() <= 1) {
            return;
        }
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(new CustomThresholdQuickSortTask(list, 0, list.size() - 1, threshold));
        } finally {
            pool.shutdown();
        }
    }

    //Custom threshold
    public static CompletableFuture<Void> sortAsync(List<Integer> list, int threshold) {
        return CompletableFuture.runAsync(() -> sort(list, threshold));
    }

    private static class CustomThresholdQuickSortTask extends RecursiveAction {
        private final List<Integer> list;
        private final int low;
        private final int high;
        private final int threshold;

        public CustomThresholdQuickSortTask(List<Integer> list, int low, int high, int threshold) {
            this.list = list;
            this.low = low;
            this.high = high;
            this.threshold = threshold;
        }

        @Override
        protected void compute() {
            if (high - low <= threshold) {
                sequentialQuickSort(list, low, high);
                return;
            }

            if (low < high) {
                int pivotIndex = partition(list, low, high);

                CustomThresholdQuickSortTask leftTask =
                        new CustomThresholdQuickSortTask(list, low, pivotIndex - 1, threshold);
                CustomThresholdQuickSortTask rightTask =
                        new CustomThresholdQuickSortTask(list, pivotIndex + 1, high, threshold);

                invokeAll(leftTask, rightTask);
            }
        }
    }
}
