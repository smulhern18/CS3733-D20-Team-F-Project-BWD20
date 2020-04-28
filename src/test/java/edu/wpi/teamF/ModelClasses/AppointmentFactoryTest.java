package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentFactoryTest {

  static TestData testData = null;
  static Appointment[] validAppointments = null;
  static DatabaseManager databaseManager = DatabaseManager.getManager();

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validAppointments = testData.validAppointments;
    databaseManager.initialize();
  }

  @AfterAll
  public static void reset() throws SQLException {
    databaseManager.reset();
  }

  @Test
  public void testCreateReadDelete() throws Exception {
    try {
      databaseManager.manipulateAppointment(null);
      fail("Creating a null value is unacceptable");
    } catch (NullPointerException e) {
      // ignore as expected
    }
    try {
      for (Appointment appointment : validAppointments) {
        databaseManager.manipulateAppointment(appointment);
        databaseManager.manipulateNode(appointment.getLocation());

        Appointment readAppointment = databaseManager.readAppointment(appointment.getId());

        assertTrue(readAppointment.equals(appointment));

        databaseManager.deleteAppointment(appointment.getId());

        try {
          readAppointment = databaseManager.readAppointment(appointment.getId());
        } catch (Exception e) {
          fail(e.getMessage() + ", " + e.getClass());
        }
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }

  @Test
  public void testCreateReadUpdateDelete() {
    try {
      for (Appointment appointment : validAppointments) {
        databaseManager.manipulateAppointment(appointment);
        databaseManager.manipulateNode(appointment.getLocation());

        appointment.setPCP("Hello");
        databaseManager.manipulateAppointment(appointment);

        Appointment readAppointment = databaseManager.readAppointment(appointment.getId());

        assertTrue(appointment.equals(readAppointment));

        databaseManager.deleteAppointment(appointment.getId());
        databaseManager.deleteNode(appointment.getLocation().getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
