import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    private static final String FILE_NAME = "Personal Finance Manager.csv";

    public void save(List<Transaction> transactions) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))){
            for (Transaction t: transactions){
                writer.println(t.toCsvString());
            }
        } catch (IOException e) {
            throw new IOException("Не удалось сохранить файл: " + e.getMessage(), e);
        }
    }

    public List<Transaction> load() throws IOException {
        List<Transaction> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()){
            return tasks;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                try {
                    Transaction transaction = Transaction.fromCsvString(line);
                    tasks.add(transaction);
                } catch (Exception exception){
                    System.err.println("Ошибка при чтении строки: " + line + " - " + exception.getMessage());
                }
            }
        }
        return tasks;
    }
}

