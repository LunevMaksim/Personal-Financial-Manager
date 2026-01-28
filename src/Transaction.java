import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private LocalDateTime date;
    private double amount;
    private Type type;
    private String category;
    private String description;


    public Transaction(int id, LocalDateTime date, double amount, Type type, String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
    }
    public Transaction() {}


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Транзакция: " +
                "id: " + id +
                ", Дата: " + date +
                ", Сумма: " + amount +
                ", Тип: " + type.getRussianType() +
                ", Категория: '" + category + '\'' +
                ", Описание: '" + description + '\'' +
                '}';
    }
}
