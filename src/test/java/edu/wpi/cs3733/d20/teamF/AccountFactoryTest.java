package edu.wpi.cs3733.d20.teamF;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.cs3733.d20.teamF.ModelClasses.Account.Account;
import java.sql.SQLException;
import javax.management.InstanceNotFoundException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountFactoryTest {

  static TestData testData = null;
  static Account[] validAccounts = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validAccounts = testData.validAccounts;
    databaseManager.initialize();
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() throws Exception {
    try {
      databaseManager.manipulateAccount(null);
      fail("Creating a null value is unacceptable");
    } catch (NullPointerException e) {
      // ignore as expected
    }
    try {
      for (Account account : validAccounts) {
        databaseManager.manipulateAccount(account);

        Account readAccount = databaseManager.readAccount(account.getUsername());

        assertTrue(readAccount.equals(account));

        databaseManager.deleteAccount(account.getUsername());

        try {
          readAccount = databaseManager.readAccount(account.getUsername());
        } catch (InstanceNotFoundException e) {
          // ignore
        } catch (Exception e) {
          fail(e.getMessage() + ", " + e.getClass());
        }
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testCreateReadUpdateDelete() {
    try {
      for (Account account : validAccounts) {
        databaseManager.manipulateAccount(account);

        account.setEmailAddress("bencraft@gmail.com");
        databaseManager.manipulateAccount(account);

        Account readAccount = databaseManager.readAccount(account.getUsername());

        assertTrue(account.equals(readAccount));

        databaseManager.deleteAccount(account.getUsername());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testGetPasswordFromUsername() {
    Account account1 = validAccounts[0];
    String password = testData.validPasswords[0];
    try {
      databaseManager.manipulateAccount(account1);
      assertTrue(databaseManager.verifyPassword(account1.getUsername(), password));
      databaseManager.deleteAccount(account1.getUsername());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
