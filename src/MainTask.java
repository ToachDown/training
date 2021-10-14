import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Scanner;

public class MainTask {

        public static void main(String[] args) {
            //:TODO TASK 1-3
            Scanner scan = new Scanner(System.in);
            System.out.println("input size: ");
            int size = scan.nextInt();
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = scan.nextInt();
            }
    //        for (int i = 0; i < size; i++) {
    //            if (array[i] % 2 == 0) {
    //                System.out.println("Чётное: " + array[i]);
    //            } else {
    //                System.out.println("Не чётное: " + array[i]);
    //            }
    //            if (array[i] % 9 == 0) {
    //                System.out.println("Кратное 9 и 3: " + array[i]);
    //            } else if (array[i] % 3 == 0) {
    //                System.out.println("Кратное 3: " + array[i]);
    //            }
    //            if (array[i] % 10 == 0) {
    //                System.out.println("Кратное 10 и 5: " + array[i]);
    //            } else if (array[i] % 5 == 0) {
    //                System.out.println("Кратное 5: " + array[i]);
    //            }
    //        }
            //:TODO TASK 4
    //        int nod = array[0];
    //        for (int i = 1; i < size; i++) {
    //            nod = findNOD(nod, array[i]);
    //        }
    //        System.out.println("NOD: " + nod);
    //        int nok = array[0];
    //        for (int i = 1; i < size; i++) {
    //            nok = findNOK(nok, array[i]);
    //        }
    //        System.out.println("NOK: " + nok);
            //:TODO TASK 5
    //        for (int i = 0; i < size; i++) {
    //            if(array[i] <= 2) {
    //                continue;
    //            }
    //            boolean prime = true;
    //            for (int j = 2; j < array[i]; j++) {
    //                if(array[i] % j == 0 ) {
    //                    prime = false;
    //                }
    //            }
    //            if (prime) {
    //                System.out.println("Простое число: " + array[i]);
    //            }
    //        }
            //: TODO TASK 6
    //        for(int i = 0; i < size; i++) {
    //            if(LuckyNumber.isLucky(array[i])){
    //                System.out.println(array[i]);
    //            }
    //            LuckyNumber.counter = 2;
    //        }
            //: TODO TASK 7
    //        for(int i = 0; i < size; i++) {
    //            int first = 1;
    //            int second = 1;
    //            int res = 1;
    //            while (max >= res) {
    //                if(res == array[i]) {
    //                    System.out.println(array[i]);
    //                }
    //                res = first + second;
    //                first = second;
    //                second = res;
    //            }
    //        }
            //:TODO TASK 8
    //        for(int i = 0; i < size; i++) {
    //            if(isPalindrome(array[i])) {
    //                System.out.println("polidrome: " + array[i]);
    //            }
    //        }

            //:TODO TASK 9
//            findPeriodForFirstTwoPositiveNumbersInARow(array);
            //:TODO TASK 10
            for(int k = 0; k < size; k++) {
                if(array[k] > 0) {
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j <= i; j++) {
                            System.out.print(C(i, j, array[k]) + " ");
                        }
                        System.out.println();
                    }
                    return;
                }
            }

        }

    public static void findPeriodForFirstTwoPositiveNumbersInARow(int[] numbers) {
        String message = "find period: ";
        if (numbers.length < 2) {
            System.out.println(message + " sourceNumbers.length < 2");
            return;
        }
        int[] twoPositiveNumbers = findFirstTwoPositiveNumbersInARow(numbers);
        if (twoPositiveNumbers.length < 2) {
            System.out.println(message + " not found 2 required numbers");
            return;
        }
        int numerator = twoPositiveNumbers[0];
        int denominator = twoPositiveNumbers[1];
        if (numerator % denominator == 0) {
            System.out.println(numerator / denominator + ".(0)");
            return;
        }

        int step = denominator % 2;
        int firstCounter = 0;
        while (step == 0) {
            denominator = denominator / 2;
            step = denominator % 2;
            firstCounter++;
        }

        step = denominator % 5;
        int secondCounter = 0;
        while (step == 0) {
            denominator = denominator / 5;
            step = denominator % 5;
            secondCounter++;
        }

        int length = 1;
        int r = 10;
        while (r != 1) {
            r = (10 * r) % denominator;
            if (r == 0) {
                break;
            }
            length++;
        }
        long beforePeriodLength;
        if (secondCounter > firstCounter) {
            beforePeriodLength = secondCounter;
        } else {
            beforePeriodLength = firstCounter;
        }
        denominator = twoPositiveNumbers[1];
        System.out.print(message + numerator / denominator + ".");
        numerator = numerator % denominator;
        for (int i = 0; i < beforePeriodLength; i++) {
            System.out.print((numerator * 10) / denominator);
            numerator = (numerator * 10) % denominator;
        }
        System.out.print("(");
        for (int i = 0; i < length; i++) {
            System.out.print((numerator * 10) / denominator);
            numerator = (numerator * 10) % denominator;
        }
        System.out.print(") = " + twoPositiveNumbers[0] + " / " + twoPositiveNumbers[1] + "\n");
    }

    public static int[] findFirstTwoPositiveNumbersInARow(int[] sourceNumbers) {
        int[] twoPositiveNumbers = new int[2];
        for (int i = 0; i < sourceNumbers.length - 1; i++) {
            if ((sourceNumbers[i] > 0) && (sourceNumbers[i + 1] > 0)) {
                twoPositiveNumbers[0] = sourceNumbers[i];
                twoPositiveNumbers[1] = sourceNumbers[i + 1];
                break;
            }
        }
        return twoPositiveNumbers;
    }


        public static boolean isPalindrome(int a) {
            int pol = a;
            int reverse = 0;
            while(pol != 0) {
                int remaind = pol % 10;
                reverse = reverse * 10 + remaind;
                pol = pol / 10;
            }
            return a == reverse;
        }

        private static int findNOK(int a, int b) {
            return (a*b)/findNOD(a, b);
        }

        private static int findNOD(int a, int b){
            while (a != 0 && b != 0) {
                if(a > b) {
                    a = a % b;
                } else {
                    b = b % a;
                }
            }
            return (a + b);
        }

        private static class LuckyNumber {

            public static int counter = 2;

            static boolean isLucky(int a) {
                if (counter > a) {
                   return true;
                }
                if(a % counter == 0) {
                   return false;
                }

                int nextPos = a - (a/counter);

                counter++;
                return isLucky(nextPos);
            }
        }

    private static long C (int n,int k, int res){
        for (int i = n - k + 1; i <= n; i++) {
            res *= i;
        }

        for (int i = 2; i <= k; i++) {
            res /= i;
        }
        return res;
    }
}
