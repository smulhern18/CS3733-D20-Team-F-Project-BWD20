package edu.wpi.teamF.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.TestData;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import javax.management.InstanceNotFoundException;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class RegisterTest extends ApplicationTest {
  private static DatabaseManager db = new DatabaseManager();
  private static AccountFactory accountFactory = AccountFactory.getFactory();

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    db.reset();
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

  @Test
  void testRegisterValidAccount() throws InstanceNotFoundException {
    // Login Page
    clickOn("Login");
    // Register Page
    clickOn("Register");

    clickOn("First Name");
    write("Tyler");

    clickOn("Last Name");
    write("Jones");

    clickOn("Email Address");
    write("twjones@wpi.edu");

    clickOn("Username");
    write("tyler");

    clickOn("Password");
    write("jonessss");

    clickOn("#registerButton");

    assertEquals(accountFactory.read("tyler").getLastName(), "Jones");
  }

  @Test
  void testRegisterInvalidPasswordAccount() throws InstanceNotFoundException {
    // Login Page
    clickOn("Login");
    // Register Page
    clickOn("Register");

    clickOn("First Name");
    write("Tyler");

    clickOn("Last Name");
    write("Jones");

    clickOn("Email Address");
    write("twjones@wpi.edu");

    clickOn("Username");
    write("tyler");

    clickOn("Password");
    write("jones");

    clickOn("#registerButton");

    verifyThat("#incorrectLabel", hasText("The password needs to contain atleast 8 characters"));
  }

  @Test
  void testRegisterInvalidEmailAccount() throws InstanceNotFoundException {
    // Login Page
    clickOn("Login");
    // Register Page
    clickOn("Register");

    clickOn("First Name");
    write("Tyler");

    clickOn("Last Name");
    write("Jones");

    clickOn("Email Address");
    write("twjones");

    clickOn("Username");
    write("tyler");

    clickOn("Password");
    write("jonessss");

    clickOn("#registerButton");

    verifyThat("#incorrectLabel", hasText("The email is not valid"));
  }
}
