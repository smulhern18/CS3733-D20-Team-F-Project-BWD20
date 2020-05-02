package edu.wpi.teamF.ModelClasses;

import java.util.Date;

public class MaintenanceRequest {
  private String id;
  private String assignee = "Not Assigned";
  private String location;
  private String description;
  private Date dateTimeSubmitted;
  private int priority;
  private boolean complete;
  private Date completed;

  public MaintenanceRequest(
      String id,
      String location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete,
      Date completed)
      throws ValidationException {
    setId(id);
    setLocation(location);
    setAssignee(assignee);
    setDescription(description);
    setDateTimeSubmitted(dateTimeSubmitted);
    setPriority(priority);
    setComplete(complete);
    setCompleted(completed);
  }

  public MaintenanceRequest(
      String location,
      String description,
      String assignee,
      Date dateTimeSubmitted,
      int priority,
      Date completed)
      throws ValidationException {
    setId(new Date().getTime() + "");
    setLocation(location);
    setAssignee("Not Assigned");
    setDescription(description);
    setDateTimeSubmitted(dateTimeSubmitted);
    setPriority(priority);
    setComplete(complete);
    setCompleted(null);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) throws ValidationException {
    Validators.idValidation(id);
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) throws ValidationException {
    Validators.nameValidation(location);
    this.location = location;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) throws ValidationException {
    Validators.nameValidation(assignee);
    this.assignee = assignee;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) throws ValidationException {
    Validators.descriptionValidation(description);
    this.description = description;
  }

  public Date getDateTimeSubmitted() {
    return dateTimeSubmitted;
  }

  public void setDateTimeSubmitted(Date dateTimeSubmitted) throws ValidationException {
    Validators.dateValidation(dateTimeSubmitted);
    this.dateTimeSubmitted = dateTimeSubmitted;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) throws ValidationException {
    Validators.priorityValidation(priority);
    this.priority = priority;
  }

  public boolean getComplete() {
    return complete;
  }

  public void setComplete(boolean complete) throws ValidationException {
    Validators.booleanValidation(complete);
    this.complete = complete;
  }

  /**
   * Checks if two service requests are equal
   *
   * @param other the other service request to check against
   * @return if the service requests are equal or not
   */
  public boolean equals(Object other) {
    boolean isEqual = false;
    if (other instanceof MaintenanceRequest) {
      MaintenanceRequest otherServiceRequest = (MaintenanceRequest) other;

      isEqual =
              this.getId().equals(otherServiceRequest.getId())
                      && this.getLocation().equals(otherServiceRequest.getLocation())
                      && this.getDescription().equals(otherServiceRequest.getDescription())
                      && this.getDateTimeSubmitted().equals(otherServiceRequest.getDateTimeSubmitted())
                      && this.getPriority() == otherServiceRequest.getPriority()
                      && this.getComplete() == otherServiceRequest.getComplete()
                      && this.getAssignee().equals(otherServiceRequest.getAssignee());
    }
    return isEqual;
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