package com.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import guru.nidi.graphviz.model.*;
import static guru.nidi.graphviz.model.Factory.*;
import guru.nidi.graphviz.engine.*;
import java.io.File;
import java.util.Map;

public class GraphHandler {
    private DirectedPseudograph<String, DefaultEdge> graph;

    public GraphHandler() {
        this.graph = new DirectedPseudograph<>(DefaultEdge.class);
    }

    public DirectedPseudograph<String, DefaultEdge> getGraph() {
        return graph;
    }


    public void parseGraph(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("digraph") || line.equals("{") || line.equals("}")) {
                continue;
            }
            if (line.contains("->")) {
                String[] parts = line.replace(";", "").split("->");
                if (parts.length == 2) {
                    String src = parts[0].trim();
                    String dst = parts[1].trim();
                    graph.addVertex(src);
                    graph.addVertex(dst);
                    graph.addEdge(src, dst);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph has ").append(graph.vertexSet().size()).append(" nodes:\n");
        for (String node : graph.vertexSet()) {
            sb.append(node).append(" ");
        }
        sb.append("\nGraph has ").append(graph.edgeSet().size()).append(" edges:\n");
        for (DefaultEdge edge : graph.edgeSet()) {
            sb.append(graph.getEdgeSource(edge)).append(" -> ").append(graph.getEdgeTarget(edge)).append("\n");
        }
        return sb.toString();
    }

    public void outputGraph(String filepath) throws IOException {
        Files.write(Paths.get(filepath), toString().getBytes());
    }

    public void addNode(String label) {
        if (!graph.containsVertex(label)) {
            graph.addVertex(label);
            System.out.println("Node added: " + label);
        }
        else{
            System.out.println("Node '" + label + "' already exists.");
        }
    }

    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if (graph.containsVertex(srcLabel) && graph.containsVertex(dstLabel)) {
            if (!graph.containsEdge(srcLabel, dstLabel)) {
                graph.addEdge(srcLabel, dstLabel);
                System.out.println("Edge added: " + srcLabel + " -> " + dstLabel);
            } else {
                System.out.println("Edge '" + srcLabel + " -> " + dstLabel + "' already exists.");
            }
        } else {
            System.out.println("Error: One or both nodes do not exist: " + srcLabel + ", " + dstLabel);
        }
    }

    public void outputDOTGraph(String path) throws IOException {
        StringBuilder sb = new StringBuilder("digraph G {\n");
        for (String v : graph.vertexSet()) {
            sb.append("\t").append(v).append(";\n");
        }
        for (DefaultEdge e : graph.edgeSet()) {
            sb.append("\t").append(graph.getEdgeSource(e)).append(" -> ").append(graph.getEdgeTarget(e)).append(";\n");
        }
        sb.append("}");
        Files.write(Paths.get(path), sb.toString().getBytes());
    }

    public void outputGraphics(String path, String format) throws IOException {
        MutableGraph g = mutGraph("graph").setDirected(true);
        Map<String, MutableNode> nodes = new HashMap<>();
        for (String v : graph.vertexSet()) {
            nodes.put(v, mutNode(v));
        }
        for (DefaultEdge e : graph.edgeSet()) {
            String src = graph.getEdgeSource(e);
            String dst = graph.getEdgeTarget(e);
            nodes.get(src).addLink(nodes.get(dst));
        }
        for (MutableNode node : nodes.values()) {
            g.add(node);
        }

        Format graphFormat = format.equalsIgnoreCase("svg") ? Format.SVG : Format.PNG;
        Graphviz.fromGraph(g).render(graphFormat).toFile(new File(path));
    }

    public void removeNode(String label) {
        if (!graph.containsVertex(label)) {
            throw new IllegalArgumentException("Node '" + label + "' does not exist.");
        }
        graph.removeVertex(label);
        System.out.println("Node removed: " + label);
    }

    public void removeNodes(String[] labels) {
        for (String label : labels) {
            try {
                removeNode(label);
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping non-existent node: " + label);
            }
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        if (!graph.containsVertex(srcLabel) || !graph.containsVertex(dstLabel)) {
            throw new IllegalArgumentException("One or both nodes do not exist.");
        }
        if (!graph.containsEdge(srcLabel, dstLabel)) {
            throw new IllegalArgumentException("Edge '" + srcLabel + " -> " + dstLabel + "' does not exist.");
        }
        graph.removeEdge(srcLabel, dstLabel);
        System.out.println("Edge removed: " + srcLabel + " -> " + dstLabel);
    }

    public static void main(String[] args) {
        try {
            GraphHandler gh = new GraphHandler();

            // TEST 1: Parsing a DOT File
            System.out.println("Parsing graph from test.dot...");
            gh.parseGraph("test.dot");
            System.out.println(gh);

            // TEST 2: Adding Nodes
            System.out.println("\nAdding nodes...");
            gh.addNode("X");
            gh.addNode("Y");
            gh.addNode("X"); // Duplicate test
            System.out.println(gh);

            // TEST 3: Adding Edges
            System.out.println("\nAdding edges...");
            gh.addEdge("X", "Y");
            gh.addEdge("Y", "X");
            gh.addEdge("X", "Y"); // Duplicate test
            System.out.println(gh);

            // TEST 4: Outputting to DOT File
            System.out.println("\nGenerating output.dot...");
            gh.outputDOTGraph("output.dot");

            // TEST 5: Generating PNG Graph
            System.out.println("\nGenerating graph.png...");
            gh.outputGraphics("graph.png", "png");

            System.out.println("\nAll tests completed. Check output.dot and graph.png!");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
