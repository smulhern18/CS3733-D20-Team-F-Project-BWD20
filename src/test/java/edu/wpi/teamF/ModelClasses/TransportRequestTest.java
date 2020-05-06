package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ServiceRequest.TransportRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransportRequestTest {

  static TestData testData = null;
  static TransportRequest[] validTransportRequest = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();
  static Node[] validNodes = null;

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validTransportRequest = testData.validTransportRequests;
    validNodes = testData.validNodes;
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
      databaseManager.manipulateServiceRequest((TransportRequest) null);

    } catch (NullPointerException e) {
      // ignore as expected
    }
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    try {
      for (TransportRequest transportRequest : validTransportRequest) {
        databaseManager.manipulateServiceRequest(transportRequest);

        TransportRequest readTransport =
            databaseManager.readTransportRequest(transportRequest.getId());
        assertTrue(readTransport.equals(transportRequest));

        databaseManager.deleteTransportRequest(transportRequest.getId());

        try {
          readTransport = databaseManager.readTransportRequest(transportRequest.getId());
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

      for (TransportRequest transportRequest : validTransportRequest) {
        databaseManager.manipulateServiceRequest(transportRequest);

        transportRequest.setDescription("Hello");
        databaseManager.manipulateServiceRequest(transportRequest);

        TransportRequest readTransport =
            databaseManager.readTransportRequest(transportRequest.getId());

        assertTrue(transportRequest.equals(readTransport));

        databaseManager.readTransportRequest(transportRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetTransportByLocation() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    TransportRequest main1 = validTransportRequest[0];
    TransportRequest main2 = validTransportRequest[1];
    TransportRequest main3 = validTransportRequest[2];
    TransportRequest main4 = validTransportRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<TransportRequest> transportAtBathroom =
          databaseManager.getTransportRequestsByLocation(testData.validNodes[0]);

      assertTrue(transportAtBathroom.contains(main1));

      assertTrue(transportAtBathroom.size() == 1);

      List<TransportRequest> transportAtNode2 =
          databaseManager.getTransportRequestsByLocation(testData.validNodes[1]);

      assertTrue(transportAtNode2.contains(main2));
      assertTrue(transportAtNode2.size() == 1);

      databaseManager.deleteTransportRequest(main1.getId());
      databaseManager.deleteTransportRequest(main2.getId());
      databaseManager.deleteTransportRequest(main3.getId());
      databaseManager.deleteTransportRequest(main4.getId());
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
    TransportRequest main1 = validTransportRequest[0];
    TransportRequest main2 = validTransportRequest[1];
    TransportRequest main3 = validTransportRequest[2];
    TransportRequest main4 = validTransportRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<TransportRequest> transportAll = databaseManager.getAllTransportRequests();

      assertTrue(transportAll.contains(main1));
      assertTrue(transportAll.contains(main2));
      assertTrue(transportAll.contains(main3));
      assertTrue(transportAll.contains(main4));
      assertTrue(transportAll.size() == 4);

      databaseManager.deleteTransportRequest(main1.getId());
      databaseManager.deleteTransportRequest(main2.getId());
      databaseManager.deleteTransportRequest(main3.getId());
      databaseManager.deleteTransportRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
