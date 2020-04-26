package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Appointment;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentFactory {
  NodeFactory nodeFactory = NodeFactory.getFactory();

  private static final AppointmentFactory factory = new AppointmentFactory();

  static AppointmentFactory getFactory() {
    return factory;
  }

  public void create(Appointment appointment) throws ValidationException {
    try {
      Validators.appointmentValidation(appointment);
    } catch (ValidationException e) {
      System.out.println(e.getMessage() + ", " + e.getClass());
    }
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.APPOINTMENTS_TABLE_NAME
            + " ( "
            + DatabaseManager.APPOINTMENT_ID_KEY
            + ", "
            + DatabaseManager.LOCATION_KEY
            + ", "
            + DatabaseManager.ROOM_KEY
            + ", "
            + DatabaseManager.USERID_KEY
            + ", "
            + DatabaseManager.PCP_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?, ?)";
    Validators.appointmentValidation(appointment);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, appointment.getId());
      prepareStatement.setString(param++, appointment.getLocation().getId());
      prepareStatement.setString(param++, appointment.getRoom());
      prepareStatement.setString(param++, appointment.getUserID());
      prepareStatement.setString(param++, appointment.getPCP());

      try {
        int numRows = prepareStatement.executeUpdate();
        if (numRows < 1) {
          throw new SQLException("Created more than one row");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public Appointment read(String id) {
    Appointment appointment = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.APPOINTMENTS_TABLE_NAME
            + " WHERE "
            + DatabaseManager.APPOINTMENT_ID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, id);

      try {
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        appointment =
            new Appointment(
                resultSet.getString(DatabaseManager.APPOINTMENT_ID_KEY),
                nodeFactory.read(resultSet.getString(DatabaseManager.LOCATION_KEY)),
                resultSet.getString(DatabaseManager.ROOM_KEY),
                resultSet.getString(DatabaseManager.USERID_KEY),
                resultSet.getString(DatabaseManager.PCP_KEY));
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println(
          "Exception in AppointmentFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return appointment;
  }

  public void update(Appointment appointment) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.APPOINTMENTS_TABLE_NAME
            + " SET "
            + DatabaseManager.APPOINTMENT_ID_KEY
            + " = ?, "
            + DatabaseManager.LOCATION_KEY
            + " = ?, "
            + DatabaseManager.ROOM_KEY
            + " = ?, "
            + DatabaseManager.USERID_KEY
            + " = ?, "
            + DatabaseManager.PCP_KEY
            + " = ? "
            + "WHERE  "
            + DatabaseManager.APPOINTMENT_ID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      preparedStatement.setString(param++, appointment.getId());
      preparedStatement.setString(param++, appointment.getLocation().getId());
      preparedStatement.setString(param++, appointment.getRoom());
      preparedStatement.setString(param++, appointment.getUserID());
      preparedStatement.setString(param++, appointment.getPCP());
      preparedStatement.setString(param++, appointment.getId());
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
            + DatabaseManager.APPOINTMENTS_TABLE_NAME
            + " WHERE "
            + DatabaseManager.APPOINTMENT_ID_KEY
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
}
