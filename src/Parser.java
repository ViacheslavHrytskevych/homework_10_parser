import java.io.*;
import java.util.*;
import java.util.List;

public class Parser {

    public static void main(String[] args) {

        System.out.println("Enter the book name:  ");

        Scanner scanner = new Scanner(System.in);
        String bookName = scanner.nextLine();

        File bookFile = new File("src/" + bookName);
        if (!bookFile.exists()) {
            System.out.println("The book cannot found! Please, try again!");
            return;
        }

        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(bookFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\W+");
                for (String word : lineWords) {
                    words.add(word.toLowerCase());
                }
            }
        } catch (IOException e) {
            System.out.println("Unknown error when reading a file");
            return;
        }

        Map<String, Integer> wordFrequency = new TreeMap<>();
        for (String word : words) {
            if (word.length() > 2) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(wordFrequency.entrySet());
        sortedList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        System.out.println("10 the most popular words is :");
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.printf("%s - %d\n", entry.getKey(), entry.getValue());
            count++;
            if (count == 10)
                break;
        }

        Set<String> uniqueWords = new HashSet<>(words);
        int uniqueWordsCount = 0;
        for (String word : uniqueWords) {
            if (word.length() > 2) {
                uniqueWordsCount++;
            }
        }
        System.out.println("Unigue words - " + uniqueWordsCount);

        String fileName = String.format("%s_statistic.txt", bookName);
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                writer.printf("%s: %d%n", entry.getKey(), entry.getValue());
            }
            writer.printf("Total unique words (with length > 2): %d%n", uniqueWordsCount);
            System.out.printf("Statistics saved to file '%s'.%n", fileName);
        } catch (IOException e) {
            System.out.println("UPS! Something is wrong!");
        }
    }
}

