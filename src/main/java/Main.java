import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter mathematical expression with: '0-9', '.', '*', '/', '+', '-'");
        System.out.println("Example: ((2.5*-3)-(10.3-4+(-3)))/4");
        System.out.println("Enter 'q' for exit");
        do {
            System.out.print("Expression: ");
            String input = scanner.next();
            if (input.equals("q"))
                break;
            try {
                double res = Calculator.calculate(input);
            }
            catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        } while (true);
    }
}
