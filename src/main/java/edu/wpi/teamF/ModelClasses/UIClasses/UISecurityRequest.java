package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UISecurityRequest extends RecursiveTreeObject<UISecurityRequest> {
  public SimpleStringProperty ID;
  public SimpleStringProperty location;
  public SimpleStringProperty guardsRequested;
  public SimpleStringProperty description;
  public SimpleStringProperty priority;
  public SimpleStringProperty assignee;
  public SimpleStringProperty dateSubmitted;
  public SimpleStringProperty completed;

  public UISecurityRequest(SecurityRequest securityRequest) {
    DateFormat date = new SimpleDateFormat("yyyy-mm-dd");
    boolean isCompleted = securityRequest.getComplete();
    String cmp;
    if (isCompleted) {
      cmp = "Complete";
    } else {
      cmp = "Incomplete";
    }

    this.ID = new SimpleStringProperty(securityRequest.getId());
    this.location = new SimpleStringProperty(securityRequest.getLocation().getId());
    this.guardsRequested = new SimpleStringProperty("" + securityRequest.getGuardsRequested());
    this.description = new SimpleStringProperty(securityRequest.getDescription());
    this.priority = new SimpleStringProperty("" + securityRequest.getPriority());
    this.assignee = new SimpleStringProperty(securityRequest.getAssignee());
    this.dateSubmitted =
        new SimpleStringProperty(date.format(securityRequest.getDateTimeSubmitted()));
    this.completed = new SimpleStringProperty(cmp);
  }

  public boolean equalsCSR(Object other) {
    boolean isEqual = false;
    if (other instanceof SecurityRequest) {
      SecurityRequest otherCS = (SecurityRequest) other;
      isEqual =
          this.description.equals(otherCS.getDescription())
              && this.location.equals(otherCS.getLocation())
              && this.ID.equals(otherCS.getId());
    }
    return isEqual;
  }
}
