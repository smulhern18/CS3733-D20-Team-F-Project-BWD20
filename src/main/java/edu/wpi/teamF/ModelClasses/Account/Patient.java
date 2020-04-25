package edu.wpi.teamF.ModelClasses.Account;

public class Patient extends Account{
    public Patient(String firstName, String lastName, String address, String username, String password)
            throws Exception {
        super(firstName, lastName, address, username, password, Type.PATIENT);
    }
}
