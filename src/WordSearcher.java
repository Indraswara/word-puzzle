import java.util.*;

public class WordSearcher {
    private final char[][] matrix;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    public WordSearcher(char[][] matrix) {
        this.matrix = matrix;
    }

    public List<String> findWords(List<String> words) {
        List<String> foundWords = new ArrayList<>();

        for (String word : words) {
            boolean wordFound = false;

            // Search for all occurrences of the starting character
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] == word.charAt(0)) {
                        // Try all 8 possible directions
                        for (int direction = 0; direction < 8; direction++) {
                            List<int[]> path = new ArrayList<>();
                            if (dfs(i, j, word, 0, path, direction)) {
                                foundWords.add(word);
                                wordFound = true;
                                markFoundPath(path);
                                break; // Move to the next word
                            }
                        }
                    }
                    if (wordFound) {
                        break; // Move to the next word
                    }
                }
                if (wordFound) {
                    break; // Move to the next word
                }
            }
        }

        return foundWords;
    }

    private boolean dfs(int row, int col, String word, int index, List<int[]> path, int direction) {
        if (index == word.length()) {
            return true;
        }

        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length ||
            matrix[row][col] != word.charAt(index)) {
            return false;
        }

        path.add(new int[]{row, col});

        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // Up, Down, Left, Right
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // Top-left, Top-right, Bottom-left, Bottom-right
        };

        int newRow = row + directions[direction][0];
        int newCol = col + directions[direction][1];

        if (dfs(newRow, newCol, word, index + 1, path, direction)) {
            return true;
        }

        path.remove(path.size() - 1);

        return false;
    }

    private void markFoundPath(List<int[]> path) {
        for (int[] pos : path) {
            int row = pos[0];
            int col = pos[1];
            matrix[row][col] = Character.toLowerCase(matrix[row][col]); // Convert to lowercase
        }
    }

    public void printMatrix() {
        for (char[] row : matrix) {
            for (char cell : row) {
                if (Character.isLowerCase(cell)) {
                    System.out.print(ANSI_YELLOW + Character.toUpperCase(cell) + " " + ANSI_RESET);
                } else {
                    System.out.print(ANSI_CYAN + cell + " " + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }
}
