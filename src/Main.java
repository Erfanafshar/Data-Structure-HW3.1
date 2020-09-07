import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static long[] getSum(int[] slices) {
        long[] ways = new long[30 * 1000 + 1];
        ways[0] = 1;

        int sum = 0;
        for (int slice : slices) {
            sum += slice;
        }
        int maxSum = sum;

        for (int slice : slices) {
            for (int i = maxSum; i >= slice; i--) {
                ways[i] += ways[i - slice];
            }
        }

        return ways;
    }

    private static long setTable(int i, int j,long[][] table) {
        if (table[i][j] > 0){
            return table[i][j];
        }

        if (j == 0 || i == j){
            return 1;
        }

        table[i][j] = setTable(i - 1, j - 1, table) + setTable(i - 1, j, table);

        return table[i][j];
    }

    public static void main(String[] args) {

        long[][] table = new long[31][31];
        int numberOfSlices;
        String input;
        long result = 0;
        int size;
        long[] firstWays;
        long[] secondWays;

        Scanner scanner = new Scanner(System.in);
        numberOfSlices = Integer.valueOf(scanner.nextLine());
        input = scanner.nextLine();

        String[] slicesString;
        int[] slices = new int[numberOfSlices];
        slicesString = input.split(" ");

        for (int i = 0; i < numberOfSlices; i++) {
            slices[i] = Integer.valueOf(slicesString[i]);
        }

        Arrays.sort(slices);

        for (int i = 0; i < slices.length - 1; i += size) {
            firstWays = getSum(Arrays.copyOfRange(slices, 0, i));
            size = 1;

            for (int j = i + 1; j < slices.length; j++) {
                if (slices[j] != slices[i]){
                    break;
                }
                size++;
            }

            for (int j = 0; j < size; j++) {
                secondWays = getSum(Arrays.copyOfRange(slices, i + j + 1, slices.length));

                for (int k = slices[i] * (j + 1); k <= 30 * 1000; k++) {
                    result += (setTable(size, j + 1, table) * firstWays[k - slices[i] * (j + 1)] * secondWays[k]);
                }
            }
        }

        System.out.println(result);
    }
}