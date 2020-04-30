package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
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
  static DatabaseManager databaseManager = DatabaseManager.getManager();

  @BeforeAll
  public static void initializeDatabase() {
    try {
      databaseManager.initialize();
      testData = new TestData();
      for (Node node : testData.validNodes) {
        databaseManager.manipulateNode(node);
      }
    } catch (SQLException e) {
      // Ignore
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      fail(e.getMessage() + e.getClass());
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
  public void testCreateReadDelete() throws Exception {
    try {
      databaseManager.manipulateEdge(null);
      fail("Creating a null value is unacceptable");
    } catch (NullPointerException e) {
      // ignore as expected
    }
    try {
      for (Edge edge : validEdges) {
        databaseManager.manipulateEdge(edge);

        Edge readEdge = databaseManager.readEdge(edge.getId());

        assertTrue(readEdge.equals(edge));

        databaseManager.deleteEdge(edge.getId());

        try {
          readEdge = databaseManager.readEdge(edge.getId());
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
        databaseManager.manipulateEdge(edge);

        edge.setNode1(validNodeIDs[6]);
        databaseManager.manipulateEdge(edge);

        Edge readEdge = databaseManager.readEdge(edge.getId());

        assertTrue(edge.equals(readEdge));

        databaseManager.deleteEdge(edge.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllEdgesForNode() {
    try {
      validEdges[1].setNode2(validNodeIDs[0]);
      databaseManager.manipulateEdge(validEdges[0]);
      databaseManager.manipulateEdge(validEdges[1]);
      databaseManager.manipulateEdge(validEdges[2]);
      databaseManager.manipulateEdge(validEdges[3]);
      ArrayList<Edge> readEdges = new ArrayList<>();
      for (Edge e : databaseManager.getAllEdgesConnectedToNode(validNodeIDs[0])) {
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
      databaseManager.manipulateEdge(validEdges[0]);
      databaseManager.manipulateEdge(validEdges[1]);
      databaseManager.manipulateEdge(validEdges[2]);
      databaseManager.manipulateEdge(validEdges[3]);
      ArrayList<Edge> readEdges = new ArrayList<>(databaseManager.getAllEdges());
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
