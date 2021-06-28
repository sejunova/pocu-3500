package academy.pocu.comp3500.lab7;

public class QuickSort {
    public static void sort(char[] array) {
        quickSortRecursive(array, 0, array.length - 1);
    }

    private static void quickSortRecursive(char[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivotPos = partition(array, left, right);

        quickSortRecursive(array, left, pivotPos - 1);
        quickSortRecursive(array, pivotPos, right);
    }

    private static int partition(char[] array, int left, int right) {
        char pivot = array[left + (right - left) / 2];
        while (left <= right) {
            while (array[left] < pivot) {
                left++;
            }
            while (array[right] > pivot) {
                right--;
            }
            if (left <= right) {
                swap(array, left++, right--);
            }
        }
        return left;
    }

    private static void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
