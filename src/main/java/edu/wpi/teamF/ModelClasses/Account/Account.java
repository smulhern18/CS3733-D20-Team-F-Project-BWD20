package edu.wpi.teamF.ModelClasses.Account;

import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import lombok.Data;

import javax.swing.*;
import java.util.Objects;

@Data
public abstract class Account {

  public enum Type {
    ADMIN("Admin"),
    STAFF("Staff"),
    USER("User");

    private String typeString;

    Type(String typeString) {
      this.typeString = typeString;
    }

    public String getTypeString() {
      return typeString;
    }

    public Type getEnum(String type) {
      for (Type v : values()) {
        if (v.getTypeString().equals(type)) {
          return v;
        }
      }
      return null;
    }
  }

  private String firstName;
  private String lastName;
  private String address;
  private String username;
  private String password;
  private Type type;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) throws ValidationException {
    Validators.nameValidation(firstName);
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) throws ValidationException {
    Validators.nameValidation(lastName);
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) throws ValidationException {
    Validators.addressValidation(address);
    this.address = address;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) throws ValidationException {
    Validators.nameValidation(username);
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) throws Exception {
    Validators.passwordValidation(password);
    this.password = PasswordHasher.createHash(password);
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) throws ValidationException {
    Validators.nullCheckValidation(type);
    this.type = type;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Account)) return false;
    Account account = (Account) o;
    return Objects.equals(firstName, account.firstName) &&
            Objects.equals(lastName, account.lastName) &&
            Objects.equals(address, account.address) &&
            Objects.equals(username, account.username) &&
            Objects.equals(password, account.password) &&
            type == account.type;
  }
}
