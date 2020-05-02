package edu.wpi.teamF.ModelClasses.Account;

public class Janitor extends Account {
  public Janitor(
      String firstName, String lastName, String address, String username, String password)
      throws Exception {
    super(firstName, lastName, address, username, password, Account.Type.JANITOR);
  }
}
