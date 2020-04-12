package edu.wpi.teamF;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.teamF.controllers.PathfindController;
import edu.wpi.teamF.factories.NodeFactory;
import edu.wpi.teamF.modelClasses.ElevatorScorer2;
import edu.wpi.teamF.modelClasses.EuclideanScorer;
import edu.wpi.teamF.modelClasses.Node;
import edu.wpi.teamF.modelClasses.ValidationException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AStarTest {

  static NodeFactory nodeFactory;
  static PathfindController pathfinder;

  @BeforeAll
  public static void setup() {
    nodeFactory = new NodeFactory();
    pathfinder = new PathfindController(nodeFactory);
    AStarTestData.initializeNodeNeighbors();
  }

  @Test
  public void testOfEuclideanScorer() throws ValidationException {

    EuclideanScorer testScorer = new EuclideanScorer();
    Node testNode1 =
        new Node((short) 0, (short) 0, null, null, null, "node1", Node.NodeType.HALL, (short) 1);
    Node testNode2 =
        new Node((short) 0, (short) 5, null, null, null, "node2", Node.NodeType.HALL, (short) 1);
    Node testNode3 =
        new Node((short) 4, (short) 3, null, null, null, "node3", Node.NodeType.HALL, (short) 1);
    assertEquals(5, testScorer.computeCost(testNode1, testNode2), "message");
    assertEquals(5, testScorer.computeCost(testNode1, testNode3), "message");
  }

  @Test
  public void testElevatorScorer2_1() throws ValidationException {
    Node elev1 =
        new Node((short) 1, (short) 1, null, null, null, "elev1", Node.NodeType.ELEV, (short) 1);
    Node elev2 =
        new Node((short) 10, (short) 10, null, null, null, "elev2", Node.NodeType.ELEV, (short) 1);
    Node elev3 =
        new Node((short) 8, (short) 3, null, null, null, "elev3", Node.NodeType.ELEV, (short) 1);
    List<Node> elevators = Arrays.asList(elev1, elev2, elev3);
    Node start =
        new Node((short) 3, (short) 2, null, null, null, "start", Node.NodeType.HALL, (short) 1);
    Node end =
        new Node((short) 10, (short) 2, null, null, null, "end", Node.NodeType.HALL, (short) 1);
    ElevatorScorer2 tester = new ElevatorScorer2(elevators);

    Assertions.assertEquals(
        elev3.getName(), tester.elevatorScorer(start, end).getName(), "Elevator 1 is closest");
  }

  @Test
  public void testGetPath() {
    List<Node> path = pathfinder.getPath(AStarTestData.nodeE, AStarTestData.nodeF);
    assertTrue(path.get(0).getName().equals("E"));
    assertTrue(path.get(1).getName().equals("I"));
    assertTrue(path.get(2).getName().equals("J"));
    assertTrue(path.get(3).getName().equals("F"));
  }
}
