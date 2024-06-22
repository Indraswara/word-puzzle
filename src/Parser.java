import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Parser {
    private final String filename;
    private final String filename_words;
    private char[][] matrix;

    public Parser(String filename, String filename_words) {
        this.filename = filename;
        this.filename_words = filename_words;
    }

    public void buildMatrix() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            int numRows = lines.size();
            int numCols = lines.get(0).replaceAll("\\s+", "").length();

            // Initialize the matrix
            matrix = new char[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                String cleanLine = lines.get(i).replaceAll("\\s+", "");
                for (int j = 0; j < numCols; j++) {
                    matrix[i][j] = cleanLine.charAt(j);
                }
            }
        }
    }
    
    public List<String> generateWordsFromFile() {
        List<String> wordList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename_words))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return wordList;
    }
    
    public char[][] getMatrix() {
        return matrix;
    }

    public static void main(String[] args) {
        try {
            Parser parser = new Parser("test.txt", "test-words.txt"); 
            parser.buildMatrix();
            char[][] matrix = parser.getMatrix();
            List<String> words = parser.generateWordsFromFile();
            WordSearcher searcher = new WordSearcher(matrix);
            List<String> foundWords = searcher.findWords(words);
            System.out.println("Found Words: " + foundWords);
            searcher.printMatrix();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
