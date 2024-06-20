import java.util.*;

public class WordSearcher {
    private Map<Character, Vector<Character>> graph;

    public WordSearcher(Map<Character, Vector<Character>> graph) {
        this.graph = graph;
    }

    public List<String> findWords(List<String> words) {
        List<String> foundWords = new ArrayList<>();

        for (String word : words) {
            char startChar = word.charAt(0);
            boolean wordFound = false;

            // Search for all occurrences of the starting character
            for (Map.Entry<Character, Vector<Character>> entry : graph.entrySet()) {
                char currentChar = entry.getKey();
                if (currentChar == startChar) {
                    // Perform DFS to check if the word exists starting from this character
                    if (dfs(entry.getKey(), word, 0, new HashSet<>())) {
                        foundWords.add(word);
                        wordFound = true;
                        break; // Move to the next word
                    }
                }
            }
        }

        return foundWords;
    }

    private boolean dfs(char currentChar, String word, int index, Set<Character> visited) {
        // Base case: If entire word is matched
        if (index == word.length()) {
            return true;
        }

        // If current character doesn't match or is already visited
        if (currentChar != word.charAt(index) || visited.contains(currentChar)) {
            return false;
        }

        // Mark current character as visited
        visited.add(currentChar);

        // Explore neighbors (horizontal, vertical, diagonal)
        for (char neighbor : graph.get(currentChar)) {
            if (dfs(neighbor, word, index + 1, visited)) {
                return true; // If word found in this path, return true
            }
        }

        // Backtrack: Unmark current character
        visited.remove(currentChar);

        return false;
    }

    public static void main(String[] args) {
        // Example usage:
        Parser parser = new Parser("../data/test.txt");
        Map<Character, Vector<Character>> graph = null;
        try {
            graph = parser.buildGraph();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        WordSearcher wordSearcher = new WordSearcher(graph);

        List<String> wordsToSearch = Arrays.asList(
            "MARSHALL", "MANHATTAN", "MOSBY", "TED", "TEACHER",
                "SLAPSGIVING", "BARNEY", "LAWYER", "LILY", "ERIKSEN",
                "STINSON", "PRESENTER", "ALDRIN", "ROBIN"
            );

        List<String> foundWords = wordSearcher.findWords(wordsToSearch);

        System.out.println("Words found:");
        for (String word : foundWords) {
            System.out.println(word);
        }
    }
}
