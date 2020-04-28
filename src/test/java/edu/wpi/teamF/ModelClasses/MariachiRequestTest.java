package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.MariachiRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MariachiRequestTest {

  static TestData testData = null;
  static SecurityRequest[] validSecurityRequest = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();
  static MariachiRequest[] validMariachiRequest = null;
  MariachiRequestFactory mariachiRequestFactory = MariachiRequestFactory.getFactory();
  NodeFactory nodeFactory = NodeFactory.getFactory();
  static Node[] validNodes = null;

  @BeforeAll
  public static void databaseIntialize() throws Exception {
    databaseManager.initialize();
  }

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validMariachiRequest = testData.validMariachiRequests;
    validNodes = testData.validNodes;
    databaseManager.reset();
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() throws Exception {
    try {
      databaseManager.manipulateServiceRequest((SecurityRequest) null);
      mariachiRequestFactory.create(null);
      fail("Creating a null value is unacceptable");
    } catch (NullPointerException e) {
      // ignore as expected
    }
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    try {
      for (MariachiRequest mariachiRequest : validMariachiRequest) {
        databaseManager.manipulateServiceRequest(mariachiRequest);

        MariachiRequest readMariachi =
            databaseManager.readMariachiServiceRequest(mariachiRequest.getId());
        assertTrue(readMariachi.equals(mariachiRequest));

        databaseManager.deleteSecurityRequest(mariachiRequest.getId());

        try {
          databaseManager.readSecurityRequest(mariachiRequest.getId());
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

      for (MariachiRequest mariachiRequest : validMariachiRequest) {
        databaseManager.manipulateServiceRequest(mariachiRequest);

        mariachiRequest.setDescription("Hello");
        databaseManager.manipulateServiceRequest(mariachiRequest);

        MariachiRequest readMain =
            databaseManager.readMariachiServiceRequest(mariachiRequest.getId());

        assertTrue(mariachiRequest.equals(readMain));

        databaseManager.deleteMariachiServiceRequest(mariachiRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetSecurityByLocation() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    MariachiRequest main1 = validMariachiRequest[0];
    MariachiRequest main2 = validMariachiRequest[1];
    MariachiRequest main3 = validMariachiRequest[2];
    MariachiRequest main4 = validMariachiRequest[3];

    try {

      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<MariachiRequest> mariachiAtBathroom =
          databaseManager.getMariachiRequestByLocation(testData.validNodes[0]);

      assertTrue(mariachiAtBathroom.contains(main1));

      assertTrue(mariachiAtBathroom.size() == 1);

      List<MariachiRequest> mariachiAtnode2 =
          databaseManager.getMariachiRequestByLocation(testData.validNodes[1]);

      assertTrue(mariachiAtnode2.contains(main2));
      assertTrue(mariachiAtnode2.size() == 1);

      databaseManager.deleteSecurityRequest(main1.getId());
      databaseManager.deleteSecurityRequest(main2.getId());
      databaseManager.deleteSecurityRequest(main3.getId());
      databaseManager.deleteSecurityRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllSecurityRequests() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    MariachiRequest main1 = validMariachiRequest[0];
    MariachiRequest main2 = validMariachiRequest[1];
    MariachiRequest main3 = validMariachiRequest[2];
    MariachiRequest main4 = validMariachiRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<SecurityRequest> securityAll = databaseManager.getAllSecurityRequests();

      assertTrue(securityAll.contains(main1));
      assertTrue(securityAll.contains(main2));
      assertTrue(securityAll.contains(main3));
      assertTrue(securityAll.contains(main4));
      assertTrue(securityAll.size() == 4);

      databaseManager.deleteMariachiServiceRequest(main1.getId());
      databaseManager.deleteMariachiServiceRequest(main2.getId());
      databaseManager.deleteMariachiServiceRequest(main3.getId());
      databaseManager.deleteMariachiServiceRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
