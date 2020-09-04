package solver;

import java.util.Arrays;

public class ComplexMatrixRow {
    ComplexNumber[] elements;

    ComplexMatrixRow(ComplexNumber[] elements) {
        this.elements = elements;
    }

    public ComplexMatrixRow multiply(ComplexNumber value) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = elements[i].multiply(value);
        }
        return this;
    }

    @Override
    public String toString() {
        return "ComplexMatrixRow{" +
                "elements=" + Arrays.toString(elements) +
                "}\n";
    }

    public static void main(String[] args) {
        ComplexMatrixRow cmr = new ComplexMatrixRow(new ComplexNumber[] {
                new ComplexNumber(2, 3),
                new ComplexNumber(54, 6) });
        System.out.println(cmr.multiply(ComplexNumber.zero()));
//        double[] arr = {1, 2, 3};

////        DoubleElement[] arr = {new DoubleElement(1), new DoubleElement(2), new DoubleElement(3)};
//        ComplexMatrixRow mr = create(arr);
//        System.out.println(mr);
//        mr.multiply(new DoubleElement(4));
//        System.out.println(mr);
//        ComplexNumber[] complexNumbers = {
//                new ComplexNumber(2, 3),
//                new ComplexNumber(5, -1),
//                new ComplexNumber(-2, 3)};
//        NumericMatrixRow<ComplexElement> cr = createComplexRow(complexNumbers);
//        System.out.println(cr);
//        cr.multiply(new ComplexElement(new ComplexNumber(3, -3)));
//        System.out.println(cr);
    }

    public ComplexMatrixRow swapElements(int col1, int col2) {
        ComplexNumber tmp = elements[col1];
        elements[col1] = elements[col2];
        elements[col2] =  tmp;
        return this;
    }

    public ComplexNumber get(int column) {
        return elements[column];
    }

    public int size() {
        return elements.length;
    }

    public ComplexMatrixRow multiplied(ComplexNumber value) {
        ComplexMatrixRow result = new ComplexMatrixRow(this.elements);
        for (int i = 0; i < elements.length; i++) {
            result.elements[i] = result.elements[i].multiply(value);
        }
        return result;
    }

    public ComplexMatrixRow add(ComplexMatrixRow other) {
        for (int i = 0; i < elements.length; i++) {
            this.elements[i] = this.elements[i].add(other.elements[i]);
        }
        return this;
    }
}

