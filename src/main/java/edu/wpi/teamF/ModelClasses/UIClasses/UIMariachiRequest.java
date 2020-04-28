package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UIMariachiRequest extends RecursiveTreeObject<UIMariachiRequest> {
  public SimpleStringProperty ID;
  public SimpleStringProperty location;
  public SimpleStringProperty songRequest;
  public SimpleStringProperty description;
  public SimpleStringProperty priority;
  public SimpleStringProperty assignee;
  public SimpleStringProperty dateSubmitted;
  public SimpleStringProperty completed;

  public UIMariachiRequest(MariachiRequest mariachiRequest) {
    DateFormat date = new SimpleDateFormat("yyyy-mm-dd");
    boolean isCompleted = mariachiRequest.getComplete();
    String cmp;
    if (isCompleted) {
      cmp = "Complete";
    } else {
      cmp = "Incomplete";
    }

    this.ID = new SimpleStringProperty(mariachiRequest.getId());
    this.location = new SimpleStringProperty(mariachiRequest.getLocation().getId());
    this.songRequest = new SimpleStringProperty("" + mariachiRequest.getSongRequest());
    this.description = new SimpleStringProperty(mariachiRequest.getDescription());
    this.priority = new SimpleStringProperty("" + mariachiRequest.getPriority());
    this.assignee = new SimpleStringProperty(mariachiRequest.getAssignee());
    this.dateSubmitted =
        new SimpleStringProperty(date.format(mariachiRequest.getDateTimeSubmitted()));
    this.completed = new SimpleStringProperty(cmp);
  }

  public boolean equalsCSR(Object other) {
    boolean isEqual = false;
    if (other instanceof MariachiRequest) {
      MariachiRequest otherCS = (MariachiRequest) other;
      isEqual =
          this.description.equals(otherCS.getDescription())
              && this.location.equals(otherCS.getLocation())
              && this.ID.equals(otherCS.getId());
    }
    return isEqual;
  }
}
