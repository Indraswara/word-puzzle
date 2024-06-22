import java.util.*;

public class WordSearcher {
    private final Map<Character, Vector<Character>> graph;
    private final char[][] matrix;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    public WordSearcher(Map<Character, Vector<Character>> graph, char[][] matrix) {
        this.graph = graph;
        this.matrix = matrix;
    }

    public List<String> findWords(List<String> words) {
        List<String> foundWords = new ArrayList<>();

        for (String word : words) {
            char startChar = word.charAt(0);
            boolean wordFound = false;

            // Search for all occurrences of the starting character
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] == startChar) {
                        // Perform DFS to check if the word exists starting from this position
                        List<int[]> path = new ArrayList<>();
                        if (dfs(i, j, word, 0, new HashSet<>(), path, -1)) {
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
        }

        return foundWords;
    }

    private boolean dfs(int row, int col, String word, int index, Set<String> visited, List<int[]> path, int direction) {
        if (index == word.length()) {
            return true;
        }

        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length ||
            visited.contains(row + "," + col) || matrix[row][col] != word.charAt(index)) {
            return false;
        }

        visited.add(row + "," + col);
        path.add(new int[]{row, col});

        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // Up, Down, Left, Right
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // Top-left, Top-right, Bottom-left, Bottom-right
        };

        if (direction == -1) { // If no direction yet, try all directions
            for (int d = 0; d < directions.length; d++) {
                if (dfs(row + directions[d][0], col + directions[d][1], word, index + 1, visited, path, d)) {
                    return true;
                }
            }
        } else { // Continue in the same direction
            int[] dir = directions[direction];
            if (dfs(row + dir[0], col + dir[1], word, index + 1, visited, path, direction)) {
                return true;
            }
        }

        visited.remove(row + "," + col);
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
        for (char[] matrix1 : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                char currentChar = matrix1[j];
                if (Character.isLowerCase(currentChar)) {
                    System.out.print(ANSI_YELLOW + Character.toUpperCase(currentChar) + " " + ANSI_RESET);
                } else {
                    System.out.print(ANSI_CYAN + Character.toUpperCase(currentChar) + " " + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }
}
