package edu.wpi.teamF.Test;

import edu.wpi.teamF.modelClasses.Node;
import edu.wpi.teamF.modelClasses.ValidationException;
import java.util.Arrays;
import java.util.HashSet;

public class TestData {

  private final String[] validNodeNames = {
    "NodeA", "NodeB", "NodeC", "NodeD", "NodeE", "NodeF", "NodeG", "NodeH", "NodeI", "NodeJ",
    "NodeK", "NodeL", "NodeM", "NodeN", "NodeO", "NodeP"
  };

  private final String[] invalidNodeNames = {
    null,
    "",
    "Have you ever heard of the tragedy of Darth Plagueis the wise? I thought not. It's not a story the jedi would tell you. Its an old sith legend"
  };

  private final Short[] validXCoordinates = {
    1, 100, 200, 300, 400, 500, 600, 700, 800, 625, 837, 1600, 4800, 32000, 325, 700
  };

  private final Short[] invalidCoordinates = {null, -1, -32768};

  private final Short[] validYCoordinates = {
    1, 100, 200, 300, 400, 500, 600, 700, 800, 625, 837, 1600, 4800, 32000, 325, 700
  };

  private final String[] validBuildings = {"A", "B", "C", "D", "E"};

  private final String[] invalidBuildings = {
    null,
    "",
    "Have you ever heard of the tragedy of Darth Plagueis the wise? I thought not. It's not a story the jedi would tell you. Its an old sith legend"
  };

  private final String[] validLongNames = {"Robert", "Franz-Ferdinand", "George", "Wilhelm"};

  private final String[] invalidLongNames = {
    null,
    "",
    "Have you ever heard of the tragedy of Darth Plagueis the wise? I thought not. It's not a story the jedi would tell you. Its an old sith legend"
  };

  private final String[] validShortNames = {"Bob", "Fern", "Greg", "Fred"};

  private final String[] invalidShortNames = {
    null,
    "",
    "Have you ever heard of the tragedy of Darth Plagueis the wise? I thought not. It's not a story the jedi would tell you. Its an old sith legend"
  };

  private final Short[] validFloors = {1, 2, 3, 4};

  private final Short[] invalidFloors = {null, -1, -32768};

  private final Node.NodeType[] validNodeTypes = {
    Node.NodeType.ELEV,
    Node.NodeType.CONF,
    Node.NodeType.DEPT,
    Node.NodeType.EXIT,
    Node.NodeType.HALL,
    Node.NodeType.INFO,
    Node.NodeType.LABS,
    Node.NodeType.REST,
    Node.NodeType.RETL,
    Node.NodeType.SERV,
    Node.NodeType.STAI
  };

  private Node.NodeType[] invalidNodeTypes = {null};

  private HashSet<String> validNeighbors1 =
      new HashSet<>(
          Arrays.asList(
              validNodeNames[4], validNodeNames[5], validNodeNames[6], validNodeNames[7]));
  private HashSet<String> validNeighbors2 =
      new HashSet<>(
          Arrays.asList(
              validNodeNames[8], validNodeNames[9], validNodeNames[10], validNodeNames[11]));
  private HashSet<String> validNeighbors3 =
      new HashSet<>(
          Arrays.asList(
              validNodeNames[12], validNodeNames[13], validNodeNames[14], validNodeNames[15]));
  private HashSet<String> validNeighbors4 =
      new HashSet<>(
          Arrays.asList(
              validNodeNames[0], validNodeNames[1], validNodeNames[2], validNodeNames[3]));

  private final HashSet<?>[] validNeighborsGeneric = {
    validNeighbors1, validNeighbors2, validNeighbors3, validNeighbors4
  };

  private final HashSet<String>[] validNeighbors = (HashSet<String>[]) validNeighborsGeneric;

  private final Node[] validNodes = {
    new Node(
        validNodeNames[0],
        validXCoordinates[0],
        validYCoordinates[0],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[0],
        validFloors[0],
        validNeighbors1),
    new Node(
        validNodeNames[1],
        validXCoordinates[1],
        validYCoordinates[1],
        validBuildings[1],
        validLongNames[1],
        validShortNames[1],
        validNodeTypes[1],
        validFloors[1],
        validNeighbors1),
    new Node(
        validNodeNames[2],
        validXCoordinates[2],
        validYCoordinates[2],
        validBuildings[2],
        validLongNames[2],
        validShortNames[2],
        validNodeTypes[2],
        validFloors[2],
        validNeighbors1),
    new Node(
        validNodeNames[3],
        validXCoordinates[3],
        validYCoordinates[3],
        validBuildings[3],
        validLongNames[3],
        validShortNames[3],
        validNodeTypes[3],
        validFloors[3],
        validNeighbors1),
    new Node(
        validNodeNames[4],
        validXCoordinates[4],
        validYCoordinates[4],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[4],
        validFloors[0],
        validNeighbors2),
    new Node(
        validNodeNames[5],
        validXCoordinates[5],
        validYCoordinates[5],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[5],
        validFloors[0],
        validNeighbors2),
    new Node(
        validNodeNames[6],
        validXCoordinates[6],
        validYCoordinates[6],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[6],
        validFloors[0],
        validNeighbors2),
    new Node(
        validNodeNames[7],
        validXCoordinates[7],
        validYCoordinates[7],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[7],
        validFloors[0],
        validNeighbors2),
    new Node(
        validNodeNames[8],
        validXCoordinates[8],
        validYCoordinates[8],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[8],
        validFloors[0],
        validNeighbors3),
    new Node(
        validNodeNames[9],
        validXCoordinates[9],
        validYCoordinates[9],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[9],
        validFloors[0],
        validNeighbors3),
    new Node(
        validNodeNames[10],
        validXCoordinates[10],
        validYCoordinates[10],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[10],
        validFloors[0],
        validNeighbors3),
    new Node(
        validNodeNames[11],
        validXCoordinates[11],
        validYCoordinates[11],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[4],
        validFloors[0],
        validNeighbors3),
    new Node(
        validNodeNames[12],
        validXCoordinates[12],
        validYCoordinates[12],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[0],
        validFloors[0],
        validNeighbors4),
    new Node(
        validNodeNames[13],
        validXCoordinates[13],
        validYCoordinates[13],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[0],
        validFloors[0],
        validNeighbors4),
    new Node(
        validNodeNames[14],
        validXCoordinates[14],
        validYCoordinates[14],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[2],
        validFloors[0],
        validNeighbors4),
    new Node(
        validNodeNames[15],
        validXCoordinates[15],
        validYCoordinates[15],
        validBuildings[0],
        validLongNames[0],
        validShortNames[0],
        validNodeTypes[1],
        validFloors[0],
        validNeighbors4)
  };

  public Node[] getValidNodes() {
    return validNodes;
  }

  public TestData() throws ValidationException {}
}
