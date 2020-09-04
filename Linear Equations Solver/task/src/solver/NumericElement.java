package solver;

public interface NumericElement<T> {
    NumericElement<T> add(NumericElement<T> other);
    NumericElement<T> subtract(NumericElement<T> other);
    NumericElement<T> multiply(NumericElement<T> other);
    NumericElement<T> divide(NumericElement<T> other);

//     add(NumericElement<T> other);
}

class ComplexElement implements NumericElement<ComplexNumber> {
    private ComplexNumber value;

    public ComplexElement(ComplexNumber value) {
        this.value = value;
    }

    @Override
    public ComplexElement add(ComplexElement other) {
        return new ComplexElement(value.add(other.value));
    }

    @Override
    public ComplexElement subtract(ComplexElement other) {
        return new ComplexElement(value.subtract(other.value));
    }

    @Override
    public ComplexElement multiply(ComplexElement other) {
        return new ComplexElement(value.multiply(other.value));
    }

    @Override
    public ComplexElement divide(ComplexElement other) {
        return new ComplexElement(value.divide(other.value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static void main(String[] args) {
        ComplexElement el1 = new ComplexElement(ComplexNumber.parse("5.5+3i"));
        ComplexElement el2 = new ComplexElement(ComplexNumber.parse("1.5+3i"));
        ComplexElement el3 = el1.add(el2);
        System.out.println(el1);
        System.out.println(el2);
        System.out.println(el3);
    }
}

class DoubleElement implements NumericElement<DoubleElement> {
    private final double value;

    DoubleElement(double value) {
        this.value = value;
    }

    @Override
    public DoubleElement add(DoubleElement other) {
        return new DoubleElement(value + other.value);
    }

    @Override
    public DoubleElement subtract(DoubleElement other) {
        return new DoubleElement(value - other.value);
    }

    @Override
    public DoubleElement multiply(DoubleElement other) {
        return new DoubleElement(value * other.value);
    }

    @Override
    public DoubleElement divide(DoubleElement other) {
        return new DoubleElement(value / other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
