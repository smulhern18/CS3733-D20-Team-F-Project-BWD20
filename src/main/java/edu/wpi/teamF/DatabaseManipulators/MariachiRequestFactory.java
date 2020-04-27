package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MariachiRequestFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final MariachiRequestFactory factory = new MariachiRequestFactory();
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  public static MariachiRequestFactory getFactory() {
    return factory;
  }

  public void create(MariachiRequest mariachiRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.SECURITY_REQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.SONG_REQUEST_KEY
            + " ) "
            + "VALUES (?, ?)";
    Validators.mariachiRequestValidation(mariachiRequest);
    serviceRequestFactory.create(mariachiRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, mariachiRequest.getId());
      prepareStatement.setString(param++, mariachiRequest.getSongRequest());
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

  public MariachiRequest read(String id) {
    MariachiRequest mariachiRequest = null;
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
          mariachiRequest =
              new MariachiRequest(
                  serviceRequest.getId(),
                  serviceRequest.getLocation(),
                  serviceRequest.getAssignee(),
                  serviceRequest.getDescription(),
                  serviceRequest.getDateTimeSubmitted(),
                  serviceRequest.getPriority(),
                  serviceRequest.getComplete(),
                  resultSet.getString(DatabaseManager.SONG_REQUEST_KEY));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return mariachiRequest;
  }

  public void update(MariachiRequest mariachiRequest) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.SECURITY_REQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.SONG_REQUEST_KEY
            + " = ?, "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      serviceRequestFactory.update(mariachiRequest);
      preparedStatement.setString(param++, mariachiRequest.getId());
      preparedStatement.setString(param++, mariachiRequest.getSongRequest());

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

  public List<MariachiRequest> getSecurityRequestsByLocation(Node location) {
    List<MariachiRequest> mariachiRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      MariachiRequest mariachiReadRequest = read(serviceRequest.getId());
      if (mariachiReadRequest != null) {
        mariachiRequests.add(mariachiReadRequest);
      }
    }
    if (mariachiRequests.size() == 0) {
      return new ArrayList<>();
    } else {
      return mariachiRequests;
    }
  }

  public List<MariachiRequest> getAllMariachiRequest() {
    List<MariachiRequest> mariachiRequests = new ArrayList<>();
    String selectStatement = "SELECT * FROM " + DatabaseManager.SECURITY_REQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      mariachiRequests = new ArrayList<>();
      ;
      while (resultSet.next()) {
        ServiceRequest serviceRequest =
            serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        MariachiRequest mariachiRequest =
            factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        mariachiRequests.add(
            new MariachiRequest(
                serviceRequest.getId(),
                serviceRequest.getLocation(),
                serviceRequest.getAssignee(),
                serviceRequest.getDescription(),
                serviceRequest.getDateTimeSubmitted(),
                serviceRequest.getPriority(),
                serviceRequest.getComplete(),
                mariachiRequest.getSongRequest()));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return mariachiRequests;
  }
}
