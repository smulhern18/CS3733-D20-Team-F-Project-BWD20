package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.ScheduleEntry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchedulerFactory {
  private static SchedulerFactory factory = new SchedulerFactory();

  public static SchedulerFactory getFactory() {
    return factory;
  }

  void create(ScheduleEntry scheduleEntry) throws Exception {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.SCHEDULER_TABLE_NAME
            + " ("
            + DatabaseManager.SCHEDULER_ID_KEY
            + ", "
            + DatabaseManager.START_DATE_KEY
            + ", "
            + DatabaseManager.START_TIME_KEY
            + ", "
            + DatabaseManager.END_DATE_KEY
            + ", "
            + DatabaseManager.END_TIME_KEY
            + ", "
            + DatabaseManager.ROOM_KEY
            + ", "
            + DatabaseManager.ACCOUNT_ID_KEY
            + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, scheduleEntry.getID());
      prepareStatement.setString(param++, scheduleEntry.getStartDate());
      prepareStatement.setString(param++, scheduleEntry.getStartTime());
      prepareStatement.setString(param++, scheduleEntry.getEndDate());
      prepareStatement.setString(param++, scheduleEntry.getEndTime());
      prepareStatement.setString(param++, scheduleEntry.getRoom());
      prepareStatement.setString(param++, scheduleEntry.getAccountID());
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

  ScheduleEntry read(String id) throws Exception {
    ScheduleEntry scheduleEntry = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.SCHEDULER_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SCHEDULER_ID_KEY
            + " = ?";
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      int param = 1;
      prepareStatement.setString(1, id);
      try {
        ResultSet resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
          scheduleEntry =
              new ScheduleEntry(
                  resultSet.getString(DatabaseManager.START_DATE_KEY),
                  resultSet.getString(DatabaseManager.START_TIME_KEY),
                  resultSet.getString(DatabaseManager.END_DATE_KEY),
                  resultSet.getString(DatabaseManager.END_TIME_KEY),
                  resultSet.getString(DatabaseManager.ROOM_KEY),
                  resultSet.getString(DatabaseManager.ACCOUNT_ID_KEY));
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return scheduleEntry;
  }

  void update(ScheduleEntry scheduleEntry) throws Exception {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.SCHEDULER_TABLE_NAME
            + " SET "
            + DatabaseManager.SCHEDULER_ID_KEY
            + " = ?, "
            + DatabaseManager.START_DATE_KEY
            + " = ?, "
            + DatabaseManager.START_TIME_KEY
            + " = ?, "
            + DatabaseManager.END_DATE_KEY
            + " = ?, "
            + DatabaseManager.END_TIME_KEY
            + " = ?, "
            + DatabaseManager.ROOM_KEY
            + " = ?, "
            + DatabaseManager.ACCOUNT_ID_KEY
            + " = ? WHERE "
            + DatabaseManager.SCHEDULER_ID_KEY
            + " = ?";

    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      prepareStatement.setString(param++, scheduleEntry.getID());
      prepareStatement.setString(param++, scheduleEntry.getStartDate());
      prepareStatement.setString(param++, scheduleEntry.getStartTime());
      prepareStatement.setString(param++, scheduleEntry.getEndDate());
      prepareStatement.setString(param++, scheduleEntry.getEndTime());
      prepareStatement.setString(param++, scheduleEntry.getRoom());
      prepareStatement.setString(param++, scheduleEntry.getAccountID());
      prepareStatement.setString(param++, scheduleEntry.getID());
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

  public void delete(String id) {
    String deleteStatement =
        "DELETE FROM "
            + DatabaseManager.SCHEDULER_TABLE_NAME
            + " WHERE "
            + DatabaseManager.SCHEDULER_ID_KEY
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

  public List<ScheduleEntry> getAllScheduleEntries() {
    List<ScheduleEntry> scheduleEntries = new ArrayList<>();
    String selectStatement = "SELECT * FROM " + DatabaseManager.SCHEDULER_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {
      scheduleEntries = new ArrayList<>();
      ;
      while (resultSet.next()) {
        scheduleEntries.add(factory.read(resultSet.getString(DatabaseManager.SCHEDULER_ID_KEY)));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in scheduleEntries read: " + e.getMessage() + ", " + e.getClass());
    }
    return scheduleEntries;
  }
}
