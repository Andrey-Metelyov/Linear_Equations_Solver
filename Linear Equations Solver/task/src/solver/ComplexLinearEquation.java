package solver;

import java.util.Arrays;

public class ComplexLinearEquation {
    private ComplexMatrixRow[] rows;

    public ComplexLinearEquation(ComplexMatrixRow[] rows) {
        this.rows = rows;
    }

    public void swapRows(int row1, int row2) {
        ComplexMatrixRow tmp = rows[row1];
        rows[row1] = rows[row2];
        rows[row2] = tmp;
    }

    public void swapColumns(int col1, int col2) {
        for (int i = 0; i < rows.length; i++) {
            rows[i].swapElements(col1, col2);
        }
    }

    public ComplexNumber getElement(int row, int column) {
        return rows[row].get(column);
    }

    public ComplexNumber[] getColumn(int column) {
        ComplexNumber[] result = new ComplexNumber[rows.length];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].get(column);
        }
        return result;
    }

    public ComplexNumber[] getLastColumn() {
        ComplexNumber[] result = new ComplexNumber[rows.length];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].get(rows[i].size() - 1);
        }
        return result;
    }

    public void multiplyRow(int row, ComplexNumber value) {
        rows[row].multiply(value);
    }

    public void addRowWithCoef(int rowToWhichAdd, int rowToBeAdded, ComplexNumber coef) {
        rows[rowToWhichAdd].add(rows[rowToBeAdded].multiplied(coef));
    }

    public ComplexMatrixRow getRow(int i) {
        return rows[i];
    }

    @Override
    public String toString() {
        return "LinearEquation{" +
                "rows=" + System.lineSeparator() + Arrays.toString(rows) +
                '}';
    }

    public static void main(String[] args) {
        ComplexMatrixRow[] rows = {
                new ComplexMatrixRow(new ComplexNumber[]{
                        new ComplexNumber(1, 2),
                        new ComplexNumber(2, 3),
                        new ComplexNumber(3, 4)}),
                new ComplexMatrixRow(new ComplexNumber[]{
                        new ComplexNumber(1, 2),
                        new ComplexNumber(2, 3),
                        new ComplexNumber(3, 4)}),
                new ComplexMatrixRow(new ComplexNumber[]{
                        new ComplexNumber(1, 2),
                        new ComplexNumber(2, 3),
                        new ComplexNumber(3, 4)})};
        ComplexLinearEquation eq = new ComplexLinearEquation(rows);
        System.out.println(eq);
        eq.addRowWithCoef(1, 0, eq.getElement(1, 0).minus());
        eq.addRowWithCoef(2, 0, eq.getElement(2, 0).minus());
        System.out.println(eq);
        eq.multiplyRow(1, ComplexNumber.one().divide(eq.getElement(1, 1)));
        System.out.println(eq);
        eq.addRowWithCoef(2, 1, eq.getElement(2, 1).minus());
        System.out.println(eq);
        eq.addRowWithCoef(2, 1, eq.getElement(2, 1).minus());
        System.out.println(eq);
        eq.addRowWithCoef(0, 1, eq.getElement(0, 1).minus());
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
        if (!rows[j].get(rows[j].size() - 1).equals(ComplexNumber.zero())) {
            boolean allZeroes = true;
            for (int i = 0; i < rows[j].size() - 1; i++) {
                if (!rows[j].get(i).equals(ComplexNumber.zero())) {
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
