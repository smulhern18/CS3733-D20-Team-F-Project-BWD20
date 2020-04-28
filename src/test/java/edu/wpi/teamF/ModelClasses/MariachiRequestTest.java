// package edu.wpi.teamF.ModelClasses;
//
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.fail;
//
// import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
// import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
// =======
// import edu.wpi.teamF.DatabaseManipulators.MariachiRequestFactory;
// import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
// import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
// import edu.wpi.teamF.TestData;
// import java.sql.SQLException;
// import java.util.List;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// public class MariachiRequestTest {
//
//  static TestData testData = null;
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//  static SecurityRequest[] validSecurityRequest = null;
//  static DatabaseManager databaseManager = DatabaseManager.getManager();
// =======
//  static MariachiRequest[] validMariachiRequest = null;
//  MariachiRequestFactory mariachiRequestFactory = MariachiRequestFactory.getFactory();
//  NodeFactory nodeFactory = NodeFactory.getFactory();
//  static DatabaseManager databaseManager = new DatabaseManager();
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//  static Node[] validNodes = null;
//
//  @BeforeAll
//  public static void databaseIntialize() throws Exception {
//    databaseManager.initialize();
//  }
//
//  @BeforeEach
//  public void initialize() throws Exception {
//    testData = new TestData();
//    validMariachiRequest = testData.validMariachiRequests;
//    validNodes = testData.validNodes;
//    databaseManager.reset();
//  }
//
//  @AfterAll
//  public static void reset() throws SQLException {
//    databaseManager.reset();
//  }
//
//  @Test
//  public void testCreateReadDelete() throws Exception {
//    try {
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//      databaseManager.manipulateServiceRequest((SecurityRequest) null);
// =======
//      mariachiRequestFactory.create(null);
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//      fail("Creating a null value is unacceptable");
//    } catch (NullPointerException e) {
//      // ignore as expected
//    }
//    try {
//      databaseManager.manipulateNode(validNodes[0]);
//      databaseManager.manipulateNode(validNodes[1]);
//      databaseManager.manipulateNode(validNodes[2]);
//      databaseManager.manipulateNode(validNodes[3]);
//
//    } catch (Exception e) {
//
//    }
//    try {
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//      for (SecurityRequest securityRequest : validSecurityRequest) {
//        databaseManager.manipulateServiceRequest(securityRequest);
//
//        SecurityRequest readSecurity =
// databaseManager.readSecurityRequest(securityRequest.getId());
//        assertTrue(readSecurity.equals(securityRequest));
//
//        databaseManager.deleteSecurityRequest(securityRequest.getId());
//
//        try {
//          readSecurity = databaseManager.readSecurityRequest(securityRequest.getId());
// =======
//      for (MariachiRequest mariachiRequest : validMariachiRequest) {
//        mariachiRequestFactory.create(mariachiRequest);
//
//        MariachiRequest readSecurity = mariachiRequestFactory.read(mariachiRequest.getId());
//        assertTrue(readSecurity.equals(mariachiRequest));
//
//        mariachiRequestFactory.delete(mariachiRequest.getId());
//
//        try {
//          readSecurity = mariachiRequestFactory.read(mariachiRequest.getId());
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//        } // catch (InstanceNotFoundException e) {
//        // ignore
//        // }
//        catch (Exception e) {
//          fail(e.getMessage() + ", " + e.getClass());
//        }
//      }
//    } catch (Exception e) {
//      fail(e.getMessage() + ", " + e.getClass());
//    }
//  }
//
//  @Test
//  public void testCreateReadUpdateDelete() {
//
//    try {
//      databaseManager.manipulateNode(validNodes[0]);
//      databaseManager.manipulateNode(validNodes[1]);
//      databaseManager.manipulateNode(validNodes[2]);
//      databaseManager.manipulateNode(validNodes[3]);
//
//    } catch (Exception e) {
//
//    }
//    try {
//
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//      for (SecurityRequest securityRequest : validSecurityRequest) {
//        databaseManager.manipulateServiceRequest(securityRequest);
//
//        securityRequest.setDescription("Hello");
//        databaseManager.manipulateServiceRequest(securityRequest);
//
//        SecurityRequest readMain = databaseManager.readSecurityRequest(securityRequest.getId());
// =======
//      for (MariachiRequest mariachiRequest : validMariachiRequest) {
//        mariachiRequestFactory.create(mariachiRequest);
//
//        mariachiRequest.setDescription("Hello");
//        mariachiRequestFactory.update(mariachiRequest);
//
//        MariachiRequest readMain = mariachiRequestFactory.read(mariachiRequest.getId());
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//
//        assertTrue(mariachiRequest.equals(readMain));
//
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//        databaseManager.deleteSecurityRequest(securityRequest.getId());
// =======
//        mariachiRequestFactory.delete(mariachiRequest.getId());
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//      }
//    } catch (Exception e) {
//      fail(e.getMessage() + ", " + e.getClass());
//    }
//  }
//
//  @Test
//  public void testGetSecurityByLocation() {
//    try {
//      databaseManager.manipulateNode(validNodes[0]);
//      databaseManager.manipulateNode(validNodes[1]);
//      databaseManager.manipulateNode(validNodes[2]);
//      databaseManager.manipulateNode(validNodes[3]);
//
//    } catch (Exception e) {
//
//    }
//    MariachiRequest main1 = validMariachiRequest[0];
//    MariachiRequest main2 = validMariachiRequest[1];
//    MariachiRequest main3 = validMariachiRequest[2];
//    MariachiRequest main4 = validMariachiRequest[3];
//
//    try {
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//      databaseManager.manipulateServiceRequest(main1);
//      databaseManager.manipulateServiceRequest(main2);
//      databaseManager.manipulateServiceRequest(main3);
//      databaseManager.manipulateServiceRequest(main4);
//
//      List<SecurityRequest> securityAtBathroom =
//          databaseManager.getSecurityRequestsByLocation(testData.validNodes[0]);
// =======
//      mariachiRequestFactory.create(main1);
//      mariachiRequestFactory.create(main2);
//      mariachiRequestFactory.create(main3);
//      mariachiRequestFactory.create(main4);
//
//      List<MariachiRequest> mariachiAtBathroom =
//          mariachiRequestFactory.getSecurityRequestsByLocation(testData.validNodes[0]);
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//
//      assertTrue(mariachiAtBathroom.contains(main1));
//
//      assertTrue(mariachiAtBathroom.size() == 1);
//
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//      List<SecurityRequest> securityAtnode2 =
//          databaseManager.getSecurityRequestsByLocation(testData.validNodes[1]);
// =======
//      List<MariachiRequest> mariachiAtnode2 =
//          mariachiRequestFactory.getSecurityRequestsByLocation(testData.validNodes[1]);
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//
//      assertTrue(mariachiAtnode2.contains(main2));
//      assertTrue(mariachiAtnode2.size() == 1);
//
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//      databaseManager.deleteSecurityRequest(main1.getId());
//      databaseManager.deleteSecurityRequest(main2.getId());
//      databaseManager.deleteSecurityRequest(main3.getId());
//      databaseManager.deleteSecurityRequest(main4.getId());
// =======
//      mariachiRequestFactory.delete(main1.getId());
//      mariachiRequestFactory.delete(main2.getId());
//      mariachiRequestFactory.delete(main3.getId());
//      mariachiRequestFactory.delete(main4.getId());
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//    } catch (Exception e) {
//      fail(e.getMessage() + ", " + e.getClass());
//    }
//  }
//
//  @Test
//  public void testGetAllSecurityRequests() {
//    try {
//      databaseManager.manipulateNode(validNodes[0]);
//      databaseManager.manipulateNode(validNodes[1]);
//      databaseManager.manipulateNode(validNodes[2]);
//      databaseManager.manipulateNode(validNodes[3]);
//
//    } catch (Exception e) {
//
//    }
//    MariachiRequest main1 = validMariachiRequest[0];
//    MariachiRequest main2 = validMariachiRequest[1];
//    MariachiRequest main3 = validMariachiRequest[2];
//    MariachiRequest main4 = validMariachiRequest[3];
//
//    try {
// <<<<<<< HEAD:src/test/java/edu/wpi/teamF/ModelClasses/SecurityRequestTest.java
//      databaseManager.manipulateServiceRequest(main1);
//      databaseManager.manipulateServiceRequest(main2);
//      databaseManager.manipulateServiceRequest(main3);
//      databaseManager.manipulateServiceRequest(main4);
//
//      List<SecurityRequest> securityAll = databaseManager.getAllSecurityRequests();
//
//      assertTrue(securityAll.contains(main1));
//      assertTrue(securityAll.contains(main2));
//      assertTrue(securityAll.contains(main3));
//      assertTrue(securityAll.contains(main4));
//      assertTrue(securityAll.size() == 4);
// =======
//      mariachiRequestFactory.create(main1);
//      mariachiRequestFactory.create(main2);
//      mariachiRequestFactory.create(main3);
//      mariachiRequestFactory.create(main4);
//
//      List<MariachiRequest> mariachiAll = mariachiRequestFactory.getAllMariachiRequest();
//
//      assertTrue(mariachiAll.contains(main1));
//      assertTrue(mariachiAll.contains(main2));
//      assertTrue(mariachiAll.contains(main3));
//      assertTrue(mariachiAll.contains(main4));
//      assertTrue(mariachiAll.size() == 4);
// >>>>>>>
// origin/KevinMariachiRequest:src/test/java/edu/wpi/teamF/ModelClasses/MariachiRequestTest.java
//
//      databaseManager.deleteSecurityRequest(main1.getId());
//      databaseManager.deleteSecurityRequest(main2.getId());
//      databaseManager.deleteSecurityRequest(main3.getId());
//      databaseManager.deleteSecurityRequest(main4.getId());
//    } catch (Exception e) {
//      fail(e.getMessage() + ", " + e.getClass());
//    }
//  }
// }
