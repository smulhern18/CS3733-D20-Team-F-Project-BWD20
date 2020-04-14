package edu.wpi.teamF.modelClasses;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamF.factories.DatabaseManager;
import edu.wpi.teamF.factories.NodeFactory;
import edu.wpi.teamF.test.TestData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeFactoryTest {

  static DatabaseManager databaseManager = new DatabaseManager();
  NodeFactory nodeFactory = NodeFactory.getFactory();

  static TestData testData = null;

  Node[] validNodes = null;

  @BeforeEach
  public void cleanTests() {
    try {
      testData = new TestData();
      validNodes = testData.getValidNodes();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @BeforeAll
  public static void initializeDatabase() {
    try {
      databaseManager.initialize();
      databaseManager.reset();
      testData = new TestData();
    } catch (SQLException e) {
      // Ignore
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testCreateAndRead() {
    try {
      nodeFactory.create(null);
    } catch (ValidationException e) {
      // Ignore, Expected
    } catch (Exception e) {
      fail(e.getMessage());
    }

    for (Node node : validNodes) {
      try {

        nodeFactory.create(node);

        Node readNode = nodeFactory.read(node.getName());

        Assertions.assertTrue(node.equals(readNode));

        nodeFactory.delete(node);

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }
  }

  @Test
  public void testCreateReadUpdateDelete() {
    try {
      nodeFactory.create(null);
    } catch (ValidationException e) {
      // Ignore, Expected
    } catch (Exception e) {
      fail(e.getMessage());
    }

    for (Node node : validNodes) {
      try {

        nodeFactory.create(node);

        Node readNode = nodeFactory.read(node.getName());

        Assertions.assertTrue(node.equals(readNode));

        node.setLongName("The Clinic");

        nodeFactory.update(node);

        readNode = nodeFactory.read(node.getName());

        Assertions.assertTrue(node.equals(readNode));

        nodeFactory.delete(node);

      } catch (Exception e) {
        fail(e.getCause() + e.getMessage());
      }
    }
  }

  @Test
  public void testGetNodesByType() {
    List<Node> validElevatorNodes = new ArrayList<>();
    try {
      for (Node node : validNodes) {
        nodeFactory.create(node);
        if (node.getType() == Node.NodeType.ELEV) {
          validElevatorNodes.add(node);
        }
      }

      List<Node> elevatorNodes = nodeFactory.getNodesByType(Node.NodeType.ELEV);

      assertTrue(elevatorNodes.size() == 3);
      assertTrue(elevatorNodes.containsAll(validElevatorNodes));

      for (Node node : validElevatorNodes) {
        nodeFactory.delete(node);
      }

      elevatorNodes = nodeFactory.getNodesByType(Node.NodeType.ELEV);

      assertNull(elevatorNodes);

      databaseManager.reset();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
