package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ReportsClass;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.management.InstanceNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportsFactoryTest {
  static TestData testData = null;
  static String[] validNodeIDs = null;
  static ReportsClass[] validReports = null;
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
    validReports = testData.validReports;
    databaseManager.initialize();
  }

  @Test
  public void testCreateReadDelete() throws Exception {

    try {
      for (ReportsClass report : validReports) {
        databaseManager.manipulateReport(report);

        ReportsClass readReport = databaseManager.readReport(report.getNodeID());

        assertTrue(readReport.equals(report));

        databaseManager.deleteReport(report.getNodeID());

        try {
          readReport = databaseManager.readReport(report.getNodeID());
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
      for (ReportsClass report : validReports) {
        databaseManager.manipulateReport(report);

        report.setTimesVisited(4);
        databaseManager.manipulateReport(report);

        ReportsClass readReport = databaseManager.readReport(report.getNodeID());

        assertTrue(report.equals(readReport));

        databaseManager.deleteReport(report.getNodeID());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllReports() {
    try {
      databaseManager.manipulateReport(validReports[0]);
      databaseManager.manipulateReport(validReports[1]);
      databaseManager.manipulateReport(validReports[2]);
      databaseManager.manipulateReport(validReports[3]);
      ArrayList<ReportsClass> readReports = new ArrayList<>();
      for (ReportsClass e : databaseManager.getAllReports()) {
        readReports.add(e);
      }
      assertTrue(readReports.size() == 4);
      boolean whatever = readReports.contains(validReports[0]);
      assertTrue(readReports.contains(validReports[0]));
      assertTrue(readReports.contains(validReports[1]));
      assertTrue(readReports.contains(validReports[2]));
      assertTrue(readReports.contains(validReports[3]));

      databaseManager.reset();
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
