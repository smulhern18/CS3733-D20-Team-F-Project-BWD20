package edu.wpi.teamF.ModelClasses.Account;

<<<<<<< Updated upstream

public class Staff extends Account {
    public Staff(String firstName, String lastName, String address, String username, String password) throws Exception {
        super(firstName, lastName, address, username, password, Type.STAFF);
=======
public class Staff extends Account {
    public Staff(String firstName, String lastName, String address, String username, String password, Type type) throws Exception {
        super(firstName, lastName, address, username, password, type);
>>>>>>> Stashed changes
    }
}
