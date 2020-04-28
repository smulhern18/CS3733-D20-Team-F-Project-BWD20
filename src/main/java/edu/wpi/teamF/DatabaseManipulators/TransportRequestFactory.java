package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.TransportRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransportRequestFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();
  private static final TransportRequestFactory factory = new TransportRequestFactory();
  private static final ServiceRequestFactory serviceRequestFactory =
      ServiceRequestFactory.getFactory();

  public static TransportRequestFactory getFactory() {
    return factory;
  }

    public void create(TransportRequest transportRequest) throws ValidationException {
        String insertStatement =
                "INSERT INTO "
                        + DatabaseManager.TRANSPORT_REQUEST_TABLE_NAME
                        + " ( "
                        + DatabaseManager.SERVICEID_KEY
                        + ", "
                        + DatabaseManager.TRANSPORT_TYPE_KEY
                        + ", "
                        + DatabaseManager.DESTINATION_KEY
                        + ", "
                        + DatabaseManager.TIME_COMPLETED_KEY
                        + " ) "
                        + "VALUES (?, ?, ?)";
        Validators.transportRequestValidation(transportRequest);
        serviceRequestFactory.create(transportRequest);
        try (PreparedStatement prepareStatement =
                     DatabaseManager.getConnection().prepareStatement(insertStatement)) {
            int param = 1;
            prepareStatement.setString(param++, transportRequest.getId());
            prepareStatement.setString(param++, transportRequest.getType());
            prepareStatement.setString(param++, transportRequest.getDestination().getId());
            prepareStatement.setTimestamp(param++, new Timestamp(transportRequest.getDateTimeCompleted().getTime()));
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

  public TransportRequest read(String id) {
    TransportRequest transportRequest = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.TRANSPORT_REQUEST_TABLE_NAME
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
                    transportRequest =
                            new TransportRequest(
                                    serviceRequest.getId(),
                                    serviceRequest.getLocation(),
                                    serviceRequest.getAssignee(),
                                    serviceRequest.getDescription(),
                                    serviceRequest.getDateTimeSubmitted(),
                                    serviceRequest.getPriority(),
                                    serviceRequest.getComplete(),
                                    resultSet.getString(DatabaseManager.TRANSPORT_TYPE_KEY),
                                    nodeFactory.read(resultSet.getString(DatabaseManager.DESTINATION_KEY)),
                                    new Date(resultSet.getTimestamp(DatabaseManager.TIME_COMPLETED_KEY).getTime()));
                }
            } catch (ValidationException e) {
                throw e;
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
        }
    return transportRequest;
    }

    public void update(TransportRequest transportRequest) {
        String updateStatement =
                "UPDATE "
                        + DatabaseManager.TRANSPORT_REQUEST_TABLE_NAME
                        + " SET "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?, "
                        + DatabaseManager.TRANSPORT_TYPE_KEY
                        + " = ?, "
                        + DatabaseManager.DESTINATION_KEY
                        + " = ?, "
                        + DatabaseManager.TIME_COMPLETED_KEY
                        + " = ? "
                        + "WHERE "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?";
        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(updateStatement)) {
            int param = 1;
            serviceRequestFactory.update(transportRequest);
            preparedStatement.setString(param++, transportRequest.getId());
            preparedStatement.setString(param++, transportRequest.getType());
            preparedStatement.setString(param++, transportRequest.getDestination().getId());
            preparedStatement.setTimestamp(param++, new Timestamp(transportRequest.getDateTimeCompleted().getTime()));
            preparedStatement.setString(param++, transportRequest.getId());
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
            + DatabaseManager.TRANSPORT_REQUEST_TABLE_NAME
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

  public List<TransportRequest> getTransportRequestsByLocation(Node location) {
    List<TransportRequest> transportRequests = new ArrayList<>();
    for (ServiceRequest serviceRequest :
        serviceRequestFactory.getServiceRequestsByLocation(location)) {
      TransportRequest transportReadRequest = read(serviceRequest.getId());
      if (transportReadRequest != null) {
        transportRequests.add(transportReadRequest);
      }
    }
    if (transportRequests.size() == 0) {
      return null;
    } else {
      return transportRequests;
    }
  }

  public List<TransportRequest> getAllTransportRequests() {
    List<TransportRequest> transportRequests = null;
    String selectStatement = "SELECT * FROM " + DatabaseManager.TRANSPORT_REQUEST_TABLE_NAME;
        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(selectStatement);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            transportRequests = new ArrayList<>();
            ;
            while (resultSet.next()) {
                ServiceRequest serviceRequest =
                        serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
                TransportRequest transportRequest =
                        factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
                transportRequests.add(
                        new TransportRequest(
                                serviceRequest.getId(),
                                serviceRequest.getLocation(),
                                serviceRequest.getAssignee(),
                                serviceRequest.getDescription(),
                                serviceRequest.getDateTimeSubmitted(),
                                serviceRequest.getPriority(),
                                serviceRequest.getComplete(),
                                transportRequest.getType(),
                                transportRequest.getDestination(),
                                transportRequest.getDateTimeCompleted()));
            }
        } catch (Exception e) {
            System.out.println(
                    "Exception in TransportRequestFactory read: " + e.getMessage() + ", " + e.getClass());
        }
        return transportRequests;
    }
  }
