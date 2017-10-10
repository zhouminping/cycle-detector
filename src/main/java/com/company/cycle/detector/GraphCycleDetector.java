package com.company.cycle.detector;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GraphCycleDetector<T> {

    private final Set<Graph<T>> cycles = new HashSet<>();
    private final Stack<T> stack = new Stack<>();
    private final Set<T> visited = new HashSet<>();

    private GraphCycleDetector() {
    }

    public static <T> Set<Graph<T>> findCycles(Graph<T> graph) {
        GraphCycleDetector<T> graphCycleDetector = new GraphCycleDetector<>();
        return graphCycleDetector.find(graph);
    }

    private Set<Graph<T>> find(Graph<T> graph) {
        Set<T> nodes = graph.nodes();
        for (T node : nodes) {
            if (!visited.contains(node)) {
                dfs(graph, node);
            }
        }
        return cycles;
    }

    private void dfs(Graph<T> graph, T node) {
        if (stack.contains(node)) {
            cycles.add(getCycleSubGraph(node));
        } else {
            stack.push(node);
            Set<T> children = graph.successors(node);
            children.forEach(child -> {
                dfs(graph, child);
            });
            stack.pop();
            visited.add(node);
        }
    }

    private Graph<T> getCycleSubGraph(T node) {
        MutableGraph<T> subgraph = GraphBuilder.directed().build();
        int index = stack.size() - 1;
        T next = stack.get(index);
        subgraph.putEdge(next, node);
        index--;
        while (!next.equals(node)) {
            T prev = stack.get(index);
            subgraph.putEdge(prev, next);
            next = prev;
            index--;
        }
        return subgraph;
    }
}