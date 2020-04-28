package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class SecurityRequest extends ServiceRequest {

  int guardsRequested;

  public SecurityRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete,
      int guardsRequested)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
    setGuardsRequested(guardsRequested);
  }

  public SecurityRequest(
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      int guardsRequested)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    setGuardsRequested(guardsRequested);
  }

  private void setGuardsRequested(int guardsRequested) throws ValidationException {
    Validators.guardsRequestedValidation(guardsRequested);
    this.guardsRequested = guardsRequested;
  }

  public int getGuardsRequested() {
    return guardsRequested;
  }
}
