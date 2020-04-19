package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.management.InstanceNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EdgeFactoryTest {

  static TestData testData = null;
  static String[] validNodeIDs = null;
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

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validNodeIDs = testData.validNodeIDs;
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

        edge.setNode1(validNodeIDs[6]);
        edgeFactory.update(edge);

        Edge readEdge = edgeFactory.read(edge.getId());

        assertTrue(edge.equals(readEdge));

        edgeFactory.delete(edge.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllEdgesForNode() {
    try {
      validEdges[1].setNode2(validNodeIDs[0]);
      edgeFactory.create(validEdges[0]);
      edgeFactory.create(validEdges[1]);
      edgeFactory.create(validEdges[2]);
      edgeFactory.create(validEdges[3]);
      ArrayList<Edge> readEdges = new ArrayList<>();
      for (Edge e : edgeFactory.getAllEdgesConnectedToNode(validNodeIDs[0])) {
        readEdges.add(e);
      }
      assertTrue(readEdges.size() == 2);
      assertTrue(
          readEdges.containsAll(new ArrayList<Edge>(Arrays.asList(validEdges[0], validEdges[1]))));

      databaseManager.reset();
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllEdges() {
    try {
      edgeFactory.create(validEdges[0]);
      edgeFactory.create(validEdges[1]);
      edgeFactory.create(validEdges[2]);
      edgeFactory.create(validEdges[3]);
      ArrayList<Edge> readEdges = new ArrayList<>(edgeFactory.getAllEdges());
      assertTrue(readEdges.size() == 4);
      assertTrue(readEdges.get(0).equals(validEdges[0]));
      assertTrue(readEdges.get(1).equals(validEdges[1]));
      assertTrue(readEdges.get(2).equals(validEdges[2]));
      assertTrue(readEdges.get(3).equals(validEdges[3]));
      databaseManager.reset();
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
