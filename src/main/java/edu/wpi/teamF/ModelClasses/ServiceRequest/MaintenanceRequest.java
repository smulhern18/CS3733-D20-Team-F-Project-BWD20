package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import java.util.Date;

public class MaintenanceRequest extends ServiceRequest {
  public MaintenanceRequest(
      String id, Node location, String description, Date dateTimeSubmitted, int priority) {
    super(id, location, description, dateTimeSubmitted, priority);
  }
}
