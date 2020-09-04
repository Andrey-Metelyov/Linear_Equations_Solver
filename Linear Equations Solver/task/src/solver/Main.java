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
    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        Map<String, String> parameters = parseParameters(args);
        if (parameters.size() < 2) {
            System.out.println("No parameters!");
            return;
        }
        writer = new FileWriter(parameters.get("-out"));
        reader = new FileReader(parameters.get("-in"));
        scanner = new Scanner(reader);
        int variables = scanner.nextInt();
        int equations = scanner.nextInt();
        scanner.nextLine();
//        int lines = Integer.parseInt(scanner.nextLine());
        ComplexMatrixRow[] rows = readMatrix(equations);
        reader.close();

        System.out.println("variables=" + variables);
        System.out.println("equations=" + equations);
        System.out.println("matrix:\n" + Arrays.toString(rows));
        if (true) return;
        System.out.println("Start solving the equation.");
//        Matrix result = solve(matrix);
//        sb.append("(" + result.getAt(0, 0));
//        for (int i = 1; i < result.getRows(); i++) {
//            sb.append(", " + result.getAt(i, 0));
//        }
//        sb.append(")");
//        System.out.println("The solution is: " + sb.toString());
//        for (int i = 0; i < result.getRows(); i++) {
//            writer.write(result.getAt(i, 0) + System.lineSeparator());
//        }
        LinearEquationResult linearEquationResult = solve(rows, variables, equations);
        double[] result = linearEquationResult.result;
        StringBuilder sb = new StringBuilder();
        if (linearEquationResult.solvingResult == LinearEquationResult.SolvingResult.ONE_SOLUTION) {
            sb.append("(").append(result[0]);
            writer.write(result[0] + System.lineSeparator());
            for (int i = 1; i < variables; i++) {
                sb.append(", ").append(result[i]);
                writer.write(result[i] + System.lineSeparator());
            }
            sb.append(")");
        } else if (linearEquationResult.solvingResult == LinearEquationResult.SolvingResult.NO_SOLUTIONS) {
            sb.append("No solutions");
            writer.write("No solutions");
        } else {
            sb.append("Infinitely many solutions");
            writer.write("Infinitely many solutions");
        }
        System.out.println("The solution is: " + sb.toString());
        writer.close();
        System.out.println("Saved to file " + parameters.get("-out"));
    }

    private static ComplexMatrixRow[] readMatrix(int equations) {
        ComplexMatrixRow[] rows = new ComplexMatrixRow[equations];
//        Matrix matrix = new Matrix(lines, lines + 1);
        for (int i = 0; i < equations; i++) {
            String[] line = scanner.nextLine().split("\\s+");
            ComplexNumber[] complexNumbers = new ComplexNumber[line.length];
            for (int j = 0; j < line.length; j++) {
                String s = line[j];
                complexNumbers[j] = new ComplexNumber(ComplexNumber.parse(s));
            }
//            matrix.setRow(i, line);
            rows[i] = new ComplexMatrixRow(complexNumbers);
        }
        return rows;
    }

    private static LinearEquationResult solve(ComplexMatrixRow[] rows, int variables, int equations) {
/*        LinearEquation le = new LinearEquation(rows);
        System.out.println(le);
//        if (equations < variables) {
//            System.out.println("equations < variables");
//            return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
//        }
        System.out.println("Rows manipulation:");
        int max = Math.min(variables, equations);
        for (int i = 0; i < max; i++) {
            double element = le.getElement(i, i);
            if (element == 0.0) {
                // swap rows
                for (int j = i + 1; j < rows.length; j++) {
                    if (le.getElement(j, i) != 0) {
                        System.out.printf("R%d <-> R%d\n", i + 1, j + 1);
                        le.swapRows(i, j);
                        element = le.getElement(i, i);
                        break;
                    }
                }
                if (element == 0) {
                    // swap columns
                    for (int j = i + 1; j < le.getRow(i).size() - 1; j++) {
                        if (le.getElement(i, j) != 0) {
                            System.out.printf("C%d <-> C%d\n", i + 1, j + 1);
                            le.swapColumns(i, j);
                            element = le.getElement(i, i);
                            break;
                        }
                    }
                    if (element == 0) {
                        System.out.println(le);
                        if (rows[i].nonZero()) {
                            System.out.println("No solutions (1)");
                            return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
                        } else {
                            System.out.println("Infinitely many solutions (1)");
                            return new LinearEquationResult(LinearEquationResult.SolvingResult.INFINITELY_MANY_SOLUTIONS, null);
                        }
                    }
                }
            }
            if (element != 1.0) {
                System.out.printf("%.2f * R%d -> R%d\n", 1.0 / element, i + 1, i + 1);
                le.multiplyRow(i, 1.0 / element);
            }
            for (int j = i + 1; j < rows.length; j++) {
                if (le.getElement(j, i) != 0) {
                    System.out.printf("1. %.2f * R%d + R%d -> R%d\n", -le.getElement(j, i), i + 1, j + 1, j + 1);
                    le.addRowWithCoef(j, i, -le.getElement(j, i));
                    if (le.isBadRow(j)) {
                        return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
                    }
//                    System.out.println(le);
                }
            }
            System.out.println(le);
        }

        System.out.println(le);
        for (int i = variables - 1; i < rows.length; i++) {
            boolean allZeroes = true;
            for (int j = 0; j < rows[i].size() - 1; j++) {
                if (rows[i].get(j) != 0.0) {
                    allZeroes = false;
                    break;
                }
            }
            if (allZeroes && rows[i].get(rows[i].size() - 1) != 0.0) {
                System.out.println("No solutions (2)");
                return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
            }
        }

        int nonZeroRows = 0;
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].nonZero()) {
                nonZeroRows++;
            }
        }
        if (nonZeroRows < variables) {
            System.out.println("Infinitely many solutions");
            return new LinearEquationResult(LinearEquationResult.SolvingResult.INFINITELY_MANY_SOLUTIONS, null);
        }

        for (int i = rows.length - 2; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                System.out.printf("2. %.2f * R%d + R%d -> R%d\n", -le.getElement(j, i + 1), i + 2, j + 1, j + 1);
                le.addRowWithCoef(j, i + 1, -le.getElement(j, i + 1));
//                System.out.println(le);
            }
        }
        System.out.println(le);
        return new LinearEquationResult(LinearEquationResult.SolvingResult.ONE_SOLUTION, le.getLastColumn());*/
        return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
    }

    private static LinearEquationResult solve(MatrixRow[] rows, int variables, int equations) {
        LinearEquation le = new LinearEquation(rows);
        System.out.println(le);
//        if (equations < variables) {
//            System.out.println("equations < variables");
//            return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
//        }
        System.out.println("Rows manipulation:");
        int max = Math.min(variables, equations);
        for (int i = 0; i < max; i++) {
            double element = le.getElement(i, i);
            if (element == 0.0) {
                // swap rows
                for (int j = i + 1; j < rows.length; j++) {
                    if (le.getElement(j, i) != 0) {
                        System.out.printf("R%d <-> R%d\n", i + 1, j + 1);
                        le.swapRows(i, j);
                        element = le.getElement(i, i);
                        break;
                    }
                }
                if (element == 0) {
                    // swap columns
                    for (int j = i + 1; j < le.getRow(i).size() - 1; j++) {
                        if (le.getElement(i, j) != 0) {
                            System.out.printf("C%d <-> C%d\n", i + 1, j + 1);
                            le.swapColumns(i, j);
                            element = le.getElement(i, i);
                            break;
                        }
                    }
                    if (element == 0) {
                        System.out.println(le);
                        if (rows[i].nonZero()) {
                            System.out.println("No solutions (1)");
                            return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
                        } else {
                            System.out.println("Infinitely many solutions (1)");
                            return new LinearEquationResult(LinearEquationResult.SolvingResult.INFINITELY_MANY_SOLUTIONS, null);
                        }
                    }
                }
            }
            if (element != 1.0) {
                System.out.printf("%.2f * R%d -> R%d\n", 1.0 / element, i + 1, i + 1);
                le.multiplyRow(i, 1.0 / element);
            }
            for (int j = i + 1; j < rows.length; j++) {
                if (le.getElement(j, i) != 0) {
                    System.out.printf("1. %.2f * R%d + R%d -> R%d\n", -le.getElement(j, i), i + 1, j + 1, j + 1);
                    le.addRowWithCoef(j, i, -le.getElement(j, i));
                    if (le.isBadRow(j)) {
                        return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
                    }
//                    System.out.println(le);
                }
            }
            System.out.println(le);
        }

        System.out.println(le);
        for (int i = variables - 1; i < rows.length; i++) {
            boolean allZeroes = true;
            for (int j = 0; j < rows[i].size() - 1; j++) {
                if (rows[i].get(j) != 0.0) {
                    allZeroes = false;
                    break;
                }
            }
            if (allZeroes && rows[i].get(rows[i].size() - 1) != 0.0) {
                System.out.println("No solutions (2)");
                return new LinearEquationResult(LinearEquationResult.SolvingResult.NO_SOLUTIONS, null);
            }
        }

        int nonZeroRows = 0;
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].nonZero()) {
                nonZeroRows++;
            }
        }
        if (nonZeroRows < variables) {
            System.out.println("Infinitely many solutions");
            return new LinearEquationResult(LinearEquationResult.SolvingResult.INFINITELY_MANY_SOLUTIONS, null);
        }

        for (int i = rows.length - 2; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                System.out.printf("2. %.2f * R%d + R%d -> R%d\n", -le.getElement(j, i + 1), i + 2, j + 1, j + 1);
                le.addRowWithCoef(j, i + 1, -le.getElement(j, i + 1));
//                System.out.println(le);
            }
        }
        System.out.println(le);
        return new LinearEquationResult(LinearEquationResult.SolvingResult.ONE_SOLUTION, le.getLastColumn());
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

    private static Map<String, String> parseParameters(String[] args) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < args.length / 2; i++) {
            result.put(args[2 * i], args[2 * i + 1]);
        }

        return result;
    }
}
