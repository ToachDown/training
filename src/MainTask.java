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
//        int max = Arrays.stream(array).max().getAsInt();
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
        for(int i = 0; i < size; i++) {
            if(isPalindrome(array[i])) {
                System.out.println("polidrome: " + array[i]);
            }
        }

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
}
