package edu.wpi.teamF;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.teamF.modelClasses.EuclideanScorer;
import edu.wpi.teamF.modelClasses.Node;
import org.junit.jupiter.api.Test;

public class AStarTest {

  Node node1 = new Node((short) 0, (short) 0, "node1", Node.NodeType.HALL, 1);
  Node node2 = new Node((short) 0, (short) 5, "node2", Node.NodeType.HALL, 1);

  @Test
  public void testOfEuclideanScorer() {
    EuclideanScorer testScorer = new EuclideanScorer();
    assertEquals(5, testScorer.computeCost(node1, node2), "message");
  }
}
