package edu.wpi.teamF.ModelClasses.Account;

public class Admin extends Account {
<<<<<<< Updated upstream
    public Admin(String firstName, String lastName, String address, String username, String password) throws Exception {
        super(firstName, lastName, address, username, password, Type.ADMIN);
=======
    public Admin(String firstName, String lastName, String address, String username, String password, Type type) throws Exception {
        super(firstName, lastName, address, username, password, type);
>>>>>>> Stashed changes
    }
}
