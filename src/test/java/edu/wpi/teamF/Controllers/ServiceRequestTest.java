package edu.wpi.teamF.Controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.SecurityRequestFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import javax.management.InstanceNotFoundException;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class ServiceRequestTest extends ApplicationTest {

  private static DatabaseManager db = new DatabaseManager();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    db.reset();
    ApplicationTest.launch(App.class);
  }

  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    db.reset();
  }

  @After
  public void afterEachTest() throws TimeoutException {
    FxToolkit.hideStage();
  }

  @Test
  void securityTest() throws InstanceNotFoundException {

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
    SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
    List<SecurityRequest> requests = securityRequestFactory.getAllSecurityRequests();

    // check that a security request with that information is in the database
    Boolean isInDatabase = false;
    for (SecurityRequest sr : requests) {
      if (sr.getDescription().equals("out of control visitor")
          && sr.getLocation().getLongName().equals("Intensive Care Unit")) {
        isInDatabase = true;
      }
    }
    assertTrue(isInDatabase);
  }
}
