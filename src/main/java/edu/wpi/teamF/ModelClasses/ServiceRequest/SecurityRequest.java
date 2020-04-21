package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.util.Date;

public class SecurityRequest extends ServiceRequest {
  public SecurityRequest(
      String id, Node location, String description, Date dateTimeSubmitted, int priority)
      throws ValidationException {
    super(id, location, description, dateTimeSubmitted, priority);
  }

  public SecurityRequest(Node location, String description, Date dateTimeSubmitted, int priority)
      throws ValidationException {
    super(location, description, dateTimeSubmitted, priority);
  }

  /**
   * Checks if two Maintenance are equal
   *
   * @param other the otherMaintenance to check against
   * @return if the Maintenance are equal or not
   */
  public boolean equals(Object other) {
    boolean isEqual = false;
    if (other instanceof SecurityRequest) {
      SecurityRequest otherSecurity = (SecurityRequest) other;

      isEqual =
          this.getId().equals(otherSecurity.getId())
              && this.getLocation().equals(otherSecurity.getLocation())
              && this.getDescription().equals(otherSecurity.getDescription())
              && this.getDateTimeSubmitted().equals(otherSecurity.getDateTimeSubmitted())
              && this.getPriority() == otherSecurity.getPriority();
    }
    return isEqual;
  }
}
