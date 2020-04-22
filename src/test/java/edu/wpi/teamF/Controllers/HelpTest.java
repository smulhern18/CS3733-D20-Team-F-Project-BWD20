package edu.wpi.teamF.Controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import java.io.IOException;
import java.sql.SQLException;
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

public class HelpTest extends ApplicationTest {

  private static DatabaseManager db = new DatabaseManager();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    ApplicationTest.launch(App.class);
  }

  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
    clickOn("Help");
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
  void testloginButton() throws InstanceNotFoundException {
    clickOn("#login");

    // check if the dialog is exist or not
    verifyThat("#loginButton", hasText(""));
  }

  @Test
  void testfindpathButton() throws InstanceNotFoundException {
    clickOn("#findpath");

    // check if the dialog is exist or not
    verifyThat("#findpathButton", hasText(""));
  }

  @Test
  void testrequestserviceButton() throws InstanceNotFoundException {
    clickOn("#requestservice");

    // check if the dialog is exist or not
    verifyThat("#requestserviceButton", hasText(""));
  }

  @Test
  void testrequestmainButton() throws InstanceNotFoundException {
    clickOn("#requestmain");

    // check if the dialog is exist or not
    verifyThat("#requestmain", hasText(""));
  }

  @Test
  void testaddnodesButton() throws InstanceNotFoundException {
    clickOn("#addnodes");

    // check if the dialog is exist or not
    verifyThat("#addnodes", hasText(""));
  }
}
