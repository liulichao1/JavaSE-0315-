package day08;
//求1+2!+3!+4!+...+20!的和
//利用递归算法求5!
public class FactorialTest {
    private static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return n* factorial(n - 1);
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println(factorial(n));
        long sum = 0L;
        for (int i = 0; i < 20; i++) {
            sum += factorial(i + 1);
        }
        System.out.println(sum);
    }
}
