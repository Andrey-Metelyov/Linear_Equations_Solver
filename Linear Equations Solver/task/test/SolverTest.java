import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.TestCase;
import solver.Main;

import java.util.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static org.hyperskill.hstest.common.FileUtils.readFile;


class Complex {
    public static final double EPSILON = 0.001;

    public static Complex add(Complex a, Complex b) {
        return new Complex(a.real + b.real, a.imag + b.imag);
    }


    public static Complex divide(Complex a, Complex b) {
        final Complex bConjugate = b.conjugate();
        final Complex a1 = Complex.multiply(a, bConjugate);
        final Complex b1 = Complex.multiply(b, bConjugate);

        return new Complex(a1.real / b1.real, a1.imag / b1.real);
    }

    public static Complex multiply(Complex a, Complex b) {
        return new Complex(a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real);
    }

    final double real;
    final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public Complex(String s) throws NumberFormatException {
        final String[] strs = split(s);
        System.out.println(strs[0] + " " + strs[1]);
        if (strs[1].contains("i")) {
            strs[1] = strs[1].replace("i", "1");
        }
        if (strs[1].equals("-")) {
            strs[1] = "-1";
        }
        if (strs[1].equals("+")) {
            strs[1] = "1";
        }
        real = Double.parseDouble(strs[0]);
        imag = Double.parseDouble(strs[1]);
    }

    public Complex conjugate() {
        return new Complex(real, -imag);
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Complex)) {
            return false;
        }

        Complex o = (Complex) other;
        return Math.abs(o.imag - imag) < EPSILON && Math.abs(o.real - real) < EPSILON;
    }

    @Override
    public String toString() {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        final DecimalFormat realFormat = new DecimalFormat("0.####", symbols);
        final DecimalFormat imagFormat = new DecimalFormat("0.####i", symbols);
        if (Math.abs(imag) < EPSILON) {
            return realFormat.format(real);
        }
        if (Math.abs(real) < EPSILON) {
            return imagFormat.format(imag);
        }
        imagFormat.setPositivePrefix("+");
        return String.format("%s%s", realFormat.format(real), imagFormat.format(imag));
    }

    private String[] split(String s) throws NumberFormatException {
        if (s.equals("i")) {
            return new String[]{"0", "1"};
        }
        if (s.equals("-i")) {
            return new String[]{"0", "-1"};
        }
        String realString = "0";
        String imagString = "0";
        int i = 1;
        for (; i < s.length(); ++i) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                realString = s.substring(0, i);
                imagString = s.substring(i, s.length() - 1);
                if (s.charAt(s.length()-1) != 'i') {
                    throw new NumberFormatException("can't parse complex");
                }
                break;
            }
            if (s.charAt(i) == 'i') {
                if (i != s.length() - 1) {
                    throw  new NumberFormatException("can't parse complex");
                }
                imagString = s.substring(0, i);
                break;
            }
        }
        if (i == s.length()) {
            realString = s;
        }
        if (imagString.length() == 0) {
            imagString = "1";
        }
        return new String[]{realString, imagString};
    }
}


class TestClue {

    String feedback;
    String outFile;
    ArrayList<Complex> complexAnswers;
    String answer;

    TestClue(String feedback, String outFile, Double[] answers) {
        this(feedback, outFile,
            Arrays
            .stream(answers)
            .map(e -> new Complex(e, 0))
            .toArray(Complex[]::new));
    }

    TestClue(String feedback, String outFile, String answer) {
        this.feedback = feedback;
        this.outFile = outFile;
        this.answer = answer;
    }

    TestClue(String feedback, String outFile, Complex[] answers) {
        this.feedback = feedback;
        this.outFile = outFile;
        this.complexAnswers = new ArrayList<>(Arrays.asList(answers));
    }
}


public class SolverTest extends StageTest<TestClue> {
    public SolverTest() {
        super(Main.class);
    }

    static String noSolutions = "No solutions";
    static String infiniteSolutions = "Infinitely many solutions";

