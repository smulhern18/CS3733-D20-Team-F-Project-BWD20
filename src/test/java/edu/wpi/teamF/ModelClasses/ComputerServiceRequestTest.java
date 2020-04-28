
package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComputerServiceRequestTest {

  static TestData testData = null;
  static ComputerServiceRequest[] validComputerServiceRequest = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();
  static Node[] validNodes = null;

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validComputerServiceRequest = testData.validComputerServiceRequests;
    validNodes = testData.validNodes;
    databaseManager.initialize();
    databaseManager.reset();
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    try {
      for (ComputerServiceRequest computerServiceRequest : validComputerServiceRequest) {
        databaseManager.manipulateServiceRequest(computerServiceRequest);

        ComputerServiceRequest readComputer =
            databaseManager.readComputerServiceRequest(computerServiceRequest.getId());
        assertTrue(readComputer.equals(computerServiceRequest));

        databaseManager.deleteComputerServiceRequest(computerServiceRequest.getId());

        try {
          readComputer = databaseManager.readComputerServiceRequest(computerServiceRequest.getId());
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
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    try {

      for (ComputerServiceRequest computerServiceRequest : validComputerServiceRequest) {
        databaseManager.manipulateServiceRequest(computerServiceRequest);

        computerServiceRequest.setDescription("Hello");
        databaseManager.manipulateServiceRequest(computerServiceRequest);

        ComputerServiceRequest readComp =
            databaseManager.readComputerServiceRequest(computerServiceRequest.getId());

        assertTrue(computerServiceRequest.equals(readComp));

        databaseManager.deleteComputerServiceRequest(computerServiceRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetMainByLocation() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    ComputerServiceRequest main1 = validComputerServiceRequest[0];
    ComputerServiceRequest main2 = validComputerServiceRequest[1];
    ComputerServiceRequest main3 = validComputerServiceRequest[2];
    ComputerServiceRequest main4 = validComputerServiceRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<ComputerServiceRequest> computerAtBathroom =
          databaseManager.getComputerServiceRequestsByLocation(testData.validNodes[0]);

      assertTrue(computerAtBathroom.contains(main1));

      assertTrue(computerAtBathroom.size() == 1);

      List<ComputerServiceRequest> computerAtnode2 =
          databaseManager.getComputerServiceRequestsByLocation(testData.validNodes[1]);

      assertTrue(computerAtnode2.contains(main2));
      assertTrue(computerAtnode2.size() == 1);

      databaseManager.deleteComputerServiceRequest(main1.getId());
      databaseManager.deleteComputerServiceRequest(main2.getId());
      databaseManager.deleteComputerServiceRequest(main3.getId());
      databaseManager.deleteComputerServiceRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllMaintenanceRequests() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    ComputerServiceRequest main1 = validComputerServiceRequest[0];
    ComputerServiceRequest main2 = validComputerServiceRequest[1];
    ComputerServiceRequest main3 = validComputerServiceRequest[2];
    ComputerServiceRequest main4 = validComputerServiceRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);
      List<ComputerServiceRequest> computerAll = databaseManager.getAllComputerServiceRequests();

      assertTrue(computerAll.contains(main1));
      assertTrue(computerAll.contains(main2));
      assertTrue(computerAll.contains(main3));
      assertTrue(computerAll.contains(main4));
      assertTrue(computerAll.size() == 4);

      databaseManager.deleteComputerServiceRequest(main1.getId());
      databaseManager.deleteComputerServiceRequest(main2.getId());
      databaseManager.deleteComputerServiceRequest(main3.getId());
      databaseManager.deleteComputerServiceRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
