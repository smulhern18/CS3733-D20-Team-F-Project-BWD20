package edu.wpi.teamF.ModelClasses.Account;

public class User extends Account {
<<<<<<< Updated upstream
    public User(String firstName, String lastName, String address, String username, String password) throws Exception {
        super(firstName, lastName, address, username, password, Type.STAFF);
=======
    public User(String firstName, String lastName, String address, String username, String password, Type type) throws Exception {
        super(firstName, lastName, address, username, password, type);
>>>>>>> Stashed changes
    }
}
