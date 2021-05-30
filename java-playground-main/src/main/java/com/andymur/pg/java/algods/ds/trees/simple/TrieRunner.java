package com.andymur.pg.java.algods.ds.trees.simple;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class TrieRunner {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.add("awe");
        trie.add("awful");
        trie.add("awkward");
        System.out.println(trie);
    }

    // awe, awful, a, awkward
    // ROOT -> (a) -> (w) -> (e -> (), f -> (u -> (l)) , k -> (w -> (a -> (r -> (d)))))
    static class Trie {
        Node root = new Node();

        public void add(String sequence) {
            Node node = root;
            for (Character symbol: sequence.toCharArray()) {
                Node newNode = node.getChildWithValue(symbol);
                if (newNode != null) {
                    node = newNode;
                } else {
                    newNode = new Node(symbol);
                    node.addChild(newNode);
                    node = newNode;
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Deque<Node> nodes = new ArrayDeque<>(root.getChildren());

            while (!nodes.isEmpty()) {
                final Node nextNode = nodes.pop();
                sb.append(nextNode.getValue()).append(", ");
                for (Node childNode: nextNode.getChildren()) {
                    nodes.push(childNode);
                }
            }
            return sb.toString();
        }

        static class Node {
            Character value = null;
            Set<Node> children = new HashSet<>();

            public Node() {
            }

            public Set<Node> getChildren() {
                return children;
            }

            public Node(Character value) {
                this.value = value;
            }

            public Character getValue() {
                return value;
            }

            private Node getChildWithValue(Character value) {
                return children.stream().filter(n -> n.getValue().equals(value)).findFirst().orElse(null);
            }

            private void addChild(Node node) {
                this.children.add(node);
            }
        }
    }
}
