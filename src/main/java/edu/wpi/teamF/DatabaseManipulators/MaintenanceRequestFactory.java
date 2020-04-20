package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.Main;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
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

    public static MaintenanceRequestFactory getFactory() {
        return factory;
    }

    public void create(MaintenanceRequest maintenanceRequest) throws ValidationException {
        String insertStatement =
                "INSERT INTO "
                        + DatabaseManager.MAINTENANCEQUEST_TABLE_NAME
                        + " ( "
                        + DatabaseManager.SERVICEID_KEY
                        + ", "
                        + DatabaseManager.NODEID_KEY
                        + ", "
                        + DatabaseManager.DESCRIPTION_KEY
                        + ", "
                        + DatabaseManager.TIME_CREATED_KEY
                        + ", "
                        + DatabaseManager.PRIORITY_KEY
                        + " ) "
                        + "VALUES (?, ?, ?, ?, ?)";
        Validators.maintenanceRequestValidation(maintenanceRequest);
        try (PreparedStatement prepareStatement =
                     DatabaseManager.getConnection().prepareStatement(insertStatement)) {
            int param = 1;
            prepareStatement.setString(param++, maintenanceRequest.getId());
            prepareStatement.setString(param++, maintenanceRequest.getLocation().getId());
            prepareStatement.setString(param++, maintenanceRequest.getDescription());
            prepareStatement.setString(param++, maintenanceRequest.getDateTimeSubmitted().toString());
            prepareStatement.setInt(param++, maintenanceRequest.getPriority());

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
                        + DatabaseManager.MAINTENANCEQUEST_TABLE_NAME
                        + " WHERE "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?";

        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(selectStatement)) {
            preparedStatement.setString(1, id);

            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    maintenanceRequest =
                            new MaintenanceRequest(
                                    resultSet.getString(DatabaseManager.SERVICEID_KEY),
                                    nodeFactory.read(resultSet.getString(DatabaseManager.NODEID_KEY)),
                                    resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                                    resultSet.getDate(DatabaseManager.TIME_CREATED_KEY),
                                    resultSet.getInt(DatabaseManager.PRIORITY_KEY));
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
                        + DatabaseManager.MAINTENANCEQUEST_TABLE_NAME
                        + " SET "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?, "
                        + DatabaseManager.NODEID_KEY
                        + " = ?, "
                        + DatabaseManager.DESCRIPTION_KEY
                        + " = ?, "
                        + DatabaseManager.TIME_CREATED_KEY
                        + " = ?, "
                        + DatabaseManager.PRIORITY_KEY
                        + " = ?, "
                        + "WHERE "
                        + DatabaseManager.SERVICEID_KEY
                        + " = ?";
        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(updateStatement)) {
            int param = 1;
            preparedStatement.setString(param++, maintenanceRequest.getId());
            preparedStatement.setString(param++, maintenanceRequest.getLocation().getId());
            preparedStatement.setString(param++, maintenanceRequest.getDescription());
            preparedStatement.setString(param++, maintenanceRequest.getDateTimeSubmitted().toString());
            preparedStatement.setInt(param++, maintenanceRequest.getPriority());
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
                        + DatabaseManager.MAINTENANCEQUEST_TABLE_NAME
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

    public List<MaintenanceRequest> getMaintenanceRequestsByLocation(Node location) {
        List<MaintenanceRequest> maintenanceRequest = null;
        String selectStatement =
                "SELECT * FROM "
                        + DatabaseManager.MAINTENANCEQUEST_TABLE_NAME
                        + " WHERE "
                        + DatabaseManager.NODEID_KEY
                        + " = ?";

        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(selectStatement)) {
            preparedStatement.setString(1, location.getId());

            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                maintenanceRequest = new ArrayList<>();
                while (resultSet.next()) {
                    maintenanceRequest.add(
                            new MaintenanceRequest(
                                    resultSet.getString(DatabaseManager.SERVICEID_KEY),
                                    nodeFactory.read(resultSet.getString(DatabaseManager.NODEID_KEY)),
                                    resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                                    resultSet.getDate(DatabaseManager.TIME_CREATED_KEY),
                                    resultSet.getInt(DatabaseManager.PRIORITY_KEY)));
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

    public List<MaintenanceRequest> getAllMaintenanceRequests() {
        List<MaintenanceRequest> maintenanceRequest = null;
        String selectStatement =
                "SELECT * FROM "
                        + DatabaseManager.MAINTENANCEQUEST_TABLE_NAME;

        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(selectStatement);
             ResultSet resultSet = preparedStatement.executeQuery()) {;
                maintenanceRequest = new ArrayList<>();
                while (resultSet.next()) {
                    maintenanceRequest.add(
                            new MaintenanceRequest(
                                    resultSet.getString(DatabaseManager.SERVICEID_KEY),
                                    nodeFactory.read(resultSet.getString(DatabaseManager.NODEID_KEY)),
                                    resultSet.getString(DatabaseManager.DESCRIPTION_KEY),
                                    resultSet.getDate(DatabaseManager.TIME_CREATED_KEY),
                                    resultSet.getInt(DatabaseManager.PRIORITY_KEY)));
                }
        } catch (Exception e) {
            System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
        }
        return maintenanceRequest;
    }
}
