package edu.wpi.teamF.ModelClasses.Account;

public class User extends Account {
    public User(String firstName, String lastName, String address, String username, String password, Type type) throws Exception {
        super(firstName, lastName, address, username, password, type);
    }
}
