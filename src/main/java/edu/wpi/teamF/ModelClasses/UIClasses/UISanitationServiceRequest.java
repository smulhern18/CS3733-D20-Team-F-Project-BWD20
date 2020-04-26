package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SanitationServiceRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UISanitationServiceRequest extends RecursiveTreeObject<UISanitationServiceRequest> {
    public SimpleStringProperty ID;
    public SimpleStringProperty location;
    public SimpleStringProperty type;
    public SimpleStringProperty description;
    public SimpleStringProperty priority;
    public SimpleStringProperty assignee;
    public SimpleStringProperty dateSubmitted;
    public SimpleStringProperty completed;

    public UISanitationServiceRequest(SanitationServiceRequest csr) {
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
        this.type = new SimpleStringProperty(csr.getType());
        this.description = new SimpleStringProperty(csr.getDescription());
        this.priority = new SimpleStringProperty("" + csr.getPriority());
        this.assignee = new SimpleStringProperty(csr.getAssignee());
        this.dateSubmitted = new SimpleStringProperty(date.format(csr.getDateTimeSubmitted()));
        this.completed = new SimpleStringProperty(cmp);
    }

    public boolean equalsCSR(Object other) {
        boolean isEqual = false;
        if (other instanceof SanitationServiceRequest) {
            SanitationServiceRequest otherCS = (SanitationServiceRequest) other;
            isEqual =
                    this.description.equals(otherCS.getDescription())
                            && this.location.equals(otherCS.getLocation())
                            && this.ID.equals(otherCS.getId());
        }
        return isEqual;
    }
}
