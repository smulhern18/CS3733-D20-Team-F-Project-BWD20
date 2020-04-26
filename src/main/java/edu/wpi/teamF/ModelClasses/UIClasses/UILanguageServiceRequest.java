package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UILanguageServiceRequest extends RecursiveTreeObject<UILanguageServiceRequest> {
  public SimpleStringProperty ID;
  public SimpleStringProperty location;
  public SimpleStringProperty language;
  public SimpleStringProperty problemType;
  public SimpleStringProperty description;
  public SimpleStringProperty priority;
  public SimpleStringProperty assignee;
  public SimpleStringProperty dateSubmitted;
  public SimpleStringProperty completed;

  public UILanguageServiceRequest(LanguageServiceRequest csr) {
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
    this.language = new SimpleStringProperty(csr.getLanguage());
    this.problemType = new SimpleStringProperty(csr.getProblemType());
    this.description = new SimpleStringProperty(csr.getDescription());
    this.priority = new SimpleStringProperty("" + csr.getPriority());
    this.assignee = new SimpleStringProperty(csr.getAssignee());
    this.dateSubmitted = new SimpleStringProperty(date.format(csr.getDateTimeSubmitted()));
    this.completed = new SimpleStringProperty(cmp);
  }

  public boolean equalsLang(Object other) {
    boolean isEqual = false;
    if (other instanceof LanguageServiceRequest) {
      LanguageServiceRequest otherLang = (LanguageServiceRequest) other;
      isEqual =
          this.description.equals(otherLang.getDescription())
              && this.location.equals(otherLang.getLocation())
              && this.ID.equals(otherLang.getId());
    }
    return isEqual;
  }
}
