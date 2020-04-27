package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
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
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  public static SecurityRequestFactory getFactory() {
    return factory;
  }

  public void create(SecurityRequest securityRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.SECURITY_REQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.GUARDS_REQUESTED_KEY
            + " ) "
            + "VALUES (?, ?)";
    Validators.securityRequestValidation(securityRequest);
    serviceRequestFactory.create(securityRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, securityRequest.getId());
      prepareStatement.setInt(param++, securityRequest.getGuardsRequested());
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
            + DatabaseManager.SECURITY_REQUEST_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, id);

      try {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          ServiceRequest serviceRequest = serviceRequestFactory.read(id);
          securityRequest =
              new SecurityRequest(
                  serviceRequest.getId(),
                  serviceRequest.getLocation(),
                  serviceRequest.getAssignee(),
                  serviceRequest.getDescription(),
                  serviceRequest.getDateTimeSubmitted(),
                  serviceRequest.getPriority(),
                  serviceRequest.getComplete(),
                  resultSet.getInt(DatabaseManager.GUARDS_REQUESTED_KEY));
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
            + DatabaseManager.SECURITY_REQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.GUARDS_REQUESTED_KEY
            + " = ?, "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      serviceRequestFactory.update(securityRequest);
      preparedStatement.setString(param++, securityRequest.getId());
      preparedStatement.setInt(param++, securityRequest.getGuardsRequested());

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
            + DatabaseManager.SECURITY_REQUEST_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    serviceRequestFactory.delete(id);
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
    List<SecurityRequest> securityRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      SecurityRequest securityReadRequest = read(serviceRequest.getId());
      if (securityReadRequest != null) {
        securityRequests.add(securityReadRequest);
      }
    }
    if (securityRequests.size() == 0) {
      return new ArrayList<>();
    } else {
      return securityRequests;
    }
  }

  public List<SecurityRequest> getAllSecurityRequest() {
    List<SecurityRequest> securityRequests = new ArrayList<>();
    String selectStatement = "SELECT * FROM " + DatabaseManager.SECURITY_REQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      securityRequests = new ArrayList<>();
      ;
      while (resultSet.next()) {
        ServiceRequest serviceRequest =
            serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        SecurityRequest securityRequest =
            factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        securityRequests.add(
            new SecurityRequest(
                serviceRequest.getId(),
                serviceRequest.getLocation(),
                serviceRequest.getAssignee(),
                serviceRequest.getDescription(),
                serviceRequest.getDateTimeSubmitted(),
                serviceRequest.getPriority(),
                serviceRequest.getComplete(),
                securityRequest.getGuardsRequested()));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return securityRequests;
  }
}
