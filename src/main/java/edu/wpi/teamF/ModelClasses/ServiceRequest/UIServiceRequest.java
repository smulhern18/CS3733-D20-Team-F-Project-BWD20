package edu.wpi.teamF.ModelClasses.ServiceRequest;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIServiceRequest extends RecursiveTreeObject<UIServiceRequest> {
  public StringProperty date;
  public StringProperty priority;
  public StringProperty location;
  public StringProperty id;
  public StringProperty description;
  public StringProperty serviceType;
  public NodeFactory nodeFactory = NodeFactory.getFactory();

  public UIServiceRequest(ServiceRequest serviceRequest) {
    int prio = serviceRequest.getPriority();
    if (prio == 1) {
      priority = new SimpleStringProperty("Low");
    } else if (prio == 2) {
      priority = new SimpleStringProperty("Medium");
    } else if (prio == 3) {
      priority = new SimpleStringProperty("High");
    }
    DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

    date = new SimpleStringProperty(dateformat.format(serviceRequest.getDateTimeSubmitted()));
    location = new SimpleStringProperty(serviceRequest.getLocation().getId());
    id = new SimpleStringProperty(serviceRequest.getId());
    description = new SimpleStringProperty((serviceRequest.getDescription()));
    if (serviceRequest instanceof MaintenanceRequest) {
      serviceType = new SimpleStringProperty("Maintenance");
    }
    if (serviceRequest instanceof SecurityRequest) {
      serviceType = new SimpleStringProperty("Security");
    }
  }

  public boolean equals(UIServiceRequest serviceRequest) {
    return this.description.get().equals(serviceRequest.description.get())
        && this.id.get().equals(serviceRequest.id.get())
        && this.serviceType.get().equals(serviceRequest.serviceType.get());
  }
}
