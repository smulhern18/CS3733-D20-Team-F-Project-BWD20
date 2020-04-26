package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UIMedicineDeliveryRequest extends RecursiveTreeObject<UIMedicineDeliveryRequest> {

  public SimpleStringProperty ID;
  public SimpleStringProperty location;
  public SimpleStringProperty medicineType;
  public SimpleStringProperty instructions;
  public SimpleStringProperty description;
  public SimpleStringProperty priority;
  public SimpleStringProperty assignee;
  public SimpleStringProperty dateSubmitted;
  public SimpleStringProperty completed;

  public UIMedicineDeliveryRequest(MedicineDeliveryRequest mdr) {
    DateFormat date = new SimpleDateFormat("yyyy-mm-dd");
    boolean isCompleted = mdr.getComplete();
    String complete;
    if (isCompleted) {
      complete = "Complete";
    } else {
      complete = "Incomplete";
    }

    this.ID = new SimpleStringProperty(mdr.getId());
    this.location = new SimpleStringProperty(mdr.getLocation().getId());
    this.medicineType = new SimpleStringProperty(mdr.getMedicineType());
    this.instructions = new SimpleStringProperty(mdr.getInstructions());
    this.description = new SimpleStringProperty(mdr.getDescription());
    this.priority = new SimpleStringProperty("" + mdr.getPriority());
    this.assignee = new SimpleStringProperty(mdr.getAssignee());
    this.dateSubmitted = new SimpleStringProperty(date.format(mdr.getDateTimeSubmitted()));
    this.completed = new SimpleStringProperty(complete);
  }

  public boolean equalsMDR(Object other) {
    boolean isEqual = false;
    if (other instanceof MedicineDeliveryRequest) {
      MedicineDeliveryRequest otherMedicineDeliveryRequest = (MedicineDeliveryRequest) other;
      isEqual =
          this.description.equals(otherMedicineDeliveryRequest.getDescription())
              && this.location.equals(otherMedicineDeliveryRequest.getLocation())
              && this.ID.equals(otherMedicineDeliveryRequest.getId());
    }
    return isEqual;
  }
}
