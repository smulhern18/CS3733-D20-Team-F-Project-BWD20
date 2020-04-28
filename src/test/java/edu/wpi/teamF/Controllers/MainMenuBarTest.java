// package edu.wpi.teamF.Controllers;
//
// import static org.testfx.api.FxAssert.verifyThat;
// import static org.testfx.matcher.control.LabeledMatchers.hasText;
//
// import edu.wpi.teamF.App;
// import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
// import java.io.IOException;
// import java.sql.SQLException;
// import java.util.concurrent.TimeoutException;
// import javafx.stage.Stage;
// import javax.management.InstanceNotFoundException;
// import org.junit.After;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.testfx.api.FxToolkit;
// import org.testfx.framework.junit5.ApplicationTest;
// import org.testfx.framework.junit5.Start;
//
// public class MainMenuBarTest extends ApplicationTest {
//
//  private static DatabaseManager db = DatabaseManager.getManager();
//
//  @BeforeAll
//  public static void setUp() throws Exception {
//    db.initialize();
//    ApplicationTest.launch(App.class);
//  }
//
//  @Start
//  public void start(Stage stage) throws IOException {
//    stage.show();
//  }
//
//  @AfterEach
//  public void tearDown() throws SQLException {
//    db.reset();
//  }
//
//  @After
//  public void afterEachTest() throws TimeoutException {
//    FxToolkit.hideStage();
//  }
//
//  //  @Test
//  //  void testPathfinderButton() throws InstanceNotFoundException {
//  //    clickOn("Pathfinder");
//  //
//  //    // check that the incorrect label is on the login screen
//  //    verifyThat("#incorrectLabel", hasText("The username or password is incorrect"));
//  //  }
//
//  @Test
//  void testServiceRequestButton() throws InstanceNotFoundException {
//    clickOn("Service Request");
//
//    // check that the submit request button is on the page
//    verifyThat("#submitRequestButton", hasText("Submit Request"));
//  }
//
//  @Test
//  void testAdminButton() throws InstanceNotFoundException {
//    clickOn("Admin");
//
//    // check that the map view switcher is on the page
//    // verifyThat("#uploadNodesButton", hasText("Upload Nodes"));
//  }
//
//  @Test
//  void testHelpButton() throws InstanceNotFoundException {
//    clickOn("Help");
//
//    // check that the delete node button is on the page
//    verifyThat("#generalquestions", hasText("General Questions"));
//  }
//
//  @Test
//  void testLoginButton() throws InstanceNotFoundException {
//    clickOn("Login");
//
//    // check that the incorrect label is on the login screen
//    verifyThat("#loginText", hasText("Login"));
//    clickOn("Main Menu");
//  }
// }
