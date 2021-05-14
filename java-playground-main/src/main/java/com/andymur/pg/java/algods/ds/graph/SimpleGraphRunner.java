package com.andymur.pg.java.algods.ds.graph;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleGraphRunner {

    // (1,2,3,4,5,6)
    // [(1,3), (2,3), (3,4), (4,5), (3,5)]

    public static void main(String[] args) {
        final SimpleGraph<Integer> graph = new SimpleGraph<>(1, 2, 3, 4, 5, 6);

        for (Pair<Integer> p : Arrays.asList(new Pair<>(1, 3),
                                             new Pair<>(2, 3),
                                             new Pair<>(3, 4),
                                             new Pair<>(4, 5),
                                             new Pair<>(3, 5)
                )) {
            graph.addEdge(p.first, p.second);
        }

        Set<Integer> visitedBfs = new HashSet<>();

        final List<Pair<Integer>> bfsPath = bfs(visitedBfs, 1, graph);
        System.out.println(bfsPath);
        System.out.println(visitedBfs);

        Set<Integer> visitedDfs = new HashSet<>();

        final List<Pair<Integer>> dfsPath = dfs(visitedDfs, 1, graph);
        System.out.println(dfsPath);
        System.out.println(visitedDfs);
    }

    static <T> List<Pair<T>> bfs(Set<T> visited,
                                 T firstNode,
                                 SimpleGraph<T> graph) {
        Queue<T> q = new ArrayDeque<>(Collections.singleton(firstNode));
        List<Pair<T>> path = new ArrayList<>();

        while(!q.isEmpty()) {
            /* main algo */
            T nextNode = q.poll();
            visited.add(nextNode);
            final Set<T> adjacentNodes = graph.getAdjacentNodes(nextNode);
            for (T adjacentNode: adjacentNodes) {
                if (!visited.contains(adjacentNode)) {
                    q.add(adjacentNode);
                }
            }

            /* path part */
            for (T adjacentNode: adjacentNodes) {
                if (!visited.contains(adjacentNode)) {
                    path.add(new Pair<>(nextNode, adjacentNode));
                }
            }
        }

        return path;
    }

    // (1,2,3,4,5,6)
    // [(1,3), (2,3), (3,4), (4,5), (3,5)]
    static <T> List<Pair<T>> dfs(final Set<T> visited,
                                 final T firstNode,
                                 final SimpleGraph<T> graph) {
        Deque<Pair<T>> stack = new ArrayDeque<>(Collections.singleton(new Pair<>(firstNode, firstNode)));
        List<Pair<T>> path = new ArrayList<>();

        while (!stack.isEmpty()) {
            /* main algo */
            final Pair<T> nextPair = stack.pop();
            final T nextNode = nextPair.second;
            visited.add(nextNode);
            final Set<T> adjacentNodes = graph.getAdjacentNodes(nextNode);
            for (T adjacentNode: adjacentNodes) {
                if (!visited.contains(adjacentNode)) {
                    stack.push(new Pair<T>(nextNode, adjacentNode));
                }
            }

            /* path part */
            if (!nextPair.first.equals(nextPair.second)) {
                path.add(nextPair);
            }
        }
        return path;
    }

    static class SimpleGraph<T> {

        private Set<T> nodes;
        private List<Pair<T>> edges;
        private boolean oriented;

        public SimpleGraph(T... nodes) {
            this(false, nodes);
        }

        public SimpleGraph(boolean oriented,
                           T... nodes) {
            this.nodes = new HashSet<>();
            this.nodes.addAll(Arrays.asList(nodes));
            this.edges = new ArrayList<>();
            this.oriented = false;
        }

        public void addEdge(T start, T end) {
            if (!nodes.contains(start) || !nodes.contains(end)) {
                throw new IllegalArgumentException("No such nodes!");
            }
            if (hasEdge(start, end)) {
                throw new IllegalArgumentException("Edge already exists!");
            }
            this.edges.add(new Pair<>(start, end));
        }

        public boolean hasEdge(T start, T end) {
            if (oriented) {
                return getEdge(start, end).isPresent() || getEdge(end, start).isPresent();
            }
            return getEdge(start, end).isPresent();
        }

        public Set<Pair<T>> getEdgesStartWith(/* not null */ T node) {
            if (oriented) {
                return edges.stream().filter(e -> e.first.equals(node)).collect(Collectors.toSet());
            }
            return edges.stream().filter(e -> e.first.equals(node) || e.second.equals(node)).collect(Collectors.toSet());
        }

        public Set<T> getAdjacentNodes(T node) {
            if (oriented) {
                return edges.stream().filter(e -> e.first.equals(node)).map(e -> e.second).collect(Collectors.toSet());
            }
            return edges.stream().filter(e -> e.first.equals(node) || e.second.equals(node))
                    .map(e -> e.second.equals(node) ? e.first : e.second).collect(Collectors.toSet());
        }

        public Optional<Pair<T>> getEdge(T start, T end) {
            final Pair<T> edge = new Pair<>(start, end);
            return edges.stream().filter(e -> e.equals(edge)).findFirst();
        }

    }

    static class Pair<T> {
        private T first;
        private T second;

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?> pair = (Pair<?>) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;
        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }
    }
}
