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

        double amount = readDouble(scanner);
        trans.setAmount(amount);


        while (true){
            System.out.print("Введите тип транзакции(Доход/расход): ");
            String type = scanner.nextLine();
            Type mayBeType = Type.fromType(type);
            if (mayBeType == null){
                System.out.println("Ошибка! Неверный тип транзакции");
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

    public void removeTransaction(Scanner scanner){
        if (getList().isEmpty()){
            System.out.println("Список транзакций пуст!");
            return;
        }
        int id;
        while (true){
            try {
                System.out.print("Введите айди для удаления: ");
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введено не числовое значение");
                scanner.nextLine();
            }
        }
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                list.remove(i);
                found = true;
                System.out.println("Транзакция с id " + id + " удалена!");
                break;
            }
        }

        if (!found) {
            System.out.println("Транзакция с id " + id + " не найдена");
        }
    }

    public void searchTransaction(Scanner scanner){
        if (getList().isEmpty()){
            System.out.println("Список транзакций пуст!");
            return;
        }
        int num = menuForSearch(scanner);
        switch (num){
            case 1:
                System.out.print("Введите категорию: ");
                String category = scanner.nextLine().toLowerCase().trim();
                for (int i = 0; i < getList().size(); i++) {
                    if (getList().get(i).getCategory().equalsIgnoreCase(category)){
                        System.out.println(getList().get(i));
                    }
                }
                break;
            case 2:
                System.out.print("Введите тип транзакции: ");
                String type = scanner.nextLine();
                Type mayBeType = Type.fromType(type);
                if (mayBeType == null) {
                    System.out.println("Ошибка! Неправильный тип транзакции.");
                    break;
                }
                for (int i = 0; i < getList().size(); i++) {
                    if (getList().get(i).getType().equals(mayBeType)){
                        System.out.println(getList().get(i));
                    }
                }
                break;
        }
    }

    public void allTransaction(){
        if (getList().isEmpty()){
            System.out.println("Список транзакций пуст!");
            return;
        }
        for(Transaction t: getList()){
            System.out.println(t);
        }
    }

    public int menuForSearch(Scanner scanner){
        System.out.println("Поиск транзакций:");
        System.out.println("1. По категории");
        System.out.println("2. По типу (доход/расход)");
        int num;
        while (true){
            try {
                System.out.print("Введите номер для поиска: ");
                num = scanner.nextInt();
                if (num == 1 || num == 2){
                    scanner.nextLine();
                    return num;
                }
                System.out.println("Неверный номер команды!");
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введено не числовое значение");
            }
            finally {
                scanner.nextLine();
            }
        }
    }

    public double readDouble(Scanner scanner) {
        double amount;
        while (true) {
            try {
                System.out.print("Введите сумму: ");
                amount = scanner.nextDouble();
                return amount;
            } catch (InputMismatchException exception) {
                System.out.println("Ошибка! Введено не числовое значение");
            } finally {
                scanner.nextLine();
            }
        }
    }

    public List<Transaction> getList() {
        return list;
    }
}
