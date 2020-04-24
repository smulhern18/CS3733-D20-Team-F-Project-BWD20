package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public abstract class ServiceRequest {

  private String id;
  private String assignee = "";
  private Node location;
  private String description;
  private Date dateTimeSubmitted;
  private int priority;
  private boolean complete;

  public ServiceRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete)
      throws ValidationException {
    setId(id);
    setLocation(location);
    setAssignee(assignee);
    setDescription(description);
    setDateTimeSubmitted(dateTimeSubmitted);
    setPriority(priority);
    setComplete(complete);
  }

  public ServiceRequest(
      Node location, String assignee, String description, Date dateTimeSubmitted, int priority)
      throws ValidationException {
    this(
        "" + System.currentTimeMillis(),
        location,
        assignee,
        description,
        dateTimeSubmitted,
        priority,
        false);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) throws ValidationException {
    Validators.idValidation(id);
    this.id = id;
  }

  public Node getLocation() {
    return location;
  }

  public void setLocation(Node location) throws ValidationException {
    Validators.nodeValidation(location);
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
    if (other instanceof ServiceRequest) {
      ServiceRequest otherServiceRequest = (ServiceRequest) other;

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
}
