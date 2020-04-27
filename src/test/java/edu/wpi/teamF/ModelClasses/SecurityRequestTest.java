package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.DatabaseManipulators.SecurityRequestFactory;
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
  SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  NodeFactory nodeFactory = NodeFactory.getFactory();
  static DatabaseManager databaseManager = new DatabaseManager();
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
  public void testCreateReadDelete() {
    try {
      securityRequestFactory.create(null);
      fail("Creating a null value is unacceptable");
    } catch (ValidationException e) {
      // ignore as expected
    }
    try {
      nodeFactory.create(validNodes[0]);
      nodeFactory.create(validNodes[1]);
      nodeFactory.create(validNodes[2]);
      nodeFactory.create(validNodes[3]);

    } catch (Exception e) {

    }
    try {
      for (SecurityRequest securityRequest : validSecurityRequest) {
        securityRequestFactory.create(securityRequest);

        SecurityRequest readSecurity = securityRequestFactory.read(securityRequest.getId());
        assertTrue(readSecurity.equals(securityRequest));

        securityRequestFactory.delete(securityRequest.getId());

        try {
          readSecurity = securityRequestFactory.read(securityRequest.getId());
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
      nodeFactory.create(validNodes[0]);
      nodeFactory.create(validNodes[1]);
      nodeFactory.create(validNodes[2]);
      nodeFactory.create(validNodes[3]);

    } catch (Exception e) {

    }
    try {

      for (SecurityRequest securityRequest : validSecurityRequest) {
        securityRequestFactory.create(securityRequest);

        securityRequest.setDescription("Hello");
        securityRequestFactory.update(securityRequest);

        SecurityRequest readMain = securityRequestFactory.read(securityRequest.getId());

        assertTrue(securityRequest.equals(readMain));

        securityRequestFactory.delete(securityRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetSecurityByLocation() {
    try {
      nodeFactory.create(validNodes[0]);
      nodeFactory.create(validNodes[1]);
      nodeFactory.create(validNodes[2]);
      nodeFactory.create(validNodes[3]);

    } catch (Exception e) {

    }
    SecurityRequest main1 = validSecurityRequest[0];
    SecurityRequest main2 = validSecurityRequest[1];
    SecurityRequest main3 = validSecurityRequest[2];
    SecurityRequest main4 = validSecurityRequest[3];

    NodeFactory nodeFactory = NodeFactory.getFactory();

    try {
      securityRequestFactory.create(main1);
      securityRequestFactory.create(main2);
      securityRequestFactory.create(main3);
      securityRequestFactory.create(main4);

      List<SecurityRequest> securityAtBathroom =
          securityRequestFactory.getSecurityRequestsByLocation(testData.validNodes[0]);

      assertTrue(securityAtBathroom.contains(main1));

      assertTrue(securityAtBathroom.size() == 1);

      List<SecurityRequest> securityAtnode2 =
          securityRequestFactory.getSecurityRequestsByLocation(testData.validNodes[1]);

      assertTrue(securityAtnode2.contains(main2));
      assertTrue(securityAtnode2.size() == 1);

      securityRequestFactory.delete(main1.getId());
      securityRequestFactory.delete(main2.getId());
      securityRequestFactory.delete(main3.getId());
      securityRequestFactory.delete(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllSecurityRequests() {
    try {
      nodeFactory.create(validNodes[0]);
      nodeFactory.create(validNodes[1]);
      nodeFactory.create(validNodes[2]);
      nodeFactory.create(validNodes[3]);

    } catch (Exception e) {

    }
    SecurityRequest main1 = validSecurityRequest[0];
    SecurityRequest main2 = validSecurityRequest[1];
    SecurityRequest main3 = validSecurityRequest[2];
    SecurityRequest main4 = validSecurityRequest[3];

    try {
      securityRequestFactory.create(main1);
      securityRequestFactory.create(main2);
      securityRequestFactory.create(main3);
      securityRequestFactory.create(main4);

      List<SecurityRequest> securityAll = securityRequestFactory.getAllSecurityRequests();

      assertTrue(securityAll.contains(main1));
      assertTrue(securityAll.contains(main2));
      assertTrue(securityAll.contains(main3));
      assertTrue(securityAll.contains(main4));
      assertTrue(securityAll.size() == 4);

      securityRequestFactory.delete(main1.getId());
      securityRequestFactory.delete(main2.getId());
      securityRequestFactory.delete(main3.getId());
      securityRequestFactory.delete(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
