package com.graph;

import org.jgrapht.graph.DefaultEdge;

import java.util.*;

public class SearchUtils {

    public static Path templateSearch(String src, String dst, Collection<List<String>> collection, boolean isBFS) {
        Set<String> visited = new HashSet<>();
        List<String> initialPath = new ArrayList<>();
        initialPath.add(src);
        collection.add(initialPath);
        visited.add(src);

        while (!collection.isEmpty()) {
            List<String> path;
            if (isBFS) {
                path = ((LinkedList<List<String>>) collection).poll();
            } else {
                path = ((Stack<List<String>>) collection).pop();
            }

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
                    collection.add(newPath);
                }
            }
        }
        return null;
    }

    public static Path bfsSearch(String src, String dst) {
        return templateSearch(src, dst, new LinkedList<>(), true);
    }

    public static Path dfsSearch(String src, String dst) {
        return templateSearch(src, dst, new Stack<>(), false);
    }
}
