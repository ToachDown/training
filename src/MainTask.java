import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainTask {

        public static void main(String[] args) {
            int[] array = inputArray();
            //:TODO TASK 1
//            outputEvenUneven(array);
            //:TODO TASK 2
//            outputMultipleNineOrThree(array);
            //:TODO TASK 3
//            outputMultipleTenOrFive(array);
            //:TODO TASK 4
//            outputArrayNODAndNOK(array);
            //:TODO TASK 5
//            outputSimpleNumbers(array);
            //:TODO TASK 6
//            outputLuckyNumbers(array);
            //:TODO TASK 7
//            outputDigitFibonacci(array);
            //:TODO TASK 8
//            outputPalindromes(array);
            //:TODO TASK 9
//            findPeriodForFirstTwoPositiveNumbersInARow(array);
            //:TODO TASK 10
//            outputTriangleOfPascal(array);

        }

    public static void outputDigitFibonacci (int[] array) {
        int max = Arrays.stream(array).max().getAsInt();
        List<Integer> arrayFibonacci = new ArrayList<>();
        int first = 1;
        int second = 1;
        int res = 1;
        while (max >= res) {
                arrayFibonacci.add(res);
                res = first + second;
                first = second;
                second = res;
        }
        Arrays.stream(array)
                .filter(number -> arrayFibonacci.contains(number))
                .forEach(System.out::println);
    }

    public static void outputSimpleNumbers (int[] array) {
        int max = Arrays.stream(array).max().getAsInt();
        List<Integer> simpleNumber = IntStream.range(2, max)
                .filter(number -> IntStream.range(2, number)
                        .filter(numb -> number % numb == 0)
                        .findFirst()
                        .isEmpty()
                )
                .boxed()
                .collect(Collectors.toList());
        Arrays.stream(array)
                .filter(number -> number > 2)
                .filter(simpleNumber::contains)
                .forEach(System.out::println);
    }

    public static int[] inputArray() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("input array size: ");
        int size = scanner.nextInt();
        int[] array = new int[size];
        return Arrays.stream(array)
                .map(x -> scanner.nextInt())
                .toArray();
    }

    public static void outputEvenUneven (int[] array) {
        Arrays.stream(array)
                .forEach(number -> {
                    if (number % 2 == 0) {
                        System.out.println("Even: " + number);
                    } else {
                        System.out.println("Uneven: " + number);
                    }
                });
    }

    public static void outputMultipleNineOrThree (int[] array) {
        Arrays.stream(array)
                .filter(number -> number % 3 == 0)
                .forEach(number -> {
                    if(number % 9 == 0) {
                        System.out.println("Multiple 9 and 3: " + number);
                    } else {
                        System.out.println("Multiple 3: " + number);
                    }
                });
    }

    public static void outputMultipleTenOrFive (int[] array) {
        Arrays.stream(array)
                .filter(number -> number % 5 == 0)
                .forEach(number -> {
                    if(number % 10 == 0) {
                        System.out.println("Multiple 10 and 5: " + number);
                    } else {
                        System.out.println("Multiple 5: " + number);
                    }
                });
    }

    public static void outputArrayNODAndNOK (int[] array) {
        int nod = array[0];
        int nok = array[0];
        for (int number : array) {
            nod = findNOD(nod, number);
            nok = findNOK(nok, number, nod);
        }
        System.out.println("NOK: " + nok + " NOD: " + nod);
    }

    public static void outputLuckyNumbers (int[] array) {
        int counter = 2;
        Arrays.stream(array)
                .filter(number -> isLucky(number, counter))
                .forEach(System.out::println);
    }

    private static boolean isLucky(int n, int counter) {
       if (counter > n)
           return true;
       if (n % counter == 0)
           return false;

       int next_position = n - (n / counter);

       counter++;
       return isLucky(next_position, counter);
    }

    private static int findNOK(int a, int b, int nod) {
        return (a*b)/ nod;
    }

    private static int findNOD(int a, int b){
        if (b==0) return a;
        return findNOD(b,a % b);
    }

    public static void outputPalindromes (int[] array) {
        Arrays.stream(array)
                .filter(MainTask::isPalindrome)
                .forEach(System.out::println);
    }

    private static boolean isPalindrome (int a) {
            String number = String.valueOf(a);
            String reverse = new StringBuilder(number).reverse().toString();
            return number.equals(reverse);
    }

    public static void outputTriangleOfPascal (int[] array) {
        Arrays.stream(array)
                .filter(number -> number > 0)
                .findFirst()
                .stream()
                .forEach(number -> {
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j <= i; j++) {
                            System.out.print(advice(i, j, number) + " ");
                        }
                        System.out.println();
                    }
                });
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
        if (numerator % denominator == 0) { // without period
            System.out.println(numerator / denominator + ".(0)");
            return;
        }

        int length = 1;
        int r = 10;
        while (r != 1) {  // find length of period
            r = (10 * r) % denominator;
            if (r == 0) {
                break;
            }
            length++;
        }

        System.out.print("period = (");
        for (int i = 0; i < length; i++) {
            System.out.print((numerator * 10) / denominator);
            numerator = (numerator * 10) % denominator;
        }
        System.out.print(") ");
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

    private static int advice (int row, int column, int base){
        for (int i = row - column + 1; i <= row; i++) {
            base *= i;
        }

        for (int i = 2; i <= column; i++) {
            base /= i;
        }
        return base;
    }
}
