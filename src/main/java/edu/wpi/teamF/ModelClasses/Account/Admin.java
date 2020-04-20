package edu.wpi.teamF.ModelClasses.Account;



public class Admin extends Account {
  public Admin(String firstName, String lastName, String address, String username, String password)
      throws Exception {
    super(firstName, lastName, address, username, password, Type.ADMIN);
  }
}


