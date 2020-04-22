package edu.wpi.teamF.Controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.TestData;
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

public class MainMenuBarTest extends ApplicationTest {

  private static DatabaseManager db = new DatabaseManager();
  private static NodeFactory nodes = NodeFactory.getFactory();
  private static EdgeFactory edges = EdgeFactory.getFactory();
  private static AccountFactory accounts = AccountFactory.getFactory();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    TestData testData = new TestData();
    for (Node node : testData.validNodes) {
      nodes.create(node);
    }
    for (Edge edge : testData.validEdges) {
      edges.create(edge);
    }
    for (Account account : testData.validAccounts) {
      accounts.create(account);
    }
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
  void testPathfinderButton() throws InstanceNotFoundException {
    clickOn("Pathfinder");

    // check that the incorrect label is on the login screen
    verifyThat("#resetButton", hasText("Reset"));
  }

  @Test
  void testServiceRequestButton() throws InstanceNotFoundException {
    clickOn("Service Request");

    // check that the submit request button is on the page
    verifyThat("#submitRequestButton", hasText("Submit Request"));
  }

  @Test
  void testAdminButton() throws InstanceNotFoundException {
    clickOn("Admin");

    // check that the map view switcher is on the page
    verifyThat("#uploadNodesButton", hasText("Upload Nodes"));
  }

  @Test
  void testHelpButton() throws InstanceNotFoundException {
    clickOn("Help");

    // check that the delete node button is on the page
    verifyThat("#generalquestions", hasText("General Questions"));
  }

  @Test
  void testLoginButton() throws InstanceNotFoundException {
    clickOn("Login");

    // check that the incorrect label is on the login screen
    verifyThat("#loginText", hasText("Login"));
    clickOn("Main Menu");
  }
}
