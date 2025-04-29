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

    public interface strategySearch {
        Path search(String src, String dst);
    }

    public static class newBFS implements strategySearch {
        @Override
        public Path search(String src, String dst) {
            return bfsSearch(src, dst);
        }
    }

    public static class neDFS implements strategySearch {
        @Override
        public Path search(String src, String dst) {
            return dfsSearch(src, dst);
        }
    }

    public static Path randomWalkSearch(String src, String dst) {
        Random random = new Random();
        List<String> path = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        path.add(src);
        visited.add(src);

        String current = src;
        while (!current.equals(dst)) {
            Set<DefaultEdge> outgoingEdges = GraphHandler.graph.outgoingEdgesOf(current);
            List<String> neighbors = new ArrayList<>();

            for (DefaultEdge edge : outgoingEdges) {
                String neighbor = GraphHandler.graph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    neighbors.add(neighbor);
                }
            }

            if (neighbors.isEmpty()) {
                return null;
            }

            String next = neighbors.get(random.nextInt(neighbors.size()));
            path.add(next);
            visited.add(next);
            current = next;

            System.out.println("visiting Path{nodes=" + path + "}");
        }

        return new Path(path);
    }

    public static class newRandomWalk implements strategySearch {
        @Override
        public Path search(String src, String dst) {
            return randomWalkSearch(src, dst);
        }
    }
}
