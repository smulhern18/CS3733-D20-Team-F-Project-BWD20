package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
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
  private static EdgeFactory edges = EdgeFactory.getFactory();
  private static AccountFactory accounts = AccountFactory.getFactory();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    TestData testData = new TestData();
    for (Node node : testData.validNodes) {
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
  public void tearDown() throws SQLException {
    db.reset();
  }

  @Test
  void testNodeSearch() throws InstanceNotFoundException {

    // Nodes to edit
    clickOn("#filterTextFieldNodes");
    write("nodeA");
    press(KeyCode.ENTER);

    clickOn("#filterTextFieldNodes");
    write("nodeB");
    press(KeyCode.ENTER);

    clickOn("#filterTextFieldNodes");
    write("nodeC");
    press(KeyCode.ENTER);
  }

  @Test
  void testUserAccounts() throws InstanceNotFoundException {
    // when click
    clickOn("User Accounts");
  }
}
