package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
<<<<<<< HEAD
import java.util.Date;
import lombok.Data;
=======
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
>>>>>>> origin/develop

@Data
public abstract class ServiceRequest {

<<<<<<< HEAD
  private String id;
  private Node Location;
  private String description;
  private Date dateTimeSubmitted;
  private int priority;
=======
    private String id;
    private Node Location;
    private String description;
    private Date dateTimeSubmitted;
    private int priority;


>>>>>>> origin/develop
}
