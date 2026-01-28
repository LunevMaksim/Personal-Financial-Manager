import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {
    public static void main(String[] args) {
        System.out.println("===Personal-Financial-Manager===");
        Scanner scanner = new Scanner(System.in);
        FinanceManager financeManager = new FinanceManager();
        menu(scanner, financeManager);
    }
    static void menu(Scanner scanner, FinanceManager financeManager){
        boolean val = true;
        while (!val){
            try {
                System.out.println("1. Добавить транзакцию");
                System.out.println("2. Удалить транзакцию");
                System.out.println("3. Найти транзакцию");
                System.out.println("4. Выход");
                System.out.print("Введите номер команды: ");
                int command = scanner.nextInt();
                switch (command){
                    case 1:
                        financeManager.addTransaction(scanner);
                        break;
                    case 2:
                        financeManager.removeTransaction(scanner);
                        break;
                    case 3:
                        financeManager.searchTransaction(scanner);
                        break;
                    case 4:
                        val = true;
                    default:
                        System.out.println("Ошибка! Номер команды не найден!");
                        scanner.nextLine();
                }
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введено нечисловое значение");
                scanner.nextLine();
            }
        }
    }
}
