package edu.wpi.teamF.Controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import edu.wpi.teamF.App;
import edu.wpi.teamF.Test.TestData;
import edu.wpi.teamF.factories.DatabaseManager;
import edu.wpi.teamF.factories.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class MainMenuUITest extends FxRobot {
  private static DatabaseManager db = new DatabaseManager();
  Stage stage;

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    TestData testData = new TestData();
    NodeFactory nodes = NodeFactory.getFactory();
    for (Node node : testData.getValidNodes()) {
      nodes.create(node);
    }
  }

  @Start
  public void start(Stage stage) throws IOException {
    App.setPS(stage);
    this.stage = stage;
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamF/views/MainMenu.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setAlwaysOnTop(true);
    stage.show();
  }

  @AfterAll
  public static void tearDown() throws SQLException {
    db.reset();
  }

  @Test
  void testSceneSwitchingDisplay() {
    // when click
    clickOn("Display");

    verifyThat("#mainMenuButton", hasText("Main Menu"));
  }

  @Test
  void testSceneSwitchingPathfinder() {
    // when click
    clickOn("Pathfinder");

    verifyThat("#mainMenuButton", hasText("Main Menu"));
  }

  @Test
  void testSceneSwitchingDownload() {
    clickOn("Download");

    verifyThat("#mainMenuButton", hasText("Main Menu"));
  }

  @Test
  void testSceneSwitchingModify() {
    clickOn("Modify Data");

    verifyThat("#mainMenuButton", hasText("Main Menu"));
  }

  @Test
  void testSceneSwitchingMain() {

    verifyThat("#titleLabel", hasText("Welcome to the Amazing Pathfinder!"));
  }
}
