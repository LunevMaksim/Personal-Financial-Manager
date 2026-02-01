import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {
    public static void main(String[] args) {
        System.out.println("===Personal-Financial-Manager===");
        Scanner scanner = new Scanner(System.in);
        FinanceManager financeManager = new FinanceManager();
        System.out.println("Данные загружены. Всего транзакций: " + financeManager.getList().size());
        menu(scanner, financeManager);
    }
    static void menu(Scanner scanner, FinanceManager financeManager){
        boolean val = false;
        while (!val){
            try {
                System.out.println("1. Добавить транзакцию");
                System.out.println("2. Удалить транзакцию");
                System.out.println("3. Найти транзакцию");
                System.out.println("4. Показать все транзакции");
                System.out.println("5. Показать статистику");
                System.out.println("6. Редактировать транзакцию");
                System.out.println("7. Выход");
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
                        financeManager.allTransaction();
                        break;
                    case 5:
                        financeManager.menuForStatistic(scanner);
                        break;
                    case 6:
                        financeManager.editTransaction(scanner);
                        break;
                    case 7:
                        val = true;
                        break;
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
