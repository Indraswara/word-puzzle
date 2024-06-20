import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Parser {
    private String filename;
    private Map<Character, Vector<Character>> graph;
    private char[][] matrix;

    public Parser(String filename) {
        this.filename = filename;
        this.graph = new HashMap<>();
    }

    public Map<Character, Vector<Character>> buildGraph() throws IOException {
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
                    char currentNode = cleanLine.charAt(j);
                    matrix[i][j] = currentNode;

                    if (!graph.containsKey(currentNode)) {
                        graph.put(currentNode, new Vector<>());
                    }

                    // Connect with horizontal and vertical neighbors
                    if (j > 0) {
                        char leftNode = cleanLine.charAt(j - 1);
                        graph.get(currentNode).add(leftNode);
                        if (!graph.containsKey(leftNode)) {
                            graph.put(leftNode, new Vector<>());
                        }
                        graph.get(leftNode).add(currentNode); // undirected graph
                    }
                    if (i > 0) {
                        char aboveNode = lines.get(i - 1).replaceAll("\\s+", "").charAt(j);
                        graph.get(currentNode).add(aboveNode);
                        if (!graph.containsKey(aboveNode)) {
                            graph.put(aboveNode, new Vector<>());
                        }
                        graph.get(aboveNode).add(currentNode); // undirected graph
                    }

                    // Connect with diagonal neighbors (top-left and top-right)
                    if (i > 0 && j > 0) {
                        char topLeftNode = lines.get(i - 1).replaceAll("\\s+", "").charAt(j - 1);
                        graph.get(currentNode).add(topLeftNode);
                        if (!graph.containsKey(topLeftNode)) {
                            graph.put(topLeftNode, new Vector<>());
                        }
                        graph.get(topLeftNode).add(currentNode); // undirected graph
                    }
                    if (i > 0 && j < numCols - 1) {
                        char topRightNode = lines.get(i - 1).replaceAll("\\s+", "").charAt(j + 1);
                        graph.get(currentNode).add(topRightNode);
                        if (!graph.containsKey(topRightNode)) {
                            graph.put(topRightNode, new Vector<>());
                        }
                        graph.get(topRightNode).add(currentNode); // undirected graph
                    }
                }
            }
        }

        return graph;
    }

    public char[][] getMatrix() {
        return matrix;
    }
}
