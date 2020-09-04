import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] characters = scanner.nextLine().split(" ");
        String character = scanner.nextLine();
        int counter = 0;
        for (String ch : characters) {
            if (ch.equals(character)) {
                counter++;
            }
        }
        System.out.println(counter);
    }
}