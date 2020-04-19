package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import javax.management.InstanceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EdgeFactoryTest {

  static TestData testData = null;
  static Node[] validNodes = null;
  static Edge[] validEdges = null;
  static NodeFactory nodeFactory = NodeFactory.getFactory();
  static DatabaseManager databaseManager = new DatabaseManager();
  EdgeFactory edgeFactory = EdgeFactory.getFactory();

  @BeforeAll
  public static void initializeDatabase() {
    try {
      databaseManager.initialize();
      databaseManager.reset();
      testData = new TestData();
      for (Node node : testData.validNodes) {
        nodeFactory.create(node);
      }
    } catch (SQLException e) {
      // Ignore
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validNodes = testData.validNodes;
    validEdges = testData.validEdges;
    databaseManager.initialize();
  }

  @Test
  public void testCreateReadDelete() {
    try {
      edgeFactory.create(null);
      fail("Creating a null value is unacceptable");
    } catch (ValidationException e) {
      // ignore as expected
    }
    try {
      for (Edge edge : validEdges) {
        edgeFactory.create(edge);

        Edge readEdge = edgeFactory.read(edge.getId());

        assertTrue(readEdge.equals(edge));

        edgeFactory.delete(edge.getId());

        try {
          readEdge = edgeFactory.read(edge.getId());
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
      for (Edge edge : validEdges) {
        edgeFactory.create(edge);

        edge.setNode1(validNodes[6]);
        edgeFactory.update(edge);

        Edge readEdge = edgeFactory.read(edge.getId());

        assertTrue(edge.equals(readEdge));

        edgeFactory.delete(edge.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
