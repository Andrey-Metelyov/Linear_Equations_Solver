import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int first = scanner.nextInt();
        int last = scanner.nextInt();
        int number = scanner.nextInt();
        first = first + (first % number > 0 ? number - (first % number) : 0);
        int count = 1 + (last - first) / number;
        System.out.println(count);
    }
}