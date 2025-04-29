package com.graph;

import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class SearchUtils {
    public static Path dfsSearch(String src, String dst) {
        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();

        if (dfsHelper(src, dst, visited, path)) {
            return new Path(path);
        }

        return null;
    }

    public static Path bfsSearch(String src, String dst) {
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(List.of(src));
        visited.add(src);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String last = path.get(path.size() - 1);

            if (last.equals(dst)) {
                return new Path(path);
            }

            for (DefaultEdge edge : GraphHandler.graph.outgoingEdgesOf(last)) {
                String neighbor = GraphHandler.graph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        return null;
    }

    public static boolean dfsHelper(String current, String target, Set<String> visited, List<String> path) {
        visited.add(current);
        path.add(current);

        if (current.equals(target)) {
            return true;
        }

        for (DefaultEdge edge : GraphHandler.graph.outgoingEdgesOf(current)) {
            String neighbor = GraphHandler.graph.getEdgeTarget(edge);
            if (!visited.contains(neighbor)) {
                if (dfsHelper(neighbor, target, visited, path)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}
