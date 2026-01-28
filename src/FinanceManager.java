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

    public void removeTransaction(Scanner scanner){
        int id;
        while (true){
            try {
                System.out.print("Введите айди для удаления: ");
                id = scanner.nextInt();
                break;
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введено не числовое значение");
                scanner.nextLine();
            }
        }
        if (id < 1 || id > getList().size()){
            System.out.println("Ошибка! Id нет в списке");
            return;
        }
        boolean val = false;
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getId() == id){
                val = true;
                getList().remove(getList().get(i));
                System.out.println("Задача №" + id + " была удалена!" );
                break;
            }
        }
        if (!val){
            System.out.println("Задача не найдена!");
        }
    }

    public void searchTransaction(Scanner scanner){
        int num = menuForSearch(scanner);
        switch (num){
            case 1:
                System.out.print("Введите категорию: ");
                String category = scanner.nextLine().toLowerCase().trim();
                for (int i = 0; i < getList().size(); i++) {
                    if (getList().get(i).getCategory().equals(category)){
                        System.out.println(getList().get(i));
                    }
                }
                break;
            case 2:
                System.out.print("Введите тип транзакции: ");
                String type = scanner.nextLine().toLowerCase().trim();
                for (int i = 0; i < getList().size(); i++) {
                    if (getList().get(i).getType().equals(type)){
                        System.out.println(getList().get(i));
                    }
                }
                break;
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

    public List<Transaction> getList() {
        return list;
    }
}
