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

    public void multiplyRow(int row, double value) {
        rows[row].multiply(value);
    }

    public void addRowsWithCoef(int row1, int row2, double coef) {
        rows[row1].add(rows[row2].multiply(coef));
    }

    @Override
    public String toString() {
        return "LinearEquation{" +
                "rows=" + Arrays.toString(rows) +
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
        eq.swapRows(0, 2);
        System.out.println(eq);
        eq.swapColumns(1, 2);
        System.out.println(eq);
        eq.multiplyRow(1, 0.5);
        System.out.println(eq);
        eq.addRowsWithCoef(1, 0, -7);
        System.out.println(eq);
    }
}
