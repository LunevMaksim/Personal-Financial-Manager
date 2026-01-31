import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

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
                ", Дата: " + date.format(DATE_FORMATTER) +
                ", Сумма: " + amount +
                ", Тип: " + type.getRussianType() +
                ", Категория: '" + category + '\'' +
                ", Описание: '" + description + '\'';
    }

    public String toCsvString(){
        return id + "; " + date.format(DATE_FORMATTER) + "; " + amount + "; " + type.getRussianType() + "; " + category + "; " + description;
    }

    public static Transaction fromCsvString(String line){
        String[] parts = line.split("; ");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Некорректный формат CSV строки: " + line);
        }

        int id = Integer.parseInt(parts[0]);
        LocalDateTime date = LocalDateTime.parse(parts[1], DATE_FORMATTER);
        double amount = Double.parseDouble(parts[2]);
        Type type = Type.fromType(parts[3]);
        String category = parts[4];
        String description = parts[5];

        return new Transaction(id, date, amount, type, category, description);
    }
}
