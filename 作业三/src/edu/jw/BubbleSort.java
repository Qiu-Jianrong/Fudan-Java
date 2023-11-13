package edu.jw;
import edu.fudan.mainSort;
public class BubbleSort implements mainSort{
    @Override
    public int[] sort(int[] input) {
        System.out.println("This is BubbleSort.");
        int [] num = input.clone();
        int n = num.length;
        for (int i = 1; i < n; ++i){
            for (int j = 0; j < n - i; ++j){
                if (num[j] > num[j + 1]){
                    int temp = num[j + 1];
                    num[j + 1] = num[j];
                    num[j] = temp;
                }
            }
        }
        return num;
    }

}
