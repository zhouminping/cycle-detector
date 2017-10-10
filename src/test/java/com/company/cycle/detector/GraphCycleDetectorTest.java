package com.company.cycle.detector;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;


public class GraphCycleDetectorTest {

    private final int u1 = 1;
    private final int u2 = 2;
    private final int u3 = 3;
    private final int u4 = 4;
    private final int u5 = 5;
    private final MutableGraph<Integer> graph = GraphBuilder.directed().build();

    @BeforeMethod
    public void setUp() throws Exception {
        graph.putEdge(u1, u2);
        graph.putEdge(u2, u1);
        graph.putEdge(u2, u3);
        graph.putEdge(u3, u4);
        graph.putEdge(u4, u5);
        graph.putEdge(u5, u1);
    }

    @Test
    public void shouldReturnTrueIfHasCycle() throws Exception {
        assertThat(Graphs.hasCycle(GraphBuilder.directed().build())).isFalse();
        assertThat(Graphs.hasCycle(graph)).isTrue();
    }

    @DataProvider
    public Object[][] cycleSubgraphs() {
        MutableGraph<Integer> cycleSubgraph1 = GraphBuilder.directed().build();
        cycleSubgraph1.putEdge(u1, u2);
        cycleSubgraph1.putEdge(u2, u1);

        MutableGraph<Integer> cycleSubgraph2 = GraphBuilder.directed().build();
        cycleSubgraph2.putEdge(u1, u2);
        cycleSubgraph2.putEdge(u2, u3);
        cycleSubgraph2.putEdge(u3, u4);
        cycleSubgraph2.putEdge(u4, u5);
        cycleSubgraph2.putEdge(u5, u1);

        return new Object[][]{{graph, ImmutableSet.of(cycleSubgraph1, cycleSubgraph2)}};
    }

    @Test(dataProvider = "cycleSubgraphs")
    public void shouldReturnCycleProjects(Graph<Integer> graph, Set<Graph<Integer>> cycleSubgraphs) throws Exception {
        assertThat(GraphCycleDetector.findCycles(graph)).hasSize(cycleSubgraphs.size())
                .containsOnlyElementsOf(cycleSubgraphs);
    }
}