package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Appointment;

public class AppointmentFactory {

    private static final AppointmentFactory factory = new AppointmentFactory();

    public static AppointmentFactory getFactory() {
        return factory;
    }

    public void create(Appointment appointment) {

    }

    public Appointment read(String id) {
        return null;
    }

    public void update(Appointment appointment) {

    }

    public void delete(String id) {

    }

}
