package edu.wpi.teamF.ModelClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UIMaintenenceRequest extends RecursiveTreeObject<UIMaintenenceRequest> {
  public SimpleStringProperty ID;
  public SimpleStringProperty location;
  public SimpleStringProperty dateCompleted;
  public SimpleStringProperty priority;
  public SimpleStringProperty assignee;
  public SimpleStringProperty dateSubmitted;
  public SimpleStringProperty completed;
  public SimpleStringProperty description;

  public UIMaintenenceRequest(MaintenanceRequest csr) {
    DateFormat date = new SimpleDateFormat("yyyy-mm-dd");

    this.ID = new SimpleStringProperty(csr.getId());
    this.location = new SimpleStringProperty(csr.getLocation().getId());
    if (csr.getTimeCompleted() == null) {
      this.dateCompleted = new SimpleStringProperty("Not Complete");
    } else {
      this.dateCompleted = new SimpleStringProperty(date.format(csr.getTimeCompleted()));
    }
    this.priority = new SimpleStringProperty("" + csr.getPriority());
    this.assignee = new SimpleStringProperty(csr.getAssignee());
    this.dateSubmitted = new SimpleStringProperty(date.format(csr.getDateTimeSubmitted()));
    if (csr.getComplete()) {
      this.completed = new SimpleStringProperty("Complete");
    } else {
      this.completed = new SimpleStringProperty("Incomplete");
    }
    this.description = new SimpleStringProperty(csr.getDescription());
  }

  public boolean equalsCSR(Object other) {
    boolean isEqual = false;
    if (other instanceof ComputerServiceRequest) {
      ComputerServiceRequest otherCS = (ComputerServiceRequest) other;
      isEqual =
          this.description.equals(otherCS.getDescription())
              && this.location.equals(otherCS.getLocation())
              && this.ID.equals(otherCS.getId());
    }
    return isEqual;
  }
}
