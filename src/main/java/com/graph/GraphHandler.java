package com.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GraphHandler {
    private DirectedPseudograph<String, DefaultEdge> graph;

    public GraphHandler() {
        this.graph = new DirectedPseudograph<>(DefaultEdge.class);
    }

    public void parseGraph(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        for (String line : lines) {
            if (line.contains("->")) {
                String[] parts = line.trim().replace(";", "").split("->");
                String src = parts[0].trim();
                String dst = parts[1].trim();
                graph.addVertex(src);
                graph.addVertex(dst);
                graph.addEdge(src, dst);
            }
        }
        System.out.println("Graph parsed: " + graph);
    }
}
