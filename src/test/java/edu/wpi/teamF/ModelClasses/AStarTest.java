package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.teamF.Controllers.PathfindController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AStarTest {

  static PathfindController pathfinder;

  @BeforeAll
  public static void setup() {
    pathfinder = new PathfindController();
    AStarTestData.initializeNodeNeighbors();
  }

  @Test
  public void testOfEuclideanScorer() throws ValidationException {

    EuclideanScorer testScorer = new EuclideanScorer();
    Node testNode1 =
        new Node(
            "node1",
            (short) 1,
            (short) 1,
            "building",
            "longName",
            "node1",
            Node.NodeType.HALL,
            (short) 1);
    Node testNode2 =
        new Node(
            "node2",
            (short) 6,
            (short) 1,
            "building",
            "longName",
            "node2",
            Node.NodeType.HALL,
            (short) 1);
    Node testNode3 =
        new Node(
            "node3",
            (short) 4,
            (short) 5,
            "building",
            "longName",
            "node3",
            Node.NodeType.HALL,
            (short) 1);
    assertEquals(5, testScorer.computeCost(testNode1, testNode2), "message");
    assertEquals(5, testScorer.computeCost(testNode1, testNode3), "message");
  }

  @Test
  public void testElevatorScorer2_1() throws ValidationException {
    Node elev1 =
        new Node(
            "elev1",
            (short) 1,
            (short) 1,
            "building",
            "longName",
            "elev1",
            Node.NodeType.ELEV,
            (short) 1);
    Node elev2 =
        new Node(
            "elev2",
            (short) 10,
            (short) 10,
            "building",
            "longName",
            "elev2",
            Node.NodeType.ELEV,
            (short) 1);
    Node elev3 =
        new Node(
            "elev3",
            (short) 3,
            (short) 8,
            "building",
            "longName",
            "elev3",
            Node.NodeType.ELEV,
            (short) 1);
    List<Node> elevators = Arrays.asList(elev1, elev2, elev3);
    Node start =
        new Node(
            "start",
            (short) 2,
            (short) 3,
            "building",
            "longName",
            "start",
            Node.NodeType.HALL,
            (short) 1);
    Node end =
        new Node(
            "end",
            (short) 2,
            (short) 10,
            "building",
            "longName",
            "end",
            Node.NodeType.HALL,
            (short) 1);
    ElevatorScorer2 tester = new ElevatorScorer2(elevators);

    Assertions.assertEquals(
        elev3.getName(), tester.elevatorScorer(start, end).getName(), "Elevator 1 is closest");
  }

  //  @Test
  //  public void testGetPath() {
  //    List<Node> path = pathfinder.getPath(AStarTestData.nodeE, AStarTestData.nodeF);
  //    assertTrue(path.get(0).getName().equals("nodeE"));
  //    assertTrue(path.get(1).getName().equals("nodeI"));
  //    assertTrue(path.get(2).getName().equals("nodeJ"));
  //    assertTrue(path.get(3).getName().equals("nodeF"));
  //  }
}
