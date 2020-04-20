package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public abstract class ServiceRequest {

  private String id;
  private Node location;
  private String description;
  private Date dateTimeSubmitted;
  private int priority;

  public ServiceRequest(
      String id, Node location, String description, Date dateTimeSubmitted, int priority)
      throws ValidationException {
    setId(id);
    setLocation(location);
    setDescription(description);
    setDateTimeSubmitted(dateTimeSubmitted);
    setPriority(priority);
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
}
