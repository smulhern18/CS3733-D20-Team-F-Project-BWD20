package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class MariachiRequest extends ServiceRequest {

  String songRequest;

  public MariachiRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      boolean complete,
      String songRequest)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
    setSongRequest(songRequest);
  }

  public MariachiRequest(
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      String songRequest)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    setSongRequest(songRequest);
  }

  private void setSongRequest(String songRequest) throws ValidationException {
    Validators.songRequestValidation(songRequest);
    this.songRequest = songRequest;
  }

  public String getSongRequest() {
    return songRequest;
  }
}
