package solver;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        // a * x = b
        // x = b / a;
        System.out.println(b / a);
    }
}
