package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class ComputerServiceRequest extends ServiceRequest {
  private String make;
  private String HardwareSoftware;
  private String OS;

  public ComputerServiceRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete,
      String make,
      String hardwareSoftware,
      String OS)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
    setHardwareSoftware(hardwareSoftware);
    setMake(make);
    setOS(OS);
  }

  public ComputerServiceRequest(
      Node location,
      String description,
      String assignee,
      Date dateTimeSubmitted,
      int priority,
      String makeModel,
      String HardwareSoftware,
      String OS)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    setHardwareSoftware(HardwareSoftware);
    setMake(makeModel);
    setOS(OS);
  }

  public String getMake() {
    return make;
  }

  public void setMake(String makeModel) throws ValidationException {
    Validators.makeValidation(makeModel);
    this.make = makeModel;
  }

  public String getHardwareSoftware() {
    return HardwareSoftware;
  }

  public void setHardwareSoftware(String hardwareSoftware) throws ValidationException {
    Validators.hardwareSoftwareValidation(hardwareSoftware);
    HardwareSoftware = hardwareSoftware;
  }

  public String getOS() {
    return OS;
  }

  public void setOS(String OS) throws ValidationException {
    Validators.osValidation(OS);
    this.OS = OS;
  }
}
