package edu.wpi.teamF.ModelClasses;

public class AStarTestData {
  // creation of example graph
  public static Node nodeA = null;
  public static Node nodeB = null;
  public static Node nodeC = null;
  public static Node nodeD = null;
  public static Node nodeE = null;
  public static Node nodeF = null;
  public static Node nodeG = null;
  public static Node nodeH = null;
  public static Node nodeI = null;
  public static Node nodeJ = null;

  public static void initializeNodeNeighbors() {
    try {
      nodeA =
          new Node(
              "nodeA",
              (short) 10,
              (short) 6,
              "A",
              "longname1",
              "ln1",
              Node.NodeType.HALL,
              (short) 1);
      nodeB =
          new Node(
              "nodeB",
              (short) 9,
              (short) 2,
              "B",
              "longname2",
              "ln2",
              Node.NodeType.HALL,
              (short) 1);
      nodeC =
          new Node(
              "nodeC",
              (short) 5,
              (short) 7,
              "C",
              "longname3",
              "ln3",
              Node.NodeType.HALL,
              (short) 1);
      nodeD =
          new Node(
              "nodeD",
              (short) 9,
              (short) 9,
              "D",
              "longname4",
              "ln4",
              Node.NodeType.HALL,
              (short) 1);
      nodeE =
          new Node(
              "nodeE",
              (short) 1,
              (short) 2,
              "E",
              "longname5",
              "ln5",
              Node.NodeType.HALL,
              (short) 1);
      nodeF =
          new Node(
              "nodeF",
              (short) 10,
              (short) 1,
              "F",
              "longname6",
              "ln6",
              Node.NodeType.HALL,
              (short) 1);
      nodeG =
          new Node(
              "nodeG",
              (short) 7,
              (short) 4,
              "G",
              "longname7",
              "ln7",
              Node.NodeType.HALL,
              (short) 1);
      nodeH =
          new Node(
              "nodeH",
              (short) 3,
              (short) 1,
              "H",
              "longname8",
              "ln8",
              Node.NodeType.HALL,
              (short) 1);
      nodeI =
          new Node(
              "nodeI",
              (short) 5,
              (short) 4,
              "I",
              "longname9",
              "ln9",
              Node.NodeType.HALL,
              (short) 1);
      nodeJ =
          new Node(
              "nodeJ",
              (short) 9,
              (short) 1,
              "J",
              "longname10",
              "ln10",
              Node.NodeType.HALL,
              (short) 1);
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
    nodeA.addNeighbor(nodeD.getName());
    nodeA.addNeighbor(nodeG.getName());
    nodeA.addNeighbor(nodeB.getName());
    nodeA.addNeighbor(nodeF.getName());
    nodeB.addNeighbor(nodeJ.getName());
    nodeB.addNeighbor(nodeG.getName());
    nodeB.addNeighbor(nodeF.getName());
    nodeB.addNeighbor(nodeA.getName());
    nodeC.addNeighbor(nodeD.getName());
    nodeD.addNeighbor(nodeA.getName());
    nodeD.addNeighbor(nodeC.getName());
    nodeE.addNeighbor(nodeH.getName());
    nodeE.addNeighbor(nodeI.getName());
    nodeF.addNeighbor(nodeJ.getName());
    nodeF.addNeighbor(nodeB.getName());
    nodeF.addNeighbor(nodeA.getName());
    nodeG.addNeighbor(nodeI.getName());
    nodeG.addNeighbor(nodeA.getName());
    nodeG.addNeighbor(nodeB.getName());
    nodeH.addNeighbor(nodeE.getName());
    nodeH.addNeighbor(nodeI.getName());
    nodeI.addNeighbor(nodeE.getName());
    nodeI.addNeighbor(nodeH.getName());
    nodeI.addNeighbor(nodeG.getName());
    nodeI.addNeighbor(nodeJ.getName());
    nodeJ.addNeighbor(nodeB.getName());
    nodeJ.addNeighbor(nodeF.getName());
    nodeJ.addNeighbor(nodeI.getName());
  }
}
