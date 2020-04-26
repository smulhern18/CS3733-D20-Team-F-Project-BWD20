package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceRequestFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final ServiceRequestFactory factory = new ServiceRequestFactory();

  public static ServiceRequestFactory getFactory() {
    return factory;
  }

  public void create(ServiceRequest serviceRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.SERVICE_REQUEST_TABLE
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.NODEID_KEY
            + ", "
            + DatabaseManager.TIME_CREATED_KEY
            + ", "
            + DatabaseManager.DESCRIPTION_KEY
            + ", "
            + DatabaseManager.ASSIGNED_KEY
            + ", "
            + DatabaseManager.PRIORITY_KEY
            + ", "
            + DatabaseManager.COMPLETED_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    Validators.serviceRequestValidation(serviceRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, serviceRequest.getId());
      prepareStatement.setString(param++, serviceRequest.getLocation().getId());
      prepareStatement.setTimestamp(
          param++, new Timestamp(serviceRequest.getDateTimeSubmitted().getTime()));
      prepareStatement.setString(param++, serviceRequest.getDescription());
      prepareStatement.setString(param++, serviceRequest.getAssignee());
      prepareStatement.setInt(param++, serviceRequest.getPriority());
      prepareStatement.setBoolean(param++, serviceRequest.getComplete());

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

  public ServiceRequest read(String id) {
    ServiceRequest serviceRequest = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.SERVICE_REQUEST_TABLE
            + " WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, id);

      try {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          serviceRequest =
              new MaintenanceRequest(
                  resultSet.getString(DatabaseManager.SERVICEID_KEY),
                  nodeFactory.read(resultSet.getString(DatabaseManager.NODEID_KEY)),
                  resultSet.getString(DatabaseManager.ASSIGNED_KEY),
                  resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                  new Date(resultSet.getTimestamp(DatabaseManager.TIME_CREATED_KEY).getTime()),
                  resultSet.getInt(DatabaseManager.PRIORITY_KEY),
                  resultSet.getBoolean(DatabaseManager.COMPLETED_KEY));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return serviceRequest;
  }

  public void update(ServiceRequest serviceRequest) {

    String updateStatement =
        "UPDATE "
            + DatabaseManager.SERVICE_REQUEST_TABLE
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.NODEID_KEY
            + " = ?, "
            + DatabaseManager.TIME_CREATED_KEY
            + " = ?, "
            + DatabaseManager.DESCRIPTION_KEY
            + " = ?, "
            + DatabaseManager.ASSIGNED_KEY
            + " = ?, "
            + DatabaseManager.PRIORITY_KEY
            + " = ?, "
            + DatabaseManager.COMPLETED_KEY
            + " = ? "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      preparedStatement.setString(param++, serviceRequest.getId());
      preparedStatement.setString(param++, serviceRequest.getLocation().getId());
      preparedStatement.setTimestamp(
          param++, new Timestamp(serviceRequest.getDateTimeSubmitted().getTime()));
      preparedStatement.setString(param++, serviceRequest.getDescription());
      preparedStatement.setString(param++, serviceRequest.getAssignee());
      preparedStatement.setInt(param++, serviceRequest.getPriority());
      preparedStatement.setBoolean(param++, serviceRequest.getComplete());
      preparedStatement.setString(param++, serviceRequest.getId());
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
            + DatabaseManager.SERVICE_REQUEST_TABLE
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

  public List<ServiceRequest> getServiceRequestsByLocation(Node location) {
    ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.SERVICE_REQUEST_TABLE
            + " WHERE "
            + DatabaseManager.NODEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, location.getId());

      try {
        serviceRequests = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          serviceRequests.add(
              new SecurityRequest(
                  resultSet.getString(DatabaseManager.SERVICEID_KEY),
                  location,
                  resultSet.getString(DatabaseManager.ASSIGNED_KEY),
                  resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                  new Date(resultSet.getTimestamp(DatabaseManager.TIME_CREATED_KEY).getTime()),
                  resultSet.getInt(DatabaseManager.PRIORITY_KEY),
                  resultSet.getBoolean(DatabaseManager.COMPLETED_KEY)));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println(
          "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return serviceRequests;
  }
}
