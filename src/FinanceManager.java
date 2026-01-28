import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class FinanceManager {
    private List<Transaction> list = new ArrayList<>();
    private int nextId = 1;

    public void addTransaction(Scanner scanner){
        Transaction trans = new Transaction();

        trans.setId(nextId++);

        trans.setDate(LocalDateTime.now());

        double amount;
        while (true){
            try {
                System.out.print("Введите сумму: ");
                amount = scanner.nextDouble();
                break;
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введено не числовое значение");
                scanner.nextLine();
            }
        }
        trans.setAmount(amount);


        while (true){
            System.out.print("Введите тип транзакции(Доход/расход): ");
            String type = scanner.nextLine();
            Type mayBeType = Type.fromType(type);
            if (mayBeType == null){
                System.out.println("Ошибка! Неверный тип транзакции");
                scanner.nextLine();
            }
            else {
                trans.setType(mayBeType);
                break;
            }
        }

        System.out.print("Введите категорию транзакции: ");
        String category = scanner.nextLine();
        trans.setCategory(category);

        System.out.print("Введите описание транзакции: ");
        String description = scanner.nextLine();
        trans.setDescription(description);

        getList().add(trans);
        System.out.println("Транзакция успешно добавлена!");
    }


    public List<Transaction> getList() {
        return list;
    }
}
