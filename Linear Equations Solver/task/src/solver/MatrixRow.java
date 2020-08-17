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

    public MatrixRow multiply(double value) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] *= value;
        }
        return this;
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
        return "MatrixRow{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
