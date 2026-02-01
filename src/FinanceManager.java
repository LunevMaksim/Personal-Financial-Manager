import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


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
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getId() == id) {
                getList().remove(i);
                found = true;
                System.out.println("Транзакция с id " + id + " удалена!");
                break;
            }
        }
        if (!found) {
            System.out.println("Транзакция с id " + id + " не найдена");
        } else {
            saveTransactions();
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

    public void calculateBalance(){
        if (getList().isEmpty()) {
            System.out.println("Нет транзакций для статистики.");
            return;
        }
        double balance = 0;
        for (int i = 0; i < getList().size(); i++) {
            double amount = getList().get(i).getAmount();
            if (getList().get(i).getType().getRussianType().equalsIgnoreCase("Доход")){
                balance += amount;
            }
            else {
                balance -= amount;
            }
        }
        System.out.println("Общий баланс: " + balance);
    }

    public void getTotalIncome(){
        if (getList().isEmpty()) {
            System.out.println("Нет транзакций для статистики.");
            return;
        }
        double getIncome = 0;
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getType() == Type.INCOME){
                getIncome += getList().get(i).getAmount();
            }
        }
        System.out.println("Доходы составили: " + getIncome);
    }
    public void getTotalExpense(){
        if (getList().isEmpty()) {
            System.out.println("Нет транзакций для статистики.");
            return;
        }
        double getExpense = 0;
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getType() == Type.EXPENSE){
                getExpense += getList().get(i).getAmount();
            }
        }
        System.out.println("Расходы составили: " + getExpense);
    }

    public void getCategoryStats() {
        if (getList().isEmpty()) {
            System.out.println("Нет транзакций для статистики.");
            return;
        }
        Map<String, Double> map = new HashMap<>();
        for (int i = 0; i < getList().size(); i++) {
            double sum = getList().get(i).getAmount();
            String category = getList().get(i).getCategory().toLowerCase().trim();
            if (!map.containsKey(category)){
                map.put(category, sum);
            }
            else {
                double thisSum = map.get(category);
                thisSum += sum;
                map.put(category, thisSum);
            }
        }

        int i = 1;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String categoryName = entry.getKey();
            categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1);
            System.out.printf("%2d. %-10s %8.2f руб%n",
                    i++, categoryName, entry.getValue());
        }
    }

    public void editTransaction(Scanner scanner){
        if (getList().isEmpty()){
            System.out.println("Список транзакций пуст!");
            return;
        }
        int id;
        while (true){
            try {
                System.out.print("Введите айди для редактирования: ");
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введено не числовое значение");
                scanner.nextLine();
            }
        }
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getId() == id) {
                System.out.println("Найдена транзакция: " + getList().get(i).getCategory() + ", " + getList().get(i).getAmount() + ", " + getList().get(i).getType() + ", " + getList().get(i).getDescription());
                int command = menuForEdit(scanner);
                if (command == 0) {
                    System.out.println("Редактирование отменено.");
                    return;
                }
                boolean val = false;
                switch (command){
                    case 1:
                        System.out.println("Текущая сумма " + getList().get(i).getAmount());
                        double newAmount = readDouble(scanner);
                        getList().get(i).setAmount(newAmount);
                        val = true;
                        break;
                    case 2:
                        System.out.println("Текущий тип " + getList().get(i).getType());
                        System.out.print("Введите новый тип: ");
                        while (true){
                            String newType = scanner.nextLine();
                            Type mayBeType = Type.fromType(newType);
                            if (mayBeType == null){
                                System.out.println("Ошибка! Неверный тип транзакции");
                            }
                            else {
                                getList().get(i).setType(mayBeType);
                                break;
                            }
                        }
                        val = true;
                        break;
                    case 3:
                        System.out.println("Текущая категория " + getList().get(i).getCategory());
                        System.out.print("Введите новую категорию: ");
                        String newCategory = scanner.nextLine();
                        getList().get(i).setCategory(newCategory);
                        val = true;
                        break;
                    case 4:
                        System.out.println("Текущее описание " + getList().get(i).getDescription());
                        System.out.print("Введите новое описание: ");
                        String newDescription = scanner.nextLine();
                        getList().get(i).setDescription(newDescription);
                        val = true;
                        break;
                }
                if (val){
                    System.out.println("Транзакция id:" + id + " была успешно изменена");
                    saveTransactions();
                }
                return;
            }
        }
        System.out.println("Транзакция с id " + id + " не найдена");
    }


    public void menuForStatistic(Scanner scanner){
        System.out.println("   → 1. Общий баланс\n" +
                "   → 2. Доходы/расходы\n" +
                "   → 3. Статистика по категориям");
        int command;
        while (true){
            try {
                System.out.print("Введите номер команды: ");
                command = scanner.nextInt();
                if (command >= 1 && command <= 3){
                    break;
                }
                System.out.println("Ошибка! Неверный ввод команды!");
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введите числовое значение!");
            }
            finally {
                scanner.nextLine();
            }
        }
        switch (command){
            case 1:
                calculateBalance();
                break;
            case 2:
                getTotalIncome();
                getTotalExpense();
                break;
            case 3:
                getCategoryStats();
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

    public int menuForEdit(Scanner scanner){
        System.out.println("Что редактируем?");
        System.out.println("1. Сумму");
        System.out.println("2. Тип");
        System.out.println("3. Категорию");
        System.out.println("4. Описание");
        System.out.println("0. Отмена");
        int command;
        while (true){
            try {
                System.out.print("Введите номер команды: ");
                command = scanner.nextInt();
                if (command >= 0 && command <= 4){
                    break;
                }
                System.out.println("Ошибка! Неверный ввод команды!");
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введите числовое значение!");
            }
            finally {
                scanner.nextLine();
            }
        }
        return command;
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
