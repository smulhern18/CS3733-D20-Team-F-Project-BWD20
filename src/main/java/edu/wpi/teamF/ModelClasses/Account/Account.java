package edu.wpi.teamF.ModelClasses.Account;

import lombok.Data;

@Data
public abstract class Account {

  public enum AccountType {
    // Values
    STAFF("Staff"),
    ADMIN("Admin"),
    USER("User");
    private String typeString;

    // Constructor
    AccountType(String type) {
      this.typeString = type;
    }

    // Get the string value from enum type
    public final String getTypeString() {
      return typeString;
    }

    // Get enum type from string
    public static AccountType getEnum(String typeString) {
     AccountType value = null;
      for (final AccountType n : values()) {
        if (n.getTypeString().equals(typeString)) {
          value = n;
        }
      }
      return value;
    }
  }

  private String FirstName;
  private String lastName;
  private String Address;
  private String Username;
  private String password;
  private String email;
  private AccountType type;


  public Account(String FirstName, String lastName, String Address, String Username,
      String password, String email, AccountType type) {
    setFirstName(FirstName);
    setLastName(lastName);
    setAddress(Address);
    setUsername(Username);
    setPassword(password);
    setEmail(email);
    setType(type);
  }


  public String getFirstName() {
    return FirstName;
  }

  public void setFirstName(String firstName) {
    FirstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return Address;
  }

  public void setAddress(String address) {
    Address = address;
  }

  public String getUsername() {
    return Username;
  }

  public void setUsername(String username) {
    Username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }
}
