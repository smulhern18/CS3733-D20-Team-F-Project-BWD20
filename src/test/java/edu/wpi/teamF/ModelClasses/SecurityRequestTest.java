package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SecurityRequestTest {

  static TestData testData = null;
  static SecurityRequest[] validSecurityRequest = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();
  static Node[] validNodes = null;

  @BeforeAll
  public static void databaseIntialize() throws Exception {
    databaseManager.initialize();
  }

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validSecurityRequest = testData.validSecurityRequests;
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
      for (SecurityRequest securityRequest : validSecurityRequest) {
        databaseManager.manipulateServiceRequest(securityRequest);

        SecurityRequest readSecurity = databaseManager.readSecurityRequest(securityRequest.getId());
        assertTrue(readSecurity.equals(securityRequest));

        databaseManager.deleteSecurityRequest(securityRequest.getId());

        try {
          readSecurity = databaseManager.readSecurityRequest(securityRequest.getId());
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

      for (SecurityRequest securityRequest : validSecurityRequest) {
        databaseManager.manipulateServiceRequest(securityRequest);

        securityRequest.setDescription("Hello");
        databaseManager.manipulateServiceRequest(securityRequest);

        SecurityRequest readMain = databaseManager.readSecurityRequest(securityRequest.getId());

        assertTrue(securityRequest.equals(readMain));

        databaseManager.deleteSecurityRequest(securityRequest.getId());
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
    SecurityRequest main1 = validSecurityRequest[0];
    SecurityRequest main2 = validSecurityRequest[1];
    SecurityRequest main3 = validSecurityRequest[2];
    SecurityRequest main4 = validSecurityRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<SecurityRequest> securityAtBathroom =
          databaseManager.getSecurityRequestsByLocation(testData.validNodes[0]);

      assertTrue(securityAtBathroom.contains(main1));

      assertTrue(securityAtBathroom.size() == 1);

      List<SecurityRequest> securityAtnode2 =
          databaseManager.getSecurityRequestsByLocation(testData.validNodes[1]);

      assertTrue(securityAtnode2.contains(main2));
      assertTrue(securityAtnode2.size() == 1);

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
    SecurityRequest main1 = validSecurityRequest[0];
    SecurityRequest main2 = validSecurityRequest[1];
    SecurityRequest main3 = validSecurityRequest[2];
    SecurityRequest main4 = validSecurityRequest[3];

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

      databaseManager.deleteSecurityRequest(main1.getId());
      databaseManager.deleteSecurityRequest(main2.getId());
      databaseManager.deleteSecurityRequest(main3.getId());
      databaseManager.deleteSecurityRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
