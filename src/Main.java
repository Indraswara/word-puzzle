import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while(true){

            String boardPath;
            String wordsPath;
            try {
                System.out.println("Selamat datang di word-puzzle");
                System.out.print("Masukkan path puzzle: ");
                boardPath = scanner.next();
                System.out.print("Masukkan path file kata yang dicari: ");
                wordsPath = scanner.next();
            } catch (Exception e) {
                System.err.println("Input error: " + e.getMessage());
                return;
            }
    
            Parser parser = new Parser(boardPath, wordsPath);
            List<String> wordsToSearch;
            char[][] matrix;
    
            try {
                parser.buildMatrix();
                matrix = parser.getMatrix();
                wordsToSearch = parser.generateWordsFromFile();
            } catch (IOException e) {
                System.err.println("Error reading puzzle or word file: " + e.getMessage());
                continue;
            }
    
            WordSearcher wordSearcher = new WordSearcher(matrix);
            List<String> foundWords = wordSearcher.findWords(wordsToSearch);
    
            System.out.println("Words found:");
            for (String word : foundWords) {
                System.out.println(word);
            }
            System.out.println("\nMatrix with found words highlighted:");
            wordSearcher.printMatrix();
    
            // Ask user if they want to continue or exit
            System.out.print("\nDo you want to continue (Y/N)? ");
            String choice = scanner.next().trim();
            if (!choice.equalsIgnoreCase("Y")) {
                System.out.println("Terima kasih telah menggunakan word-puzzle.");
                break;
            }
        }
        scanner.close();
    } 
}
