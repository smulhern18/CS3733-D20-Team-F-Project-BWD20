package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.SingleFloorAStar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SingleFloorAStarTest {

  static SingleFloorAStar pathfinder;
  static NodeFactory nodeFactory = NodeFactory.getFactory();

  @BeforeAll
  public static void setup() throws Exception {
    pathfinder = new SingleFloorAStar();
  }

  @Test
  public static void testPathfindToLocation() throws Exception {
    Node aNode1 = nodeFactory.read("FDEPT00105");
    Node bNode1 = nodeFactory.read("FHALL00105");
    Node cNode1 = nodeFactory.read("FHALL00205");
    Node dNode1 = nodeFactory.read("FHALL00305");
    Node eNode1 = nodeFactory.read("FHALL00405");
    Node fNode1 = nodeFactory.read("FDEPT00505");
    Path returnPath = pathfinder.pathfind(aNode1, fNode1);
    assertTrue(returnPath.getPath().get(0).equals(aNode1));
    assertTrue(returnPath.getPath().get(1).equals(bNode1));
    assertTrue(returnPath.getPath().get(2).equals(cNode1));
    assertTrue(returnPath.getPath().get(3).equals(dNode1));
    assertTrue(returnPath.getPath().get(4).equals(eNode1));
    assertTrue(returnPath.getPath().get(5).equals(fNode1));
  }

  @Test
  public static void testPathfindBuildingFeature() throws Exception {
    Node aNode1 = nodeFactory.read("FSERV00405");
    Node bNode1 = nodeFactory.read("FHALL01905");
    Node cNode1 = nodeFactory.read("FHALL01305");
    Node dNode1 = nodeFactory.read("FHALL02005");
    Node eNode1 = nodeFactory.read("FSTAI00505");
    Path returnPath = pathfinder.pathfind(aNode1, Node.NodeType.STAI);
    assertTrue(returnPath.getPath().get(0).equals(aNode1));
    assertTrue(returnPath.getPath().get(1).equals(bNode1));
    assertTrue(returnPath.getPath().get(2).equals(cNode1));
    assertTrue(returnPath.getPath().get(3).equals(dNode1));
    assertTrue(returnPath.getPath().get(4).equals(eNode1));
  }
}
