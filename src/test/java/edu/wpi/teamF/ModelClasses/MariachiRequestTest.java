package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.MariachiRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MariachiRequestTest {

  static TestData testData = null;
  static MariachiRequest[] validMariachiRequest = null;
  MariachiRequestFactory mariachiRequestFactory = MariachiRequestFactory.getFactory();
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
    validMariachiRequest = testData.validMariachiRequests;
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
      mariachiRequestFactory.create(null);
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
      for (MariachiRequest mariachiRequest : validMariachiRequest) {
        mariachiRequestFactory.create(mariachiRequest);

        MariachiRequest readSecurity = mariachiRequestFactory.read(mariachiRequest.getId());
        assertTrue(readSecurity.equals(mariachiRequest));

        mariachiRequestFactory.delete(mariachiRequest.getId());

        try {
          readSecurity = mariachiRequestFactory.read(mariachiRequest.getId());
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

      for (MariachiRequest mariachiRequest : validMariachiRequest) {
        mariachiRequestFactory.create(mariachiRequest);

        mariachiRequest.setDescription("Hello");
        mariachiRequestFactory.update(mariachiRequest);

        MariachiRequest readMain = mariachiRequestFactory.read(mariachiRequest.getId());

        assertTrue(mariachiRequest.equals(readMain));

        mariachiRequestFactory.delete(mariachiRequest.getId());
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
    MariachiRequest main1 = validMariachiRequest[0];
    MariachiRequest main2 = validMariachiRequest[1];
    MariachiRequest main3 = validMariachiRequest[2];
    MariachiRequest main4 = validMariachiRequest[3];

    NodeFactory nodeFactory = NodeFactory.getFactory();

    try {
      mariachiRequestFactory.create(main1);
      mariachiRequestFactory.create(main2);
      mariachiRequestFactory.create(main3);
      mariachiRequestFactory.create(main4);

      List<MariachiRequest> mariachiAtBathroom =
          mariachiRequestFactory.getSecurityRequestsByLocation(testData.validNodes[0]);

      assertTrue(mariachiAtBathroom.contains(main1));

      assertTrue(mariachiAtBathroom.size() == 1);

      List<MariachiRequest> mariachiAtnode2 =
          mariachiRequestFactory.getSecurityRequestsByLocation(testData.validNodes[1]);

      assertTrue(mariachiAtnode2.contains(main2));
      assertTrue(mariachiAtnode2.size() == 1);

      mariachiRequestFactory.delete(main1.getId());
      mariachiRequestFactory.delete(main2.getId());
      mariachiRequestFactory.delete(main3.getId());
      mariachiRequestFactory.delete(main4.getId());
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
    MariachiRequest main1 = validMariachiRequest[0];
    MariachiRequest main2 = validMariachiRequest[1];
    MariachiRequest main3 = validMariachiRequest[2];
    MariachiRequest main4 = validMariachiRequest[3];

    try {
      mariachiRequestFactory.create(main1);
      mariachiRequestFactory.create(main2);
      mariachiRequestFactory.create(main3);
      mariachiRequestFactory.create(main4);

      List<MariachiRequest> mariachiAll = mariachiRequestFactory.getAllMariachiRequest();

      assertTrue(mariachiAll.contains(main1));
      assertTrue(mariachiAll.contains(main2));
      assertTrue(mariachiAll.contains(main3));
      assertTrue(mariachiAll.contains(main4));
      assertTrue(mariachiAll.size() == 4);

      nodeFactory.delete(main1.getId());
      nodeFactory.delete(main2.getId());
      nodeFactory.delete(main3.getId());
      nodeFactory.delete(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
