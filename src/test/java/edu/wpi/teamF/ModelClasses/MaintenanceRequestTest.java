package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.MaintenanceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaintenanceRequestTest {

  static TestData testData = null;
  static MaintenanceRequest[] validMaintenanceRequest = null;
  MaintenanceRequestFactory maintenanceRequestFactory = MaintenanceRequestFactory.getFactory();
  NodeFactory nodeFactory = NodeFactory.getFactory();
  static DatabaseManager databaseManager = new DatabaseManager();

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validMaintenanceRequest = testData.validMaintenanceRequests;
    databaseManager.initialize();
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() {
    try {
      maintenanceRequestFactory.create(null);
      fail("Creating a null value is unacceptable");
    } catch (ValidationException e) {
      // ignore as expected
    }
    try {
      for (MaintenanceRequest maintenanceRequest : validMaintenanceRequest) {
        maintenanceRequestFactory.create(maintenanceRequest);

        MaintenanceRequest readMaintenance =
            maintenanceRequestFactory.read(maintenanceRequest.getId());
        assertTrue(readMaintenance.equals(maintenanceRequest));

        maintenanceRequestFactory.delete(maintenanceRequest.getId());

        try {
          readMaintenance = maintenanceRequestFactory.read(maintenanceRequest.getId());
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
        maintenanceRequestFactory.create(maintenanceRequest);

        maintenanceRequest.setDescription("Hello");
        maintenanceRequestFactory.update(maintenanceRequest);

        MaintenanceRequest readMain = maintenanceRequestFactory.read(maintenanceRequest.getId());

        assertTrue(maintenanceRequest.equals(readMain));

        maintenanceRequestFactory.delete(maintenanceRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetMainByLocation() {
    MaintenanceRequest main1 = validMaintenanceRequest[0];
    MaintenanceRequest main2 = validMaintenanceRequest[1];
    MaintenanceRequest main3 = validMaintenanceRequest[2];
    MaintenanceRequest main4 = validMaintenanceRequest[3];

    NodeFactory nodeFactory = NodeFactory.getFactory();

    try {
      maintenanceRequestFactory.create(main1);
      maintenanceRequestFactory.create(main2);
      maintenanceRequestFactory.create(main3);
      maintenanceRequestFactory.create(main4);

      List<MaintenanceRequest> maintenanceAtBathroom =
          maintenanceRequestFactory.getMaintenanceRequestsByLocation(testData.validNodes[0]);

      assertTrue(maintenanceAtBathroom.contains(main1));

      assertTrue(maintenanceAtBathroom.size() == 1);

      List<MaintenanceRequest> maintenanceAtnode2 =
          maintenanceRequestFactory.getMaintenanceRequestsByLocation(testData.validNodes[1]);

      assertTrue(maintenanceAtnode2.contains(main2));
      assertTrue(maintenanceAtnode2.size() == 1);

      maintenanceRequestFactory.delete(main1.getId());
      maintenanceRequestFactory.delete(main2.getId());
      maintenanceRequestFactory.delete(main3.getId());
      maintenanceRequestFactory.delete(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllNodes() {
    MaintenanceRequest main1 = validMaintenanceRequest[0];
    MaintenanceRequest main2 = validMaintenanceRequest[1];
    MaintenanceRequest main3 = validMaintenanceRequest[2];
    MaintenanceRequest main4 = validMaintenanceRequest[3];

    try {
      maintenanceRequestFactory.create(main1);
      maintenanceRequestFactory.create(main2);
      maintenanceRequestFactory.create(main3);
      maintenanceRequestFactory.create(main4);

      List<MaintenanceRequest> maintenanceAll =
          maintenanceRequestFactory.getAllMaintenanceRequests();

      assertTrue(maintenanceAll.contains(main1));
      assertTrue(maintenanceAll.contains(main2));
      assertTrue(maintenanceAll.contains(main3));
      assertTrue(maintenanceAll.contains(main4));
      assertTrue(maintenanceAll.size() == 4);

      nodeFactory.delete(main1.getId());
      nodeFactory.delete(main2.getId());
      nodeFactory.delete(main3.getId());
      nodeFactory.delete(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
