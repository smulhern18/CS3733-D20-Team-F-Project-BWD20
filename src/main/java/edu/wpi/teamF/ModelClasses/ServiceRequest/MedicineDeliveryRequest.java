package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class MedicineDeliveryRequest extends ServiceRequest {

  private String medicineType;
  private String instructions;

  public MedicineDeliveryRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete,
      String medicineType,
      String instructions)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
    setMedicineType(medicineType);
    setInstructions(instructions);
  }

  public MedicineDeliveryRequest(
      Node location,
      String description,
      String assignee,
      Date dateTimeSubmitted,
      int priority,
      String medicineType,
      String instructions)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    setMedicineType(medicineType);
    setInstructions(instructions);
  }

  public String getMedicineType() {
    return medicineType;
  }

  public void setMedicineType(String medicineType) throws ValidationException {
    Validators.medicineTypeValidation(medicineType);
    this.medicineType = medicineType;
  }

  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) throws ValidationException {
    Validators.instructionsValidation(instructions);
    this.instructions = instructions;
  }
}
