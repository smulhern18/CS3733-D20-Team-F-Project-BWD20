package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LaundryServiceRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LanguageFactoryTest {

  static TestData testData = null;
  static LanguageServiceRequest[] validLanguageServiceRequest = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();
  static Node[] validNodes = null;

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validLanguageServiceRequest = testData.validLanguageServiceRequests;
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
      databaseManager.manipulateServiceRequest((LaundryServiceRequest) null);
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
      for (LanguageServiceRequest languageServiceRequest : validLanguageServiceRequest) {
        databaseManager.manipulateServiceRequest(languageServiceRequest);

        LanguageServiceRequest readLanguage =
            databaseManager.readLanguageServiceRequest(languageServiceRequest.getId());
        assertTrue(readLanguage.equals(languageServiceRequest));

        databaseManager.deleteLaundryServiceRequest(languageServiceRequest.getId());

        try {
          readLanguage = databaseManager.readLanguageServiceRequest(languageServiceRequest.getId());
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

      for (LanguageServiceRequest languageServiceRequest : validLanguageServiceRequest) {
        databaseManager.manipulateServiceRequest(languageServiceRequest);

        languageServiceRequest.setDescription("Hello");
        databaseManager.manipulateServiceRequest(languageServiceRequest);

        LanguageServiceRequest readLanguage =
            databaseManager.readLanguageServiceRequest(languageServiceRequest.getId());

        assertTrue(languageServiceRequest.equals(readLanguage));

        databaseManager.deleteLaundryServiceRequest(languageServiceRequest.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetMainByLocation() throws Exception {
    try {
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    LanguageServiceRequest main1 = validLanguageServiceRequest[0];
    main1.setLocation(validNodes[2]);
    LanguageServiceRequest main2 = validLanguageServiceRequest[1];
    main2.setLocation(validNodes[3]);
    databaseManager.manipulateServiceRequest(main1);
    databaseManager.manipulateServiceRequest(main2);

    List<LanguageServiceRequest> languageAtBathroom =
        databaseManager.getLanguageRequestsByLocation(testData.validNodes[0]);

    assertTrue(languageAtBathroom.contains(main1));

    assertTrue(languageAtBathroom.size() == 1);

    List<LanguageServiceRequest> languageAtnode2 =
        databaseManager.getLanguageRequestsByLocation(testData.validNodes[1]);

    assertTrue(languageAtnode2.contains(main2));
    assertTrue(languageAtnode2.size() == 1);

    databaseManager.deleteLanguageServiceRequest(main1.getId());
    databaseManager.deleteLanguageServiceRequest(main2.getId());
  }

  @Test
  public void testGetAllLanguageRequests() throws Exception {
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);

    } catch (Exception e) {

    }
    LanguageServiceRequest main1 = validLanguageServiceRequest[0];
    LanguageServiceRequest main2 = validLanguageServiceRequest[1];
    main2.setId(new Date().getTime() + 50 + "");

    try {
      databaseManager.manipulateServiceRequest(main1);
      databaseManager.manipulateServiceRequest(main2);
      List<LanguageServiceRequest> languageAll = databaseManager.getAllLanguageServiceRequests();

      assertTrue(languageAll.contains(main1));
      assertTrue(languageAll.contains(main2));
      assertTrue(languageAll.size() == 2);

      databaseManager.deleteLanguageServiceRequest(main1.getId());
      databaseManager.deleteLanguageServiceRequest(main2.getId());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
