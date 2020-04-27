package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.MedicineDeliveryRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedicineDeliveryRequestFactoryTest {

  static TestData testData = null;
  static MedicineDeliveryRequest[] validMedicineDeliveryRequest = null;
  MedicineDeliveryRequestFactory medicineDeliveryRequestFactory =
      MedicineDeliveryRequestFactory.getFactory();
  NodeFactory nodeFactory = NodeFactory.getFactory();
  static DatabaseManager databaseManager = new DatabaseManager();
  static Node[] validNodes = null;

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validMedicineDeliveryRequest = testData.validMedicineDeliveryRequests;
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
      medicineDeliveryRequestFactory.create(null);
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
      for (MedicineDeliveryRequest medicineDeliveryRequest : validMedicineDeliveryRequest) {
        medicineDeliveryRequestFactory.create(medicineDeliveryRequest);

        MedicineDeliveryRequest readMedicine =
            medicineDeliveryRequestFactory.read(medicineDeliveryRequest.getId());
        assertTrue(readMedicine.equals(medicineDeliveryRequest));

        medicineDeliveryRequestFactory.delete(medicineDeliveryRequest.getId());

        try {
          readMedicine = medicineDeliveryRequestFactory.read(medicineDeliveryRequest.getId());
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

      for (MedicineDeliveryRequest medicineDeliveryRequest : validMedicineDeliveryRequest) {
        medicineDeliveryRequestFactory.create(medicineDeliveryRequest);

        medicineDeliveryRequest.setDescription("Hello");
        medicineDeliveryRequestFactory.update(medicineDeliveryRequest);

        MedicineDeliveryRequest readMed =
            medicineDeliveryRequestFactory.read(medicineDeliveryRequest.getId());

        assertTrue(medicineDeliveryRequest.equals(readMed));

        medicineDeliveryRequestFactory.delete(medicineDeliveryRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetMainByLocation() {
    try {
      nodeFactory.create(validNodes[0]);
      nodeFactory.create(validNodes[1]);
      nodeFactory.create(validNodes[2]);
      nodeFactory.create(validNodes[3]);

    } catch (Exception e) {

    }
    MedicineDeliveryRequest main1 = validMedicineDeliveryRequest[0];
    MedicineDeliveryRequest main2 = validMedicineDeliveryRequest[1];
    MedicineDeliveryRequest main3 = validMedicineDeliveryRequest[2];
    MedicineDeliveryRequest main4 = validMedicineDeliveryRequest[3];

    NodeFactory nodeFactory = NodeFactory.getFactory();

    try {
      medicineDeliveryRequestFactory.create(main1);
      medicineDeliveryRequestFactory.create(main2);
      medicineDeliveryRequestFactory.create(main3);
      medicineDeliveryRequestFactory.create(main4);

      List<MedicineDeliveryRequest> computerAtBathroom =
          medicineDeliveryRequestFactory.getMedicineDeliveryRequestsByLocation(
              testData.validNodes[0]);

      assertTrue(computerAtBathroom.contains(main1));

      assertTrue(computerAtBathroom.size() == 1);

      List<MedicineDeliveryRequest> computerAtnode2 =
          medicineDeliveryRequestFactory.getMedicineDeliveryRequestsByLocation(
              testData.validNodes[1]);

      assertTrue(computerAtnode2.contains(main2));
      assertTrue(computerAtnode2.size() == 1);

      medicineDeliveryRequestFactory.delete(main1.getId());
      medicineDeliveryRequestFactory.delete(main2.getId());
      medicineDeliveryRequestFactory.delete(main3.getId());
      medicineDeliveryRequestFactory.delete(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetAllMaintenanceRequests() {
    try {
      nodeFactory.create(validNodes[0]);
      nodeFactory.create(validNodes[1]);
      nodeFactory.create(validNodes[2]);
      nodeFactory.create(validNodes[3]);

    } catch (Exception e) {

    }
    MedicineDeliveryRequest main1 = validMedicineDeliveryRequest[0];
    MedicineDeliveryRequest main2 = validMedicineDeliveryRequest[1];
    MedicineDeliveryRequest main3 = validMedicineDeliveryRequest[2];
    MedicineDeliveryRequest main4 = validMedicineDeliveryRequest[3];

    try {
      medicineDeliveryRequestFactory.create(main1);
      medicineDeliveryRequestFactory.create(main2);
      medicineDeliveryRequestFactory.create(main3);
      medicineDeliveryRequestFactory.create(main4);
      List<MedicineDeliveryRequest> computerAll =
          medicineDeliveryRequestFactory.getAllMedicineDeliveryRequests();

      assertTrue(computerAll.contains(main1));
      assertTrue(computerAll.contains(main2));
      assertTrue(computerAll.contains(main3));
      assertTrue(computerAll.contains(main4));
      assertTrue(computerAll.size() == 4);

      medicineDeliveryRequestFactory.delete(main1.getId());
      medicineDeliveryRequestFactory.delete(main2.getId());
      medicineDeliveryRequestFactory.delete(main3.getId());
      medicineDeliveryRequestFactory.delete(main4.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
