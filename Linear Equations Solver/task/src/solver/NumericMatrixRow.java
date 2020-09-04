package solver;

import java.util.Arrays;

public class NumericMatrixRow<T extends NumericElement<T>> {
    T[] elements;

    NumericMatrixRow(T[] elements) {
        this.elements = elements;
    }

    public NumericMatrixRow<T> multiply(T value) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].multiply(value);
        }
        return this;
    }

    public NumericMatrixRow<T> add(NumericMatrixRow<T> other) {
        for (int i = 0; i < elements.length; i++) {
            NumericElement<T> el = other.elements[i];
            this.elements[i].add(el);
//            this.elements[i] = this.elements[i].add(el);
//            this.elements[i] = this.elements[i].add(el);
//            this.elements[i] = this.elements[i].add(other.elements[i]);
        }
        return this;
    }

    public NumericMatrixRow<T> swapElements(int col1, int col2) {
        T tmp = elements[col1];
        elements[col1] = elements[col2];
        elements[col2] =  tmp;
        return this;
    }

    public T get(int column) {
        return elements[column];
    }

    @Override
    public String toString() {
        return "NumericMatrixRow{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }

    public static void main(String[] args) {
        double[] arr = {1, 2, 3};
//        DoubleElement[] arr = {new DoubleElement(1), new DoubleElement(2), new DoubleElement(3)};
        /*NumericMatrixRow<DoubleElement> mr = create(arr);
        System.out.println(mr);
        mr.multiply(new DoubleElement(4));
        System.out.println(mr);*/
        ComplexNumber[] complexNumbers = {
                new ComplexNumber(2, 3),
                new ComplexNumber(5, -1),
                new ComplexNumber(-2, 3)};
        NumericMatrixRow<ComplexElement> cr = createComplexRow(complexNumbers);
        System.out.println(cr);
        cr.multiply(new ComplexElement(new ComplexNumber(3, -3)));
        System.out.println(cr);
        cr.add(cr);
        System.out.println(cr);
    }


    /*private NumericMatrixRow<T> createComplexRow2(T[] arr) {
        T[] elements = new T[arr.length];
        for (int i = 0; i < arr.length; i++) {
            elements[i] = new T(arr[i]);
        }
        NumericMatrixRow<T> result = new NumericMatrixRow<>(elements);
        return result;
    }*/

    private static NumericMatrixRow<ComplexElement> createComplexRow(ComplexNumber[] arr) {
        ComplexElement[] elements = new ComplexElement[arr.length];
        for (int i = 0; i < arr.length; i++) {
            elements[i] = new ComplexElement(arr[i]);
        }
        NumericMatrixRow<ComplexElement> result = new NumericMatrixRow<>(elements);
        return result;
    }

    private static NumericMatrixRow<DoubleElement> create(double[] arr) {
        DoubleElement[] elements = new DoubleElement[arr.length];
        for (int i = 0; i < arr.length; i++) {
            elements[i] = new DoubleElement(arr[i]);
        }
        NumericMatrixRow<DoubleElement> result = new NumericMatrixRow<>(elements);
        return result;
    }

    public int size() {
        return elements.length;
    }
}

