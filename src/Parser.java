import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Parser {
    private String filename;

    public Parser(String filename) {
        this.filename = filename;
    }

    public Map<Character, Vector<Character>> buildGraph() throws IOException {
        Map<Character, Vector<Character>> graph = new HashMap<>();

        // Read the matrix from file and build graph
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            int numRows = lines.size();
            int numCols = lines.get(0).replaceAll("\\s+", "").length();

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    char currentNode = lines.get(i).replaceAll("\\s+", "").charAt(j);
                    if (!graph.containsKey(currentNode)) {
                        graph.put(currentNode, new Vector<>());
                    }

                    // Connect with horizontal and vertical neighbors
                    if (j > 0) {
                        char leftNode = lines.get(i).replaceAll("\\s+", "").charAt(j - 1);
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

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser("../data/test.txt");
        Map<Character, Vector<Character>> graph = parser.buildGraph();

        // Print the graph (for verification)
        for (Map.Entry<Character, Vector<Character>> entry : graph.entrySet()) {
            System.out.print(entry.getKey() + " connected with: ");
            System.out.println(entry.getValue());
        }
    }
}
