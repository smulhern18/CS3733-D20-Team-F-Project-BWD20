package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.PasswordHasher;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import javax.management.InstanceNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountFactoryTest {

  static TestData testData = null;
  static Account[] validAccounts = null;
  AccountFactory accountFactory = AccountFactory.getFactory();
  static DatabaseManager databaseManager = new DatabaseManager();

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
  public void testCreateReadDelete() {
    try {
      accountFactory.create(null);
      fail("Creating a null value is unacceptable");
    } catch (ValidationException e) {
      // ignore as expected
    }
    try {
      for (Account account : validAccounts) {
        accountFactory.create(account);

        Account readAccount = accountFactory.read(account.getUsername());

        assertTrue(readAccount.equals(account));

        accountFactory.delete(account.getUsername());

        try {
          readAccount = accountFactory.read(account.getUsername());
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
        accountFactory.create(account);

        account.setEmailAddress("bencraft@gmail.com");
        accountFactory.update(account);

        Account readAccount = accountFactory.read(account.getUsername());

        assertTrue(account.equals(readAccount));

        accountFactory.delete(account.getUsername());
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
      accountFactory.create(account1);
      assertTrue(
          PasswordHasher.verifyPassword(
              password, accountFactory.getPasswordByUsername(account1.getUsername())));
      accountFactory.delete(account1.getUsername());
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
