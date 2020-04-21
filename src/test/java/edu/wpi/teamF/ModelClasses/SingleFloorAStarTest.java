package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.Main;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.SingleFloorAStar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SingleFloorAStarTest {

  static SingleFloorAStar pathfinder;
  static NodeFactory nodeFactory = NodeFactory.getFactory();
  static EdgeFactory edgeFactory = EdgeFactory.getFactory();
  static DatabaseManager dbm = new DatabaseManager();
  static CSVManipulator csvm = new CSVManipulator();
  static List<Node> nodeList;
  static Map<String, Node> nodeMap = new HashMap<>();

  @BeforeAll
  public static void setup() throws Exception {

    dbm.initialize();
    csvm.readCSVFileNode(Main.class.getResourceAsStream("CSVFiles/MapFAllnodes.csv"));
    csvm.readCSVFileEdge(Main.class.getResourceAsStream("CSVFiles/MapFAlledges.csv"));

    nodeList = new ArrayList<>();

    for (Node node : nodeFactory.getAllNodes()) {
      if (node.getId().charAt(node.getId().length() - 1) == '5') {
        nodeList.add(node);
        node.setEdges(edgeFactory.getAllEdgesConnectedToNode(node.getId()));
        // System.out.println(node.getId() + " - " + node.getNeighborNodes());
      }
    }
    pathfinder = new SingleFloorAStar(nodeList);
    for (Node node : nodeList) {
      nodeMap.put(node.getId(), node);
    }
  }

  @Test
  public void testPathfindToLocation() throws Exception {
    Node aNode1 = nodeMap.get("FDEPT00105");
    Node bNode1 = nodeMap.get("FHALL00105");
    Node cNode1 = nodeMap.get("FHALL00205");
    Node dNode1 = nodeMap.get("FHALL00305");
    Node eNode1 = nodeMap.get("FDEPT00405");
    Node xNode1 = nodeMap.get("FHALL00405");
    Node fNode1 = nodeMap.get("FDEPT00505");
    Path returnPath = pathfinder.pathfind(aNode1, fNode1);
    assertTrue(returnPath.getPath().get(0).getId().equals(aNode1.getId()));
    assertTrue(returnPath.getPath().get(1).getId().equals(bNode1.getId()));
    assertTrue(returnPath.getPath().get(2).getId().equals(cNode1.getId()));
    assertTrue(returnPath.getPath().get(3).getId().equals(dNode1.getId()));
    assertTrue(returnPath.getPath().get(4).getId().equals(eNode1.getId()));
    assertTrue(returnPath.getPath().get(5).getId().equals(xNode1.getId()));
    assertTrue(returnPath.getPath().get(6).getId().equals(fNode1.getId()));
  }

  @Test
  public void testPathfindBuildingFeature() throws Exception {
    Node aNode1 = nodeMap.get("FSERV00405");
    Node bNode1 = nodeMap.get("FHALL01905");
    Node cNode1 = nodeMap.get("FHALL01305");
    Node dNode1 = nodeMap.get("FHALL02005");
    Node eNode1 = nodeMap.get("FSTAI00505");
    Path returnPath = pathfinder.pathfind(aNode1, Node.NodeType.STAI);
    assertTrue(returnPath.getPath().get(0).getId().equals(aNode1.getId()));
    assertTrue(returnPath.getPath().get(1).getId().equals(bNode1.getId()));
    assertTrue(returnPath.getPath().get(2).getId().equals(cNode1.getId()));
    assertTrue(returnPath.getPath().get(3).getId().equals(dNode1.getId()));
    assertTrue(returnPath.getPath().get(4).getId().equals(eNode1.getId()));
  }
}
