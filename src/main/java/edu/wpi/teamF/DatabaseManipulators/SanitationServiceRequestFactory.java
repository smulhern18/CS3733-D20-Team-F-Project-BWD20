package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SanitationServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanitationServiceRequestFactory {

  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final SanitationServiceRequestFactory factory =
      new SanitationServiceRequestFactory();
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  public static SanitationServiceRequestFactory getFactory() {
    return factory;
  }

  public void create(SanitationServiceRequest sanitationServiceRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.COMPUTER_REQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.SANITATION_TYPE_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?)";
    Validators.sanitationServiceValidation(sanitationServiceRequest);
    serviceRequestFactory.create(sanitationServiceRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, sanitationServiceRequest.getId());
      prepareStatement.setString(param++, sanitationServiceRequest.getType());
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

  public SanitationServiceRequest read(String id) {
    SanitationServiceRequest sanitationService = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.SANITATION_REQUEST_TABLE_NAME
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
          sanitationService =
              new SanitationServiceRequest(
                  serviceRequest.getId(),
                  serviceRequest.getLocation(),
                  serviceRequest.getAssignee(),
                  serviceRequest.getDescription(),
                  serviceRequest.getDateTimeSubmitted(),
                  serviceRequest.getPriority(),
                  serviceRequest.getComplete(),
                  resultSet.getString(DatabaseManager.SANITATION_TYPE_KEY));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return sanitationService;
  }

  public void update(SanitationServiceRequest sanitationServiceRequest) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.SANITATION_REQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.SANITATION_TYPE_KEY
            + " = ? "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      serviceRequestFactory.update(sanitationServiceRequest);
      preparedStatement.setString(param++, sanitationServiceRequest.getId());
      preparedStatement.setString(param++, sanitationServiceRequest.getType());
      preparedStatement.setString(param++, sanitationServiceRequest.getId());
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
            + DatabaseManager.SANITATION_REQUEST_TABLE_NAME
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

  public List<SanitationServiceRequest> getSanitationRequestsByLocation(Node location) {
    List<SanitationServiceRequest> sanitationRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      SanitationServiceRequest sanitationReadRequest = read(serviceRequest.getId());
      if (sanitationReadRequest != null) {
        sanitationRequests.add(sanitationReadRequest);
      }
    }
    if (sanitationRequests.size() == 0) {
      return null;
    } else {
      return sanitationRequests;
    }
  }

  public List<SanitationServiceRequest> getAllSanitationRequests() {
    List<SanitationServiceRequest> sanitationRequests = new ArrayList<>();
    String selectStatement = "SELECT * FROM " + DatabaseManager.SANITATION_REQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      sanitationRequests = new ArrayList<>();
      ;
      while (resultSet.next()) {
        ServiceRequest serviceRequest =
            serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        SanitationServiceRequest sanitationServiceRequest =
            factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        sanitationRequests.add(
            new SanitationServiceRequest(
                serviceRequest.getId(),
                serviceRequest.getLocation(),
                serviceRequest.getAssignee(),
                serviceRequest.getDescription(),
                serviceRequest.getDateTimeSubmitted(),
                serviceRequest.getPriority(),
                serviceRequest.getComplete(),
                sanitationServiceRequest.getType()));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return sanitationRequests;
  }
}
