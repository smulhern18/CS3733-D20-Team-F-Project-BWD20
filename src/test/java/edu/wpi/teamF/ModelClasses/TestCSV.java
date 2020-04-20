package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.TestData;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.*;

public class TestCSV {

  /** Utility classes instantiation */
  static DatabaseManager databaseManager = new DatabaseManager();

  static CSVManipulator csvManipulator = new CSVManipulator();
  static NodeFactory nodeFactory = NodeFactory.getFactory();
  static TestData testData = null;
  static EdgeFactory edgeFactory = EdgeFactory.getFactory();

  Node[] validNodes = null;
  HashSet<String> validNeighbors1 = null;

  @BeforeEach
  public void cleanTests() {
    try {

      testData = new TestData();
      // validNodes = testData.getValidNodes();
      //  validNeighbors1 = testData.getValidNeighbors1();

      // testData = new TestData();

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

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testReadAndWriteCSVNodes() {
    int i = 0;
    csvManipulator.readCSVFileNode(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVNodeTest.csv"));
    List<Node> list = nodeFactory.getAllNodes();
    for (Node n : list) {

      Assertions.assertTrue(n.equals(validNodes[i]));
      i++;
    }

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    csvManipulator.writeCSVFileNode(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(wfile.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVNodeTest.csv").toURI()).toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {

    }
  }

  @Test
  public void testReadAndWriteCSVEdges() {
    Edge edge;
    csvManipulator.readCSVFileEdge(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVEdgeTest.csv"));
    try {
      edge = edgeFactory.read("NodeA");
      Assertions.assertTrue(edge.equals(validNeighbors1));

    } catch (Exception e) {

    }
  }
}
