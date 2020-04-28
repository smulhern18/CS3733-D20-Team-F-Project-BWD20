package edu.wpi.teamF.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.TestData;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javax.management.InstanceNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class DataManipulatorTest extends ApplicationTest {

  private static DatabaseManager db = DatabaseManager.getManager();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    TestData testData = new TestData();
    for (Node node : testData.validNodes) {
      db.manipulateNode(node);
    }
    for (Edge edge : testData.validEdges) {
      db.manipulateEdge(edge);
    }
    for (Account account : testData.validAccounts) {
      db.manipulateAccount(account);
    }
    ApplicationTest.launch(App.class);
  }

  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
    clickOn("Admin");
  }

  @AfterAll
  static void tearDown() throws SQLException {
    db.reset();
  }

  @Test
  void testNodeSearch() throws Exception {

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

  // Table view tests Nodes

  @Test
  void testCreateNode() throws Exception {
    clickOn("#addNodePaneButton");
    clickOn("#nodeIDInput");
    write("NODETEST");
    clickOn("#xCoorInput");
    write("1000");
    clickOn("#yCoorInput");
    write("1000");
    clickOn("#typeInput");
    write("HALL");
    clickOn("#shortNameInput");
    write("TEST");
    clickOn("#longNameInput");
    write("TEST");
    clickOn("#addNodeButton");
    String ID = db.readNode("NODETEST").getId();
    assertEquals("NODETEST", ID);
  }

  // updatetest
  @Test
  void testUpdateNode() throws Exception {
    clickOn("#filterTextFieldNodes");
    write("nodeA");
    sleep(1000);
    clickOn("10");
    write("1200");
    press(KeyCode.ENTER);
    clickOn("#updateNodesButton");

    short xCoord = db.readNode("nodeA").getXCoord();
    assertEquals((short) 1200, xCoord);
  }

  // deletenode
  @Test
  void testDeleteNode() throws Exception {
    clickOn("#nodeToDelete");
    write("nodeB");
    clickOn("#deleteNodeButton");

    assertNull(db.readNode("nodeB"));
  }

  // Table view test Edges
  @Test
  void testEdgeSearch() {
    clickOn("#filterTextFieldEdges");
    write("nodeA_nodeH");
  }

  @Test
  void testEdgeUpdate() throws Exception {
    clickOn("#filterTextFieldEdges");
    write("nodeT_nodeT");
    sleep(500);
    doubleClickOn("nodeT");
    write("nodeC");
    press(KeyCode.ENTER);
    clickOn("#updateEdgesButton");

    assertEquals(db.readEdge("nodeT_nodeT").getNode1(), "nodeC");
  }

  @Test
  void testEdgeAdd() {}

  @Test
  void testEdgeDelete() {}

  // Map view tests

  @Test
  void testUserAccounts() throws InstanceNotFoundException {
    // when click
    clickOn("User Accounts");
  }
}
