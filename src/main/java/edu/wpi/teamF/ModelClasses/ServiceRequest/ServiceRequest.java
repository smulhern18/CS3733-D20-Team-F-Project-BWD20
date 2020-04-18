package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import java.util.Date;
import lombok.Data;

@Data
public abstract class ServiceRequest {

  private String id;
  private Node Location;
  private String description;
  private Date dateTimeSubmitted;
  private int priority;
}
