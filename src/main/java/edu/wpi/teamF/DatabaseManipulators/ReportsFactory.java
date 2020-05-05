package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.ServiceRequest.ReportsClass;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.management.InstanceNotFoundException;

public class ReportsFactory {

  private static final ReportsFactory factory = new ReportsFactory();

  public static ReportsFactory getFactory() {
    return factory;
  }

  private ReportsFactory() {}

  /**
   * Creates report entries in the database
   *
   * @param report the node to add
   */
  public void create(ReportsClass report) throws Exception {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.REPORTS_TABLE_NAME
            + " ( "
            + DatabaseManager.NODEID_KEY
            + ", "
            + DatabaseManager.TIMESSANITIZED_KEY
            + ", "
            + DatabaseManager.LASTSANITIZER_KEY
            + ", "
            + DatabaseManager.TIMESVISITED_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?)";

    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, report.getNodeID());
      prepareStatement.setInt(param++, report.getTimesSanitized());
      prepareStatement.setString(param++, report.getSanitizer());
      prepareStatement.setInt(param++, report.getTimesVisited());

      try {
        int numRows = prepareStatement.executeUpdate();
        if (numRows < 1) {
          throw new SQLException("Created more than one rows");
        }
      } catch (SQLException e) {
        throw e;
      }
    } catch (SQLException e) {
      throw e;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * read a report from the database
   *
   * @param id the nodeID to read
   * @return the node read
   */
  public ReportsClass read(String id) throws InstanceNotFoundException {
    ReportsClass report = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.REPORTS_TABLE_NAME
            + " WHERE "
            + DatabaseManager.NODEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        report =
            new ReportsClass(
                resultSet.getString(DatabaseManager.NODEID_KEY),
                resultSet.getInt(DatabaseManager.TIMESVISITED_KEY),
                resultSet.getInt(DatabaseManager.TIMESSANITIZED_KEY),
                resultSet.getString(DatabaseManager.LASTSANITIZER_KEY));
      } else {
        throw new InstanceNotFoundException();
      }

    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println(
          "Exception in ReportsFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return report;
  }

  /**
   * Updates a node in the database
   *
   * @param report the node to update
   */
  public void update(ReportsClass report) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.REPORTS_TABLE_NAME
            + " SET "
            + DatabaseManager.NODEID_KEY
            + " = ?, "
            + DatabaseManager.TIMESVISITED_KEY
            + " = ?, "
            + DatabaseManager.TIMESSANITIZED_KEY
            + " = ?, "
            + DatabaseManager.LASTSANITIZER_KEY
            + " = ? "
            + "WHERE "
            + DatabaseManager.NODEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      preparedStatement.setString(param++, report.getNodeID());
      preparedStatement.setInt(param++, report.getTimesVisited());
      preparedStatement.setInt(param++, report.getTimesSanitized());
      preparedStatement.setString(param++, report.getSanitizer());
      preparedStatement.setString(param++, report.getNodeID());
      int numRows = preparedStatement.executeUpdate();
      if (numRows != 1) {
        throw new Exception("Updated " + numRows + " rows");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Deletes a report object from the database
   *
   * @param id the ID to delete an entry with
   */
  public void delete(String id) {
    String deleteStatement =
        "DELETE FROM "
            + DatabaseManager.REPORTS_TABLE_NAME
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
  /**
   * Gets all the nodes from the database
   *
   * @return a list of all nodes in the database
   */
  public List<ReportsClass> getAllReports() {
    List<ReportsClass> report = new ArrayList<>();
    String selectStatement = "SELECT * FROM " + DatabaseManager.REPORTS_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      report = new ArrayList<>();
      while (resultSet.next()) {
        report.add(
            new ReportsClass(
                resultSet.getString(DatabaseManager.NODEID_KEY),
                resultSet.getInt(DatabaseManager.TIMESVISITED_KEY),
                resultSet.getInt(DatabaseManager.TIMESSANITIZED_KEY),
                resultSet.getString(DatabaseManager.LASTSANITIZER_KEY)));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in NodeFactory getNodesByType: " + e.getMessage() + ", " + e.getClass());
    }
    return report;
  }
}
