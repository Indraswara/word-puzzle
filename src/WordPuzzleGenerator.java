import java.util.*;

public class WordPuzzleGenerator {
    private char[][] puzzle;
    private char[][] answer;
    private List<String> placedWords;

    public WordPuzzleGenerator(int size) {
        this.puzzle = new char[size][size];
        this.answer = new char[size][size];
        this.placedWords = new ArrayList<>();
    }

    public void generatePuzzle(List<String> words) {
        // Initialize puzzle and answer grids with random characters
        initializeGrids();

        // Place each word in the puzzle
        for (String word : words) {
            placeWord(word.toUpperCase()); // Place word in uppercase for consistency
        }

        // Copy the answer grid to answer array
        for (int i = 0; i < puzzle.length; i++) {
            System.arraycopy(puzzle[i], 0, answer[i], 0, puzzle[0].length);
        }
    }

    private void initializeGrids() {
        Random random = new Random();
        // Fill both grids with random uppercase letters ('A' to 'Z')
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                char randomChar = (char) (random.nextInt(26) + 'A');
                puzzle[i][j] = randomChar;
                answer[i][j] = randomChar;
            }
        }
    }

    private void placeWord(String word) {
        Random random = new Random();
        boolean wordPlaced = false;

        while (!wordPlaced) {
            int direction = random.nextInt(8); // Random direction: 0-7 (0=right, 1=down, ...)
            int startRow = random.nextInt(puzzle.length);
            int startCol = random.nextInt(puzzle[0].length);

            // Try placing the word in the chosen direction
            if (canPlaceWord(word, startRow, startCol, direction)) {
                placeWordInGrid(word, startRow, startCol, direction);
                placedWords.add(word);
                wordPlaced = true;
            }
        }
    }

    private boolean canPlaceWord(String word, int startRow, int startCol, int direction) {
        int wordLength = word.length();
        int endRow = startRow + getDeltaRow(direction) * (wordLength - 1);
        int endCol = startCol + getDeltaCol(direction) * (wordLength - 1);

        // Check if the word fits within the grid boundaries
        if (endRow < 0 || endRow >= puzzle.length || endCol < 0 || endCol >= puzzle[0].length) {
            return false;
        }

        // Check if the positions are free
        for (int i = 0; i < wordLength; i++) {
            int currentRow = startRow + i * getDeltaRow(direction);
            int currentCol = startCol + i * getDeltaCol(direction);
            if (puzzle[currentRow][currentCol] != answer[currentRow][currentCol]) {
                return false;
            }
        }

        return true;
    }

    private void placeWordInGrid(String word, int startRow, int startCol, int direction) {
        for (int i = 0; i < word.length(); i++) {
            int currentRow = startRow + i * getDeltaRow(direction);
            int currentCol = startCol + i * getDeltaCol(direction);
            puzzle[currentRow][currentCol] = word.charAt(i);
        }
    }

    private int getDeltaRow(int direction) {
        switch (direction) {
            case 0: return 0;   // Right
            case 1: return 1;   // Down
            case 2: return 1;   // Down-Right
            case 3: return 1;   // Down-Left
            case 4: return 0;   // Left
            case 5: return -1;  // Up-Left
            case 6: return -1;  // Up
            case 7: return -1;  // Up-Right
            default: return 0;
        }
    }

    private int getDeltaCol(int direction) {
        switch (direction) {
            case 0: return 1;   // Right
            case 1: return 0;   // Down
            case 2: return 1;   // Down-Right
            case 3: return -1;  // Down-Left
            case 4: return -1;  // Left
            case 5: return -1;  // Up-Left
            case 6: return 0;   // Up
            case 7: return 1;   // Up-Right
            default: return 0;
        }
    }

    public void printPuzzle() {
        System.out.println("Puzzle:");
        printGrid(puzzle);
    }

    private void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("JAVA", 
        "PYTHON", 
        "C", 
        "ALGORITHM", 
        "COMPUTER");

        int size = 10; // Size of the NxN grid
        WordPuzzleGenerator generator = new WordPuzzleGenerator(size);
        generator.generatePuzzle(words);

        generator.printPuzzle();
        System.out.println();
    }
}
