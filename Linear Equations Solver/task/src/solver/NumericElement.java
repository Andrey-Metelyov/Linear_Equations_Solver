package solver;

public interface NumericElement {
    NumericElement add(NumericElement other);
    NumericElement subtract(NumericElement other);
    NumericElement multiply(NumericElement other);
    NumericElement divide(NumericElement other);
}

class DoubleElement implements NumericElement {
    double value;

    DoubleElement(double value) {
        this.value = value;
    }

    @Override
    public NumericElement add(NumericElement other) {
        return new DoubleElement(value + ((DoubleElement) other).value);
    }

    @Override
    public NumericElement subtract(NumericElement other) {
        return new DoubleElement(value - ((DoubleElement) other).value);
    }

    @Override
    public NumericElement multiply(NumericElement other) {
        return new DoubleElement(value * ((DoubleElement) other).value);
    }

    @Override
    public NumericElement divide(NumericElement other) {
        return new DoubleElement(value / ((DoubleElement) other).value);
    }
}