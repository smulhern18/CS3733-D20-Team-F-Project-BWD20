package edu.wpi.teamF.ModelClasses.Account;


public class User extends Account {
  public User(String firstName, String lastName, String address, String username, String password)
      throws Exception {
    super(firstName, lastName, address, username, password, Type.USER);
  }
}


