package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.AppointmentFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentFactoryTest {

  static TestData testData = null;
  static Appointment[] validAppointments = null;
  AppointmentFactory appointmentFactory = AppointmentFactory.getFactory();
  DatabaseManager databaseManager = new DatabaseManager();

  @BeforeEach
  public void initialize() throws Exception {
    testData = new TestData();
    validAppointments = testData.validAppointments;
    databaseManager.initialize();
  }

  @Test
  public void testCreateReadDelete() {
    try {
      appointmentFactory.create(null);
      fail("Creating a null value is unacceptable");
    } catch (ValidationException e) {
      // ignore as expected
    }
    try {
      for (Appointment appointment : validAppointments) {
        appointmentFactory.create(appointment);

        Appointment readAppointment = appointmentFactory.read(appointment.getId());

        assertTrue(readAppointment.equals(appointment));

        appointmentFactory.delete(appointment.getId());

        try {
          readAppointment = appointmentFactory.read(appointment.getId());
          // } catch (InstanceNotFoundException e) {
          // ignore
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
        appointmentFactory.create(appointment);

        appointment.setPCP("Hello");
        appointmentFactory.update(appointment);

        Appointment readAppointment = appointmentFactory.read(appointment.getId());

        assertTrue(appointment.equals(readAppointment));

        appointmentFactory.delete(appointment.getId());
      }
    } catch (Exception e) {
      fail(e.getMessage() + ", " + e.getClass());
    }
  }
}
