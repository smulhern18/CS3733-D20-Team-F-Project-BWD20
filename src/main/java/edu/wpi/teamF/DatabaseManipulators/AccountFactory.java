package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.Admin;
import edu.wpi.teamF.ModelClasses.Account.Staff;
import edu.wpi.teamF.ModelClasses.Account.User;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.management.InstanceNotFoundException;

public class AccountFactory {

  private static final AccountFactory factory = new AccountFactory();

  public static AccountFactory getFactory() {
    return factory;
  }

  public void create(Account account) throws ValidationException {
    Validators.accountValidation(account);
    String createStatement =
        "INSERT INTO "
            + DatabaseManager.ACCOUNT_TABLE_NAME
            + " ( "
            + DatabaseManager.USER_NAME_KEY
            + ", "
            + DatabaseManager.PASSWORD_KEY
            + ", "
            + DatabaseManager.LAST_NAME_KEY
            + ", "
            + DatabaseManager.FIRST_NAME_KEY
            + ", "
            + DatabaseManager.EMAIL_ADDRESS_KEY
            + ", "
            + DatabaseManager.USER_TYPE_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(createStatement)) {
      int param = 1;
      preparedStatement.setString(param++, account.getUsername());
      preparedStatement.setString(param++, account.getPassword());
      preparedStatement.setString(param++, account.getLastName());
      preparedStatement.setString(param++, account.getFirstName());
      preparedStatement.setString(param++, account.getEmailAddress());
      preparedStatement.setInt(param++, account.getType().getTypeOrdinal());

      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e.getMessage() + ", " + e.getClass());
    }
  }

  public Account read(String username) throws InstanceNotFoundException {
    Account account = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.ACCOUNT_TABLE_NAME
            + " WHERE "
            + DatabaseManager.USER_NAME_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, username);

      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
      Account.Type type = Account.Type.getEnum(resultSet.getInt(DatabaseManager.USER_TYPE_KEY));
      switch (type.getTypeOrdinal()) {
        case (0):
          account =
              new Admin(
                  resultSet.getString(DatabaseManager.FIRST_NAME_KEY),
                  resultSet.getString(DatabaseManager.LAST_NAME_KEY),
                  resultSet.getString(DatabaseManager.EMAIL_ADDRESS_KEY),
                  resultSet.getString(DatabaseManager.USER_NAME_KEY),
                  resultSet.getString(DatabaseManager.PASSWORD_KEY));
          break;
        case (1):
          account =
              new Staff(
                  resultSet.getString(DatabaseManager.FIRST_NAME_KEY),
                  resultSet.getString(DatabaseManager.LAST_NAME_KEY),
                  resultSet.getString(DatabaseManager.EMAIL_ADDRESS_KEY),
                  resultSet.getString(DatabaseManager.USER_NAME_KEY),
                  resultSet.getString(DatabaseManager.PASSWORD_KEY));
          break;
        case (2):
          account =
              new User(
                  resultSet.getString(DatabaseManager.FIRST_NAME_KEY),
                  resultSet.getString(DatabaseManager.LAST_NAME_KEY),
                  resultSet.getString(DatabaseManager.EMAIL_ADDRESS_KEY),
                  resultSet.getString(DatabaseManager.USER_NAME_KEY),
                  resultSet.getString(DatabaseManager.PASSWORD_KEY));
          break;
        default:
          throw new ValidationException("Illegal Type of Account: " + type.getTypeOrdinal());
      }
    } catch (InstanceNotFoundException e) {
      throw e;
    } catch (Exception e) {
      System.out.println(e.getMessage() + ", " + e.getClass());
    }
    return account;
  }

  public void update(Account account) throws ValidationException {
    Validators.accountValidation(account);
    String updateStatement =
        "UPDATE "
            + DatabaseManager.ACCOUNT_TABLE_NAME
            + " SET "
            + DatabaseManager.USER_NAME_KEY
            + " = ?, "
            + DatabaseManager.PASSWORD_KEY
            + " = ?, "
            + DatabaseManager.FIRST_NAME_KEY
            + " = ?, "
            + DatabaseManager.LAST_NAME_KEY
            + " = ?, "
            + DatabaseManager.EMAIL_ADDRESS_KEY
            + " = ?, "
            + DatabaseManager.USER_TYPE_KEY
            + " = ?"
            + " WHERE "
            + DatabaseManager.USER_NAME_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      preparedStatement.setString(param++, account.getUsername());
      preparedStatement.setString(param++, account.getPassword());
      preparedStatement.setString(param++, account.getFirstName());
      preparedStatement.setString(param++, account.getLastName());
      preparedStatement.setString(param++, account.getEmailAddress());
      preparedStatement.setInt(param++, account.getType().getTypeOrdinal());
      preparedStatement.setString(param++, account.getUsername());

      int numRows = preparedStatement.executeUpdate();
      if (numRows > 1) {
        throw new Exception(
            "We done fucked up, theres two of them in the database: " + account.getUsername());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage() + ", " + e.getClass());
    }
  }

  public void delete(String username) {
    String deleteStatement =
        "DELETE FROM "
            + DatabaseManager.ACCOUNT_TABLE_NAME
            + " WHERE "
            + DatabaseManager.USER_NAME_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(deleteStatement)) {
      preparedStatement.setString(1, username);

      int numRows = preparedStatement.executeUpdate();
      if (numRows != 1) {
        throw new SQLException("Rut Roh Raggy, we deleted " + numRows + " Rows!");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage() + ", " + e.getClass());
    }
  }

  public String getPasswordByUsername(String username) {
    String hashword = null;
    String selectStatement =
        "SELECT "
            + DatabaseManager.PASSWORD_KEY
            + " FROM "
            + DatabaseManager.ACCOUNT_TABLE_NAME
            + " WHERE "
            + DatabaseManager.USER_NAME_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, username);

      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
      hashword = resultSet.getString(DatabaseManager.PASSWORD_KEY);
    } catch (SQLException e) {
      System.out.println(e.getMessage() + ", " + e.getClass());
    }
    return hashword;


  }
}
