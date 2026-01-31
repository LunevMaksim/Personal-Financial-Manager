import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class FinanceManager {
    private List<Transaction> list = new ArrayList<>();
    private int nextId = 1;
    private FileStorage storage;

    public FinanceManager() {
        this.storage = new FileStorage();
        this.list = loadTransactions();
    }

    public List<Transaction> loadTransactions() {
        try {
            List<Transaction> loaded = storage.load();
            if (!loaded.isEmpty()) {
                nextId = loaded.stream()
                        .mapToInt(Transaction::getId)
                        .max()
                        .orElse(0) + 1;
            }
            return loaded;
        } catch (IOException e) {
            System.err.println("Ошибка загрузки данных: " + e.getMessage());
            return new ArrayList<>();
        }
    }

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
        saveTransactions();
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
        } else {
            saveTransactions();
            System.out.println("Транзакция с id " + id + " удалена!");
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

    public void saveTransactions() {
        try {
            storage.save(list);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    public List<Transaction> getList() {
        return list;
    }
}
