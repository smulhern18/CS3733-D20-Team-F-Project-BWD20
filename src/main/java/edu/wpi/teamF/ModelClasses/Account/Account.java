package edu.wpi.teamF.ModelClasses.Account;

import lombok.Data;

@Data
public abstract class Account {

  public enum Type {}

  private String firstName;
  private String lastName;
  private String address;
  private String username;
  private String password;
  private Type type;
}
