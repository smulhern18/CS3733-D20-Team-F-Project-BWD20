package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MaintenanceRequestFactory {
    private static final MaintenanceRequestFactory factory = new MaintenanceRequestFactory();

    public static MaintenanceRequestFactory getFactory() {
        return factory;
    }

    public void create(MaintenanceRequest maintenanceRequest) {
        String insertStatement =
                "INSERT INTO "
                        + DatabaseManager.NODES_TABLE_NAME
                        + " ( "
                        + DatabaseManager.NODEID_KEY
                        + ", "
                        + DatabaseManager.X_COORDINATE_KEY
                        + ", "
                        + DatabaseManager.Y_COORDINATE_KEY
                        + ", "
                        + DatabaseManager.BUILDING_KEY
                        + ", "
                        + DatabaseManager.LONG_NAME_KEY
                        + ", "
                        + DatabaseManager.SHORT_NAME_KEY
                        + ", "
                        + DatabaseManager.TYPE_KEY
                        + ", "
                        + DatabaseManager.FLOOR_KEY
                        + " ) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Validators.nodeValidation(node);
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
                        + DatabaseManager.NODES_TABLE_NAME
                        + " WHERE "
                        + DatabaseManager.NODEID_KEY
                        + " = ?";

        try (PreparedStatement preparedStatement =
                     DatabaseManager.getConnection().prepareStatement(selectStatement)) {
            preparedStatement.setString(1, id);

            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    maintenanceRequest =
                            new MaintenanceRequest(
                                    resultSet.getString(DatabaseManager.NODEID_KEY),
                                    resultSet.getShort(DatabaseManager.X_COORDINATE_KEY),
                                    resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY),
                                    resultSet.getString(DatabaseManager.BUILDING_KEY),
                                    resultSet.getString(DatabaseManager.LONG_NAME_KEY),
                                    resultSet.getString(DatabaseManager.SHORT_NAME_KEY),
                                    Node.NodeType.getEnum(resultSet.getString(DatabaseManager.TYPE_KEY)),
                                    resultSet.getShort(DatabaseManager.FLOOR_KEY));
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
                        + DatabaseManager.NODES_TABLE_NAME
                        + " SET "
                        + DatabaseManager.NODEID_KEY
                        + " = ?, "
                        + DatabaseManager.X_COORDINATE_KEY
                        + " = ?, "
                        + DatabaseManager.Y_COORDINATE_KEY
                        + " = ?, "
                        + DatabaseManager.BUILDING_KEY
                        + " = ?, "
                        + DatabaseManager.LONG_NAME_KEY
                        + " = ?, "
                        + DatabaseManager.SHORT_NAME_KEY
                        + " = ?, "
                        + DatabaseManager.TYPE_KEY
                        + " = ?, "
                        + DatabaseManager.FLOOR_KEY
                        + " = ? "
                        + "WHERE "
                        + DatabaseManager.NODEID_KEY
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
                        + DatabaseManager.NODES_TABLE_NAME
                        + " WHERE "
                        + DatabaseManager.NODEID_KEY
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
        return null;
    }

    public List<MaintenanceRequest> getAllMaintenanceRequests() {
        return null;
    }
}
