package com.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import guru.nidi.graphviz.model.*;
import static guru.nidi.graphviz.model.Factory.*;
import guru.nidi.graphviz.engine.*;

import java.io.File;

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

    public void addNode(String label) {
        if (!graph.containsVertex(label)) {
            graph.addVertex(label);
            System.out.println("Node added: " + label);
        }
    }

    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if (graph.containsVertex(srcLabel) && graph.containsVertex(dstLabel) && !graph.containsEdge(srcLabel, dstLabel)) {
            graph.addEdge(srcLabel, dstLabel);
            System.out.println("Edge added: " + srcLabel + " -> " + dstLabel);
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

    public void outputGraphics(String path) throws IOException {
        MutableGraph g = mutGraph("graph").setDirected(true);
        for (String v : graph.vertexSet()) {
            g.add(mutNode(v));
        }
        for (DefaultEdge e : graph.edgeSet()) {
            g.add(mutNode(graph.getEdgeSource(e)).addLink(mutNode(graph.getEdgeTarget(e))));
        }
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(path));
    }
}
