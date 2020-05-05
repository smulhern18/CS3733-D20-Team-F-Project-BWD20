package edu.wpi.cs3733.d20.teamF;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.cs3733.d20.teamF.ModelClasses.MaintenanceRequest;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaintenanceRequestTest {

  static TestData testData = null;
  static MaintenanceRequest[] validMaintenanceRequest = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validMaintenanceRequest = testData.validMaintenanceRequests;
    databaseManager.initialize();
    databaseManager.reset();
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() throws Exception {
    try {
      databaseManager.manipulateServiceRequest((MaintenanceRequest) null);
      fail("Creating a null value is unacceptable");
    } catch (NullPointerException e) {
      // ignore as expected
    }
    try {
      for (MaintenanceRequest maintenanceRequest : validMaintenanceRequest) {
        databaseManager.manipulateServiceRequest(maintenanceRequest);

        MaintenanceRequest readMaintenance =
            databaseManager.readMaintenanceRequest(maintenanceRequest.getId());
        assertTrue(readMaintenance.equals(maintenanceRequest));

        databaseManager.deleteMaintenanceRequest(maintenanceRequest.getId());

        try {
          readMaintenance = databaseManager.readMaintenanceRequest(maintenanceRequest.getId());
        } // catch (InstanceNotFoundException e) {
        // ignore
        // }
        catch (Exception e) {
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

      for (MaintenanceRequest maintenanceRequest : validMaintenanceRequest) {
        databaseManager.manipulateServiceRequest(maintenanceRequest);

        maintenanceRequest.setDescription("Hello");
        databaseManager.manipulateServiceRequest(maintenanceRequest);

        MaintenanceRequest readMain =
            databaseManager.readMaintenanceRequest(maintenanceRequest.getId());

        assertTrue(maintenanceRequest.equals(readMain));

        databaseManager.readMaintenanceRequest(maintenanceRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllMaintenanceRequests() {
    MaintenanceRequest main1 = validMaintenanceRequest[0];
    MaintenanceRequest main2 = validMaintenanceRequest[1];
    MaintenanceRequest main3 = validMaintenanceRequest[2];
    MaintenanceRequest main4 = validMaintenanceRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<MaintenanceRequest> maintenanceAll = databaseManager.getAllMaintenanceRequests();

      assertTrue(maintenanceAll.contains(main1));
      assertTrue(maintenanceAll.contains(main2));
      assertTrue(maintenanceAll.contains(main3));
      assertTrue(maintenanceAll.contains(main4));
      assertTrue(maintenanceAll.size() == 4);

      databaseManager.deleteMaintenanceRequest(main1.getId());
      databaseManager.deleteMaintenanceRequest(main2.getId());
      databaseManager.deleteMaintenanceRequest(main3.getId());
      databaseManager.deleteMaintenanceRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
