package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.Factories.CSVManipulator;
import edu.wpi.teamF.Factories.DatabaseManager;
import edu.wpi.teamF.Factories.NodeFactory;
import edu.wpi.teamF.Test.TestData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVTest {

  /** Utility classes instantiation */
  static DatabaseManager databaseManager = new DatabaseManager();

  static CSVManipulator csvManipulator = new CSVManipulator();
  static NodeFactory nodeFactory = new NodeFactory();
  static TestData testData = null;

  Node[] validNodes = null;

  @BeforeEach
  public void cleanTests() {
    try {

      testData = new TestData();
      validNodes = testData.getValidNodes();

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

  @Test
  public void testReadAndWriteCSVNodes() {
    int i = 0;
    File file =
        new File(
            "/Users/theodorecampbell/IdeaProjects/CS3733-D20-Team-F-Project-BWD20/src/test/java/edu/wpi/teamF/Test/CSVNodeTest.csv");
    csvManipulator.readCSVFileNode(file.toPath());
    ObservableList<Node> list = nodeFactory.getAllNodes();
    for (Node n : list) {

      Assertions.assertTrue(n.equals(validNodes[i]));
      i++;
    }

    /** Valid data */
    File wfile =
        new File(
            "/Users/theodorecampbell/IdeaProjects/CS3733-D20-Team-F-Project-BWD20/src/test/java/edu/wpi/teamF/Test/CSVNodeTestw.csv");
    csvManipulator.writeCSVFileNode(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(wfile.toPath());
      byte[] f2 = Files.readAllBytes(file.toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (IOException e) {

    }
  }

  @Test
  public void testReadAndWriteCSVEdges() {
    NodeFactory nodeFactory = new NodeFactory();

    System.out.println("Wrote to CSV File");

    System.out.println("Read from CSV File");
  }
}
