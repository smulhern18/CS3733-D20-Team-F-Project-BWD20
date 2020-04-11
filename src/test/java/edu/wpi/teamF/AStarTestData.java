package edu.wpi.teamF;

import edu.wpi.teamF.modelClasses.Node;

public class AStarTestData {
    // creation of example graph
    Node nodeA = new Node((short) 10, (short) 6, "A", Node.NodeType.HALL, 1);
    Node nodeB = new Node((short) 9, (short) 2, "J", Node.NodeType.HALL, 1);
    Node nodeC = new Node((short) 5, (short) 7, "B", Node.NodeType.HALL, 1);
    Node nodeD = new Node((short) 9, (short) 9, "C", Node.NodeType.HALL, 1);
    Node nodeE = new Node((short) 1, (short) 2, "D", Node.NodeType.HALL, 1);
    Node nodeF = new Node((short) 10, (short) 1, "E", Node.NodeType.HALL, 1);
    Node nodeG = new Node((short) 7, (short) 4, "F", Node.NodeType.HALL, 1);
    Node nodeH = new Node((short) 3, (short) 1, "G", Node.NodeType.HALL, 1);
    Node nodeI = new Node((short) 5, (short) 4, "H", Node.NodeType.HALL, 1);
    Node nodeJ = new Node((short) 9, (short) 1, "I", Node.NodeType.HALL, 1);

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
    nodeD.addNeighbor(nodeC);
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
