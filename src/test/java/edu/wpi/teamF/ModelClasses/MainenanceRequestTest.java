package edu.wpi.teamF.ModelClasses;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import javax.management.InstanceNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class MaintenanceRequestTest {

  static TestData testData = null;
  static Node[] validNodes = null;
  MaintenanceRequestFactory maintenanceRequestFactory = MaintenanceRequestFactory.getFactory();
  NodeFactory nodeFactory = NodeFactory.getFactory();
  static DatabaseManager databaseManager = new DatabaseManager();

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validNodes = testData.validNodes;
    databaseManager.initialize();
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() {
    try {
      nodeFactory.create(null);
      fail("Creating a null value is unacceptable");
    } catch (ValidationException e) {
      // ignore as expected
    }
    try {
      for (Node node : validNodes) {
        nodeFactory.create(node);

        Node readNode = nodeFactory.read(node.getId());
        assertTrue(readNode.equals(node));

        nodeFactory.delete(node.getId());

        try {
          readNode = nodeFactory.read(node.getId());
        } catch (InstanceNotFoundException e) {
          // ignore
        } catch (Exception e) {
          fail(e.getMessage() + ", " + e.getClass());
        }
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testCreateReadUpdateDelete() {
    try {
      for (Node node : validNodes) {
        nodeFactory.create(node);

        node.setBuilding("Hello");
        nodeFactory.update(node);

        Node readNode = nodeFactory.read(node.getId());

        assertTrue(node.equals(readNode));

        nodeFactory.delete(node.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetNodesByType() {
    Node node1 = validNodes[0];
    Node node2 = validNodes[1];
    Node node3 = validNodes[2];
    Node node4 = validNodes[3];

    node1.setType(Node.NodeType.ELEV);
    node2.setType(Node.NodeType.ELEV);
    node3.setType(Node.NodeType.CONF);
    node4.setType(Node.NodeType.DEPT);

    NodeFactory nodeFactory = NodeFactory.getFactory();

    try {
      nodeFactory.create(node1);
      nodeFactory.create(node2);
      nodeFactory.create(node3);
      nodeFactory.create(node4);

      List<Node> elevatorNodes = nodeFactory.getNodesByType(Node.NodeType.ELEV);

      assertTrue(elevatorNodes.contains(node1));
      assertTrue(elevatorNodes.contains(node2));
      assertTrue(elevatorNodes.size() == 2);

      List<Node> conferenceNodes = nodeFactory.getNodesByType(Node.NodeType.CONF);
      assertTrue(conferenceNodes.contains(node3));
      assertTrue(conferenceNodes.size() == 1);

      nodeFactory.delete(node1.getId());
      nodeFactory.delete(node2.getId());
      nodeFactory.delete(node3.getId());
      nodeFactory.delete(node4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllNodes() {
    Node node1 = validNodes[0];
    Node node2 = validNodes[1];
    Node node3 = validNodes[2];
    Node node4 = validNodes[3];

    node1.setType(Node.NodeType.ELEV);
    node2.setType(Node.NodeType.ELEV);
    node3.setType(Node.NodeType.CONF);
    node4.setType(Node.NodeType.DEPT);

    NodeFactory nodeFactory = NodeFactory.getFactory();

    try {
      nodeFactory.create(node1);
      nodeFactory.create(node2);
      nodeFactory.create(node3);
      nodeFactory.create(node4);

      List<Node> elevatorNodes = nodeFactory.getAllNodes();

      assertTrue(elevatorNodes.contains(node1));
      assertTrue(elevatorNodes.contains(node2));
      assertTrue(elevatorNodes.contains(node3));
      assertTrue(elevatorNodes.contains(node4));
      assertTrue(elevatorNodes.size() == 4);

      nodeFactory.delete(node1.getId());
      nodeFactory.delete(node2.getId());
      nodeFactory.delete(node3.getId());
      nodeFactory.delete(node4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

}
