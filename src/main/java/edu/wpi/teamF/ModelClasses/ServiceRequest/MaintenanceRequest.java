package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.util.Date;

public class MaintenanceRequest extends ServiceRequest {
  public MaintenanceRequest(
      String id, Node location, String description, Date dateTimeSubmitted, int priority)
      throws ValidationException {
    super(id, location, description, dateTimeSubmitted, priority);
  }
  /**
   * Checks if two Maintenance are equal
   *
   * @param other the otherMaintenance to check against
   * @return if the Maintenance are equal or not
   */
  public boolean equals(Object other) {
    boolean isEqual = false;
    if (other instanceof MaintenanceRequest) {
      MaintenanceRequest otherMaintenance = (MaintenanceRequest) other;

      isEqual =
          this.getId().equals(otherMaintenance.getId())
              && this.getLocation().equals(otherMaintenance.getLocation())
              && this.getDescription() == otherMaintenance.getDescription()
              && this.getDateTimeSubmitted() == otherMaintenance.getDateTimeSubmitted()
              && this.getPriority() == otherMaintenance.getPriority();
    }
    return isEqual;
  }
}
