package solver;

public interface NumericElement<T> {
    T add(T other);
    T subtract(T other);
    T multiply(T other);
    T divide(T other);
}

class ComplexElement implements NumericElement<ComplexElement> {
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
