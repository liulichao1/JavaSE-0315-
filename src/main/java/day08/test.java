package day08;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] ints = new int[10];
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints.length - 1 - i; j++) {
                if (ints[j + 1] < ints[j]) {
                    int temp = ints[j];
                    ints[j] = ints[j + 1];
                    ints[j+1] = ints[temp];
                }
            }
        }
        System.out.println(Arrays.toString(ints));
    }
}
