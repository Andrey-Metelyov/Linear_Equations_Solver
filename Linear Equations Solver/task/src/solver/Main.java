package solver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static FileWriter writer;
    private static FileReader reader;
    public static void main(String[] args) throws IOException {
        Map<String, String> parameters = parseParameters(args);
        writer = new FileWriter(parameters.get("-out"));
        reader = new FileReader(parameters.get("-in"));
        Scanner scanner = new Scanner(reader);
        int lines = Integer.parseInt(scanner.nextLine());
        Matrix matrix = new Matrix(lines, lines + 1);
        for (int i = 0; i < lines; i++) {
            double[] line = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            matrix.setRow(i, line);
        }
        reader.close();
        System.out.println("Start solving the equation.");
        Matrix result = solve(matrix);
        StringBuilder sb = new StringBuilder();
        sb.append("(" + result.getAt(0, 0));
        for (int i = 1; i < result.getRows(); i++) {
            sb.append(", " + result.getAt(i, 0));
        }
        sb.append(")");
        System.out.println("The solution is: " + sb.toString());
        writer.write(result.toString());
        writer.close();
        System.out.println("Saved to file " + parameters.get("-out"));
    }

    private static Matrix solve(Matrix matrix) {
//        System.out.println(matrix);
        double[][] array = matrix.toArray();

        for (int i = 0; i < array.length; i++) {
            double[] line = array[i];
            if (line[i] != 1.0) {
                for (int j = i + 1; j < line.length; j++) {
                    line[j] /= line[i];
                }
                System.out.printf("%.2f * R%d -> R%d\n", 1 / line[i], i + 1, i + 1);
                line[i] = 1.0;
            }
            for (int j = i + 1; j < array.length; j++) {
                double koef = array[j][i];
                for (int k = 0; k < array[j].length; k++) {
                    array[j][k] -= line[k] * koef;
                }
                System.out.printf("%.2f * R%d - R%d -> R%d\n", koef, i + 1, j + 1, j + 1);
//                showMatrix(array);
            }
//            showMatrix(array);
        }
        for (int i = array.length - 2; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                double koef = array[j][i + 1];
                System.out.printf("%.2f * R%d - R%d -> R%d\n", koef, i + 2, j + 1, j + 1);
                array[j][i + 1] = 0.0;
                array[j][array[j].length - 1] -= koef * array[i + 1][array[i + 1].length - 1];
//                showMatrix(array);
            }
        }
        Matrix result = new Matrix(matrix.getRows(), 1);
        for (int i = 0; i < matrix.getRows(); i++) {
            result.setAt(i, 0, array[i][array[i].length - 1]);
        }
        return result;

        /*
        Matrix row = matrix.getRow(0);
        row = row.multiply(1.0 / row.getAt(0, 0));
        for (int i = 1; i < matrix.getRows(); i++) {
            Matrix rowFrom = matrix.getRow(i);
            double koef = -rowFrom.getAt(0, 0) / row.getAt(0, 0);
            rowFrom = rowFrom.add(row.multiply(koef));
            System.out.println(rowFrom);
            matrix.setRow(i, rowFrom, 0);
//            row = rowFrom;
            System.out.printf("%.2f * R%d + R%d -> R%d\n", koef, i, i + 1, i + 1);
        }
        System.out.println(matrix);*/
    }

    private static void showMatrix(double[][] array) {
        for (double[] doubles : array) {
            for (double aDouble : doubles) {
                System.out.print(aDouble + " ");
            }
            System.out.println();
        }
    }

    private static void menu(String[] args) throws IOException {
    }

    private static Map<String, String> parseParameters(String[] args) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < args.length / 2; i++) {
            result.put(args[2 * i], args[2 * i + 1]);
        }

        return result;
    }
}
