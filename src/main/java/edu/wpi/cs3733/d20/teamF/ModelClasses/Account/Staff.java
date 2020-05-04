package edu.wpi.cs3733.d20.teamF.ModelClasses.Account;

public class Staff extends Account {

  Specialty specialty;


  public Staff(String firstName, String lastName, String address, String username, String password, Specialty specialty)
      throws Exception {
    super(firstName, lastName, address, username, password, Type.STAFF);
    setSpecialty(specialty);
  }
}
