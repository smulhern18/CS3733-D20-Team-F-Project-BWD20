package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDeliveryRequestFactory {

  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final MedicineDeliveryRequestFactory factory =
      new MedicineDeliveryRequestFactory();
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  public static MedicineDeliveryRequestFactory getFactory() {
    return factory;
  }

  public void create(MedicineDeliveryRequest medicineDeliveryRequest) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.MEDICINE_DELIVERY_REQUEST_TABLE_NAME
            + " ( "
            + DatabaseManager.SERVICEID_KEY
            + ", "
            + DatabaseManager.MEDICINE_TYPE_KEY
            + ", "
            + DatabaseManager.INSTRUCTIONS_KEY
            + " ) "
            + "VALUES (?, ?, ?)";
    Validators.medicineDeliveryRequestValidation(medicineDeliveryRequest);
    serviceRequestFactory.create(medicineDeliveryRequest);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, medicineDeliveryRequest.getId());
      prepareStatement.setString(param++, medicineDeliveryRequest.getMedicineType());
      prepareStatement.setString(param++, medicineDeliveryRequest.getInstructions());
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

  public MedicineDeliveryRequest read(String id) {
    MedicineDeliveryRequest medicineDeliveryRequest = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.MEDICINE_DELIVERY_REQUEST_TABLE_NAME
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
          medicineDeliveryRequest =
              new MedicineDeliveryRequest(
                  serviceRequest.getId(),
                  serviceRequest.getLocation(),
                  serviceRequest.getAssignee(),
                  serviceRequest.getDescription(),
                  serviceRequest.getDateTimeSubmitted(),
                  serviceRequest.getPriority(),
                  serviceRequest.getComplete(),
                  resultSet.getString(DatabaseManager.MEDICINE_TYPE_KEY),
                  resultSet.getString(DatabaseManager.INSTRUCTIONS_KEY));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return medicineDeliveryRequest;
  }

  public void update(MedicineDeliveryRequest medicineDeliveryRequest) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.MEDICINE_DELIVERY_REQUEST_TABLE_NAME
            + " SET "
            + DatabaseManager.SERVICEID_KEY
            + " = ?, "
            + DatabaseManager.MEDICINE_TYPE_KEY
            + "= ?,"
            + DatabaseManager.INSTRUCTIONS_KEY
            + "= ? "
            + "WHERE "
            + DatabaseManager.SERVICEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      serviceRequestFactory.update(medicineDeliveryRequest);
      preparedStatement.setString(param++, medicineDeliveryRequest.getId());
      preparedStatement.setString(param++, medicineDeliveryRequest.getMedicineType());
      preparedStatement.setString(param++, medicineDeliveryRequest.getInstructions());
      preparedStatement.setString(param++, medicineDeliveryRequest.getId());
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
            + DatabaseManager.MEDICINE_DELIVERY_REQUEST_TABLE_NAME
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

  public List<MedicineDeliveryRequest> getMedicineDeliveryRequestsByLocation(Node location) {
    List<MedicineDeliveryRequest> medicineDeliveryRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      MedicineDeliveryRequest medicineDeliveryRequest = read(serviceRequest.getId());
      if (medicineDeliveryRequest != null) {
        medicineDeliveryRequests.add(medicineDeliveryRequest);
      }
    }
    if (medicineDeliveryRequests.size() == 0) {
      return null;
    } else {
      return medicineDeliveryRequests;
    }
  }

  public List<MedicineDeliveryRequest> getAllMedicineDeliveryRequests() {
    List<MedicineDeliveryRequest> medicineDeliveryRequests = null;
    String selectStatement =
        "SELECT * FROM " + DatabaseManager.MEDICINE_DELIVERY_REQUEST_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      medicineDeliveryRequests = new ArrayList<>();
      ;
      while (resultSet.next()) {
        ServiceRequest serviceRequest =
            serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        MedicineDeliveryRequest medicineDeliveryRequest =
            factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
        medicineDeliveryRequests.add(
            new MedicineDeliveryRequest(
                serviceRequest.getId(),
                serviceRequest.getLocation(),
                serviceRequest.getAssignee(),
                serviceRequest.getDescription(),
                serviceRequest.getDateTimeSubmitted(),
                serviceRequest.getPriority(),
                serviceRequest.getComplete(),
                medicineDeliveryRequest.getMedicineType(),
                medicineDeliveryRequest.getInstructions()));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in MedicineDeliveryRequestFactory read: "
              + e.getMessage()
              + ", "
              + e.getClass());
    }
    return medicineDeliveryRequests;
  }
}
