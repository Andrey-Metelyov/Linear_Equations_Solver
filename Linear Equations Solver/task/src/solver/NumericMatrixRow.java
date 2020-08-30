package solver;

public class NumericMatrixRow<T extends NumericElement> {
    T[] elements;

    NumericMatrixRow(T[] elements) {
        this.elements = elements;
    }

    public NumericMatrixRow multiply(T value) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = (T) elements[i].multiply(value);
        }
        return this;
    }

    public static void main(String[] args) {
        DoubleElement[] arr = {new DoubleElement(1), new DoubleElement(2), new DoubleElement(3)};
        NumericMatrixRow<DoubleElement> mr = new NumericMatrixRow<>(arr);
//        mr.multiply(4);
    }
}

