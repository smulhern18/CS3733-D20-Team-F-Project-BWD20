package edu.wpi.teamF;

import edu.wpi.teamF.modelClasses.Node;

public class AStarTestData {
  // creation of example graph
  public static Node nodeA = new Node((short) 10, (short) 6, "A", Node.NodeType.HALL, 1);
  public static Node nodeB = new Node((short) 9, (short) 2, "B", Node.NodeType.HALL, 1);
  public static Node nodeC = new Node((short) 5, (short) 7, "C", Node.NodeType.HALL, 1);
  public static Node nodeD = new Node((short) 9, (short) 9, "D", Node.NodeType.HALL, 1);
  public static Node nodeE = new Node((short) 1, (short) 2, "E", Node.NodeType.HALL, 1);
  public static Node nodeF = new Node((short) 10, (short) 1, "F", Node.NodeType.HALL, 1);
  public static Node nodeG = new Node((short) 7, (short) 4, "G", Node.NodeType.HALL, 1);
  public static Node nodeH = new Node((short) 3, (short) 1, "H", Node.NodeType.HALL, 1);
  public static Node nodeI = new Node((short) 5, (short) 4, "I", Node.NodeType.HALL, 1);
  public static Node nodeJ = new Node((short) 9, (short) 1, "J", Node.NodeType.HALL, 1);

  public static void initializeNodeNeighbors() {
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
  }
}
