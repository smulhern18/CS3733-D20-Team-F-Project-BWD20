package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComputerServiceRequestFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final ComputerServiceRequestFactory factory = new ComputerServiceRequestFactory();
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  static ComputerServiceRequestFactory getFactory() {
    return factory;
  }

  public void create(ComputerServiceRequest computerServiceRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.COMPUTER_REQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.MAKE_KEY
            + ", "
            + DatabaseManager.HARDWARESOFTWARE_KEY
            + ", "
            + DatabaseManager.OS_ID_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?)";
    Validators.computerServiceValidation(computerServiceRequest);
    serviceRequestFactory.create(computerServiceRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, computerServiceRequest.getId());
      prepareStatement.setString(param++, computerServiceRequest.getMake());
      prepareStatement.setString(param++, computerServiceRequest.getHardwareSoftware());
      prepareStatement.setString(param++, computerServiceRequest.getOS());
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

  public static ComputerServiceRequest read(String id) {
    ComputerServiceRequest computerService = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.COMPUTER_REQUEST_TABLE_NAME
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
          computerService =
              new ComputerServiceRequest(
                  serviceRequest.getId(),
                  serviceRequest.getLocation(),
                  serviceRequest.getAssignee(),
                  serviceRequest.getDescription(),
                  serviceRequest.getDateTimeSubmitted(),
                  serviceRequest.getPriority(),
                  serviceRequest.getComplete(),
                  resultSet.getString(DatabaseManager.MAKE_KEY),
                  resultSet.getString(DatabaseManager.HARDWARESOFTWARE_KEY),
                  resultSet.getString(DatabaseManager.OS_ID_KEY));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return computerService;
  }

  public void update(ComputerServiceRequest computerServiceRequest) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.COMPUTER_REQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.MAKE_KEY
            + " = ?, "
            + DatabaseManager.HARDWARESOFTWARE_KEY
            + " = ?, "
            + DatabaseManager.OS_ID_KEY
            + " = ? "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      serviceRequestFactory.update(computerServiceRequest);
      preparedStatement.setString(param++, computerServiceRequest.getId());
      preparedStatement.setString(param++, computerServiceRequest.getMake());
      preparedStatement.setString(param++, computerServiceRequest.getHardwareSoftware());
      preparedStatement.setString(param++, computerServiceRequest.getOS());
      preparedStatement.setString(param++, computerServiceRequest.getId());
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
            + DatabaseManager.COMPUTER_REQUEST_TABLE_NAME
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

  public List<ComputerServiceRequest> getComputerRequestsByLocation(Node location) {
    List<ComputerServiceRequest> computerRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      ComputerServiceRequest computerReadRequest = read(serviceRequest.getId());
      if (computerReadRequest != null) {
        computerRequests.add(computerReadRequest);
      }
    }
    if (computerRequests.size() == 0) {
      return null;
    } else {
      return computerRequests;
    }
  }

  public List<ComputerServiceRequest> getAllComputerRequests() {
    List<ComputerServiceRequest> computerRequests = null;
    String selectStatement = "SELECT * FROM " + DatabaseManager.COMPUTER_REQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      computerRequests = new ArrayList<>();
      ;
      while (resultSet.next()) {
        ServiceRequest serviceRequest =
            serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        ComputerServiceRequest computerServiceRequest =
            factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        computerRequests.add(
            new ComputerServiceRequest(
                serviceRequest.getId(),
                serviceRequest.getLocation(),
                serviceRequest.getAssignee(),
                serviceRequest.getDescription(),
                serviceRequest.getDateTimeSubmitted(),
                serviceRequest.getPriority(),
                serviceRequest.getComplete(),
                computerServiceRequest.getMake(),
                computerServiceRequest.getHardwareSoftware(),
                computerServiceRequest.getOS()));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return computerRequests;
  }
}
