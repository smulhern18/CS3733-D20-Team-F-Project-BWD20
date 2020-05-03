package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
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
  static DatabaseManager databaseManager = DatabaseManager.getManager();

  static CSVManipulator csvManipulator = new CSVManipulator();
  static NodeFactory nodeFactory = NodeFactory.getFactory();
  static TestData testData = null;

  Node[] validNodes = null;
  Edge[] validEdge = null;
  MaintenanceRequest[] validMaintenance = null;
  MariachiRequest[] validMariachiRequest = null;
  SecurityRequest[] validSecurityRequest = null;
  Account[] validAccounts = null;
  HashSet<String> validNeighbors1 = null;

  @BeforeEach
  public void cleanTests() {
    try {

      testData = new TestData();
      validNodes = testData.validNodes;
      validEdge = testData.validEdges;
      validMaintenance = testData.validMaintenanceRequests;
      validMariachiRequest = testData.validMariachiRequests;
      validAccounts = testData.validAccounts;
      validSecurityRequest = testData.validSecurityRequests;

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @BeforeEach
  public void initializeDatabase() {
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
  public void testReadAndWriteCSVNodes() throws Exception {
    int i = 0;
    csvManipulator.readCSVFileNode(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVNodeTest.csv"));
    List<Node> list = databaseManager.getAllNodes();
    for (Node n : list) {

      Assertions.assertTrue(n.equals(validNodes[i]));
      i++;
    }

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MapFAllnodes.csv");
    csvManipulator.writeCSVFileNode(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVNodeTest.csv").toURI()).toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVEdges() throws Exception {
    Edge edge;
    int i = 0;

    csvManipulator.readCSVFileEdge(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVEdgeTest.csv"));
    try {
      List<Edge> list = databaseManager.getAllEdges();
      for (Edge e : list) {
        Assertions.assertTrue(e.equals(validEdge[i]));
        i++;
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MapFAlledges.csv");
    csvManipulator.writeCSVFileEdge(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVEdgeTest.csv").toURI()).toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVMaintenance() throws Exception {

    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileMaintenanceService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVMaintenanceRequestTest.csv"));

    List<MaintenanceRequest> list = databaseManager.getAllMaintenanceRequests();

    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validMaintenance[0]));
    Assertions.assertTrue(list.contains(validMaintenance[1]));
    Assertions.assertTrue(list.contains(validMaintenance[2]));
    Assertions.assertTrue(list.contains(validMaintenance[3]));
    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MaintenanceBackup.csv");
    csvManipulator.writeCSVFileMaintenanceService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(
                      getClass()
                          .getResource("/edu/wpi/teamF/CSVMaintenanceRequestTest.csv")
                          .toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVSecurity() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileSecurityService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVSecurityTest.csv"));

    List<SecurityRequest> list = databaseManager.getAllSecurityRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validSecurityRequest[0]));
    Assertions.assertTrue(list.contains(validSecurityRequest[1]));
    Assertions.assertTrue(list.contains(validSecurityRequest[2]));
    Assertions.assertTrue(list.contains(validSecurityRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/SecurityBackup.csv");
    csvManipulator.writeCSVFileSecurityService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVSecurityTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVAccount() throws Exception {
    int i = 0;

    csvManipulator.readCSVFileAccount(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVAccountTest.csv"));

    List<Account> list = databaseManager.getAllAccounts();
    for (Account a : list) {

      //  Assertions.assertTrue(a.getFirstName().equals(validAccounts[i].getFirstName()));
      i++;
    }

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/AccountBackup.csv");
    csvManipulator.writeCSVFileAccount(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVAccountTest.csv").toURI())
                  .toPath());
      // assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }
}