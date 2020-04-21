package edu.wpi.teamF.Controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
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

public class LoginTest extends ApplicationTest {

  private static DatabaseManager db = new DatabaseManager();
  private static AccountFactory accountFactory = AccountFactory.getFactory();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    TestData testData = new TestData();
    for (Account account : testData.validAccounts) {
      accountFactory.create(account);
    }
    ApplicationTest.launch(App.class);
  }

  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
    clickOn("Login");
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
  void testAccountNotValid() throws InstanceNotFoundException {
    System.out.println("Starting testAccountNotValid test");

    // Login Page
    clickOn("Login");

    // Username:
    clickOn("#usernameInput");
    write("Tyler");

    // Password:
    clickOn("#passwordInput");
    write("Jones");

    // Attempt login
    clickOn("#loginButton");

    // Verify that the user remains on the login page
    verifyThat("#orLogin", hasText("or"));
  }
}
