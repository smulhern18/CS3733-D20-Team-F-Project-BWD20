package edu.wpi.teamF.ModelClasses.Account;

public class Staff extends Account {
  public static enum Specialty {
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
      return null;
    }
  }

  Specialty specialty;


  public Staff(String firstName, String lastName, String address, String username, String password, Specialty specialty)
      throws Exception {
    super(firstName, lastName, address, username, password, Type.STAFF);
    setSpecialty(specialty);
  }

  public Specialty getSpecialty() {
    return specialty;
  }

  public void setSpecialty(Specialty specialty) {
    this.specialty = specialty;
  }

}
