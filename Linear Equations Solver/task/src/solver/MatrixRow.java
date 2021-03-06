package solver;

import java.util.Arrays;

public class MatrixRow {
    private double[] elements;

    public MatrixRow(double[] elements) {
        this.elements = Arrays.copyOf(elements, elements.length);
    }

    public double get(int col) {
        return elements[col];
    }

    public MatrixRow set(int col, double element) {
        elements[col] = element;
        return this;
    }

    public int size() {
        return elements.length;
    }

    public MatrixRow multiply(double value) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] *= value;
        }
        return this;
    }

    public MatrixRow multiplied(double value) {
        MatrixRow result = new MatrixRow(this.elements);
        for (int i = 0; i < elements.length; i++) {
            result.elements[i] *= value;
        }
        return result;
    }

    public MatrixRow swapElements(int col1, int col2) {
        double tmp = elements[col1];
        elements[col1] = elements[col2];
        elements[col2] =  tmp;
        return this;
    }

    public MatrixRow add(MatrixRow other) {
        for (int i = 0; i < elements.length; i++) {
            this.elements[i] += other.elements[i];
        }
        return this;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements) + System.lineSeparator();
    }

    public boolean nonZero() {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != 0.0) {
                return true;
            }
        }
        return false;
    }
}
