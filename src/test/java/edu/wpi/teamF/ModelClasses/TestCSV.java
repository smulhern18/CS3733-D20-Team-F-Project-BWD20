package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.ServiceRequest.*;
import edu.wpi.teamF.TestData;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.*;

public class TestCSV {

  /** Utility classes instantiation */
  static DatabaseManager databaseManager = DatabaseManager.getManager();

  static CSVManipulator csvManipulator = new CSVManipulator();
  static NodeFactory nodeFactory = NodeFactory.getFactory();
  static TestData testData = null;

  Node[] validNodes = null;
  Edge[] validEdge = null;
  MaintenanceRequest[] validMaintenance = null;
  MariachiRequest[] validMariachiRequest = null;
  SecurityRequest[] validSecurityRequest = null;
  MedicineDeliveryRequest[] validMedicineDeliveryRquest = null;
  ComputerServiceRequest[] validComputerServiceRequest = null;
  LanguageServiceRequest[] validLanguageServiceRequest = null;
  LaundryServiceRequest[] validLaundryRequest = null;
  SanitationServiceRequest[] validSanitationRequest = null;
  TransportRequest[] validTransportRequest = null;
  FlowerRequest[] validFlowerRequest = null;
  Account[] validAccounts = null;
  HashSet<String> validNeighbors1 = null;

