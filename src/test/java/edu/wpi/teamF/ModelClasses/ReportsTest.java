package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestStats;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.TransportRequest;
import edu.wpi.teamF.TestData;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportsTest {

  /** Utility classes instantiation */
  static DatabaseManager databaseManager = DatabaseManager.getManager();

  static CSVManipulator csvManipulator = new CSVManipulator();
  static ServiceRequestStats serviceRequestStats = new ServiceRequestStats();
  TestData testData = null;
  Node[] validNodes = null;
  MaintenanceRequest[] validMaintenance = null;
  TransportRequest[] validTransportRequest = null;

  @BeforeEach
  public void cleanTests() {
    try {

      testData = new TestData();
      validNodes = testData.validNodes;
      validMaintenance = testData.validMaintenanceRequests;

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

  @AfterAll
  public static void cleanup() throws Exception {
    databaseManager.reset();
  }

  @Test
  public void testWritingReport() throws Exception {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    try {
      databaseManager.manipulateServiceRequest(validMaintenance[0]);
      databaseManager.manipulateServiceRequest(validMaintenance[1]);
      databaseManager.manipulateServiceRequest(validMaintenance[2]);
      databaseManager.manipulateServiceRequest(validMaintenance[3]);
      databaseManager.manipulateServiceRequest(testData.validComputerServiceRequests[0]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    // add transport tests
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/ServiceRequestReport.csv");

    serviceRequestStats.downloadStatistics(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(
                      getClass().getResource("/edu/wpi/teamF/MaintenanceRequestReport.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testTop5LessThen5() throws Exception {
    ArrayList<String> data = new ArrayList<String>();
    for (int i = 0; i <= 4; i++) {

      data.add("Jim");
      data.add("6");
    }
    Assertions.assertTrue(data.equals(serviceRequestStats.top5(data)));
  }

  @Test
  public void testTop5moreThen5() throws Exception {
    ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> data1 = new ArrayList<String>();
    ArrayList<String> data2 = new ArrayList<String>();
    for (int i = 0; i <= 10; i++) {
      data.add("s" + i);
      data.add("" + i);
    }

    data1.add("s10");
    data1.add("10");
    data1.add("s9");
    data1.add("9");
    data1.add("s8");
    data1.add("8");
    data1.add("s7");
    data1.add("7");
    data1.add("s6");
    data1.add("6");

    data2 = serviceRequestStats.top5(data);
    Assertions.assertTrue(data1.equals(data2));
  }
}
