package solver;

public class ComplexNumber {
    private double x;
    private double y;

    public ComplexNumber(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ComplexNumber(ComplexNumber other) {
        this.x = other.x;
        this.y = other.y;
    }

    public ComplexNumber plus(ComplexNumber other) {
        return new ComplexNumber(this.x + other.x, this.y + other.y);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        double x = this.x * other.x - this.y * other.y;
        double y = this.y * other.x + other.y * this.x;
        return new ComplexNumber(x, y);
    }

    public ComplexNumber multiply(double k) {
        return new ComplexNumber(k * x, k * y);
    }

    public ComplexNumber divide(ComplexNumber other) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = other.x;
        double y2 = other.y;
        double divider = (x2 * x2) + (y2 * y2);
        if (divider == 0) {
            throw new IllegalArgumentException("Division by (" + x2 + ", " + y2 + ")");
        }
        double x = (x1 * x2 + y1 * y2) / divider;
        double y = (y1 * x2 - x1 * y2) / divider;
        return new ComplexNumber(x, y);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("(").append(x).append(", ").append(y).append(") = ");
        if (x != 0.0) {
            sb.append(x);
        }
        if (y != 0.0) {
            if (x !=  0 && y > 0) {
                sb.append("+");
            }
            if (y < 0) {
                sb.append("-");
            }
            if (Math.abs(y) != 1) {
                sb.append(Math.abs(y));
            }
            sb.append("i");
        } else if (x == 0.0) {
            sb.append(x);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ComplexNumber z1 = new ComplexNumber(-14, 15);
        ComplexNumber z2 = new ComplexNumber(0, 1);

//        System.out.println(z1.plus(z2));
//        System.out.println(z1.multiply(z1));
//        ComplexNumber z = new ComplexNumber(5, -4);
//        double k = -7;
//        System.out.println(z.multiply(k));
//        z1 = new ComplexNumber(2, -1);
//        z2 = new ComplexNumber(1, 1);
        System.out.println(z1.divide(z2));
        System.out.println(z1.divide(new ComplexNumber(0, 0.01)));
        System.out.println(new ComplexNumber(0,0));
        System.out.println(new ComplexNumber(0,1));
        System.out.println(new ComplexNumber(0,-1));
        System.out.println(new ComplexNumber(1,-1));
        System.out.println(new ComplexNumber(1,0));
        System.out.println(new ComplexNumber(1,1));

        System.out.println(new ComplexNumber(0,0));
        System.out.println(new ComplexNumber(0,2));
        System.out.println(new ComplexNumber(0,-2));
        System.out.println(new ComplexNumber(2,-2));
        System.out.println(new ComplexNumber(2,0));
        System.out.println(new ComplexNumber(2,2));

        System.out.println(new ComplexNumber(0,0));
        System.out.println(new ComplexNumber(0,2));
        System.out.println(new ComplexNumber(0,-2));
        System.out.println(new ComplexNumber(-2,-2));
        System.out.println(new ComplexNumber(-2,0));
        System.out.println(new ComplexNumber(-2,2));
        System.out.println(new ComplexNumber(-1,-2));
        System.out.println(new ComplexNumber(-1,0));
        System.out.println(new ComplexNumber(-1,2));
    }
}
