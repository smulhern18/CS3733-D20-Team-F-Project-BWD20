package edu.wpi.teamF.pathfinding;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {

    private final Set<GraphNode> nodes; //make a set of GraphNode from either scratch or csv

    public Graph(Set<GraphNode> nodes) {
        this.nodes = nodes;

        //creation of example graph
        GraphNode nodeA = new GraphNode(10, 6, "A");
        GraphNode nodeB = new GraphNode(9, 2, "B");
        GraphNode nodeC = new GraphNode(5, 7, "C");
        GraphNode nodeD = new GraphNode(9, 9, "D");
        GraphNode nodeE = new GraphNode(1, 2, "E");
        GraphNode nodeF = new GraphNode(10, 1, "F");
        GraphNode nodeG = new GraphNode(7, 4, "G");
        GraphNode nodeH = new GraphNode(3, 1, "H");
        GraphNode nodeI = new GraphNode(5, 4, "I");
        GraphNode nodeJ = new GraphNode(9, 1, "J");

        nodeA.addNeighbor(nodeD);
        nodeA.addNeighbor(nodeG);
        nodeA.addNeighbor(nodeB);
        nodeA.addNeighbor(nodeF);
        nodeB.addNeighbor(nodeJ);
        nodeB.addNeighbor(nodeG);
        nodeB.addNeighbor(nodeF);
        nodeB.addNeighbor(nodeA);
        nodeC.addNeighbor(nodeD);
        nodeD.addNeighbor(nodeA);
        nodeB.addNeighbor(nodeC);
        nodeE.addNeighbor(nodeH);
        nodeE.addNeighbor(nodeI);
        nodeF.addNeighbor(nodeJ);
        nodeF.addNeighbor(nodeB);
        nodeF.addNeighbor(nodeA);
        nodeG.addNeighbor(nodeI);
        nodeG.addNeighbor(nodeA);
        nodeG.addNeighbor(nodeB);
        nodeH.addNeighbor(nodeE);
        nodeH.addNeighbor(nodeI);
        nodeI.addNeighbor(nodeE);
        nodeI.addNeighbor(nodeH);
        nodeI.addNeighbor(nodeG);
        nodeI.addNeighbor(nodeJ);
        nodeJ.addNeighbor(nodeB);
        nodeJ.addNeighbor(nodeF);
        nodeJ.addNeighbor(nodeI);

        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeD);
        nodes.add(nodeE);
        nodes.add(nodeF);
        nodes.add(nodeG);
        nodes.add(nodeH);
        nodes.add(nodeI);
        nodes.add(nodeJ);

    }

    public Set<GraphNode> getNodes(){
        return nodes;
    }

    public GraphNode getNode(String find){
        for(GraphNode i: this.nodes){
            if(i.getId() == find){
                return i;
            }
        }

    }

}
