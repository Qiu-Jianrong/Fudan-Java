package edu.wjc;
import edu.fudan.mainSort;

public class QuickSort implements mainSort{
    @Override
    public int[] sort(int[] input) {
        System.out.println("This is QuickSort.");
        int[] num = input.clone();
        quicksort(num, 0, input.length);
        return num;
    }
    private int partition(int[] num, int start, int end){
        // Choose the first number to be the pivot.
        assert end - start >= 2;
        int pivot = num[start];

        // Dynamically mark the final position of the pivot number,
        // which is also the number of less-than numbers
        int res = start;

        for (int i = start + 1; i < end; ++i){
            if (num[i] < pivot){
                // exchange num[i] and num[res + 1] (assert res + 1 no beyond)
                // then exchange num[res] and num[res + 1]
                // So that we make pivot step afterword, and num[i] just before pivot
                int temp = num[i];
                num[i] = num[res + 1];
                num[res + 1] = temp;

                temp = num[res];
                num[res] = num[res + 1];
                num[res + 1] = temp;

                // res += 1, which means one more num will fall before pivot.
                ++res;
            }
        }

        return res;
    }
    private void quicksort(int[] num, int start, int end){
        if (end - start <= 1)
            return;
        int point = partition(num, start, end);
        quicksort(num, start, point);
        quicksort(num, point + 1, end);
    }
}
