package edu.wpi.teamF.Controllers;

import static org.testng.AssertJUnit.assertEquals;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.*;
import edu.wpi.teamF.Main;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class ServiceRequestTest extends ApplicationTest {

  private static DatabaseManager db = DatabaseManager.getManager();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    db.reset();
    db.readNodes(Main.class.getResourceAsStream("CSVFiles/MapFAllnodes.csv"));
    db.readEdges(Main.class.getResourceAsStream("CSVFiles/MapFAlledges.csv"));
    ApplicationTest.launch(App.class);
  }

  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
    clickOn("Service Request");
  }

  @AfterEach
  public void tearDown() throws SQLException {}

  @After
  public void afterEachTest() throws TimeoutException {
    FxToolkit.hideStage();
  }

  @Test
  void securityTest() throws Exception {

    clickOn("#choiceBoxLoc");
    clickOn("Intensive Care Unit");
    clickOn("#textAreaDesc");
    write("out of control visitor");
    clickOn("#choiceBoxPriority");
    clickOn("2");
    clickOn("#choiceBoxType");
    clickOn("Security");
    clickOn("Submit Request");

    // get access to all the security requests in the database
    List<SecurityRequest> requests = db.getAllSecurityRequests();

    // check that a security request with that information is in the database
    assertEquals(1, requests.size());
    assertEquals(2, requests.get(0).getPriority());
    assertEquals("FDEPT00105", requests.get(0).getLocation().getId());
  }

  @Test
  void maintenanceTest() throws Exception {

    clickOn("#choiceBoxLoc");
    clickOn("Suite 51");
    clickOn("#textAreaDesc");
    write("NOT ASSIGNED");
    clickOn("#choiceBoxPriority");
    clickOn("3");
    clickOn("#choiceBoxType");
    clickOn("Maintenance");
    clickOn("Submit Request");

    // get access to all the security requests in the database
    List<MaintenanceRequest> requests = db.getAllMaintenanceRequests();

    // check that a security request with that information is in the database
    assertEquals(1, requests.size());
    assertEquals(3, requests.get(0).getPriority());
    assertEquals("FSERV00405", requests.get(0).getLocation().getId());
  }
}
