package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.util.Date;

public class MaintenanceRequest extends ServiceRequest {
  public MaintenanceRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
  }

  public MaintenanceRequest(
      Node location, String description, String assignee, Date dateTimeSubmitted, int priority)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
  }
}
