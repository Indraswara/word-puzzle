import java.io.IOException;
import java.util.*;

public class WordSearcher {
    private Map<Character, Vector<Character>> graph;
    private char[][] matrix;
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
                        if (dfs(i, j, word, 0, new HashSet<>(), path)) {
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

    private boolean dfs(int row, int col, String word, int index, Set<Character> visited, List<int[]> path) {
        if (index == word.length()) {
            return true;
        }
    
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length ||
            visited.contains(matrix[row][col]) || matrix[row][col] != word.charAt(index)) {
            return false;
        }
    
        visited.add(matrix[row][col]);
        path.add(new int[]{row, col});
    
        int[] dRow = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dCol = {0, 0, -1, 1, -1, 1, -1, 1};
    
        if (index == 0) {
            char firstChar = word.charAt(0);
            for (int d = 0; d < 8; d++) {
                if (dfs(row + dRow[d], col + dCol[d], word, 1, visited, path)) {
                    return true;
                }
            }
        } else if (index > 0 && index < path.size()) {
            int currentDirection = getDirection(path.get(index), path.get(index - 1));
            int d = currentDirection;
            if (dfs(row + dRow[d], col + dCol[d], word, index + 1, visited, path)) {
                return true;
            }
        }
    
        visited.remove(matrix[row][col]);
        path.remove(path.size() - 1);
    
        return false;
    }
    
    private int getDirection(int[] currentPos, int[] prevPos) {
        if (prevPos == null || prevPos.length != 2) {
            return -1; // Invalid direction
        }
    
        int dRow = currentPos[0] - prevPos[0];
        int dCol = currentPos[1] - prevPos[1];
    
        if (dRow == 0 && dCol == -1) {
            return 2; // Left
        } else if (dRow == 0 && dCol == 1) {
            return 3; // Right
        } else if (dRow == -1 && dCol == 0) {
            return 0; // Up
        } else if (dRow == 1 && dCol == 0) {
            return 1; // Down
        } else if (dRow == -1 && dCol == -1) {
            return 4; // Top-left
        } else if (dRow == -1 && dCol == 1) {
            return 5; // Top-right
        } else if (dRow == 1 && dCol == -1) {
            return 6; // Bottom-left
        } else if (dRow == 1 && dCol == 1) {
            return 7; // Bottom-right
        } else {
            return -1; // Invalid direction
        }
    }

    private void markFoundPath(List<int[]> path) {
        for (int[] pos : path) {
            int row = pos[0];
            int col = pos[1];
            matrix[row][col] = Character.toLowerCase(matrix[row][col]); // Convert to lowercase
        }
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                char currentChar = matrix[i][j];
                if (Character.isLowerCase(currentChar)) {
                    System.out.print(ANSI_YELLOW + Character.toUpperCase(currentChar) + " " + ANSI_RESET);
                } else {
                    System.out.print(ANSI_CYAN + Character.toUpperCase(currentChar) + " " + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Example usage:
        Parser parser = new Parser("../data/large-1.txt", "../data/large-1-words.txt");
        Map<Character, Vector<Character>> graph = null;
        List<String> wordsToSearch = null;
        char[][] matrix = null;

        try {
            graph = parser.buildGraph();
            matrix = parser.getMatrix();
            wordsToSearch = parser.generateWordsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        WordSearcher wordSearcher = new WordSearcher(graph, matrix);


        List<String> foundWords = wordSearcher.findWords(wordsToSearch);

        System.out.println("Words found:");
        for (String word : foundWords) {
            System.out.println(word);
        }

        System.out.println("\nMatrix with found words highlighted:");
        wordSearcher.printMatrix();
    }
}