    @Override
    public List<TestCase<TestClue>>  generate() {
        return List.of(
            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{1d, 2d, 3d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "3 3\n" +
                    "1 1 2 9\n" +
                    "2 4 -3 1\n" +
                    "3 6 -5 0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{1d, 1d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "1 0 1\n" +
                    "0 1 1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Check if you process \"-in\" argument from command line",
                    "out.txt",
                    new Double[]{1d, 1d}))
                .addArguments(new String[]{"-in", "input.txt", "-out", "out.txt"})
                .addFile("input.txt",
                "2 2\n" +
                    "1 0 1\n" +
                    "0 1 1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Check if you process \"-out\" argument from command line",
                    "output.txt",
                    new Double[]{1d, 1d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "output.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "1 0 1\n" +
                    "0 1 1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{2d, 2d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "1 0 2\n" +
                    "0 1 2"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{1d, 1d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "2 0 2\n" +
                    "0 2 2"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{-1d, 1d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "1 2 1\n" +
                    "3 4 1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "This test is a system of 20 linear equations",
                    "out.txt",
                    new Double[]{
                        0.5428d,
                        -2.3923d,
                        1.5789d,
                        -1.3679d,
                        0.6433d,
                        -1.7531d,
                        -0.0432d,
                        -0.7503d,
                        -0.8245d,
                        -0.4562d,
                        -1.2163d,
                        0.3093d,
                        -0.1105d,
                        1.1717d,
                        -0.5873d,
                        -1.3933d,
                        1.1229d,
                        3.0693d,
                        1.1995d,
                        1.5399}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "20 20\n" +
                    "26 90 17 67 68 9 60 38 37 38 76 14 33 94 88 58 99 84 9 45 18\n" +
                    "37 34 20 53 10 61 75 49 91 84 55 84 84 81 30 22 42 76 33 27 2\n" +
                    "99 69 63 57 39 45 33 43 99 26 25 24 80 91 62 90 54 77 88 32 94\n" +
                    "37 40 14 5 47 30 5 21 36 77 57 38 29 3 61 12 81 19 39 56 9\n" +
                    "36 73 71 39 9 9 31 10 84 7 13 45 9 34 2 14 88 43 17 4 86\n" +
                    "31 29 76 89 26 35 11 55 37 5 41 96 19 18 100 20 21 49 83 5 20\n" +
                    "18 18 25 70 79 74 30 66 41 93 63 2 90 4 46 1 77 89 21 47 52\n" +
                    "32 62 27 80 57 10 35 44 97 18 58 19 5 81 33 54 83 66 25 75 75\n" +
                    "56 53 13 91 30 11 72 52 13 86 73 88 94 20 25 77 90 75 73 52 36\n" +
                    "63 9 40 40 35 90 55 92 12 98 34 37 64 21 67 91 15 65 82 87 30\n" +
                    "71 5 65 64 6 20 9 81 40 56 39 93 74 55 83 81 74 2 58 86 58\n" +
                    "13 50 31 86 73 36 83 27 37 96 37 28 75 91 15 78 90 56 57 18 18\n" +
                    "34 9 51 11 92 54 25 91 61 69 37 37 89 91 95 50 10 16 69 71 66\n" +
                    "25 16 79 36 6 28 51 100 5 28 97 23 44 32 50 2 96 18 5 48 44\n" +
                    "80 38 47 96 41 72 85 79 2 3 96 14 2 65 97 38 76 73 88 59 89\n" +
                    "31 98 88 52 49 68 46 79 26 30 31 76 84 87 27 16 66 55 78 2 46\n" +
                    "57 2 32 78 70 7 95 56 77 97 49 14 74 7 85 48 83 59 71 5 44\n" +
                    "4 64 4 43 54 99 77 7 72 82 27 22 29 94 53 48 65 88 26 86 42\n" +
                    "69 59 62 63 42 29 73 18 82 78 48 60 84 73 84 9 82 53 14 1 12\n" +
                    "98 2 47 62 69 11 28 14 83 32 94 24 71 1 16 91 53 50 38 26 17"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "This test is a system of 20 linear equations",
                    "out.txt",
                    new Double[]{
                        0.2182d,
                        0.3886d,
                        0.2337d,
                        0.5804d,
                        -0.1867d,
                        0.3536d,
                        -0.5597d,
                        -0.4706d,
                        -0.3946d,
                        -0.4577d,
                        0.371d,
                        -0.1959d,
                        1.1403d,
                        0.2808d,
                        -0.8712d,
                        -0.3355d,
                        -0.1309d,
                        -0.3008d,
                        0.6355d,
                        0.4716d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "20 20\n" +
                    "0.11 0.62 0.28 0.94 0.53 0.94 0.06 1.0 0.61 0.45 0.03 0.79 0.87 0.32 0.66 0.5 0.14 0.82 0.44 0.13 0.43\n" +
                    "0.8 0.7 0.16 0.09 0.4 0.63 0.46 1.0 0.9 0.14 0.03 0.8 0.98 0.71 0.67 0.42 0.73 0.01 0.75 0.59 0.64\n" +
                    "0.99 0.01 0.45 0.85 0.17 0.14 0.58 0.27 0.96 0.52 0.74 0.89 0.72 0.93 0.22 0.9 0.41 0.78 0.56 0.67 0.84\n" +
                    "0.19 0.35 0.98 0.28 0.74 0.42 0.82 0.51 0.33 0.84 0.85 0.46 0.71 0.93 0.11 0.88 0.08 0.72 0.68 0.73 0.82\n" +
                    "0.59 0.52 0.17 0.62 0.9 0.66 0.23 0.47 0.29 0.78 0.43 0.99 0.67 0.63 0.38 0.85 0.72 0.6 0.97 0.75 0.96\n" +
                    "0.66 0.24 0.9 0.43 0.39 0.31 0.64 0.17 0.75 0.62 0.38 0.38 0.41 0.38 0.41 0.9 0.29 0.21 0.54 0.87 0.35\n" +
                    "0.06 0.57 0.04 0.74 0.27 0.75 0.0 0.52 0.42 0.85 0.35 0.19 0.57 0.42 0.93 0.77 0.09 0.4 0.83 0.39 0.46\n" +
                    "0.87 0.77 0.71 0.61 0.73 0.28 0.02 0.96 0.29 0.37 0.27 0.43 0.02 0.82 0.16 0.34 0.49 0.9 0.35 0.11 0.18\n" +
                    "0.87 0.7 0.4 0.27 0.35 0.57 0.36 0.89 0.09 0.78 0.64 0.84 0.06 0.69 0.41 0.4 0.64 0.55 0.81 0.69 0.27\n" +
                    "0.34 0.49 0.27 1.0 0.78 0.01 0.58 0.87 0.47 0.27 0.23 0.35 0.55 0.06 0.67 0.74 0.17 0.68 0.75 0.76 0.16\n" +
                    "0.38 0.71 0.29 0.94 0.84 0.46 0.98 0.0 0.89 0.58 0.09 0.04 0.04 0.66 0.21 0.58 0.8 0.96 0.78 0.67 0.11\n" +
                    "0.36 0.37 0.1 0.36 0.46 0.15 0.99 0.38 0.39 0.52 0.76 0.68 0.94 0.31 0.21 0.99 0.78 0.17 0.15 0.06 0.06\n" +
                    "0.94 0.3 0.35 0.24 0.79 0.86 0.7 0.81 0.2 0.23 0.52 0.91 0.55 0.21 0.47 0.44 0.1 0.97 0.61 0.13 0.03\n" +
                    "0.03 0.28 0.32 0.06 0.54 0.08 1.0 0.69 0.29 0.48 0.84 0.37 0.74 0.13 0.2 0.75 0.46 0.03 0.68 0.72 0.33\n" +
                    "0.43 0.78 0.69 0.91 0.8 0.68 0.46 0.9 0.65 0.19 0.81 0.28 0.3 0.75 0.94 0.05 0.65 0.29 0.61 0.74 0.68\n" +
                    "0.01 0.46 0.38 0.72 0.97 0.39 0.88 0.62 0.93 0.26 0.58 0.02 0.95 0.49 0.69 0.64 0.47 0.53 0.43 0.1 0.09\n" +
                    "0.83 0.72 0.84 0.41 0.53 0.53 0.67 0.09 0.49 0.42 0.88 0.14 0.09 0.02 0.49 0.29 0.29 0.17 0.08 0.9 0.45\n" +
                    "0.54 0.57 0.53 0.23 0.78 0.89 0.24 0.98 0.7 0.75 0.46 0.85 0.39 0.58 0.36 0.29 0.54 0.83 0.97 0.62 0.34\n" +
                    "0.3 0.64 0.71 0.07 0.03 0.76 0.25 0.34 0.97 0.93 0.48 0.57 0.98 0.33 0.4 0.18 0.01 0.81 0.38 0.87 0.95\n" +
                    "0.25 0.7 0.07 0.4 0.67 0.84 0.12 0.43 0.61 0.7 0.89 0.88 0.48 0.14 0.32 0.98 0.15 0.87 0.34 0.81 0.37"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Check is the first element is zero",
                    "out.txt",
                    new Double[]{1d, 1d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "0 1 1\n" +
                    "1 0 1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{2d, 1d}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "0 1 1\n" +
                    "1 0 2"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{
                        -0.5175d,
                        -0.1523d,
                        0.7669d,
                        2.0115d,
                        0.0958d,
                        0.2849d,}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "6 6\n" +
                    "2 6 1 3 9 1 6\n" +
                    "9 2 4 3 6 1 5\n" +
                    "6 5 9 1 4 2 6\n" +
                    "4 1 1 2 9 2 4\n" +
                    "5 4 6 2 3 1 6\n" +
                    "3 5 4 1 7 9 6"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{
                        0.241,
                        0.5984,
                        0.2851,
                        0.1325,
                        0.1446,
                        0.4257,}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "6 6\n" +
                    "0 6 1 3 9 1 6\n" +
                    "9 0 4 3 6 1 5\n" +
                    "6 5 0 1 4 2 6\n" +
                    "4 1 1 0 9 2 4\n" +
                    "5 4 6 2 0 1 6\n" +
                    "3 5 4 1 7 0 6"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{
                        -65.8154,
                        13.0615,
                        -4.4,
                        2.6154,
                        -0.9231,
                        3.5385,}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "6 6\n" +
                    "1 6 1 1 9 1 6\n" +
                    "0 1 4 3 2 1 5\n" +
                    "0 0 0 1 4 2 6\n" +
                    "0 0 0 2 9 2 4\n" +
                    "0 0 0 2 3 1 6\n" +
                    "0 0 5 1 7 9 6"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "",
                    "out.txt",
                    new Double[]{
                        -50.381,
                        10.6508,
                        -2.0794,
                        0.5556,
                        -1d,
                        3d,}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "6 6\n" +
                    "1 6 1 1 9 1 6\n" +
                    "0 1 4 3 2 1 5\n" +
                    "0 0 0 0 0 2 6\n" +
                    "0 0 0 0 2 2 4\n" +
                    "0 0 0 9 2 1 6\n" +
                    "0 0 7 1 7 9 6"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "Test exactly like in the example",
                    "out.txt",
                    noSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "3 4\n" +
                    "0 1 2 9\n" +
                    "0 1 3 1\n" +
                    "1 0 6 0\n" +
                    "2 0 2 0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "There are 4 rows, 3 columns, and a single solution",
                    "out.txt",
                    new Double[]{
                        8d,
                        1d,
                        0d,}))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "3 4\n" +
                    "1 1 2 9\n" +
                    "0 1 3 1\n" +
                    "0 0 6 0\n" +
                    "0 0 0 0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "There are 4 rows, 3 columns, and no solutions",
                    "out.txt",
                    noSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "3 4\n" +
                    "1 1 2 9\n" +
                    "0 1 3 1\n" +
                    "0 0 6 0\n" +
                    "0 0 0 7"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "There are 4 rows, 3 columns, and infinite solutions",
                    "out.txt",
                    infiniteSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "3 4\n" +
                    "1 1 2 9\n" +
                    "0 1 3 1\n" +
                    "0 2 6 2\n" +
                    "0 0 0 0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "There are 3 rows, 4 columns, and no solutions",
                    "out.txt",
                    noSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "4 3\n" +
                    "1 1 2 9 7\n" +
                    "0 1 3 1 2\n" +
                    "0 2 6 2 9"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "There are 3 rows, 4 columns, and infinite solutions",
                    "out.txt",
                    infiniteSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "4 3\n" +
                    "1 1 2 9 7\n" +
                    "0 1 3 1 2\n" +
                    "0 2 6 3 9"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "There are 3 rows, 4 columns, and no solutions",
                    "out.txt",
                    noSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "4 3\n" +
                    "1 0 0 0 1\n" +
                    "0 0 0 0 0\n" +
                    "1 0 0 0 0"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "This is the first test with complex numbers. " +
                        "Maybe output format is wrong?",
                    "out.txt",
                    new Complex[]{
                        new Complex(0, -1),
                        new Complex(0, -1),
                    }))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "i 0 1\n" +
                    "0 i 1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "This test is about complex numbers",
                    "out.txt",
                    noSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "i -i i\n" +
                    "-i i i"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "This test is about complex numbers",
                    "out.txt",
                    infiniteSolutions))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "2 2\n" +
                    "i -i i\n" +
                    "-i i -i"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(
                    "This test is about complex numbers",
                    "out.txt",
                    new Complex[]{
                        new Complex(-0.0879, 0.1686),
                        new Complex(-0.0707, -0.0877),
                        new Complex(0.6987, 0.8726),
                    }))
                .addArguments(new String[]{"-in", "in.txt", "-out", "out.txt"})
                .addFile("in.txt",
                "3 3\n" +
                    "1+i 2+6i 7-8i 12\n" +
                    "-7i 123 12+i i\n" +
                    "11-11i 12+i -i 1+i")

        );
    }

    @Override
    public CheckResult check(String reply, TestClue clue) {
        try {
            reply = readFile(clue.outFile);

            if (reply.isEmpty()) {
                return new CheckResult(false,
                    "File is empty or cannot be opened. " +
                        "Did you close the file in your program?");
            }

            if (clue.complexAnswers == null) {
                reply = reply.strip().toLowerCase();
                clue.answer = clue.answer.strip().toLowerCase();
                return new CheckResult(reply.equals(clue.answer), clue.feedback);
            }

            if (reply.strip().toLowerCase().equals(noSolutions) ||
                reply.strip().toLowerCase().equals(infiniteSolutions)) {
                return new CheckResult(false,
                    "There is a single solution but your program prints " +
                        "\"" + reply + "\"");
            }

            Complex[] actual =
                Arrays
                    .stream(reply.split("\n"))
                    .map(String::strip)
                    .map(Complex::new)
                    .toArray(Complex[]::new);

            Complex[] expected =
                clue.complexAnswers.toArray(Complex[]::new);

            if (actual.length != expected.length) {
                return new CheckResult(false, clue.feedback);
            }

            for (int i = 0; i < actual.length; i++) {
                if (!actual[i].equals(expected[i])) {
                    return new CheckResult(false, clue.feedback);
                }
            }

            return CheckResult.correct();
        }
        catch (NumberFormatException ex) {
            return new CheckResult(false,
                "Numbers should be written in file on " +
                    "separate lines without any additional symbols");
        }
    }
}
