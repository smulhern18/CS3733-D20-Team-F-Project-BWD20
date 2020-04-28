package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlowerServiceRequestFactoryTest {

  static TestData testData = null;
  static FlowerRequest[] validFlowerRequest = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();
  static Node[] validNodes = null;

  @BeforeAll
  public static void databaseIntialize() throws Exception {
    databaseManager.initialize();
  }

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validFlowerRequest = testData.validFlowerRequests;
    validNodes = testData.validNodes;
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() {
    try {
      databaseManager.manipulateServiceRequest((FlowerRequest) null);
      fail("Creating a null value is unacceptable");
    } catch (ValidationException e) {
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
      for (FlowerRequest flowerRequest : validFlowerRequest) {
        databaseManager.manipulateServiceRequest(flowerRequest);

        FlowerRequest readFlower = databaseManager.readFlowerRequest(flowerRequest.getId());
        assertTrue(readFlower.equals(flowerRequest));

        databaseManager.deleteFlowerRequest(flowerRequest.getId());

        try {
          readFlower = databaseManager.readFlowerRequest(flowerRequest.getId());
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

      for (FlowerRequest flowerRequest : validFlowerRequest) {
        databaseManager.manipulateServiceRequest(flowerRequest);

        flowerRequest.setDescription("Hello");
        databaseManager.manipulateServiceRequest(flowerRequest);

        FlowerRequest readMain = databaseManager.readFlowerRequest(flowerRequest.getId());

        assertTrue(flowerRequest.equals(readMain));

        databaseManager.deleteFlowerRequest(flowerRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetFlowersByLocation() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    FlowerRequest main1 = validFlowerRequest[0];
    FlowerRequest main2 = validFlowerRequest[1];
    FlowerRequest main3 = validFlowerRequest[2];
    FlowerRequest main4 = validFlowerRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<FlowerRequest> flowersAtBathroom =
          databaseManager.getFlowerRequestsByLocation(testData.validNodes[0]);

      assertTrue(flowersAtBathroom.contains(main1));

      assertTrue(flowersAtBathroom.size() == 1);

      List<FlowerRequest> flowersAtnode2 =
          databaseManager.getFlowerRequestsByLocation(testData.validNodes[1]);

      assertTrue(flowersAtnode2.contains(main2));
      assertTrue(flowersAtnode2.size() == 1);

      databaseManager.deleteFlowerRequest(main1.getId());
      databaseManager.deleteFlowerRequest(main2.getId());
      databaseManager.deleteFlowerRequest(main3.getId());
      databaseManager.deleteFlowerRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllFlowerRequests() {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    FlowerRequest main1 = validFlowerRequest[0];
    FlowerRequest main2 = validFlowerRequest[1];
    FlowerRequest main3 = validFlowerRequest[2];
    FlowerRequest main4 = validFlowerRequest[3];

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      databaseManager.manipulateServiceRequest(main3);
      databaseManager.manipulateServiceRequest(main4);

      List<FlowerRequest> flowersAll = databaseManager.getAllFlowerRequests();

      assertTrue(flowersAll.contains(main1));
      assertTrue(flowersAll.contains(main2));
      assertTrue(flowersAll.contains(main3));
      assertTrue(flowersAll.contains(main4));
      assertTrue(flowersAll.size() == 4);

      databaseManager.deleteNode(main1.getId());
      databaseManager.deleteNode(main2.getId());
      databaseManager.deleteNode(main3.getId());
      databaseManager.deleteNode(main4.getId());
      databaseManager.deleteFlowerRequest(main1.getId());
      databaseManager.deleteFlowerRequest(main2.getId());
      databaseManager.deleteFlowerRequest(main3.getId());
      databaseManager.deleteFlowerRequest(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
