package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LaundryServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaundryServiceRequestFactory {
    NodeFactory nodeFactory = NodeFactory.getFactory();
    private static final LaundryServiceRequestFactory factory =
            new LaundryServiceRequestFactory();
    private static final ServiceRequestFactory serviceRequestFactory =
            ServiceRequestFactory.getFactory();

    public static LaundryServiceRequestFactory getFactory() {
        return factory;
    }

    public void create(LaundryServiceRequest launduaryServiceRequest) throws ValidationException {
        String insertStatement =
                "INSERT INTO "
                        + DatabaseManager.LAUNDRY_REQUEST_TABLE_NAME
                        + " ( "
                        + DatabaseManager.SERVICEID_KEY
                        + ", "
                        + DatabaseManager.ITEMS_KEY
                        + ", "
                        + DatabaseManager.QUANTITY_KEY
                        + ", "
                        + DatabaseManager.TEMPERTURE_KEY
                        + " ) "
                        + "VALUES (?, ?, ?, ?)";
        Validators.launduaryServiceValidation(launduaryServiceRequest);
        serviceRequestFactory.create(launduaryServiceRequest);
        try (PreparedStatement prepareStatement =
                     DatabaseManager.getConnection().prepareStatement(insertStatement)) {
            int param = 1;
            prepareStatement.setString(param++, launduaryServiceRequest.getId());
            prepareStatement.setString(param++, launduaryServiceRequest.getItems());
            prepareStatement.setString(param++, launduaryServiceRequest.getTemperature());
            prepareStatement.setString(param++, launduaryServiceRequest.getQuantity());
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

    public LaundryServiceRequest read(String id) {
        LaundryServiceRequest launduaryService = null;
        String selectStatement =
                "SELECT * FROM "
                        + DatabaseManager.LAUNDRY_REQUEST_TABLE_NAME
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
                    launduaryService =
                            new LaundryServiceRequest(
                                    serviceRequest.getId(),
                                    serviceRequest.getLocation(),
                                    serviceRequest.getAssignee(),
                                    serviceRequest.getDescription(),
                                    serviceRequest.getDateTimeSubmitted(),
                                    serviceRequest.getPriority(),
                                    serviceRequest.getComplete(),
                                    resultSet.getString(DatabaseManager.ITEMS_KEY),
                                    resultSet.getString(DatabaseManager.TEMPERTURE_KEY),
                                    resultSet.getString(DatabaseManager.QUANTITY_KEY));
                }
            } catch (ValidationException e) {
                throw e;
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
        }
        return launduaryService;
    }

    public void update(LaundryServiceRequest laundryServiceRequest) {
        String updateStatement =
                "UPDATE "
                        + DatabaseManager.LAUNDRY_REQUEST_TABLE_NAME
                        + " SET "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?, "
                        + DatabaseManager.ITEMS_KEY
                        + " = ?, "
                        + DatabaseManager.QUANTITY_KEY
                        + " = ?, "
                        + DatabaseManager.TEMPERTURE_KEY
                        + " = ? "
                        + "WHERE "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?";
        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(updateStatement)) {
            int param = 1;
            serviceRequestFactory.update(laundryServiceRequest);
            preparedStatement.setString(param++, laundryServiceRequest.getId());
            preparedStatement.setString(param++, laundryServiceRequest.getItems());
            preparedStatement.setString(param++, laundryServiceRequest.getQuantity());
            preparedStatement.setString(param++, laundryServiceRequest.getTemperature());
            preparedStatement.setString(param++, laundryServiceRequest.getId());
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
                        + DatabaseManager.LAUNDRY_REQUEST_TABLE_NAME
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

    public List<LaundryServiceRequest> getLaunduaryRequestsByLocation(Node location) {
        List<LaundryServiceRequest> launduaryRequests = new ArrayList<>();
        for (ServiceRequest serviceRequest :
                serviceRequestFactory.getServiceRequestsByLocation(location)) {
            LaundryServiceRequest launduaryReadRequest = read(serviceRequest.getId());
            if (launduaryReadRequest != null) {
                launduaryRequests.add(launduaryReadRequest);
            }
        }
        if (launduaryRequests.size() == 0) {
            return null;
        } else {
            return launduaryRequests;
        }
    }

    public List<LaundryServiceRequest> getAllLaundryRequests() {
        List<LaundryServiceRequest> laundryRequests = new ArrayList<>();
        String selectStatement = "SELECT * FROM " + DatabaseManager.LAUNDRY_REQUEST_TABLE_NAME;

        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(selectStatement);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            laundryRequests = new ArrayList<>();
            ;
            while (resultSet.next()) {
                ServiceRequest serviceRequest =
                        serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
                LaundryServiceRequest laundryServiceRequest =
                        factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
                laundryRequests.add(
                        new LaundryServiceRequest(
                                serviceRequest.getId(),
                                serviceRequest.getLocation(),
                                serviceRequest.getAssignee(),
                                serviceRequest.getDescription(),
                                serviceRequest.getDateTimeSubmitted(),
                                serviceRequest.getPriority(),
                                serviceRequest.getComplete(),
                                laundryServiceRequest.getItems(),
                                laundryServiceRequest.getQuantity(),
                                laundryServiceRequest.getTemperature()));
            }
        } catch (Exception e) {
            System.out.println(
                    "Exception in LaundryFactory read: " + e.getMessage() + ", " + e.getClass());
        }
        return laundryRequests;
    }
}
