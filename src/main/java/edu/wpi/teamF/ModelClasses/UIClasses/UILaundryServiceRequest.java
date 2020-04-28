package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LaundryServiceRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UILaundryServiceRequest extends RecursiveTreeObject<UILaundryServiceRequest> {
  public SimpleStringProperty ID;
  public SimpleStringProperty location;
  public SimpleStringProperty items;
  public SimpleStringProperty temperature;
  public SimpleStringProperty quantity;
  public SimpleStringProperty description;
  public SimpleStringProperty priority;
  public SimpleStringProperty assignee;
  public SimpleStringProperty dateSubmitted;
  public SimpleStringProperty completed;

  public UILaundryServiceRequest(LaundryServiceRequest csr) {
    DateFormat date = new SimpleDateFormat("yyyy-mm-dd");
    boolean isCompleted = csr.getComplete();
    String cmp;
    if (isCompleted) {
      cmp = "Complete";
    } else {
      cmp = "Incomplete";
    }

    this.ID = new SimpleStringProperty(csr.getId());
    this.location = new SimpleStringProperty(csr.getLocation().getId());
    this.items = new SimpleStringProperty(csr.getItems());
    this.temperature = new SimpleStringProperty(csr.getTemperature());
    this.quantity = new SimpleStringProperty(csr.getQuantity());
    this.description = new SimpleStringProperty(csr.getDescription());
    this.priority = new SimpleStringProperty("" + csr.getPriority());
    this.assignee = new SimpleStringProperty(csr.getAssignee());
    this.dateSubmitted = new SimpleStringProperty(date.format(csr.getDateTimeSubmitted()));
    this.completed = new SimpleStringProperty(cmp);
  }

  public boolean equalsLSR(Object other) {
    boolean isEqual = false;
    if (other instanceof LaundryServiceRequest) {
      LaundryServiceRequest otherCS = (LaundryServiceRequest) other;
      isEqual =
          this.description.equals(otherCS.getDescription())
              && this.location.equals(otherCS.getLocation())
              && this.ID.equals(otherCS.getId());
    }
    return isEqual;
  }
}
