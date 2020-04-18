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

  private String FirstName;
  private String lastName;
  private String Address;
  private String Username;
  private String password;
  private String email;
  private Type type;

public Account(
      String FirstName,
      String lastName,
      String Address,
      String Username,
      String password,
      String email,
      Type type) throws ValidationException{
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
  public void setFirstName(String firstName) throws ValidationException {
    Validators.nameValidation(firstName);
    this.FirstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) throws ValidationException {
    Validators.nameValidation(lastName);

    this.lastName = lastName;
  }
  public String getAddress() {

    return Address;
  }
  public void setAddress(String address) throws ValidationException {
    Validators.addressValidation(address);
    this.Address = address;
  }

  public String getUsername() {
    return Username;
  }

  public void setUsername(String username) throws ValidationException {
    Validators.nameValidation(username);
    this.Username = username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }


  public void setPassword(String password) throws ValidationException {
    Validators.passwordValidation(password);
    try {
      this.password = PasswordHasher.createHash(password);
    }catch (Exception e){
      System.out.println(e);
    }
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
    return Objects.equals(FirstName, account.FirstName) &&
            Objects.equals(lastName, account.lastName) &&
            Objects.equals(Address, account.Address) &&
            Objects.equals(Username, account.Username) &&
            Objects.equals(password, account.password) &&
            type == account.type;
  }

}
