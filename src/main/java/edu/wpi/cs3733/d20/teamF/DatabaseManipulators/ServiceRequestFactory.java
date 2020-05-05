package edu.wpi.cs3733.d20.teamF.DatabaseManipulators;

<<<<<<< HEAD:src/main/java/edu/wpi/cs3733/d20/teamF/DatabaseManipulators/ServiceRequestFactory.java
import edu.wpi.cs3733.d20.teamF.ModelClasses.ValidationException;
import edu.wpi.cs3733.d20.teamF.ModelClasses.MaintenanceRequest;
=======
import edu.wpi.cs3733.d20.teamF.ModelClasses.MaintenanceRequest;
import edu.wpi.cs3733.d20.teamF.ModelClasses.ValidationException;
>>>>>>> 8fa2e494a687483df95e4e676e255de962391745:src/main/java/edu/wpi/teamF/DatabaseManipulators/ServiceRequestFactory.java
import edu.wpi.cs3733.d20.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceRequestFactory {
  private static final ServiceRequestFactory factory = new ServiceRequestFactory();

  static ServiceRequestFactory getFactory() {
    return factory;
  }

  public void create(MaintenanceRequest serviceRequest) throws ValidationException {
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
            + DatabaseManager.MAINTENANCE_TYPE_KEY
            + ", "
            + DatabaseManager.ESTIMATEDCOMPLETION_KEY
            + ", "
            + DatabaseManager.ESTIMATEDCOST_KEY
            + ", "
            + DatabaseManager.COMPLETED_KEY
            + ", "
            + DatabaseManager.DATECOMPLETED_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    Validators.serviceRequestValidation(serviceRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, serviceRequest.getId());
      prepareStatement.setString(param++, serviceRequest.getLocation());
      prepareStatement.setTimestamp(
          param++, new Timestamp(serviceRequest.getDateTimeSubmitted().getTime()));
      prepareStatement.setString(param++, serviceRequest.getDescription());
      prepareStatement.setString(param++, serviceRequest.getAssignee());
      prepareStatement.setInt(param++, serviceRequest.getPriority());
      prepareStatement.setString(param++, serviceRequest.getType());
      prepareStatement.setTimestamp(
          param++, new Timestamp(serviceRequest.getEstimatedCompletionDate().getTime()));
      prepareStatement.setDouble(param++, serviceRequest.getEstimatedCost());
      prepareStatement.setBoolean(param++, serviceRequest.getComplete());
      Date dateComplete = serviceRequest.getTimeCompleted();
      if (dateComplete == null) {
        prepareStatement.setTimestamp(param++, null);
      } else {
        prepareStatement.setTimestamp(param++, new Timestamp(dateComplete.getTime()));
      }

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
    MaintenanceRequest serviceRequest = null;
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
          Timestamp timestamp = resultSet.getTimestamp(DatabaseManager.DATECOMPLETED_KEY);
          Date complete = null;
          if (timestamp != null) {
            complete = new Date(timestamp.getTime());
          }
          serviceRequest =
              new MaintenanceRequest(
                  resultSet.getString(DatabaseManager.SERVICEID_KEY),
                  resultSet.getString(DatabaseManager.NODEID_KEY),
                  resultSet.getString(DatabaseManager.ASSIGNED_KEY),
                  resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                  new Date(resultSet.getTimestamp(DatabaseManager.TIME_CREATED_KEY).getTime()),
                  resultSet.getInt(DatabaseManager.PRIORITY_KEY),
                  resultSet.getString(DatabaseManager.MAINTENANCE_TYPE_KEY),
                  new Date(
                      resultSet.getTimestamp(DatabaseManager.ESTIMATEDCOMPLETION_KEY).getTime()),
                  resultSet.getDouble(DatabaseManager.ESTIMATEDCOST_KEY),
                  resultSet.getBoolean(DatabaseManager.COMPLETED_KEY),
                  complete);
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

  public void update(MaintenanceRequest serviceRequest) {

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
            + DatabaseManager.MAINTENANCE_TYPE_KEY
            + " = ?, "
            + DatabaseManager.ESTIMATEDCOMPLETION_KEY
            + " = ?, "
            + DatabaseManager.ESTIMATEDCOST_KEY
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
      preparedStatement.setString(param++, serviceRequest.getLocation());
      preparedStatement.setTimestamp(
          param++, new Timestamp(serviceRequest.getDateTimeSubmitted().getTime()));
      preparedStatement.setString(param++, serviceRequest.getDescription());
      preparedStatement.setString(param++, serviceRequest.getAssignee());
      preparedStatement.setInt(param++, serviceRequest.getPriority());
      preparedStatement.setString(param++, serviceRequest.getType());
      preparedStatement.setTimestamp(
          param++, new Timestamp(serviceRequest.getEstimatedCompletionDate().getTime()));
      preparedStatement.setDouble(param++, serviceRequest.getEstimatedCost());
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

  public List<MaintenanceRequest> getAllMaintenanceRequests() {
    List<MaintenanceRequest> allMain = null;
    String selectAllStatement = "SELECT * FROM " + DatabaseManager.SERVICE_REQUEST_TABLE;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectAllStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      allMain = new ArrayList<>();
      while (resultSet.next()) {
        Timestamp timestamp = resultSet.getTimestamp(DatabaseManager.DATECOMPLETED_KEY);
        Date complete = null;
        if (timestamp != null) {
          complete = new Date(timestamp.getTime());
        }
        MaintenanceRequest serviceRequest =
            new MaintenanceRequest(
                resultSet.getString(DatabaseManager.SERVICEID_KEY),
                resultSet.getString(DatabaseManager.NODEID_KEY),
                resultSet.getString(DatabaseManager.ASSIGNED_KEY),
                resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                new Date(resultSet.getTimestamp(DatabaseManager.TIME_CREATED_KEY).getTime()),
                resultSet.getInt(DatabaseManager.PRIORITY_KEY),
                resultSet.getString(DatabaseManager.MAINTENANCE_TYPE_KEY),
                new Date(resultSet.getTimestamp(DatabaseManager.ESTIMATEDCOMPLETION_KEY).getTime()),
                resultSet.getDouble(DatabaseManager.ESTIMATEDCOST_KEY),
                resultSet.getBoolean(DatabaseManager.COMPLETED_KEY),
                complete);
        allMain.add(serviceRequest);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage() + ", " + e.getClass());
    }
    if (allMain.size() == 0) {
      return null;
    } else {
      return allMain;
    }
  }
}
