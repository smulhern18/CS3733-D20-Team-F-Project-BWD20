package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;

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

  public MaintenanceRequest(
      Node location, String description, String assignee, Date dateTimeSubmitted, int priority)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    defaultCompleted();
  }

  private void defaultCompleted() throws ValidationException {
    setCompleted(null);
  }

  public Date getTimeCompleted() {
    return completed;
  }

  public void setCompleted(Date completed) throws ValidationException {
    if (completed != null) {
      Validators.dateValidation(completed);
    }
    this.completed = completed;
  }
}
