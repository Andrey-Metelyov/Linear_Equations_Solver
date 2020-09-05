package solver;

import java.util.Arrays;

public class LinearEquation {
    private MatrixRow[] rows;

    public LinearEquation(MatrixRow[] rows) {
        this.rows = rows;
    }

    public void swapRows(int row1, int row2) {
        MatrixRow tmp = rows[row1];
        rows[row1] = rows[row2];
        rows[row2] = tmp;
    }

    public void swapColumns(int col1, int col2) {
        for (int i = 0; i < rows.length; i++) {
            rows[i].swapElements(col1, col2);
        }
    }

    public double getElement(int row, int column) {
        return rows[row].get(column);
    }

    public double[] getColumn(int column) {
        double[] result = new double[rows.length];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].get(column);
        }
        return result;
    }

    public Double[] getLastColumn() {
        Double[] result = new Double[rows.length];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].get(rows[i].size() - 1);
        }
        return result;
    }

    public void multiplyRow(int row, double value) {
        rows[row].multiply(value);
    }

    public void addRowWithCoef(int rowToWhichAdd, int rowToBeAdded, double coef) {
        rows[rowToWhichAdd].add(rows[rowToBeAdded].multiplied(coef));
    }

    public MatrixRow getRow(int i) {
        return rows[i];
    }

    @Override
    public String toString() {
        return "LinearEquation{" +
                "rows=" + System.lineSeparator() + Arrays.toString(rows) +
                '}';
    }

    public static void main(String[] args) {
        MatrixRow[] rows = {
                new MatrixRow(new double[]{1,2,3}),
                new MatrixRow(new double[]{4,5,6}),
                new MatrixRow(new double[]{7,8,9})
        };
        LinearEquation eq = new LinearEquation(rows);
        System.out.println(eq);
        eq.addRowWithCoef(1, 0, -eq.getElement(1, 0));
        eq.addRowWithCoef(2, 0, -eq.getElement(2, 0));
        System.out.println(eq);
        eq.multiplyRow(1, 1.0 / eq.getElement(1, 1));
        System.out.println(eq);
        eq.addRowWithCoef(2, 1, -eq.getElement(2, 1));
        System.out.println(eq);
        eq.addRowWithCoef(2, 1, -eq.getElement(2, 1));
        System.out.println(eq);
        eq.addRowWithCoef(0, 1, -eq.getElement(0, 1));
        System.out.println(eq);
        System.out.println(Arrays.toString(eq.getColumn(2)));

//        eq.swapRows(0, 2);
//        System.out.println(eq);
//        eq.swapColumns(1, 2);
//        System.out.println(eq);
//        eq.multiplyRow(1, 0.5);
//        System.out.println(eq);
    }

    public boolean isBadRow(int j) {
        if (rows[j].get(rows[j].size() - 1) != 0.0) {
            boolean allZeroes = true;
            for (int i = 0; i < rows[j].size() - 1; i++) {
                if (rows[j].get(i) != 0.0) {
                    allZeroes = false;
                    break;
                }
            }
            return allZeroes;
        } else {
            return false;
        }
    }
}
