package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceRequestFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final MaintenanceRequestFactory factory = new MaintenanceRequestFactory();
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  static MaintenanceRequestFactory getFactory() {
    return factory;
  }

  public void create(MaintenanceRequest maintenanceRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.MAINTENANCE_REQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + " ) "
            + "VALUES (?)";
    Validators.maintenanceRequestValidation(maintenanceRequest);
    serviceRequestFactory.create(maintenanceRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, maintenanceRequest.getId());

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

  public MaintenanceRequest read(String id) {
    MaintenanceRequest maintenanceRequest = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.MAINTENANCE_REQUEST_TABLE_NAME
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
          maintenanceRequest =
              new MaintenanceRequest(
                  serviceRequest.getId(),
                  serviceRequest.getLocation(),
                  serviceRequest.getAssignee(),
                  serviceRequest.getDescription(),
                  serviceRequest.getDateTimeSubmitted(),
                  serviceRequest.getPriority(),
                  serviceRequest.getComplete());
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return maintenanceRequest;
  }

  public void update(MaintenanceRequest maintenanceRequest) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.MAINTENANCE_REQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ? "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      serviceRequestFactory.update(maintenanceRequest);
      preparedStatement.setString(param++, maintenanceRequest.getId());
      preparedStatement.setString(param++, maintenanceRequest.getId());
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
            + DatabaseManager.MAINTENANCE_REQUEST_TABLE_NAME
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

  public List<MaintenanceRequest> getMaintenanceRequestsByLocation(Node location) {
    List<MaintenanceRequest> maintenanceRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      MaintenanceRequest maintenanceRequest = read(serviceRequest.getId());
      if (maintenanceRequest != null) {
        maintenanceRequests.add(maintenanceRequest);
      }
    }
    if (maintenanceRequests.size() == 0) {
      return null;
    } else {
      return maintenanceRequests;
    }
  }

  public List<MaintenanceRequest> getAllMaintenanceRequests() {
    List<MaintenanceRequest> maintenanceRequests = new ArrayList<>();
    String selectStatement = "SELECT * FROM " + DatabaseManager.MAINTENANCE_REQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      maintenanceRequests = new ArrayList<>();
      ;
      while (resultSet.next()) {
        ServiceRequest serviceRequest =
            serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        maintenanceRequests.add(
            new MaintenanceRequest(
                serviceRequest.getId(),
                serviceRequest.getLocation(),
                serviceRequest.getAssignee(),
                serviceRequest.getDescription(),
                serviceRequest.getDateTimeSubmitted(),
                serviceRequest.getPriority(),
                serviceRequest.getComplete()));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return maintenanceRequests;
  }
}
