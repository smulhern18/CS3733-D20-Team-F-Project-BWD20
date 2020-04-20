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
import java.util.List;

public class SecurityRequestFactory {
    private static final SecurityRequestFactory factory = new SecurityRequestFactory();

    public static SecurityRequestFactory getFactory() {
        return factory;
    }

    public void create(SecurityRequest securityRequest) {

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
            prepareStatement.setString(param++, securityRequest.getId());
            prepareStatement.setString(param++, securityRequest.getLocation().getId());
            prepareStatement.setString(param++, securityRequest.getDescription());
            prepareStatement.setString(param++, securityRequest.getDateTimeSubmitted().toString());
            prepareStatement.setInt(param++, securityRequest.getPriority());

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

    public SecurityRequest read(String id) {

        SecurityRequest securityRequest = null;
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
                    securityRequest =
                            new SecurityRequest(
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
        return securityRequest;
    }

    public void update(SecurityRequest securityRequest) {

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
            preparedStatement.setString(param++, securityRequest.getId());
            preparedStatement.setString(param++, securityRequest.getLocation().getId());
            preparedStatement.setString(param++, securityRequest.getDescription());
            preparedStatement.setString(param++, securityRequest.getDateTimeSubmitted().toString());
            preparedStatement.setInt(param++, securityRequest.getPriority());
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

    public List<SecurityRequest> getSecurityRequestsByLocation(Node location) {
        return null;
    }

    public List<SecurityRequest> getAllSecurityRequests() {
        return null;
    }
}
