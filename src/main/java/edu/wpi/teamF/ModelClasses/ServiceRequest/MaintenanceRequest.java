package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class MaintenanceRequest extends ServiceRequest {
  private Date completed;

  public MaintenanceRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete,
      Date completed)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
    setCompleted(completed);
  }

  public MaintenanceRequest(
      Node location,
      String description,
      String assignee,
      Date dateTimeSubmitted,
      int priority,
      Date completed)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    setCompleted(completed);
  }

  public Date getTimeCompleted() {
    return completed;
  }

  public void setCompleted(Date completed) throws ValidationException {
    Validators.dateValidation(completed);
    this.completed = completed;
  }
}
