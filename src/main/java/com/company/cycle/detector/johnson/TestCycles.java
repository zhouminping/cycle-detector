package com.company.cycle.detector.johnson;


import java.util.List;


/**
 * Testfile for elementary cycle search.
 *
 * @author Frank Meyer
 */
public class TestCycles {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String nodes[] = new String[10];
        boolean adjMatrix[][] = new boolean[5][5];

        for (int i = 0; i < 5; i++) {
            nodes[i] = "Node " + i;
        }

		/*adjMatrix[0][1] = true;
        adjMatrix[1][2] = true;
		adjMatrix[2][0] = true;
		adjMatrix[2][4] = true;
		adjMatrix[1][3] = true;
		adjMatrix[3][6] = true;
		adjMatrix[6][5] = true;
		adjMatrix[5][3] = true;
		adjMatrix[6][7] = true;
		adjMatrix[7][8] = true;
		adjMatrix[7][9] = true;
		adjMatrix[9][6] = true;*/

        adjMatrix[0][1] = true;
        adjMatrix[1][0] = true;
        adjMatrix[1][2] = true;
        adjMatrix[2][3] = true;
        adjMatrix[3][4] = true;
        adjMatrix[4][2] = true;

        ElementaryCyclesSearch<String> ecs = new ElementaryCyclesSearch<>(adjMatrix, nodes);
        List cycles = ecs.getElementaryCycles();
        for (int i = 0; i < cycles.size(); i++) {
            List cycle = (List) cycles.get(i);
            for (int j = 0; j < cycle.size(); j++) {
                String node = (String) cycle.get(j);
                System.out.print(node + " -> ");
            }
            System.out.println(cycle.get(0));
        }
    }

}
