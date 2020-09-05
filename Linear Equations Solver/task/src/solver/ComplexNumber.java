package solver;

public class ComplexNumber {
    private final double x;
    private final double y;

    public ComplexNumber(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ComplexNumber(ComplexNumber other) {
        this.x = other.x;
        this.y = other.y;
    }

    public static ComplexNumber one() {
        return new ComplexNumber(1.0, 0.0);
    }

    public static ComplexNumber zero() {
        return new ComplexNumber(0.0, 0.0);
    }

    public static void main(String[] args) {
        ComplexNumber number = ComplexNumber.parse("5.5i");
        System.out.println(ComplexNumber.parse("1+i"));
        System.out.println(ComplexNumber.parse("i"));
        System.out.println(ComplexNumber.parse("-i"));
        System.out.println(ComplexNumber.parse("5.5i"));
        System.out.println(ComplexNumber.parse("5.5"));
        System.out.println(ComplexNumber.parse("1+5.5i"));
        System.out.println(ComplexNumber.parse("1-5.5i"));
        System.out.println(ComplexNumber.parse("-1+5.5i"));
        System.out.println(ComplexNumber.parse("-1-5.5i"));
        System.out.println(ComplexNumber.parse("-1"));
        System.out.println(ComplexNumber.parse("-5.5i"));
/*        ComplexNumber z1 = new ComplexNumber(-14, 15);
        ComplexNumber z2 = new ComplexNumber(0, 1);
        ComplexNumber z3 = z1.minus();
        System.out.println(z1);
        System.out.println(z3);
        System.out.println(z1.add(z3).equals(ComplexNumber.zero()));
        System.out.println(z1);
        System.out.println(z1.divide(ComplexNumber.one()));
        System.out.println();
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
        System.out.println(new ComplexNumber(-1,2));*/
    }

    public static ComplexNumber parse(String string) {
        double x = 0;
        double y = 0;
        int signPosition = Math.max(string.lastIndexOf("-"), string.lastIndexOf("+"));
        if (signPosition == -1) {
            // signs: just real or imaginary parts present
            if (string.charAt(string.length() - 1) == 'i') {
                x = 0.0;
                String part = string.substring(0, string.length() - 1);
                y = part.length() > 0 ? Double.parseDouble(part) : 1.0;
            } else {
                x = Double.parseDouble(string);
                ;
                y = 0.0;
            }
        } else {
            if (signPosition != 0) {
                x = Double.parseDouble(string.substring(0, signPosition));
                String part = string.substring(signPosition, string.length() - 1);
                y = part.length() > 1 ? Double.parseDouble(part) : part.equals("-") ? -1.0 : 1.0;
            } else {
                if (string.charAt(string.length() - 1) == 'i') {
                    x = 0.0;
                    String part = string.substring(0, string.length() - 1);
                    y = part.isEmpty() ? 1.0 : part.equals("-") ? -1.0 : Double.parseDouble(part);
                } else {
                    x = Double.parseDouble(string);
                    y = 0.0;
                }
            }
        }
        return new ComplexNumber(x, y);
    }

    public ComplexNumber minus() {
        return new ComplexNumber(-x, -y);
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.x + other.x, this.y + other.y);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.x - other.x, this.y - other.y);
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
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ComplexNumber complexNumber = (ComplexNumber) obj;
        return x == complexNumber.x && y == complexNumber.y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("(").append(x).append(", ").append(y).append(") = ");
        if (x != 0.0) {
            sb.append(x);
        }
        if (y != 0.0) {
            if (x != 0 && y > 0) {
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

    public boolean isZero() {
        return x == 0.0 && y == 0.0;
    }
}