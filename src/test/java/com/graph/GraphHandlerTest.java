package com.graph;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GraphHandlerTest {

    @Test
    void testParseGraph() throws IOException {
        GraphHandler gh = new GraphHandler();
        gh.parseGraph("test.dot");

        assertEquals(3, gh.getGraph().vertexSet().size(), "Incorrect number of nodes parsed.");
        assertEquals(3, gh.getGraph().edgeSet().size(), "Incorrect number of edges parsed.");
    }

    @Test
    void testAddNode() {
        GraphHandler gh = new GraphHandler();
        gh.addNode("X");
        gh.addNode("Y");

        assertTrue(gh.getGraph().containsVertex("X"), "Node X should exist.");
        assertTrue(gh.getGraph().containsVertex("Y"), "Node Y should exist.");

        int initialSize = gh.getGraph().vertexSet().size();
        gh.addNode("X"); // Duplicate
        assertEquals(initialSize, gh.getGraph().vertexSet().size(), "Duplicate nodes should not be added.");
    }

    @Test
    void testAddEdge() {
        GraphHandler gh = new GraphHandler();
        gh.addNode("A");
        gh.addNode("B");
        gh.addEdge("A", "B");

        assertTrue(gh.getGraph().containsEdge("A", "B"), "Edge A -> B should exist.");

        int initialSize = gh.getGraph().edgeSet().size();
        gh.addEdge("A", "B"); // Duplicate
        assertEquals(initialSize, gh.getGraph().edgeSet().size(), "Duplicate edges should not be added.");
    }

    @Test
    void testOutputDOTGraph() throws IOException {
        GraphHandler gh = new GraphHandler();
        gh.addNode("A");
        gh.addNode("B");
        gh.addNode("C"); // Add missing node
        gh.addEdge("A", "B");
        gh.addEdge("B", "C"); // Add missing edge
        gh.addEdge("C", "A"); // Add missing edge
        gh.outputDOTGraph("output.dot");

        String expected = Files.readString(Paths.get("expected.txt")).trim();
        String actual = Files.readString(Paths.get("output.dot")).trim();

        assertEquals(expected, actual, "DOT file output does not match expected format.");
    }

    @Test
    void testOutputGraphics() throws IOException {
        GraphHandler gh = new GraphHandler();
        gh.addNode("A");
        gh.addNode("B");
        gh.addNode("C");
        gh.addEdge("A", "B");
        gh.addEdge("B", "C");
        gh.addEdge("C", "A");
        gh.outputGraphics("graph.png", "png");

        assertTrue(Files.exists(Paths.get("graph.png")), "PNG file was not created.");
    }

    @Test
    void testRemoveNodeAndEdge() {
        GraphHandler gh = new GraphHandler();
        gh.addNode("A");
        gh.addNode("B");
        gh.addEdge("A", "B");

        gh.removeEdge("A", "B");
        assertFalse(gh.getGraph().containsEdge("A", "B"), "Edge A -> B should have been removed.");

        gh.removeNode("A");
        assertFalse(gh.getGraph().containsVertex("A"), "Node A should have been removed.");
    }

    @Test
    void testRemoveNonExistentNodeThrowsException() {
        GraphHandler gh = new GraphHandler();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gh.removeNode("Z");
        });
        assertEquals("Node 'Z' does not exist.", exception.getMessage());
    }

    @Test
    void testRemoveNonExistentEdgeThrowsException() {
        GraphHandler gh = new GraphHandler();
        gh.addNode("A");
        gh.addNode("B");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gh.removeEdge("A", "B");
        });
        assertEquals("Edge 'A -> B' does not exist.", exception.getMessage());
    }
}