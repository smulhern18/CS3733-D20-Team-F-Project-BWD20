package edu.wpi.teamF.ModelClasses.Account;

import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
<<<<<<< HEAD
import lombok.Data;
=======
>>>>>>> 1f3758e2f780dea7fdfb51813cdc000fa09db9f5
import java.util.Objects;
import javax.swing.*;
import lombok.Data;

@Data
public abstract class Account {

  public static enum Type {
    ADMIN(0),
    STAFF(1),
    USER(2);

    private Integer typeOrdinal;

    Type(Integer typeOrdinal) {
      this.typeOrdinal = typeOrdinal;
    }

    public Integer getTypeOrdinal() {
      return typeOrdinal;
    }

    public static Type getEnum(Integer type) {
      for (Type v : values()) {
        if (v.getTypeOrdinal().equals(type)) {
          return v;
        }
      }
      return null;
    }
  }

  private String firstName;
  private String lastName;
  private String Address;
  private String Username;
  private String password;
  private String email;
  private Type type;

<<<<<<< Updated upstream
<<<<<<< HEAD
  public Account(String firstName, String lastName, String address, String username, String password, Type type) throws Exception {
    setFirstName(firstName);
=======
  public Account(
      String FirstName,
      String lastName,
      String Address,
      String Username,
      String password,
      String email,
      Type type)
      throws ValidationException {
    setFirstName(FirstName);
>>>>>>> 1f3758e2f780dea7fdfb51813cdc000fa09db9f5
=======
  public Account(String firstName, String lastName, String address, String username, String password, Type type) throws Exception {
    setFirstName(firstName);
>>>>>>> Stashed changes
    setLastName(lastName);
    setAddress(address);
    setUsername(username);
    setPassword(password);
    setType(type);
  }

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
    } catch (Exception e) {
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
<<<<<<< HEAD
    return Objects.equals(firstName, account.firstName) &&
            Objects.equals(lastName, account.lastName) &&
            Objects.equals(Address, account.Address) &&
            Objects.equals(Username, account.Username) &&
            Objects.equals(password, account.password) &&
            type == account.type;
=======
    return Objects.equals(FirstName, account.FirstName)
        && Objects.equals(lastName, account.lastName)
        && Objects.equals(Address, account.Address)
        && Objects.equals(Username, account.Username)
        && Objects.equals(password, account.password)
        && type == account.type;
>>>>>>> 1f3758e2f780dea7fdfb51813cdc000fa09db9f5
  }
}
