package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.TransportRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UITransportRequest extends RecursiveTreeObject<UITransportRequest> {
  public SimpleStringProperty ID;
  public SimpleStringProperty location;
  public SimpleStringProperty destination;
  public SimpleStringProperty dateCompleted;
  public SimpleStringProperty priority;
  public SimpleStringProperty assignee;
  public SimpleStringProperty dateSubmitted;
  public SimpleStringProperty completed;
  public SimpleStringProperty description;
  public SimpleStringProperty type;

  public UITransportRequest(TransportRequest csr) {
    DateFormat date = new SimpleDateFormat("yyyy-mm-dd");

    this.ID = new SimpleStringProperty(csr.getId());
    this.location = new SimpleStringProperty(csr.getLocation().getId());
    this.destination = new SimpleStringProperty(csr.getDestination().getId());
    this.dateCompleted = new SimpleStringProperty(date.format(csr.getDateTimeCompleted()));
    this.priority = new SimpleStringProperty("" + csr.getPriority());
    this.assignee = new SimpleStringProperty(csr.getAssignee());
    this.dateSubmitted = new SimpleStringProperty(date.format(csr.getDateTimeSubmitted()));
    if (csr.getComplete()) {
      this.completed = new SimpleStringProperty("Complete");
    } else {
      this.completed = new SimpleStringProperty("Incomplete");
    }
    this.description = new SimpleStringProperty(csr.getDescription());
    this.type = new SimpleStringProperty(csr.getType());
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
