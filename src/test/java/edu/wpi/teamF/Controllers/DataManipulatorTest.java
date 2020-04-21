package edu.wpi.teamF.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.TestData;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javax.management.InstanceNotFoundException;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class DataManipulatorTest extends ApplicationTest {

  private static DatabaseManager db = new DatabaseManager();
  private static NodeFactory nodes = NodeFactory.getFactory();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    TestData testData = new TestData();
    for (Node node : testData.validNodesWithEdges) {
      nodes.create(node);
    }
    ApplicationTest.launch(App.class);
  }

  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
    clickOn("Admin");
  }

  @After
  public void afterEachTest() throws TimeoutException {
    FxToolkit.hideStage();
  }

  @AfterEach
  public static void tearDown() throws SQLException {
    db.reset();
  }

  @Test
  void testNodeSearch() throws InstanceNotFoundException {
    clickOn("#filterTextFieldNodes");

    // Nodes to edit
    write("LSTAF00402");
    clickOn("1000");
    write("1200");
    press(KeyCode.ENTER);
    clickOn("Update Nodes");
    // change a value

    // click on update nodes
    // verify that new data is there -> check in database that that value is updated
    short xCoord = nodes.read("LSTAF00402").getXCoord();
    assertEquals(xCoord, (short) 1000);
  }

  @Test
  void testUserAccounts() {
    // when click
    clickOn("User Accounts");

    verifyThat("#updateStaff", hasText("Update Staff"));
  }
}
