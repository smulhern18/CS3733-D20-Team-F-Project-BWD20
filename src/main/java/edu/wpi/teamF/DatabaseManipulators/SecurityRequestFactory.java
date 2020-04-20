package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecurityRequestFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final SecurityRequestFactory factory = new SecurityRequestFactory();

  public static SecurityRequestFactory getFactory() {
    return factory;
  }

  public void create(SecurityRequest securityRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.SECURITYQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.NODEID_KEY
            + ", "
            + DatabaseManager.DESCRIPTION_KEY
            + ", "
            + DatabaseManager.TIME_CREATED_KEY
            + ", "
            + DatabaseManager.PRIORITY_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?, ?)";
    Validators.securityRequestValidation(securityRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, securityRequest.getId());
      prepareStatement.setString(param++, securityRequest.getLocation().getId());
      prepareStatement.setString(param++, securityRequest.getDescription());
      prepareStatement.setString(param++, securityRequest.getDateTimeSubmitted().toString());
      prepareStatement.setInt(param++, securityRequest.getPriority());

      try {
        int numRows = prepareStatement.executeUpdate();
        if (numRows < 1) {
          throw new SQLException("Created more than one rows");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public SecurityRequest read(String id) {
    SecurityRequest securityRequest = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.SECURITYQUEST_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, id);

      try {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          securityRequest =
              new SecurityRequest(
                  resultSet.getString(DatabaseManager.SERVICEID_KEY),
                  nodeFactory.read(resultSet.getString(DatabaseManager.NODEID_KEY)),
                  resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                  resultSet.getDate(DatabaseManager.TIME_CREATED_KEY),
                  resultSet.getInt(DatabaseManager.PRIORITY_KEY));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return securityRequest;
  }

  public void update(SecurityRequest securityRequest) {

    String updateStatement =
        "UPDATE "
            + DatabaseManager.SECURITYQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.NODEID_KEY
            + " = ?, "
            + DatabaseManager.DESCRIPTION_KEY
            + " = ?, "
            + DatabaseManager.TIME_CREATED_KEY
            + " = ?, "
            + DatabaseManager.PRIORITY_KEY
            + " = ?, "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      preparedStatement.setString(param++, securityRequest.getId());
      preparedStatement.setString(param++, securityRequest.getLocation().getId());
      preparedStatement.setString(param++, securityRequest.getDescription());
      preparedStatement.setString(param++, securityRequest.getDateTimeSubmitted().toString());
      preparedStatement.setInt(param++, securityRequest.getPriority());
      int numRows = preparedStatement.executeUpdate();
      if (numRows != 1) {
        throw new Exception("Updated " + numRows + " rows");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void delete(String id) {

    String deleteStatement =
        "DELETE FROM "
            + DatabaseManager.SECURITYQUEST_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(deleteStatement)) {
      preparedStatement.setString(1, id);

      int numRows = preparedStatement.executeUpdate();
      if (numRows > 1) {
        throw new SQLException("Deleted " + numRows + " rows");
      }
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage() + ", " + e.getCause());
    }
  }

  public List<SecurityRequest> getSecurityRequestsByLocation(Node location) {
    List<SecurityRequest> securityRequest = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.SECURITYQUEST_TABLE_NAME
            + " WHERE "
            + DatabaseManager.NODEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, location.getId());

      try {
        securityRequest = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          securityRequest.add(
              new SecurityRequest(
                  resultSet.getString(DatabaseManager.SERVICEID_KEY),
                  nodeFactory.read(resultSet.getString(DatabaseManager.NODEID_KEY)),
                  resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                  resultSet.getDate(DatabaseManager.TIME_CREATED_KEY),
                  resultSet.getInt(DatabaseManager.PRIORITY_KEY)));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return securityRequest;
  }

  public List<SecurityRequest> getAllSecurityRequests() {
    List<SecurityRequest> securityRequest = null;
    String selectStatement = "SELECT * FROM " + DatabaseManager.SECURITYQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      securityRequest = new ArrayList<>();
      ;
      while (resultSet.next()) {
        securityRequest.add(
            new SecurityRequest(
                resultSet.getString(DatabaseManager.SERVICEID_KEY),
                nodeFactory.read(resultSet.getString(DatabaseManager.NODEID_KEY)),
                resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                resultSet.getDate(DatabaseManager.TIME_CREATED_KEY),
                resultSet.getInt(DatabaseManager.PRIORITY_KEY)));
      }
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return securityRequest;
  }
}
