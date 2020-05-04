package edu.wpi.cs3733.d20.teamF.ModelClasses.Account;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.d20.teamF.ModelClasses.ValidationException;
import edu.wpi.cs3733.d20.teamF.ModelClasses.Validators;
import java.util.Objects;

public abstract class Account extends RecursiveTreeObject<Account> {

  public static enum Type {
    ADMIN(0),
    STAFF(1),
    USER(2),
    PATIENT(3),
    JANITOR(4),
    NURSE(5);

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

  public static enum Specialty {
    NONE(-1),
    ELEVATOR(0),
    ELECTRICIAN(1),
    PLUMBER(2),
    GROUNDSKEEPER(3),
    HVAC(4);

    private Integer typeOrdinal;

    Specialty(Integer typeOrdinal) {
      this.typeOrdinal = typeOrdinal;
    }

    public Integer getTypeOrdinal() {
      return typeOrdinal;
    }

    public static Staff.Specialty getEnum(Integer type) {
      for (Staff.Specialty v : values()) {
        if (v.getTypeOrdinal().equals(type)) {
          return v;
        }
      }
      return NONE;
    }
  }

  private String firstName;
  private String lastName;
  private String emailAddress;
  private String Username;
  private String password;
  private Type type;
  private Specialty specialty = null;

  public Account(
      String firstName,
      String lastName,
      String emailAddress,
      String username,
      String password,
      Type type)
      throws Exception {
    setFirstName(firstName);
    setLastName(lastName);
    setEmailAddress(emailAddress);
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

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) throws ValidationException {
    Validators.emailAddressValidation(emailAddress);
    this.emailAddress = emailAddress;
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

  public void setPassword(String password) throws Exception {
    Validators.passwordValidation(password);
    if (password.contains("sha1:64000:")) {
      this.password = password;
    } else {
      this.password = PasswordHasher.createHash(password);
    }
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) throws ValidationException {
    Validators.nullCheckValidation(type);
    this.type = type;
  }

  public Specialty getSpecialty() {
    return specialty;
  }

  public void setSpecialty(Specialty specialty) {
    this.specialty = specialty;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Account)) return false;
    Account account = (Account) o;
    try {
      return Objects.equals(account.getFirstName(), this.firstName)
          && Objects.equals(account.getLastName(), this.lastName)
          && Objects.equals(account.getEmailAddress(), this.emailAddress)
          && Objects.equals(account.getUsername(), this.Username)
          && Objects.equals(account.getPassword(), this.password)
          && account.getType() == this.type
          && account.getSpecialty() == this.specialty;
    } catch (Exception e) {
      return false;
    }
  }
}
