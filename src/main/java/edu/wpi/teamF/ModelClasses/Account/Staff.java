package edu.wpi.teamF.ModelClasses.Account;


public class Staff extends Account {
  public Staff(String firstName, String lastName, String address, String username, String password)
      throws Exception {
    super(firstName, lastName, address, username, password, Type.STAFF);
  }
}