  @BeforeEach
  public void cleanTests() {
    try {

      testData = new TestData();
      validNodes = testData.validNodes;
      validEdge = testData.validEdges;
      validMaintenance = testData.validMaintenanceRequests;
      validMariachiRequest = testData.validMariachiRequests;
      validAccounts = testData.validAccounts;
      validSecurityRequest = testData.validSecurityRequests;
      validMedicineDeliveryRquest = testData.validMedicineDeliveryRequests;
      validComputerServiceRequest = testData.validComputerServiceRequests;
      validFlowerRequest = testData.validFlowerRequests;
      validLanguageServiceRequest = testData.validLanguageServiceRequests;
      validLaundryRequest = testData.validLaundryRequests;
      validSanitationRequest = testData.validSanitationRequests;
      validTransportRequest = testData.validTransportRequests;

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @BeforeEach
  public void initializeDatabase() {
    try {
      databaseManager.initialize();
      databaseManager.reset();
      testData = new TestData();
    } catch (SQLException e) {
      // Ignore
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVNodes() throws Exception {
    int i = 0;
    csvManipulator.readCSVFileNode(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVNodeTest.csv"));
    List<Node> list = databaseManager.getAllNodes();
    for (Node n : list) {

      Assertions.assertTrue(n.equals(validNodes[i]));
      i++;
    }

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MapFAllnodes.csv");
    csvManipulator.writeCSVFileNode(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVNodeTest.csv").toURI()).toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVEdges() throws Exception {
    Edge edge;
    int i = 0;

    csvManipulator.readCSVFileEdge(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVEdgeTest.csv"));
    try {
      List<Edge> list = databaseManager.getAllEdges();
      for (Edge e : list) {
        Assertions.assertTrue(e.equals(validEdge[i]));
        i++;
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MapFAlledges.csv");
    csvManipulator.writeCSVFileEdge(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVEdgeTest.csv").toURI()).toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVMaintenance() throws Exception {

    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileMaintenanceService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVMaintenanceRequestTest.csv"));

    List<MaintenanceRequest> list = databaseManager.getAllMaintenanceRequests();

    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validMaintenance[0]));
    Assertions.assertTrue(list.contains(validMaintenance[1]));
    Assertions.assertTrue(list.contains(validMaintenance[2]));
    Assertions.assertTrue(list.contains(validMaintenance[3]));
    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MaintenanceBackup.csv");
    csvManipulator.writeCSVFileMaintenanceService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(
                      getClass()
                          .getResource("/edu/wpi/teamF/CSVMaintenanceRequestTest.csv")
                          .toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVSecurity() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileSecurityService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVSecurityTest.csv"));

    List<SecurityRequest> list = databaseManager.getAllSecurityRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validSecurityRequest[0]));
    Assertions.assertTrue(list.contains(validSecurityRequest[1]));
    Assertions.assertTrue(list.contains(validSecurityRequest[2]));
    Assertions.assertTrue(list.contains(validSecurityRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/SecurityBackup.csv");
    csvManipulator.writeCSVFileSecurityService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVSecurityTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVAccount() throws Exception {
    int i = 0;

    csvManipulator.readCSVFileAccount(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVAccountTest.csv"));

    List<Account> list = databaseManager.getAllAccounts();
    for (Account a : list) {

      //  Assertions.assertTrue(a.getFirstName().equals(validAccounts[i].getFirstName()));
      i++;
    }

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/AccountBackup.csv");
    csvManipulator.writeCSVFileAccount(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVAccountTest.csv").toURI())
                  .toPath());
      // assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVMariachi() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileMariachiService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVMariachiTest.csv"));

    List<MariachiRequest> list = databaseManager.getAllMariachiServiceRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validMariachiRequest[0]));
    Assertions.assertTrue(list.contains(validMariachiRequest[1]));
    Assertions.assertTrue(list.contains(validMariachiRequest[2]));
    Assertions.assertTrue(list.contains(validMariachiRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MariachiBackup.csv");
    csvManipulator.writeCSVFileMariachiService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVMariachiTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVMedicine() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileMedicineDeliveryService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVMedicineTest.csv"));

    List<MedicineDeliveryRequest> list = databaseManager.getAllMedicineDeliveryRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validMedicineDeliveryRquest[0]));
    Assertions.assertTrue(list.contains(validMedicineDeliveryRquest[1]));
    Assertions.assertTrue(list.contains(validMedicineDeliveryRquest[2]));
    Assertions.assertTrue(list.contains(validMedicineDeliveryRquest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/MedicineBackup.csv");
    csvManipulator.writeCSVFileMedicineDeliveryService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVMedicineTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVComputerService() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileComputerService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVComputerServiceRequest.csv"));

    List<ComputerServiceRequest> list = databaseManager.getAllComputerServiceRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validComputerServiceRequest[0]));
    Assertions.assertTrue(list.contains(validComputerServiceRequest[1]));
    Assertions.assertTrue(list.contains(validComputerServiceRequest[2]));
    Assertions.assertTrue(list.contains(validComputerServiceRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/ComputerServiceBackup.csv");
    csvManipulator.writeCSVFileComputerService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(
                      getClass()
                          .getResource("/edu/wpi/teamF/CSVComputerServiceRequest.csv")
                          .toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVFlower() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileFlowerService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVFlowerTest.csv"));

    List<FlowerRequest> list = databaseManager.getAllFlowerRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validFlowerRequest[0]));
    Assertions.assertTrue(list.contains(validFlowerRequest[1]));
    Assertions.assertTrue(list.contains(validFlowerRequest[2]));
    Assertions.assertTrue(list.contains(validFlowerRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/FlowerBackup.csv");
    csvManipulator.writeCSVFileFlowerService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVFlowerTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVLanguage() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileLanguageService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVLanguageRequest.csv"));

    List<LanguageServiceRequest> list = databaseManager.getAllLanguageServiceRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validLanguageServiceRequest[0]));
    Assertions.assertTrue(list.contains(validLanguageServiceRequest[1]));
    Assertions.assertTrue(list.contains(validLanguageServiceRequest[2]));
    Assertions.assertTrue(list.contains(validLanguageServiceRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/LanguageBackup.csv");
    csvManipulator.writeCSVFileLanguageService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVLanguageRequest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVSanitation() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileSanitationService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVSanitationTest.csv"));

    List<SanitationServiceRequest> list = databaseManager.getAllSanitationRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validSanitationRequest[0]));
    Assertions.assertTrue(list.contains(validSanitationRequest[1]));
    Assertions.assertTrue(list.contains(validSanitationRequest[2]));
    Assertions.assertTrue(list.contains(validSanitationRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/SanitationBackup.csv");
    csvManipulator.writeCSVFileSanitationService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVSanitationTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVLaundry() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileLaundryService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVLaundryTest.csv"));

    List<LaundryServiceRequest> list = databaseManager.getAllLaunduaryRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validLaundryRequest[0]));
    Assertions.assertTrue(list.contains(validLaundryRequest[1]));
    Assertions.assertTrue(list.contains(validLaundryRequest[2]));
    Assertions.assertTrue(list.contains(validLaundryRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/LaundryBackup.csv");
    csvManipulator.writeCSVFileLaundryService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVLaundryTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAndWriteCSVTransport() throws Exception {
    int i = 0;
    try {
      databaseManager.manipulateNode(validNodes[0]);
      databaseManager.manipulateNode(validNodes[1]);
      databaseManager.manipulateNode(validNodes[2]);
      databaseManager.manipulateNode(validNodes[3]);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
    csvManipulator.readCSVFileTransportService(
        getClass().getResourceAsStream("/edu/wpi/teamF/CSVTransportTest.csv"));

    List<TransportRequest> list = databaseManager.getAllTransportRequests();
    int j = 0;
    Assertions.assertTrue(list.size() == 4);
    Assertions.assertTrue(list.contains(validTransportRequest[0]));
    Assertions.assertTrue(list.contains(validTransportRequest[1]));
    Assertions.assertTrue(list.contains(validTransportRequest[2]));
    Assertions.assertTrue(list.contains(validTransportRequest[3]));

    /** Valid data */
    File wfile = new File("src/test/java/edu/wpi/teamF/Test/");
    File file = new File("src/test/java/edu/wpi/teamF/Test/TransportBackup.csv");
    csvManipulator.writeCSVFileTransportService(wfile.toPath());
    try {
      byte[] f1 = Files.readAllBytes(file.toPath());
      byte[] f2 =
          Files.readAllBytes(
              new File(getClass().getResource("/edu/wpi/teamF/CSVTransportTest.csv").toURI())
                  .toPath());
      assertTrue(Arrays.equals(f1, f2));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadAllDataIn() throws Exception {

    File file = new File("src/test/java/edu/wpi/teamF/Test/");
    csvManipulator.uploadDatabase(file.toPath());
    int i = 0;
    List<Node> list = databaseManager.getAllNodes();
    for (Node n : list) {

      Assertions.assertTrue(n.equals(validNodes[i]));
      i++;
    }
    i = 0;
    try {
      List<Edge> list1 = databaseManager.getAllEdges();
      for (Edge e : list1) {
        Assertions.assertTrue(e.equals(validEdge[i]));
        i++;
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
    List<TransportRequest> list2 = databaseManager.getAllTransportRequests();

    Assertions.assertTrue(list2.size() == 4);
    Assertions.assertTrue(list2.contains(validTransportRequest[0]));
    Assertions.assertTrue(list2.contains(validTransportRequest[1]));
    Assertions.assertTrue(list2.contains(validTransportRequest[2]));
    Assertions.assertTrue(list2.contains(validTransportRequest[3]));

    List<LaundryServiceRequest> list3 = databaseManager.getAllLaunduaryRequests();

    Assertions.assertTrue(list3.size() == 4);
    Assertions.assertTrue(list3.contains(validLaundryRequest[0]));
    Assertions.assertTrue(list3.contains(validLaundryRequest[1]));
    Assertions.assertTrue(list3.contains(validLaundryRequest[2]));
    Assertions.assertTrue(list3.contains(validLaundryRequest[3]));

    List<SanitationServiceRequest> list4 = databaseManager.getAllSanitationRequests();

    Assertions.assertTrue(list4.size() == 4);
    Assertions.assertTrue(list4.contains(validSanitationRequest[0]));
    Assertions.assertTrue(list4.contains(validSanitationRequest[1]));
    Assertions.assertTrue(list4.contains(validSanitationRequest[2]));
    Assertions.assertTrue(list4.contains(validSanitationRequest[3]));

    List<LanguageServiceRequest> list5 = databaseManager.getAllLanguageServiceRequests();

    Assertions.assertTrue(list5.size() == 4);
    Assertions.assertTrue(list5.contains(validLanguageServiceRequest[0]));
    Assertions.assertTrue(list5.contains(validLanguageServiceRequest[1]));
    Assertions.assertTrue(list5.contains(validLanguageServiceRequest[2]));
    Assertions.assertTrue(list5.contains(validLanguageServiceRequest[3]));

    List<LanguageServiceRequest> list6 = databaseManager.getAllLanguageServiceRequests();

    Assertions.assertTrue(list6.size() == 4);
    Assertions.assertTrue(list6.contains(validLanguageServiceRequest[0]));
    Assertions.assertTrue(list6.contains(validLanguageServiceRequest[1]));
    Assertions.assertTrue(list6.contains(validLanguageServiceRequest[2]));
    Assertions.assertTrue(list6.contains(validLanguageServiceRequest[3]));

    List<FlowerRequest> list7 = databaseManager.getAllFlowerRequests();

    Assertions.assertTrue(list7.size() == 4);
    Assertions.assertTrue(list7.contains(validFlowerRequest[0]));
    Assertions.assertTrue(list7.contains(validFlowerRequest[1]));
    Assertions.assertTrue(list7.contains(validFlowerRequest[2]));
    Assertions.assertTrue(list7.contains(validFlowerRequest[3]));

    List<ComputerServiceRequest> list8 = databaseManager.getAllComputerServiceRequests();

    Assertions.assertTrue(list8.size() == 4);
    Assertions.assertTrue(list8.contains(validComputerServiceRequest[0]));
    Assertions.assertTrue(list8.contains(validComputerServiceRequest[1]));
    Assertions.assertTrue(list8.contains(validComputerServiceRequest[2]));
    Assertions.assertTrue(list8.contains(validComputerServiceRequest[3]));

    List<MedicineDeliveryRequest> list9 = databaseManager.getAllMedicineDeliveryRequests();

    Assertions.assertTrue(list9.size() == 4);
    Assertions.assertTrue(list9.contains(validMedicineDeliveryRquest[0]));
    Assertions.assertTrue(list9.contains(validMedicineDeliveryRquest[1]));
    Assertions.assertTrue(list9.contains(validMedicineDeliveryRquest[2]));
    Assertions.assertTrue(list9.contains(validMedicineDeliveryRquest[3]));

    List<MariachiRequest> list10 = databaseManager.getAllMariachiServiceRequests();

    Assertions.assertTrue(list10.size() == 4);
    Assertions.assertTrue(list10.contains(validMariachiRequest[0]));
    Assertions.assertTrue(list10.contains(validMariachiRequest[1]));
    Assertions.assertTrue(list10.contains(validMariachiRequest[2]));
    Assertions.assertTrue(list10.contains(validMariachiRequest[3]));

    List<SecurityRequest> list11 = databaseManager.getAllSecurityRequests();
    int j = 0;
    Assertions.assertTrue(list11.size() == 4);
    Assertions.assertTrue(list11.contains(validSecurityRequest[0]));
    Assertions.assertTrue(list11.contains(validSecurityRequest[1]));
    Assertions.assertTrue(list11.contains(validSecurityRequest[2]));
    Assertions.assertTrue(list11.contains(validSecurityRequest[3]));

    List<MaintenanceRequest> list12 = databaseManager.getAllMaintenanceRequests();

    Assertions.assertTrue(list12.size() == 4);
    Assertions.assertTrue(list12.contains(validMaintenance[0]));
    Assertions.assertTrue(list12.contains(validMaintenance[1]));
    Assertions.assertTrue(list12.contains(validMaintenance[2]));
    Assertions.assertTrue(list12.contains(validMaintenance[3]));
  }
}
