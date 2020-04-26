package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import org.junit.runner.Request;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerServiceRequestFactory {
    NodeFactory nodeFactory = NodeFactory.getFactory();
    private static final FlowerServiceRequestFactory factory = new FlowerServiceRequestFactory();
    private static final ServiceRequestFactory serviceRequestFactory =
            ServiceRequestFactory.getFactory();

    public static FlowerServiceRequestFactory getFactory() {
        return factory;
    }

    public void create(FlowerRequest flowerRequest) throws ValidationException {
        String insertStatement =
                "INSERT INTO "
                        + DatabaseManager.FLOWER_REQUEST_TABLE_NAME
                        + " ( "
                        + DatabaseManager.SERVICEID_KEY
                        + ", "
                        + DatabaseManager.RECIPIENT_NAME_KEY
                        + ", "
                        + DatabaseManager.ROOM_NUMBER_KEY
                        + ", "
                        + DatabaseManager.BOUQUET_KEY
                        + ", "
                        + DatabaseManager.MESSAGE_KEY
                        + ", "
                        + DatabaseManager.BUYER_NAME_KEY
                        + ", "
                        + DatabaseManager.PHONE_NUMBER_KEY
                        + ", "
                        + DatabaseManager.GIFT_WRAP_KEY
                        + " ) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Validators.FlowerValidation(flowerRequest);
        serviceRequestFactory.create(flowerRequest);
        try (PreparedStatement prepareStatement =
                     DatabaseManager.getConnection().prepareStatement(insertStatement)) {
            int param = 1;
            prepareStatement.setString(param++, flowerRequest.getId());
            prepareStatement.setString(param++, flowerRequest.getRecipientInput());
            prepareStatement.setInt(param++, flowerRequest.getRoomInput());
            prepareStatement.setString(param++, flowerRequest.getMessageInput());
            prepareStatement.setString(param++, flowerRequest.getBuyerName());
            prepareStatement.setString(param++, flowerRequest.getPhoneNumber());
            prepareStatement.setBoolean(param++, flowerRequest.getGiftWrap());
            prepareStatement.setString(param++, flowerRequest.getChoice());
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

    public FlowerRequest read(String id) {
        FlowerRequest flowerService = null;
        String selectStatement =
                "SELECT * FROM "
                        + DatabaseManager.FLOWER_REQUEST_TABLE_NAME
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
                    flowerService =
                            new FlowerRequest(
                                    serviceRequest.getId(),
                                    serviceRequest.getLocation(),
                                    serviceRequest.getAssignee(),
                                    serviceRequest.getDescription(),
                                    serviceRequest.getDateTimeSubmitted(),
                                    serviceRequest.getPriority(),
                                    resultSet.getString(DatabaseManager.RECIPIENT_NAME_KEY),
                                    resultSet.getInt(DatabaseManager.ROOM_NUMBER_KEY),
                                    resultSet.getString(DatabaseManager.BOUQUET_KEY),
                                    resultSet.getString(DatabaseManager.MESSAGE_KEY),
                                    resultSet.getString(DatabaseManager.BUYER_NAME_KEY),
                                    resultSet.getString(DatabaseManager.PHONE_NUMBER_KEY),
                                    resultSet.getBoolean(DatabaseManager.GIFT_WRAP_KEY),
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
        return flowerService;
    }

    public void update(FlowerRequest flowerRequest) {
        String updateStatement =
                "UPDATE "
                        + DatabaseManager.FLOWER_REQUEST_TABLE_NAME
                        + " SET "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?, "
                        + DatabaseManager.RECIPIENT_NAME_KEY
                        + " = ?, "
                        + DatabaseManager.ROOM_NUMBER_KEY
                        + " = ?, "
                        + DatabaseManager.BOUQUET_KEY
                        + " = ? "
                        + DatabaseManager.MESSAGE_KEY
                        + " = ? "
                        + DatabaseManager.BUYER_NAME_KEY
                        + " = ? "
                        + DatabaseManager.PHONE_NUMBER_KEY
                        + " = ? "
                        + DatabaseManager.GIFT_WRAP_KEY
                        + " = ? "
                        + "WHERE "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?";
        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(updateStatement)) {
            int param = 1;
            serviceRequestFactory.update(flowerRequest);
            preparedStatement.setString(param++, flowerRequest.getId());
            preparedStatement.setString(param++, flowerRequest.getRecipientInput());
            preparedStatement.setInt(param++, flowerRequest.getRoomInput());+458+9
            preparedStatement.setString(param++, flowerRequest.getChoice());
            preparedStatement.setString(param++, flowerRequest.getMessageInput());
            preparedStatement.setString(param++, flowerRequest.getBuyerName());
            preparedStatement.setString(param++, flowerRequest.getPhoneNumber());
            preparedStatement.setBoolean(param++, flowerRequest.getGiftWrap());
            preparedStatement.setString(param++, flowerRequest.getId());

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
                        + DatabaseManager.FLOWER_REQUEST_TABLE_NAME
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

    public List<FlowerRequest> getFlowerRequestsByLocation(Node location) {
        List<FlowerRequest> flowerRequests = new ArrayList<>();
        for (ServiceRequest serviceRequest :
                serviceRequestFactory.getServiceRequestsByLocation(location)) {
            FlowerRequest flowerReadRequest = read(serviceRequest.getId());
            if (flowerReadRequest != null) {
                flowerRequests.add(flowerReadRequest);
            }
        }
        if (flowerRequests.size() == 0) {
            return null;
        } else {
            return flowerRequests;
        }
    }

    public List<FlowerRequest> getAllFlowerRequests() {
        List<FlowerRequest> flowerRequests = new ArrayList<>();
        String selectStatement = "SELECT * FROM " + DatabaseManager.FLOWER_REQUEST_TABLE_NAME;

        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(selectStatement);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            flowerRequests = new ArrayList<>();
            ;
            while (resultSet.next()) {
                ServiceRequest serviceRequest =
                        serviceRequestFactory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
                FlowerRequest flowerRequest =
                        factory.read(resultSet.getString(DatabaseManager.SERVICEID_KEY));
                flowerRequests.add(
                        new FlowerRequest(
                                serviceRequest.getId(),
                                serviceRequest.getLocation(),
                                serviceRequest.getAssignee(),
                                serviceRequest.getDescription(),
                                serviceRequest.getDateTimeSubmitted(),
                                serviceRequest.getPriority(),
                                serviceRequest.getComplete(),
                                flowerRequest.getRecipientInput(),
                                flowerRequest.getRoomInput(),
                                flowerRequest.getChoice(),
                                flowerRequest.getMessageInput(),
                                flowerRequest.getBuyerName(),
                                flowerRequest.getPhoneNumber(),
                                flowerRequest.getGiftWrap()));
            }
        } catch (Exception e) {
            System.out.println(
                    "Exception in SecurityFactory read: " + e.getMessage() + ", " + e.getClass());
        }
        return flowerRequests;
    }
}
