package edu.wpi.teamF.ModelClasses.Account;

public class Nurse extends Account {

  public Nurse(String firstName, String lastName, String address, String username, String password)
      throws Exception {
    super(firstName, lastName, address, username, password, Type.NURSE);
  }
}
