import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        int[][] array = new int[rows][];
        for (int i = 0; i < rows; i++) {
            array[i] = new int[columns];
            for (int j = 0; j < columns; j++) {
                array[i][j] = scanner.nextInt();
            }
        }
        int rotate = scanner.nextInt();
//        Collections.rotate(Arrays.asList(array.clone()), rotate);
        rotate = rotate % rows;
        if (rotate > 0) {
            array = rotateRows(array, rotate);
        }

        showArray(array);
//        swapRows(array, 0, rotate);
    }

    private static void showArray(int[][] array) {
        for (int[] ints : array) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    private static int[][] rotateRows(int[][] array, int rotate) {
        int rows = array.length;
        int[][] result = new int[rows][];
        for (int i = 0; i < result.length; i++) {
            result[(rotate + i) % rows] = array[i];
        }
        return result;
    }

//    private static void swapRows(int[][] array, int row1, int row2) {
//
//    }
}